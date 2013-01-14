package org.eob;

import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;
import org.eob.file.inf.EobScriptFunction;
import org.eob.file.inf.commands.*;
import org.eob.file.inf.commands.condition.ConditionalOperator;
import org.eob.file.inf.commands.condition.RelationOperator;
import org.eob.file.inf.commands.condition.TermLeaf;
import org.eob.file.inf.commands.condition.TermNode;
import org.eob.file.inf.commands.condition.expression.*;
import org.eob.file.inf.EobTrigger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * User: Bifrost
 * Date: 12.1.2013
 * Time: 23:12
 */
public class CommandPrintVisitor implements CommandVisitor {
    private OutputStreamWriter output = null;
    private Stack<String> outputStack = new Stack<String>();
    private Map<Integer, Integer> positionMap;
    private int spaces = 0;

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

    public CommandPrintVisitor(String levelScriptFile, Map<Integer, Integer> positionMap, boolean debug, boolean scriptDebug) {
        this.positionMap = positionMap;
        this.scriptDebug = scriptDebug;
        if (debug) {
            EobLogger.println("Writing text file with name: " + levelScriptFile + " ...");
        }
        try {
            output = new OutputStreamWriter(new FileOutputStream(levelScriptFile));
        } catch (FileNotFoundException ex) {
            EobLogger.println("File not found.");
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void parseTrigger(EobTrigger trigger, List<EobCommand> script) {
        writeToOutput(String.format("Trigger %d on position %d, %d on flags %d {\n", trigger.triggerId, trigger.x, trigger.y, trigger.flags));
        spaces = 4;

        conditionEndPosStack = new Stack<ConditionInfo>();
        for (int pos = positionMap.get(trigger.addressStart) - 1; pos < positionMap.get(trigger.addressEnd); pos++) {
            script.get(pos).accept(this);
        }
        writeToOutput("}\n\n");
    }

    public void parseFunction(EobScriptFunction function, List<EobCommand> script) {
        writeToOutput(String.format("Function %s() {\n", function.functionName));
        spaces = 4;

        conditionEndPosStack = new Stack<ConditionInfo>();
        for (int pos = positionMap.get(function.addressStart) - 1; pos < positionMap.get(function.addressEnd); pos++) {
            script.get(pos).accept(this);
        }
        writeToOutput("}\n\n");
    }

    public void close() {
        if (output != null) {
            try {
                output.close();
            } catch (IOException ex) {
                EobLogger.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void writeToOutput(String text) {
        try {
            output.write(text);
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void formatLine(EobCommand command) throws IOException {
        if (checkStructure(command)) {
            showLineNumber(command, false);
            if (spaces > 0) {
                output.write(String.format("%" + spaces + "s", ""));
            }
        }
    }

    private void showLineNumber(EobCommand command, boolean spacesOnly) throws IOException {
        if (scriptDebug) {
            long digits = (long) Math.ceil(Math.log10(positionMap.size() + 1));
            if (spacesOnly) {
                output.write(String.format("%" + digits + "s ", ""));
            } else {
                output.write(String.format("%" + digits + "d ", positionMap.get(command.originalPos)));
            }
        }
    }

    private boolean checkStructure(EobCommand command) throws IOException {
        boolean showNextLineStructure = true;

        // Check the end of the IF command
        while (!conditionEndPosStack.empty() && conditionEndPosStack.peek().address == command.originalPos) {
            conditionEndPosStack.pop();
            spaces -= 4;
            showLineNumber(command, true);
            if (spaces > 0) {
                output.write(String.format("%" + spaces + "s}\n", ""));
            } else {
                output.write("}\n");
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
                        output.write(String.format("%" + (spaces - 4) + "s} else {%s", "", scriptDebug ? " // " : "\n"));
                    } else {
                        output.write("} else {" + (scriptDebug ? " // " : "\n"));
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
        try {
            formatLine(damageCommand);
            output.write(String.format("damage(%d, %dD%d + %d)\n", damageCommand.whom, damageCommand.rolls, damageCommand.sides, damageCommand.base));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(LauncherCommand launcherCommand) {
        try {
            formatLine(launcherCommand);
            switch (launcherCommand.launcherType) {
                case Spell:
                    output.write(String.format("spell.launch(%d, %d, %d, %s, %s) // spellId, x, y, position, direction\n",
                            launcherCommand.spellId, launcherCommand.x, launcherCommand.y, launcherCommand.inSquarePositionType.name(), launcherCommand.direction.name()));
                    break;
                case Item:
                    output.write(String.format("item.launch(%d, %d, %d, %s, %s) // itemId, x, y, position, direction\n",
                            launcherCommand.itemId, launcherCommand.x, launcherCommand.y, launcherCommand.inSquarePositionType.name(), launcherCommand.direction.name()));
                    break;
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(GiveXpCommand giveXpCommand) {
        try {
            formatLine(giveXpCommand);
            output.write(String.format("experience.add(%s, %d)\n",
                    giveXpCommand.giveXpType.name(), giveXpCommand.experience));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(ScriptEndCommand scriptEndCommand) {
        try {
            formatLine(scriptEndCommand);
            if (scriptDebug) {
                output.write("end\n");
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(IdentifyAllItemsCommand identifyAllItemsCommand) {
        try {
            formatLine(identifyAllItemsCommand);
            output.write(String.format("item.identifyAllAt(%d, %d)\n", identifyAllItemsCommand.x, identifyAllItemsCommand.y));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(StealSmallItemsCommand stealSmallItemsCommand) {
        try {
            formatLine(stealSmallItemsCommand);
            output.write(String.format("item.steal(%d, %d, %d, %s) // whoomId, x, y, position\n",
                    stealSmallItemsCommand.whom, stealSmallItemsCommand.x, stealSmallItemsCommand.y, stealSmallItemsCommand.inSquarePositionType.name()));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(TeleportCommand teleportCommand) {
        try {
            formatLine(teleportCommand);
            output.write(String.format("teleport.teleport(%s, %d, %d, %d, %d) // type, srcX, srcY, dstX, dstY\n",
                    teleportCommand.teleportType.name(), teleportCommand.sourceX, teleportCommand.sourceY,
                    teleportCommand.destX, teleportCommand.destY));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(JumpCommand jumpCommand) {
        try {
            formatLine(jumpCommand);
            if (scriptDebug) {
                output.write(String.format("jump %d\n", positionMap.get(jumpCommand.address)));
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(ReturnCommand returnCommand) {
        try {
            formatLine(returnCommand);
            output.write("return\n");
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(CallCommand callCommand) {
        try {
            formatLine(callCommand);
            output.write(String.format("call(%d)\n", positionMap.get(callCommand.address)));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(CreateMonsterCommand createMonsterCommand) {
        try {
            formatLine(createMonsterCommand);
            output.write(String.format("monster.create(%d, %d, %d, %d, %s, %s, %d, %d, %d, %s, %d, %d) // unknown, moveTime, x, y, position, direction, monsterType, imageId, phase, pause, itemId 50%%, itemId\n",
                    createMonsterCommand.unknown, createMonsterCommand.moveTime, createMonsterCommand.x, createMonsterCommand.y,
                    createMonsterCommand.inSquarePositionType.name(), createMonsterCommand.direction.name(), createMonsterCommand.monsterTypeId, createMonsterCommand.imageId,
                    createMonsterCommand.phase, createMonsterCommand.pause ? "true" : "false", createMonsterCommand.pocketItem50Id, createMonsterCommand.pocketItemId));

        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(EncounterCommand encounterCommand) {
        try {
            formatLine(encounterCommand);
            output.write(String.format("callEncounter(%d)\n", encounterCommand.encounterId));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(ClearFlagCommand clearFlagCommand) {
        try {
            formatLine(clearFlagCommand);
            switch (clearFlagCommand.flagType) {
                case Maze:
                    output.write(String.format("maze.clearFlag(%s)\n", clearFlagCommand.flag));
                    break;
                case Global:
                    output.write(String.format("global.clearFlag(%s)\n", clearFlagCommand.flag));
                    break;
                case Monster:
                    output.write(String.format("monster.clearFlag(%d,%s)\n", clearFlagCommand.monsterId, clearFlagCommand.flag));
                    break;
                case Event:
                    output.write("callClearEvent?()\n");
                    break;
                case Party:
                    output.write("party.callClearEvent?(FUNC_SETVAL, PARTY_SAVEREST, 0)\n");
                    break;
                default:
                    output.write("??? unknown command\n");
                    break;
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(TurnCommand turnCommand) {
        try {
            formatLine(turnCommand);
            switch (turnCommand.turnType) {
                default:
                case Unknown:
                    output.write(String.format("???.turn(%s)\n", turnCommand.directionType.name()));
                    break;
                case Party:
                    output.write(String.format("party.turn(%s)\n", turnCommand.directionType.name()));
                    break;
                case Item:
                    output.write(String.format("item.turn(%s)\n", turnCommand.directionType.name()));
                    break;
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(ChangeWallCommand changeWallCommand) {
        try {
            formatLine(changeWallCommand);
            switch (changeWallCommand.subtype) {
                case CompleteBlock:
                    output.write(String.format("wall.changeBlock(%d, %d, %d, %d) // x, y, fromWallId, toWallId\n",
                            changeWallCommand.x, changeWallCommand.y, changeWallCommand.fromWall, changeWallCommand.toWall));
                    break;
                case OneWall:
                    output.write(String.format("wall.changeBlock(%d, %d, %s, %d, %d) // x, y, fromWallId, toWallId\n",
                            changeWallCommand.x, changeWallCommand.y, changeWallCommand.side.name(), changeWallCommand.fromWall, changeWallCommand.toWall));
                    break;
                case OpenDoor:
                    output.write(String.format("door.open(%d, %d)\n", changeWallCommand.x, changeWallCommand.y));
                    break;
                default:
                    output.write("??? unknown command\n");
                    break;
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(NewItemCommand newItemCommand) {
        try {
            formatLine(newItemCommand);
            output.write(String.format("item.create(%d, %d, %s, %d) // x, y, position, itemTypeId\n",
                    newItemCommand.x, newItemCommand.y, newItemCommand.inSquarePosition.name(), newItemCommand.itemTypeId));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(SoundCommand soundCommand) {
        try {
            formatLine(soundCommand);
            output.write(String.format("play.sound(%d, %d)\n", soundCommand.soundId, soundCommand.soundPosition));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(OpenDoorCommand openDoorCommand) {
        try {
            formatLine(openDoorCommand);
            output.write(String.format("door.open(%d, %d)\n", openDoorCommand.x, openDoorCommand.y));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(CloseDoorCommand closeDoorCommand) {
        try {
            formatLine(closeDoorCommand);
            output.write(String.format("door.close(%d, %d)\n", closeDoorCommand.x, closeDoorCommand.y));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(SetWallCommand setWallCommand) {
        try {
            formatLine(setWallCommand);
            switch (setWallCommand.subtype) {
                case CompleteBlock:
                    output.write(String.format("wall.setBlock(%d, %d, %d) // x, y, wallId\n",
                            setWallCommand.x, setWallCommand.y, setWallCommand.wallMappingIndex));
                    break;
                case OneWall:
                    output.write(String.format("wall.setWall(%d, %d, %s, %d) // x, y, direction, wallId\n",
                            setWallCommand.x, setWallCommand.y, setWallCommand.direction.name(), setWallCommand.wallMappingIndex));
                    break;
                case PartyFacing:
                    // Direction?
                    output.write(String.format("party.setFacing(%d)\n", setWallCommand.partyFacing));
                    break;
                default:
                    output.write("??? unknown command\n");
                    break;
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(SetFlagCommand setFlagCommand) {
        try {
            formatLine(setFlagCommand);
            switch (setFlagCommand.flagType) {
                case Maze:
                    output.write(String.format("maze.setFlag(%s)\n", setFlagCommand.flag));
                    break;
                case Global:
                    output.write(String.format("global.setFlag(%s)\n", setFlagCommand.flag));
                    break;
                case Monster:
                    output.write(String.format("monster.setFlag(%d,%s)\n", setFlagCommand.monsterId, setFlagCommand.flag));
                    break;
                case Event:
                    output.write("callSetEvent?()\n");
                    break;
                case Party:
                    output.write("party.callSetEvent(FUNC_SETVAL, PARTY_SAVEREST, 0)\n");
                    break;
                default:
                    output.write("??? unknown command\n");
                    break;
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(MessageCommand messageCommand) {
        try {
            formatLine(messageCommand);
            output.write(String.format("show(\"%s\", Color.%s)\n", messageCommand.message, messageCommand.vgaColor.name()));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(ConditionCommand conditionCommand) {
        try {
            formatLine(conditionCommand);
            if (scriptDebug) {
                output.write(String.format("if (%s) { // if not: jump %d\n", outputStack.pop(), positionMap.get(conditionCommand.jumpPosition)));
            } else {
                output.write(String.format("if (%s) {\n", outputStack.pop()));
            }
            conditionEndPosStack.push(new ConditionInfo(conditionCommand.jumpPosition, false));
            spaces += 4;
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(ChangeLevelCommand changeLevelCommand) {
        try {
            formatLine(changeLevelCommand);
            switch (changeLevelCommand.changeLevelType) {
                case ChangeLevel:
                    output.write(String.format("level.change(%d, %d, %d, %d) // level, x, y, direction\n",
                            changeLevelCommand.level, changeLevelCommand.x, changeLevelCommand.y, changeLevelCommand.direction));
                    break;
                case InLevel:
                    output.write(String.format("level.teleport(%d, %d, %d) // x, y, direction\n",
                            changeLevelCommand.x, changeLevelCommand.y, changeLevelCommand.direction));
                    break;
                default:
                    output.write("??? unknown command\n");
                    break;
            }
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(GraphicsDataCommand graphicsDataCommand) {
        try {
            output.write("Unsupported script command: graphicsData\n");
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(ItemConsumeCommand itemConsumeCommand) {
        try {
            formatLine(itemConsumeCommand);
            output.write(String.format("item.consume(%s, %d, %d, %d) // type, x, y, itemtypeId\n",
                    itemConsumeCommand.itemConsumeType.name(), itemConsumeCommand.x, itemConsumeCommand.y, itemConsumeCommand.itemTypeId));
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(WallMappingCommand wallMappingCommand) {
        try {
            output.write("Unsupported script command: wallMapping\n");
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
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
        outputStack.push(String.format("party.inventory.count(%d, %d)", partyInventoryCountLeaf.itemType, partyInventoryCountLeaf.flags));
    }

    @Override
    public void visit(MazeItemCountLeaf mazeItemCountLeaf) {
        outputStack.push(String.format("maze.itemCount(%d, %d, %d)", mazeItemCountLeaf.x, mazeItemCountLeaf.y, mazeItemCountLeaf.itemTypeId));
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
        outputStack.push(String.format("maze.wallSide(%d, %d, %d)", mazeWallSideLeaf.side, mazeWallSideLeaf.x, mazeWallSideLeaf.y));
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
        outputStack.push(String.format("party.containsRace(%d)", partyContainsRaceLeaf.raceId));
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
        outputStack.push(String.format("party.containsClass(%d)", partyContainsClassLeaf.classId));
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
}
