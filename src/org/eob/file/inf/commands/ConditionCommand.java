package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.EobLogger;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.commands.condition.*;
import org.eob.file.inf.commands.condition.expression.*;
import org.eob.file.inf.EobCommand;
import org.eob.model.Couple;

import java.util.*;

/**
 * User: Bifrost
 * Date: 12/3/12
 * Time: 10:29 PM
 */
public class ConditionCommand extends EobCommand {
    private static List<ExpressionLeaf> registeredExpressions = new ArrayList<ExpressionLeaf>();

    public final int jumpPosition;
    public final ConditionNode condition;

    static {
        register(new MiscValueLeaf()); // 0x00 - 0x7F
        register(new PartyVisibleLeaf()); // 0xDA
        register(new MiscRollDiceLeaf()); // 0xDB
        register(new PartyContainsClassLeaf()); // 0xDC
        register(new PartyContainsRaceLeaf()); // 0xDD
        register(new MiscTriggerFlagLeaf()); // 0xE0
        register(new PartyItemNameIdLeaf()); // 0xE7, 0xCF
        register(new PartyItemUniqueNameIdLeaf()); // 0xE7, 0xD0
        register(new PartyItemTypeLeaf()); // 0xE7, 0xE1
        register(new MouseItemTypeLeaf()); // 0xE7, 0xF5
        register(new PartyItemSubTypeLeaf()); // 0xE7, 0xF6
        register(new MazeWallSideLeaf()); // 0xE9
        register(new PartyDirectionLeaf()); // 0xED
        register(new MazeFlagLeaf()); // 0xEF
        register(new MiscFlagLeaf()); // 0xF0
        register(new PartyInventoryCountLeaf()); // 0xF1,0xF5
        register(new PartyPositionCheckLeaf()); // 0xF1,!0xF5
        register(new MonsterCountLeaf()); // 0xF3
        register(new MazeItemCountLeaf()); // 0xF5
        register(new MazeWallTypeLeaf()); // 0xF7
//        register(new PartyContainsAlignmentLeaf());
    }

    private ConditionCommand(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        super(0xEE, pos, "Condition");

        int subPosition = 1;
        Stack<ConditionNode> nodes = new Stack<ConditionNode>();

        TermLeaf termLeafPrototype = new TermLeaf();
        TermNode termNodePrototype = new TermNode();

        while (true) {
            int command = ByteArrayUtility.getByte(levelInfData, pos + subPosition);
            if (command == 0xEE) {
                subPosition++;
                break;
            }

            boolean commandParsed = false;

            // Term commands
            for (RelationOperator relationOperator : RelationOperator.values()) {
                if (relationOperator.eobCommandId == command) {
                    TermLeaf parse = termLeafPrototype.parse(levelInfData, pos + subPosition, eobGlobalData);
                    parse.nodeRight = nodes.pop();
                    parse.nodeLeft = nodes.pop();
                    if (parse.nodeLeft instanceof ExpressionLeaf && parse.nodeRight instanceof ExpressionLeaf) {
                        ExpressionLeaf left = (ExpressionLeaf) parse.nodeLeft;
                        ExpressionLeaf right = (ExpressionLeaf) parse.nodeRight;
                        if (left.leafType.equals(LeafType.UndefinedYet)) {
                            if (right.leafType.equals(LeafType.UndefinedYet)) {
                                right.leafType = LeafType.Number;
                            }
                            left.leafType = right.leafType;
                        } else if (right.leafType.equals(LeafType.UndefinedYet)) {
                            right.leafType = left.leafType;
                        }
                    }
                    nodes.push(parse);
                    commandParsed = true;
                    subPosition += parse.originalCommandSize();
                    break;
                }
            }
            if (commandParsed) {
                continue;
            }
            for (ConditionalOperator conditionalOperator : ConditionalOperator.values()) {
                if (conditionalOperator.eobCommandId == command) {
                    TermNode parse = termNodePrototype.parse(levelInfData, pos + subPosition, eobGlobalData);
                    parse.termRight = (Term) nodes.pop();
                    parse.termLeft = (Term) nodes.pop();
                    nodes.push(parse);
                    commandParsed = true;
                    subPosition += parse.originalCommandSize();
                    break;
                }
            }
            if (commandParsed) {
                continue;
            }

            // Expression commands
            for (ConditionNode registeredExpression : registeredExpressions) {
                ConditionNode conditionNode = registeredExpression.parse(levelInfData, pos + subPosition, eobGlobalData);
                if (conditionNode != null) {
                    if (conditionNode instanceof ListNode) {
                        nodes.addAll(((ListNode) conditionNode).nodes);
                        commandParsed = true;
                        subPosition += conditionNode.originalCommandSize();
                    } else {
                        nodes.add(conditionNode);
                        commandParsed = true;
                        subPosition += conditionNode.originalCommandSize();
                    }
                    break;
                }
            }
            if (!commandParsed) {
                EobLogger.println(String.format("Unknown expression with id: 0x%02x in condition command.", command));
                throw new UnsupportedOperationException(String.format("Unknown expression with id: 0x%02x in condition command.", command));
            }
        }

        jumpPosition = ByteArrayUtility.getWord(levelInfData, pos + subPosition);
        subPosition += 2;

        if (nodes.size() != 1) {
            EobLogger.println(String.format("Conditional parse error. Stack depth was %d at exit.", nodes.size()));
            throw new UnknownError(String.format("Conditional parse error. Stack depth was %d at exit.", nodes.size()));
        }
        condition = nodes.get(0);
        originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + subPosition);

        // Find info for subtype
        Couple<MiscValueLeaf, Integer> result = prepareSubTypeInfo(nodes.get(0));
        if (result.first != null) {
            EobLogger.print(String.format("ItemType for subtype %d was not found", result.first.value));
        }
    }

    private Couple<MiscValueLeaf, Integer> prepareSubTypeInfo(ConditionNode node) {
        if (node instanceof TermLeaf) {
            TermLeaf leaf = (TermLeaf) node;
            if (leaf.nodeLeft instanceof MiscValueLeaf) {
                MiscValueLeaf valueLeaf = (MiscValueLeaf) leaf.nodeLeft;
                if (valueLeaf.leafType.equals(LeafType.ItemSubType)) {
                    return new Couple<MiscValueLeaf, Integer>(valueLeaf, null);
                } else if (valueLeaf.leafType.equals(LeafType.ItemType)) {
                    return new Couple<MiscValueLeaf, Integer>(null, valueLeaf.value);
                }
            }
            if (leaf.nodeRight instanceof MiscValueLeaf) {
                MiscValueLeaf valueLeaf = (MiscValueLeaf) leaf.nodeRight;
                if (valueLeaf.leafType.equals(LeafType.ItemSubType)) {
                    return new Couple<MiscValueLeaf, Integer>(valueLeaf, null);
                } else if (valueLeaf.leafType.equals(LeafType.ItemType)) {
                    return new Couple<MiscValueLeaf, Integer>(null, valueLeaf.value);
                }
            }
            return new Couple<MiscValueLeaf, Integer>(null, null);
        } else if (node instanceof TermNode) {
            TermNode termNode = (TermNode) node;
            Couple<MiscValueLeaf, Integer> left = prepareSubTypeInfo(termNode.termLeft);
            Couple<MiscValueLeaf, Integer> right = prepareSubTypeInfo(termNode.termRight);
            if (left.first == null && left.second == null) {
                return right;
            }
            if (right.first == null && right.second == null) {
                return left;
            }
            if (left.second != null && right.second != null) {
                if (left.second.equals(right.second)) {
                    return left;
                }
                // Values are ignored
                return new Couple<MiscValueLeaf, Integer>(null, null);
            }
            if (left.first != null && right.first != null) {
                EobLogger.print(String.format("ItemType for subtype %d and %d was not found", left.first.value, right.first.value));
                return new Couple<MiscValueLeaf, Integer>(null, null);
            }
            MiscValueLeaf subItemTypeValueLeaf = left.first != null ? left.first : right.first;
            subItemTypeValueLeaf.parentValue = left.second != null ? left.second : right.second;

            return new Couple<MiscValueLeaf, Integer>(null, null);
        }
        return new Couple<MiscValueLeaf, Integer>(null, null);
    }

    public static ConditionCommand parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        try {
            if (ByteArrayUtility.getByte(levelInfData, pos) == 0xEE) {
                return new ConditionCommand(levelInfData, pos, eobGlobalData);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static void register(ExpressionLeaf node) {
        registeredExpressions.add(node);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        condition.accept(visitor);
        visitor.visit(this);
    }
}