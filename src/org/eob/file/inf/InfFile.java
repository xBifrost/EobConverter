package org.eob.file.inf;

import org.eob.*;
import org.eob.file.FileUtility;
import org.eob.file.cps.CpsFile;
import org.eob.file.inf.commands.*;
import org.eob.model.MonsterObject;

import java.util.*;

/**
 * Triger flag:
 * 0 - on click
 * 1 - on enter
 * 3 - on push/insert item
 * 4 - on enter / on put item
 * 5 - on pick up
 * 6 - on atack
 * 7 - on put in
 * 8 - on plate change (party, item)
 * 9 - on flying item / on flying spell
 * 10 - on enter / on flying item
 * 11 - on change level
 * <p/>
 * triger.flag:
 * 1 - party
 * 4 - item
 * <p/>
 * User: Bifrost
 * Date: 10/28/12
 * Time: 10:53 PM
 */
public class InfFile {
    public final int levelId;
    private final byte[] levelInfData;

    public final int triggersOffset;
    public final String mazeName;
    public final String vmpVcnName;
    public final String paletteName;
    public final byte unknown[]; // Array size: 9
    public final int monster1ImageCompressionMethod;
    public final String monster1Name;
    public final int monster2ImageCompressionMethod;
    public final String monster2Name;
    public final byte unknown2[]; // Array size: 5
    public final List<MonsterObject> monsterObjects = new ArrayList<MonsterObject>();
    public final List<EobCommand> commands = new ArrayList<EobCommand>();
    public final List<EobCommand> script = new ArrayList<EobCommand>();
    public final List<EobTrigger> triggers = new ArrayList<EobTrigger>();
    public final List<EobScriptFunction> scriptFunctions = new ArrayList<EobScriptFunction>();

    public InfFile(int levelId, byte[] levelInfDataPacked, EobGlobalData eobGlobalData, boolean writeUnpacked) {
        this.levelId = levelId;
        this.levelInfData = new CpsFile(levelInfDataPacked).getData();

        if (writeUnpacked) {
            FileUtility.writeFile(String.format(EobConverter.LEVEL_INF_UNPACKED, levelId), levelInfData, false);
        }

        int pos = 0;
        triggersOffset = ByteArrayUtility.getWord(levelInfData, pos);
        pos += 2;
        mazeName = ByteArrayUtility.getString(levelInfData, pos, 12);
        pos += 12;
        vmpVcnName = ByteArrayUtility.getString(levelInfData, pos, 12);
        pos += 12;
        paletteName = ByteArrayUtility.getString(levelInfData, pos, 12);
        pos += 12;
        unknown = Arrays.copyOfRange(levelInfData, pos, pos + 9);
        pos += 9;
        monster1ImageCompressionMethod = ByteArrayUtility.getByte(levelInfData, pos);
        pos++;
        monster1Name = ByteArrayUtility.getString(levelInfData, pos, 12);

        pos += 12;
        monster2ImageCompressionMethod = ByteArrayUtility.getByte(levelInfData, pos);
        pos++;
        monster2Name = ByteArrayUtility.getString(levelInfData, pos, 12);

        pos += 12;
        unknown2 = Arrays.copyOfRange(levelInfData, pos, pos + 5);
        pos += 5;
        for (int monsterIdx = 0; monsterIdx < 30; monsterIdx++) {
            byte[] monster = Arrays.copyOfRange(levelInfData, pos, pos + 14);
            pos += 14;
            MonsterObject newMonsterObject = new MonsterObject(this.levelId, monster, eobGlobalData);
            if (newMonsterObject.index > 0) {
                monsterObjects.add(newMonsterObject);
            }
        }
        int commandsCount = ByteArrayUtility.getWord(levelInfData, pos);
        pos += 2;
        ParseCommand parseCommand = new ParseCommand();
        EobLogger.println(String.format("Command count: %d", commandsCount));
        for (int commandIdx = 0; commandIdx < commandsCount; commandIdx++) {
            EobCommand command = parseCommand.parse(levelInfData, pos);
            if (command == null) {
                EobLogger.println(String.format("Unknown command: 0x%02x (Position: 0x%04x)", ByteArrayUtility.getByte(levelInfData, pos), pos));
                break;
            }
            pos += command.originalCommands.length;
            commands.add(command);
        }

        EobLogger.println("script parsing...");
        int commandIdx = 0;
        while (pos < triggersOffset) {
            EobCommand command = parseCommand.parseScript(levelId, levelInfData, pos, eobGlobalData);
            if (command == null) {
                EobLogger.println(String.format("Unknown command: 0x%02x (Position: 0x%04x)", ByteArrayUtility.getByte(levelInfData, pos), pos));
                break;
            }
            pos += command.originalCommands.length;
            commandIdx++;
            script.add(command);
        }
        EobLogger.println(String.format("Script command count: %d", commandIdx));

        // prepare commandMap
        Map<Integer, Integer> commandMap = new HashMap<Integer, Integer>();
        pos = 0;
        for (EobCommand command : script) {
            commandMap.put(command.originalPos, pos);
            pos++;
        }

        pos = triggersOffset;
        EobLogger.println("triggers parsing...");
        int triggersCount = ByteArrayUtility.getWord(levelInfData, pos);
        pos += 2;
        for (int triggerIdx = 0; triggerIdx < triggersCount; triggerIdx++) {
            int position = ByteArrayUtility.getWord(levelInfData, pos);
            int flags = ByteArrayUtility.getByte(levelInfData, pos + 2);
            int address = ByteArrayUtility.getWord(levelInfData, pos + 3);
            EobTrigger trigger = new EobTrigger(triggerIdx, position, flags, address);
            pos += 5;
            triggers.add(trigger);
        }
        Collections.sort(triggers, new Comparator<EobTrigger>() {
            @Override
            public int compare(EobTrigger o1, EobTrigger o2) {
                return ((Integer) o1.addressStart).compareTo(o2.addressStart);
            }
        });
        for (EobTrigger trigger : triggers) {
            boolean counting = false;
            for (EobCommand command : script) {
                if (command.originalPos == trigger.addressStart) {
                    counting = true;
                }
                if (counting && command instanceof ScriptEndCommand) {
                    trigger.addressEnd = command.originalPos;
                    break;
                }
            }
        }
        EobLogger.println(String.format("Script triggers count: %d", triggersCount));
        EobLogger.println("");

        EobLogger.println("script function finding...");
        TreeMap<Integer, Integer> parsedFunctions = new TreeMap<Integer, Integer>();
        for (EobCommand command : script) {
            if (command instanceof CallCommand) {
                int functionStartAdr = ((CallCommand) command).address;
                parsedFunctions.put(functionStartAdr, -1);
            }
            pos++;
        }
        for (Integer address : parsedFunctions.keySet()) {
            Integer value = parsedFunctions.ceilingKey(address + 1);
            if (value == null) {
                value = triggers.get(0).addressStart;
            }
            parsedFunctions.put(address, value - 1);
        }

        int functionPos = 0;
        for (Map.Entry<Integer, Integer> entry : parsedFunctions.entrySet()) {
            scriptFunctions.add(new EobScriptFunction("function" + functionPos, entry.getKey(), entry.getValue()));
            functionPos++;
        }

        EobLogger.println(String.format("Functions count: %d", parsedFunctions.size()));
        EobLogger.println("");

        // Check script
        for (EobTrigger trigger : triggers) {
            boolean checking = false;
            for (EobCommand command : script) {
                if (command.originalPos == trigger.addressStart) {
                    checking = true;
                }
                if (checking && command instanceof ConditionCommand) {
                    ConditionCommand conditionCommand = (ConditionCommand) command;
                    checkJumpPosition(trigger.addressStart, trigger.addressEnd, conditionCommand.jumpPosition);
                }
                if (checking && command instanceof JumpCommand) {
                    JumpCommand jumpCommand = (JumpCommand) command;
                    checkJumpPosition(trigger.addressStart, trigger.addressEnd, jumpCommand.address);
                }
                if (command.originalPos >= trigger.addressEnd) {
                    break;
                }
            }
        }
        for (EobScriptFunction function : scriptFunctions) {
            boolean checking = false;
            for (EobCommand command : script) {
                if (command.originalPos == function.addressStart) {
                    checking = true;
                }
                if (checking && command instanceof ConditionCommand) {
                    ConditionCommand conditionCommand = (ConditionCommand) command;
                    checkJumpPosition(function.addressStart, function.addressEnd, conditionCommand.jumpPosition);
                }
                if (checking && command instanceof JumpCommand) {
                    JumpCommand jumpCommand = (JumpCommand) command;
                    checkJumpPosition(function.addressStart, function.addressEnd, jumpCommand.address);
                }
                if (command.originalPos >= function.addressEnd) {
                    break;
                }
            }
        }
    }

    private void checkJumpPosition(int addressStart, int addressEnd, int jumpPosition) {
        if (jumpPosition < addressStart) {
            EobLogger.println(String.format("[Script error] Jump to negative address! Command at address: 0x%04x", addressStart));
            return;
        }
        if (jumpPosition > addressEnd) {
            EobLogger.println(String.format("[Script error] Jump out of the script! Command at address: 0x%04x", addressStart));
            return;
        }
    }

    public byte[] getLevelInfData() {
        return levelInfData;
    }
}
