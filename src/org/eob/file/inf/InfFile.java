package org.eob.file.inf;

import org.eob.ByteArrayUtility;
import org.eob.EobConverter;
import org.eob.EobLogger;
import org.eob.ItemParser;
import org.eob.file.FileUtility;
import org.eob.file.cps.CpsFile;
import org.eob.model.EobCommand;
import org.eob.model.EobTrigger;
import org.eob.model.MonsterObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
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

    public InfFile(int levelId, byte[] levelInfDataPacked, ItemParser itemParser, boolean writeUnpacked) {
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
            MonsterObject newMonsterObject = new MonsterObject(this.levelId, monster, itemParser);
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
            EobCommand command = parseCommand.parseScript(levelInfData, pos);
            if (command == null) {
                EobLogger.println(String.format("Unknown command: 0x%02x (Position: 0x%04x)", ByteArrayUtility.getByte(levelInfData, pos), pos));
                break;
            }
            pos += command.originalCommands.length;
            commandIdx++;
            script.add(command);
        }
        EobLogger.println(String.format("Script command count: %d", commandIdx));

        pos = triggersOffset;
        EobLogger.println("triggers parsing...");
        int triggersCount = ByteArrayUtility.getWord(levelInfData, pos);
        pos += 2;
        for (int triggerIdx = 0; triggerIdx < triggersCount; triggerIdx++) {
            int position = ByteArrayUtility.getWord(levelInfData, pos);
            int flags = ByteArrayUtility.getByte(levelInfData, pos+2);
            int address = ByteArrayUtility.getWord(levelInfData, pos+3);
            EobTrigger trigger = new EobTrigger(position, flags, address);
            pos += 5;
            triggers.add(trigger);
        }
        EobLogger.println(String.format("Script triggers count: %d", triggersCount));
        EobLogger.println("");
    }

    public byte[] getLevelInfData() {
        return levelInfData;
    }
}
