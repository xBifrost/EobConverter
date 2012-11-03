package org.eob.model;

import org.eob.ByteArrayUtility;
import org.eob.ItemParser;
import org.eob.enums.DirectionType;
import org.eob.enums.InSquarePositionType;

/**
 * User: Bifrost
 * Date: 10/22/12
 * Time: 7:54 PM
 */
public class MonsterObject {
    public final int index;
    public final int levelType;
    public final int levelId;
    public final int x;
    public final int y;
    public final InSquarePositionType squarePosition;
    public final DirectionType direction;
    public final Monster monster;
    public final int imageId; // Image monster (0 - monster1 from level*.inf file, 1 - monster2 from level*.inf file)
    public final int phase; // animation phase?
    public final boolean pause; // Monster freeze?
    public final ItemObject pocketItem50; // 50% chance to drop this item
    public final ItemObject pocketItem; // 100% chance to drop this item


    public MonsterObject(int levelId, byte[] monsterData, ItemParser itemParser) {
        this.levelId = levelId;
        int pos = 0;
        index = ByteArrayUtility.getByte(monsterData, pos);
        pos++;
        levelType = ByteArrayUtility.getByte(monsterData, pos);
        pos++;

        int position = ByteArrayUtility.getWord(monsterData, pos);
        pos += 2;
        x = position & 0x1F;
        y = position >> 5;

        squarePosition = InSquarePositionType.getMonsterPositionById(ByteArrayUtility.getByte(monsterData, pos));
        pos++;
        direction = DirectionType.getDirectionById(ByteArrayUtility.getByte(monsterData, pos));
        pos++;
        monster = Monster.getById(ByteArrayUtility.getByte(monsterData, pos));
        pos++;
        imageId = ByteArrayUtility.getByte(monsterData, pos);
        pos++;
        phase = ByteArrayUtility.getByte(monsterData, pos);
        pos++;
        pause = ByteArrayUtility.getByte(monsterData, pos) != 0;
        pos++;

        int weaponIndex = ByteArrayUtility.getWord(monsterData, pos);
        if (weaponIndex != 0) {
            pocketItem50 = itemParser.getItemByIndex(weaponIndex);
        } else {
            pocketItem50 = null;
        }
        pos += 2;

        int itemIndex = ByteArrayUtility.getWord(monsterData, pos);
        if (itemIndex != 0) {
            pocketItem = itemParser.getItemByIndex(itemIndex);
        } else {
            pocketItem = null;
        }
    }
}
