package org.eob;

import org.eob.file.inf.*;
import org.eob.file.inf.commands.*;
import org.eob.file.inf.commands.condition.ConditionalOperator;
import org.eob.file.inf.commands.condition.RelationOperator;
import org.eob.file.inf.commands.condition.TermLeaf;
import org.eob.file.inf.commands.condition.TermNode;
import org.eob.file.inf.commands.condition.expression.*;

import java.io.*;
import java.util.List;
import java.util.Stack;

/**
 * User: Bifrost
 * Date: 12.1.2013
 * Time: 23:12
 */
public class CommandPrintVisitor implements CommandVisitor {
    private PrintWriter output = null;
    private Stack<String> outputStack = new Stack<String>();
    private VisitorGlobalData visitorGlobalData;
    private int spaces = 0;

    private final String endOfLine;
    private final String quotes;

    private Stack<ConditionInfo> conditionEndPosStack = new Stack<ConditionInfo>();
    private boolean scriptDebug;

    private class ConditionInfo {
        public int address;
        public boolean elseBranch;

        ConditionInfo(int address, boolean elseBranch) {
            this.address = address;
            this.elseBranch = elseBranch;
        }
    }

    public CommandPrintVisitor(PrintWriter output, VisitorGlobalData visitorGlobalData, boolean scriptDebug, String quotes, String endOfLine) {
        this.visitorGlobalData = visitorGlobalData;
        this.scriptDebug = scriptDebug;
        this.output = output;
        this.quotes = quotes;
        this.endOfLine = endOfLine;
    }

    public void parseTrigger(EobTrigger trigger, List<EobCommand> script) {
        writeToOutput(String.format("Trigger %d at [%d, %d] on %s {" + endOfLine, trigger.triggerId, trigger.x, trigger.y, translate(trigger.flags)));
        spaces = 4;

        conditionEndPosStack = new Stack<ConditionInfo>();
        for (int pos = visitorGlobalData.positionMap.get(trigger.addressStart) - 1; pos < visitorGlobalData.positionMap.get(trigger.addressEnd); pos++) {
            script.get(pos).accept(this);
        }
        writeToOutput("}" + endOfLine + endOfLine);
    }

    public void parseFunction(EobScriptFunction function, List<EobCommand> script) {
        writeToOutput(String.format("Function %s() {" + endOfLine, function.functionName));
        spaces = 4;

        conditionEndPosStack = new Stack<ConditionInfo>();
        for (int pos = visitorGlobalData.positionMap.get(function.addressStart) - 1; pos < visitorGlobalData.positionMap.get(function.addressEnd); pos++) {
            script.get(pos).accept(this);
        }
        writeToOutput("}" + endOfLine + endOfLine);
    }

    public void writeToOutput(String text) {
        if (output != null) {
            output.write(text);
        }
    }

    private void formatLine(EobCommand command) {
        if (checkStructure(command)) {
            showLineNumber(command, false);
            if (spaces > 0) {
                writeToOutput(String.format("%" + spaces + "s", ""));
            }
        }
    }

    private void showLineNumber(EobCommand command, boolean spacesOnly) {
        if (scriptDebug) {
            long digits = (long) Math.ceil(Math.log10(visitorGlobalData.positionMap.size() + 1));
            if (spacesOnly) {
                writeToOutput(String.format("%" + digits + "s ", ""));
            } else {
                writeToOutput(String.format("%" + digits + "d ", visitorGlobalData.positionMap.get(command.originalPos)));
            }
        }
    }

    private boolean checkStructure(EobCommand command) {
        boolean showNextLineStructure = true;

        // Check the end of the IF command
        while (!conditionEndPosStack.empty() && conditionEndPosStack.peek().address == command.originalPos) {
            conditionEndPosStack.pop();
            spaces -= 4;
            showLineNumber(command, true);
            if (spaces > 0) {
                writeToOutput(String.format("%" + spaces + "s}" + endOfLine, ""));
            } else {
                writeToOutput("}" + endOfLine);
            }
        }

        // Check the IF-ELSE command
        if (command instanceof JumpCommand) {
            if (conditionEndPosStack.peek().address != command.originalPos + 3) {
                EobLogger.println("Unexpected Jump command at address " + command.originalPos);
            } else {
                showLineNumber(command, false);
                if (spaces > 0) {
                    if (spaces > 4) {
                        writeToOutput(String.format("%" + (spaces - 4) + "s} else {%s", "", scriptDebug ? " // " : endOfLine));
                    } else {
                        writeToOutput("} else {" + (scriptDebug ? " // " : endOfLine));
                    }
                    showNextLineStructure = false;
                }

                conditionEndPosStack.pop();
                int address = ((JumpCommand) command).address;
                int pos = conditionEndPosStack.size() - 1;
                while (pos > 0 && conditionEndPosStack.get(pos).address < address) {
                    pos--;
                }

                if (conditionEndPosStack.size() == 0 || conditionEndPosStack.get(pos).address > address) {
                    conditionEndPosStack.push(new ConditionInfo(address, true));
                } else {
                    conditionEndPosStack.add(pos, new ConditionInfo(address, true));
                }
            }
        }
        if (command instanceof ScriptEndCommand) {
            showNextLineStructure = scriptDebug;
        }

        return showNextLineStructure;
    }

    @Override
    public void visit(DamageCommand damageCommand) {
        formatLine(damageCommand);
        writeToOutput(String.format("party.damage(%d, %dD%d + %d)" + endOfLine, damageCommand.whom, damageCommand.rolls, damageCommand.sides, damageCommand.base));
    }

    @Override
    public void visit(LauncherCommand launcherCommand) {
        formatLine(launcherCommand);
        switch (launcherCommand.launcherType) {
            case Spell:
                writeToOutput(String.format("spell.launch(%d, %d, %d, %s, %s) // spellId, x, y, position, direction" + endOfLine,
                        launcherCommand.spellId, launcherCommand.x, launcherCommand.y, launcherCommand.inSquarePositionType.name(), launcherCommand.direction.name()));
                break;
            case Item:
                writeToOutput(String.format("item.launch(Item.%s, %d, %d, %s, %s) // itemId, x, y, position, direction (name: " + quotes + "%s" + quotes + "%s)" + endOfLine,
                        launcherCommand.itemObject.item.getElementType(true), launcherCommand.x, launcherCommand.y,
                        launcherCommand.inSquarePositionType.name(), launcherCommand.direction.name(),
                        launcherCommand.itemObject.item.getDescription(true),
                        scriptDebug ? ", id: " + launcherCommand.itemObject.itemIndex : ""));
                break;
        }
    }

    @Override
    public void visit(GiveXpCommand giveXpCommand) {
        formatLine(giveXpCommand);
        writeToOutput(String.format("experience.add(%s, %d)" + endOfLine,
                giveXpCommand.giveXpType.name(), giveXpCommand.experience));
    }

    @Override
    public void visit(ScriptEndCommand scriptEndCommand) {
        formatLine(scriptEndCommand);
        if (scriptDebug) {
            writeToOutput("end" + endOfLine);
        }
    }

    @Override
    public void visit(IdentifyAllItemsCommand identifyAllItemsCommand) {
        formatLine(identifyAllItemsCommand);
        writeToOutput(String.format("item.identifyAllAt(%d, %d)" + endOfLine, identifyAllItemsCommand.x, identifyAllItemsCommand.y));
    }

    @Override
    public void visit(StealSmallItemsCommand stealSmallItemsCommand) {
        formatLine(stealSmallItemsCommand);
        writeToOutput(String.format("item.steal(%d, %d, %d, %s) // whoomId, x, y, position" + endOfLine,
                stealSmallItemsCommand.whom, stealSmallItemsCommand.x, stealSmallItemsCommand.y, stealSmallItemsCommand.inSquarePositionType.name()));
    }

    @Override
    public void visit(TeleportCommand teleportCommand) {
        formatLine(teleportCommand);
        writeToOutput(String.format("teleport.teleport(%s, %d, %d, %d, %d) // type, srcX, srcY, dstX, dstY" + endOfLine,
                teleportCommand.teleportType.name(), teleportCommand.sourceX, teleportCommand.sourceY,
                teleportCommand.destX, teleportCommand.destY));
    }

    @Override
    public void visit(JumpCommand jumpCommand) {
        formatLine(jumpCommand);
        if (scriptDebug) {
            writeToOutput(String.format("jump %d" + endOfLine, visitorGlobalData.positionMap.get(jumpCommand.address)));
        }
    }

    @Override
    public void visit(ReturnCommand returnCommand) {
        formatLine(returnCommand);
        writeToOutput("return" + endOfLine);
    }

    @Override
    public void visit(CallCommand callCommand) {
        formatLine(callCommand);
        if (scriptDebug) {
            writeToOutput(String.format("%s() // call(%d) " + endOfLine,
                    visitorGlobalData.scriptFunctionMap.get(callCommand.address).functionName,
                    visitorGlobalData.positionMap.get(callCommand.address)));
        } else {
            writeToOutput(String.format("%s()" + endOfLine, visitorGlobalData.scriptFunctionMap.get(callCommand.address).functionName));
        }
    }

    @Override
    public void visit(CreateMonsterCommand createMonsterCommand) {
        formatLine(createMonsterCommand);
        int item50Index = createMonsterCommand.pocketItem50.itemIndex;
        int itemIndex = createMonsterCommand.pocketItem.itemIndex;
        String item50Desc = item50Index == 0 ? "" : createMonsterCommand.pocketItem50.item.getDescription(true);
        String itemDesc = itemIndex == 0 ? "" : createMonsterCommand.pocketItem.item.getDescription(true);

        writeToOutput(String.format("monster.create(%d, %d, %d, %d, %s, %s, Monster.%s, %d, %d, %s, Item.%s, Item.%s) // unknown, moveTime, x, y, position, direction, monster%s, imageId, phase, pause, item50%% (name: %s), item (name: %s)" + endOfLine,
                createMonsterCommand.unknown, createMonsterCommand.moveTime, createMonsterCommand.x, createMonsterCommand.y,
                createMonsterCommand.inSquarePositionType.name(), createMonsterCommand.direction.name(), createMonsterCommand.monsterType.monsterName, createMonsterCommand.imageId,
                createMonsterCommand.phase, createMonsterCommand.pause ? "true" : "false",
                item50Index == 0 ? "none" : createMonsterCommand.pocketItem50.item.getElementType(true),
                itemIndex == 0 ? "none" : createMonsterCommand.pocketItem.item.getElementType(true),
                scriptDebug ? " (id: " + createMonsterCommand.monsterType.monsterId + ")" : "",
                scriptDebug ? quotes + item50Desc + quotes + ", id: " + item50Index : quotes + item50Desc + quotes,
                scriptDebug ? quotes + itemDesc + quotes + ", id: " + itemIndex : quotes + itemDesc + quotes));

    }

    @Override
    public void visit(EncounterCommand encounterCommand) {
        formatLine(encounterCommand);
        writeToOutput(String.format("callEncounter(%d)" + endOfLine, encounterCommand.encounterId));
    }

    @Override
    public void visit(ClearFlagCommand clearFlagCommand) {
        formatLine(clearFlagCommand);
        switch (clearFlagCommand.flagType) {
            case Maze:
                writeToOutput(String.format("maze.clearFlag(%s)" + endOfLine, clearFlagCommand.flag));
                break;
            case Global:
                writeToOutput(String.format("global.clearFlag(%s)" + endOfLine, clearFlagCommand.flag));
                break;
            case Monster:
                writeToOutput(String.format("monster.clearFlag(%d,%s)" + endOfLine, clearFlagCommand.monsterId, clearFlagCommand.flag));
                break;
            case Event:
                writeToOutput("callClearEvent?()" + endOfLine);
                break;
            case Party:
                writeToOutput("party.callClearEvent?(FUNC_SETVAL, PARTY_SAVEREST, 0)" + endOfLine);
                break;
            default:
                writeToOutput("??? unknown command" + endOfLine);
                break;
        }
    }

    @Override
    public void visit(TurnCommand turnCommand) {
        formatLine(turnCommand);
        switch (turnCommand.turnGroupType) {
            default:
            case Unknown:
                writeToOutput(String.format("???.turn(Turn.%s)" + endOfLine, turnCommand.turnType.name()));
                break;
            case Party:
                writeToOutput(String.format("party.turn(Turn.%s)" + endOfLine, turnCommand.turnType.name()));
                break;
            case Item:
                writeToOutput(String.format("item.turn(Turn.%s)" + endOfLine, turnCommand.turnType.name()));
                break;
        }
    }

    @Override
    public void visit(ChangeWallCommand changeWallCommand) {
        formatLine(changeWallCommand);
        switch (changeWallCommand.subtype) {
            case CompleteBlock:
                writeToOutput(String.format("wall.changeBlock(%d, %d, Wall.%s, Wall.%s) // x, y, fromWallId, toWallId%s" + endOfLine,
                        changeWallCommand.x, changeWallCommand.y,
                        changeWallCommand.fromWall.internalName, changeWallCommand.toWall.internalName,
                        scriptDebug ? " (formWallId: " + changeWallCommand.fromWall.wallId + ", toWallId: " + changeWallCommand.toWall.wallId + ")" : ""));
                break;
            case OneWall:
                writeToOutput(String.format("wall.changeWall(%d, %d, %s, Wall.%s, Wall.%s) // x, y, side, fromWallId, toWallId%s" + endOfLine,
                        changeWallCommand.x, changeWallCommand.y, changeWallCommand.side.name(),
                        changeWallCommand.fromWall.internalName, changeWallCommand.toWall.internalName,
                        scriptDebug ? " (formWallId: " + changeWallCommand.fromWall.wallId + ", toWallId: " + changeWallCommand.toWall.wallId + ")" : ""));
                break;
            case OpenDoor:
                writeToOutput(String.format("door.open(%d, %d)" + endOfLine, changeWallCommand.x, changeWallCommand.y));
                break;
            default:
                writeToOutput("??? unknown command" + endOfLine);
                break;
        }
    }

    @Override
    public void visit(NewItemCommand newItemCommand) {
        formatLine(newItemCommand);
        writeToOutput(String.format("item.create(%d, %d, %s, Item.%s) // x, y, position, elementType (name: " + quotes + "%s" + quotes + "%s)" + endOfLine,
                newItemCommand.x, newItemCommand.y, newItemCommand.inSquarePosition.name(),
                newItemCommand.itemObject.item.getElementType(true), newItemCommand.itemObject.item.getDescription(true),
                scriptDebug ? ", id: " + newItemCommand.itemObject.itemIndex : ""));
    }

    @Override
    public void visit(SoundCommand soundCommand) {
        formatLine(soundCommand);
        writeToOutput(String.format("play.sound(%d, %d)" + endOfLine, soundCommand.soundId, soundCommand.soundPosition));
    }

    @Override
    public void visit(OpenDoorCommand openDoorCommand) {
        formatLine(openDoorCommand);
        writeToOutput(String.format("door.open(%d, %d)" + endOfLine, openDoorCommand.x, openDoorCommand.y));
    }

    @Override
    public void visit(CloseDoorCommand closeDoorCommand) {
        formatLine(closeDoorCommand);
        writeToOutput(String.format("door.close(%d, %d)" + endOfLine, closeDoorCommand.x, closeDoorCommand.y));
    }

    @Override
    public void visit(SetWallCommand setWallCommand) {
        formatLine(setWallCommand);
        switch (setWallCommand.subtype) {
            case CompleteBlock:
                writeToOutput(String.format("wall.setBlock(%d, %d, Wall.%s) // x, y, wallId%s" + endOfLine,
                        setWallCommand.x, setWallCommand.y, setWallCommand.wall.internalName,
                        scriptDebug ? " (id: " + setWallCommand.wall.wallId + ")" : ""));
                break;
            case OneWall:
                writeToOutput(String.format("wall.setWall(%d, %d, %s, Wall.%s) // x, y, direction, wallId%s" + endOfLine,
                        setWallCommand.x, setWallCommand.y, setWallCommand.direction.name(), setWallCommand.wall.internalName,
                        scriptDebug ? " (id: " + setWallCommand.wall.wallId + ")" : ""));
                break;
            case PartyFacing:
                // Direction?
                writeToOutput(String.format("party.setFacing(%d)" + endOfLine, setWallCommand.partyFacing));
                break;
            default:
                writeToOutput("??? unknown command" + endOfLine);
                break;
        }
    }

    @Override
    public void visit(SetFlagCommand setFlagCommand) {
        formatLine(setFlagCommand);
        switch (setFlagCommand.flagType) {
            case Maze:
                writeToOutput(String.format("maze.setFlag(%s)" + endOfLine, setFlagCommand.flag));
                break;
            case Global:
                writeToOutput(String.format("global.setFlag(%s)" + endOfLine, setFlagCommand.flag));
                break;
            case Monster:
                writeToOutput(String.format("monster.setFlag(%d,%s)" + endOfLine, setFlagCommand.monsterId, setFlagCommand.flag));
                break;
            case Event:
                writeToOutput("callSetEvent?()" + endOfLine);
                break;
            case Party:
                writeToOutput("party.callSetEvent(FUNC_SETVAL, PARTY_SAVEREST, 0)" + endOfLine);
                break;
            default:
                writeToOutput("??? unknown command" + endOfLine);
                break;
        }
    }

    @Override
    public void visit(MessageCommand messageCommand) {
        formatLine(messageCommand);
        writeToOutput(String.format("show(" + quotes + "%s" + quotes + ", Color.%s)" + endOfLine, messageCommand.message, messageCommand.vgaColor.name()));
    }

    @Override
    public void visit(ConditionCommand conditionCommand) {
        formatLine(conditionCommand);
        if (scriptDebug) {
            writeToOutput(String.format("if (%s) { // if not: jump %d" + endOfLine,
                    outputStack.pop(), visitorGlobalData.positionMap.get(conditionCommand.jumpPosition)));
        } else {
            writeToOutput(String.format("if (%s) {" + endOfLine, outputStack.pop()));
        }
        conditionEndPosStack.push(new ConditionInfo(conditionCommand.jumpPosition, false));
        spaces += 4;
    }

    @Override
    public void visit(ChangeLevelCommand changeLevelCommand) {
        formatLine(changeLevelCommand);
        switch (changeLevelCommand.changeLevelType) {
            case ChangeLevel:
                writeToOutput(String.format("level.change(%d, %d, %d, %s) // level, x, y, direction" + endOfLine,
                        changeLevelCommand.level, changeLevelCommand.x, changeLevelCommand.y, changeLevelCommand.direction.name()));
                break;
            case InLevel:
                writeToOutput(String.format("level.teleport(%d, %d, %s) // x, y, direction" + endOfLine,
                        changeLevelCommand.x, changeLevelCommand.y, changeLevelCommand.direction.name()));
                break;
            default:
                writeToOutput("??? unknown command" + endOfLine);
                break;
        }
    }

    @Override
    public void visit(GraphicsDataCommand graphicsDataCommand) {
        writeToOutput("Unsupported script command: graphicsData" + endOfLine);
    }

    @Override
    public void visit(ItemConsumeCommand itemConsumeCommand) {
        formatLine(itemConsumeCommand);
        writeToOutput(String.format("item.consume(%s, %d, %d, ItemType.%s) // type, x, y, itemtype%s" + endOfLine,
                itemConsumeCommand.itemConsumeType.name(), itemConsumeCommand.x, itemConsumeCommand.y,
                itemConsumeCommand.itemType.elementType,
                scriptDebug ? " (id: " + itemConsumeCommand.itemType.itemTypeId + ")" : ""));
    }

    @Override
    public void visit(WallMappingCommand wallMappingCommand) {
        writeToOutput("Unsupported script command: wallMapping" + endOfLine);
    }

    //--------------------
    //--- Conditionals ---
    //--------------------


    @Override
    public void visit(MiscValueLeaf miscValueLeaf) {
        outputStack.push(((Integer) miscValueLeaf.value).toString());
    }

    @Override
    public void visit(MonsterCountLeaf monsterCountLeaf) {
        if (monsterCountLeaf.x != -1) {
            outputStack.push(String.format("monster.countAt(%d, %d)", monsterCountLeaf.x, monsterCountLeaf.y));
        } else {
            outputStack.push(String.format("monster.countByType(%d)", monsterCountLeaf.monsterTypeId));
        }
    }

    @Override
    public void visit(PartyPositionCheckLeaf partyPositionCheckLeaf) {
        outputStack.push(String.format("party.atPosition(%d, %d)", partyPositionCheckLeaf.x, partyPositionCheckLeaf.y));
    }

    @Override
    public void visit(PartyInventoryCountLeaf partyInventoryCountLeaf) {
        outputStack.push(String.format("party.inventory.count(ItemType.%s%s, %d)",
                partyInventoryCountLeaf.itemType.elementType,
                scriptDebug ? "/*id: " + partyInventoryCountLeaf.itemType.itemTypeId + "*/" : "",
                partyInventoryCountLeaf.flags));
    }

    @Override
    public void visit(MazeItemCountLeaf mazeItemCountLeaf) {
        outputStack.push(String.format("maze.itemCount(%d, %d, ItemType.%s%s)", mazeItemCountLeaf.x, mazeItemCountLeaf.y,
                mazeItemCountLeaf.itemType.elementType,
                scriptDebug ? "/*id: " + mazeItemCountLeaf.itemType.itemTypeId + "*/" : ""));
    }

    @Override
    public void visit(MazeFlagLeaf mazeFlagLeaf) {
        outputStack.push(String.format("maze.flag(%d)", mazeFlagLeaf.flagId));
    }

    @Override
    public void visit(MiscTriggerFlagLeaf miscTriggerFlagLeaf) {
        outputStack.push("trigger.flag");
    }

    @Override
    public void visit(MazeWallSideLeaf mazeWallSideLeaf) {
        outputStack.push(String.format("maze.wallSide(%s, %d, %d)", mazeWallSideLeaf.side.name(), mazeWallSideLeaf.x, mazeWallSideLeaf.y));
    }

    @Override
    public void visit(MazeWallNumberLeaf mazeWallNumberLeaf) {
        outputStack.push(String.format("maze.wallNumber(%d, %d)", mazeWallNumberLeaf.x, mazeWallNumberLeaf.y));
    }

    @Override
    public void visit(MiscFalseLeaf miscFalseLeaf) {
        outputStack.push("0");
    }

    @Override
    public void visit(MiscTrueLeaf miscTrueLeaf) {
        outputStack.push("1");
    }

    @Override
    public void visit(PartyContainsAlignmentLeaf partyContainsAlignmentLeaf) {
        outputStack.push(String.format("party.containsAlignment(%d)", partyContainsAlignmentLeaf.alignmentId));
    }

    @Override
    public void visit(PartyContainsRaceLeaf partyContainsRaceLeaf) {
        outputStack.push(String.format("party.containsRace(Race.%s)", partyContainsRaceLeaf.race.name()));
    }

    @Override
    public void visit(PartyItemTypeLeaf partyItemTypeLeaf) {
        outputStack.push("party.item.type");
    }

    @Override
    public void visit(PartyItemValueLeaf partyItemValueLeaf) {
        outputStack.push(String.format("party.item.value"));
    }

    @Override
    public void visit(PartyItemNameIdLeaf partyItemNameIdLeaf) {
        outputStack.push(String.format("party.item.isNameId(%d))", partyItemNameIdLeaf.nameId));
    }

    @Override
    public void visit(PartyItemUniqueNameIdLeaf partyItemUniqueNameIdLeaf) {
        outputStack.push(String.format("party.item.isUniqueNameId(%d)", partyItemUniqueNameIdLeaf.nameId));
    }

    @Override
    public void visit(PartyContainsClassLeaf partyContainsClassLeaf) {
        outputStack.push(String.format("party.containsClass(Class.%s)", partyContainsClassLeaf.classType));
    }

    @Override
    public void visit(MiscRollDiceLeaf miscRollDiceLeaf) {
        outputStack.push(String.format("rollDice(%dD%d + %d)", miscRollDiceLeaf.rolls, miscRollDiceLeaf.sides, miscRollDiceLeaf.base));
    }

    @Override
    public void visit(PartyItemLeaf partyItemLeaf) {
        outputStack.push("party.item");
    }

    @Override
    public void visit(PartyDirectionLeaf partyDirectionLeaf) {
        outputStack.push("party.direction");
    }

    @Override
    public void visit(MiscFlagLeaf miscFlagLeaf) {
        outputStack.push(String.format("misc.flag(Global, %d)", miscFlagLeaf.flagId));
    }

    @Override
    public void visit(PartyVisibleLeaf partyVisibleLeaf) {
        outputStack.push("party.isVisible");
    }

    @Override
    public void visit(ListNode listNode) {
        // Nothing to do
    }

    @Override
    public void visit(TermLeaf termLeaf) {
        String right = outputStack.pop();
        String left = outputStack.pop();
        outputStack.push(left + " " + translate(termLeaf.operator) + " " + right);
    }

    @Override
    public void visit(TermNode termNode) {
        String right = outputStack.pop();
        String left = outputStack.pop();
        outputStack.push("(" + left + " " + translate(termNode.operator) + " " + right + ")");
    }

    private String translate(ConditionalOperator operator) {
        switch (operator) {
            case And:
                return "&&";
            case Or:
                return "||";
        }
        return "??";
    }

    private String translate(RelationOperator operator) {
        switch (operator) {
            case Equal:
                return "=";
            case NotEqual:
                return "<>";
            case Less:
                return "<";
            case LessOrEqual:
                return "<=";
            case Greater:
                return ">";
            case GreaterOrEqual:
                return ">=";
        }
        return "??";
    }

    private String translate(TriggerFlagsType triggerFlags) {
        switch (triggerFlags) {
            case OnClick:
                return "Click";
            case OnEnter:
                return "Enter";
            case OnEnterLeave:
                return "Enter, Leave";
            case OnPutItem:
                return "PutItem";
            case OnEnterOrPutItem:
                return "Enter, PutItem";
            case OnPickUp:
                return "PickUpItem";
            case OnEnterOrPickUp:
                return "Attack";
            case OnPutOrPickUpItem:
                return "PutItem, PickUpItem";
            case OnEnterLeavePutPickUp:
                return "Enter, Leave, PutItem, PickUpItem";
            case OnFlyingObject:
                return "FlyingObject";
            case OnEnterOrFlyingObject:
                return "Enter, FlyingObject";
            case OnEnterOrPutItemOrFlyingObject:
                return "Enter, PutItem, FlyingObject";
        }
        return "??";
    }
}
