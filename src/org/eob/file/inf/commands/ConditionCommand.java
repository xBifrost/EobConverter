package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobLogger;
import org.eob.file.inf.commands.condition.*;
import org.eob.file.inf.commands.condition.expression.*;
import org.eob.model.EobCommand;

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
        register(new MiscFalseLeaf()); // 0x00
        register(new MiscTrueLeaf()); // 0x01
        register(new MiscValueLeaf()); // 0x02 - 0x7F
        register(new MiscTriggerFlagLeaf()); // 0xE0
        register(new MazeWallSideLeaf()); // 0xE9
        register(new PartyDirectionLeaf()); // 0xED
        register(new MazeFlagLeaf()); // 0xEF
        register(new PartyInventoryCountLeaf()); // 0xF1,0xF5
        register(new PartyPositionCheckLeaf()); // 0xF1,!0xF5
        register(new MazeItemCountLeaf()); // 0xF5
        register(new MazeWallNumberLeaf()); // 0xF7
//        register(new PartyContainsAlignmentLeaf());
//        register(new PartyContainsClassLeaf());
//        register(new PartyContainsRaceLeaf());
//        register(new PartyItemLeaf());
//        register(new PartyItemNameIdLeaf());
//        register(new PartyItemTypeLeaf());
//        register(new PartyItemUniqueNameIdLeaf());
//        register(new PartyItemValueLeaf());
//        register(new PartyVisibleLeaf());
    }

    private ConditionCommand(byte[] levelInfData, int pos) {
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
                    TermLeaf parse = termLeafPrototype.parse(levelInfData, pos + subPosition);
                    parse.nodeRight = nodes.pop();
                    parse.nodeLeft = nodes.pop();
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
                    TermNode parse = termNodePrototype.parse(levelInfData, pos + subPosition);
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
                ConditionNode conditionNode = registeredExpression.parse(levelInfData, pos + subPosition);
                if (conditionNode != null) {
                    nodes.add(conditionNode);
                    commandParsed = true;
                    subPosition += conditionNode.originalCommandSize();
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
    }

    public static ConditionCommand parse(byte[] levelInfData, int pos) {
        try {
            if (ByteArrayUtility.getByte(levelInfData, pos) == 0xEE) {
                return new ConditionCommand(levelInfData, pos);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static void register(ExpressionLeaf node) {
        registeredExpressions.add(node);
    }
}

