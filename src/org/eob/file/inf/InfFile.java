package org.eob.file.inf;

import org.eob.ByteArrayUtility;
import org.eob.ItemParser;
import org.eob.file.cps.CpsFile;
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
    public final int commandsCount;
    public final List<Object> commands = new ArrayList<Object>();

    public InfFile(int levelId, byte[] levelInfDataPacked, ItemParser itemParser) {
        this.levelId = levelId;
        this.levelInfData = new CpsFile(levelInfDataPacked).getData();

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
        commandsCount = ByteArrayUtility.getWord(levelInfData, pos);
        pos += 2;
        for (int commandIdx = 0; commandIdx < commandsCount; commandIdx++) {
            int commandId = ByteArrayUtility.getByte(levelInfData, pos);
            pos++;

        }
    }

    public byte[] getLevelInfData() {
        return levelInfData;
    }
}
