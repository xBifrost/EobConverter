package org.eob;

import org.eob.enums.GameSupportType;
import org.eob.enums.WallType;
import org.eob.external.ExternalChangeCommand;
import org.eob.file.dat.ItemTypeDatFile;
import org.eob.model.*;

import java.util.*;

/**
 * User: Bifrost
 * Date: 16.1.2013
 * Time: 23:17
 */
public class EobGlobalData {
    public ItemTypeDatFile itemTypeDatFile = null;
    public ItemParser itemParser = null;

    public List<ExternalChangeCommand> externalChangeCommands = null;
    public Map<String, String> assetNamesMap = new HashMap<String, String>();
    public Map<String, Set<String>> assetReverseNamesMap = new LinkedHashMap<String, Set<String>>();

    public final List<Item> registeredItems = new ArrayList<Item>();
    private final Map<ItemType, Map<Long, SubItemType>> registeredSubItems = new HashMap<ItemType, Map<Long, SubItemType>>();
    private final Map<Monster, String> registeredMonsters = new LinkedHashMap<Monster, String>();
    private final List<Wall> registeredWalls = new ArrayList<Wall>();

    // Conversion map to convert different eob walls (internal names) into one (internal name)
    // First side of the wall is primary (For example: Door contain button)
    public final Map<List<String>, String> wallConversion = new HashMap<List<String>, String>();


    public SubItemType addSubItem(SubItemType subItemType) {
        if (subItemType.itemType == null) {
            return subItemType;
        }

        Map<Long, SubItemType> subItemTypeMap = registeredSubItems.get(subItemType.itemType);
        if (subItemTypeMap == null) {
            subItemTypeMap = new LinkedHashMap<Long, SubItemType>();
            registeredSubItems.put(subItemType.itemType, subItemTypeMap);
        }
        subItemTypeMap.put(subItemType.id, subItemType);
        return subItemType;
    }

    public SubItemType getSubItemById(ItemType itemType, int id) {
        if (id == 0) {
            return SubItemType.NotSubtype;
        }

        Map<Long, SubItemType> typeMap = registeredSubItems.get(itemType);
        SubItemType subItemType = typeMap != null ? typeMap.get((long) id) : null;
        if (subItemType == null) {
            subItemType = new SubItemType(id, itemType, "unknown_" + id, "Unknown " + id, "");
            EobLogger.println("Unknown sub type of item type. (Item type: " + itemType.elementType + ", SubType: " + id + ")");
        }
        return subItemType;
    }

    public Item getItemOrCreateNew(Long notIdentifiedNameId, Long identifiedNameId, Boolean glowMagic, Boolean cursed, Long imageId, ItemType itemType, int itemSubType, Long countValue) {
        Item newItem = new Item(ItemName.itemNames.get(notIdentifiedNameId).name, ItemName.itemNames.get(identifiedNameId).name,
                glowMagic, cursed, imageId, itemType, getSubItemById(itemType, itemSubType), countValue);

        for (Item registeredItem : registeredItems) {
            if (registeredItem.equals(newItem)) {
                return registeredItem;
            }
        }

        registeredItems.add(newItem);
        return newItem;
    }

    public Monster createMonster(String internalName, int monsterType, String monsterName) {
        Monster monster = new Monster(monsterType, monsterName);
        registeredMonsters.put(monster, internalName);
        return monster;
    }

    public Monster getMonsterById(int monsterId) {
        for (Monster monster : registeredMonsters.keySet()) {
            if (monster.monsterId == monsterId) {
                return monster;
            }
        }

        return createMonster("unknown", monsterId, "");
    }

    public Wall createWall(String internalName, Long wallId, WallType wallType, boolean containCompartment, Set<Long> level) {
        Wall key = new Wall(wallId, internalName, wallType, containCompartment, Arrays.asList(GameSupportType.values()), level);
        registeredWalls.add(key);
        return key;
    }

    public Wall createWall(String internalName, Long wallId, WallType wallType, List<GameSupportType> gameSupportType) {
        Wall key = new Wall(wallId, internalName, wallType, false, gameSupportType, null);
        registeredWalls.add(key);
        return key;
    }

    public Wall getWallById(long wallId, int levelId) {
        for (Wall wall : registeredWalls) {
            if (wall.wallId == wallId) {
                if (wall.level == null || wall.level.contains((long) levelId)) {
                    return wall;
                }
            }
        }

        return createWall("unknown", wallId, WallType.Unknown, false, null);
    }


}
