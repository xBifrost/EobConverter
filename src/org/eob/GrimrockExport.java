package org.eob;

import org.eob.enums.GameSupportType;
import org.eob.enums.InSquarePositionType;
import org.eob.enums.WallType;
import org.eob.export.MonsterGroup;
import org.eob.file.inf.InfFile;
import org.eob.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * User: Bifrost
 * Date: 10/25/12
 * Time: 6:19 PM
 */
@SuppressWarnings("FieldCanBeLocal")
public class GrimrockExport {
    private Map<Long, LevelParser> levels = new TreeMap<Long, LevelParser>();
    private Map<Long, InfFile> levelsInfo = new TreeMap<Long, InfFile>();

    private static String namePrefix = "eob_";

    private static String DUNGEON_FILE = "dungeon.lua";
    private static String ITEM_FILE = "items.lua";
    private static String MONSTER_FILE = "monsters.lua";

    private ItemParser itemParser;
    private int maxLevel;
    private boolean debug;

    private static String LEVEL_FILE = "level%d.lua";
    private final Map<String, String> externalChanges = new HashMap<String, String>();
    private final Map<String, String> externalElementName = new HashMap<String, String>();

    // Map of the already defined monster groups
    private final Map<String, String> monstersByPosition = new HashMap<String, String>();

    // Indexes
    private final Map<String, Long> itemNameNextIndex = new HashMap<String, Long>();
    private final Map<String, Long> monsterNameNextIndex = new HashMap<String, Long>();
    private final Map<String, Long> monsterGroupNextIndex = new HashMap<String, Long>();

    private final List<GrimrockLevelSettings> levelsSettings = Arrays.asList(
            new GrimrockLevelSettings(1L, "Severs 1", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(2L, "Severs 2", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(3L, "Severs 3", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(4L, "Dungeon 1", "temple", "assets/samples/music/temple_ambient.ogg"),
            new GrimrockLevelSettings(5L, "Dungeon 2", "temple", "assets/samples/music/temple_ambient.ogg"),
            new GrimrockLevelSettings(6L, "Dungeon 3", "temple", "assets/samples/music/temple_ambient.ogg"),
            new GrimrockLevelSettings(7L, "Drow 1", "prison", "assets/samples/music/prison_ambient.ogg"),
            new GrimrockLevelSettings(8L, "Drow 2", "prison", "assets/samples/music/prison_ambient.ogg"),
            new GrimrockLevelSettings(9L, "Drow 3", "prison", "assets/samples/music/prison_ambient.ogg"),
            new GrimrockLevelSettings(10L, "Hive 1", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(11L, "Hive 2", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(12L, "Sanctum", "temple", "assets/samples/music/temple_ambient.ogg")
    );

    public GrimrockExport(List<String> externalChangesList, ItemParser itemParser, int maxLevel, boolean debug) {
        this.itemParser = itemParser;
        this.maxLevel = maxLevel;
        this.debug = debug;
        prepareExternalChanges(externalChangesList);
    }

    public void addLevel(LevelParser levelParser) {
        levels.put((long) levelParser.levelId, levelParser);
    }

    public void addLevelInfo(InfFile infFile) {
        levelsInfo.put((long) infFile.levelId, infFile);
    }

    public void exportIntoGrimrock(boolean addLevelInSeparateFile) {
        exportItems(itemParser, ITEM_FILE, maxLevel);
        exportMonsters(levelsInfo.values(), MONSTER_FILE);

        if (addLevelInSeparateFile) {
            for (Long levelId : levels.keySet()) {
                try {
                    FileWriter outFile = new FileWriter(String.format(LEVEL_FILE, levelId));
                    PrintWriter out = new PrintWriter(outFile);
                    out.println(String.format("-- This file has been generated by EobConverter v%s", EobConverter.CONVERTER_VERSION));

                    exportGrimrockLevel(levels.get(levelId), levelsInfo.get(levelId), out);

                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                FileWriter outFile = new FileWriter(DUNGEON_FILE);
                PrintWriter out = new PrintWriter(outFile);
                out.println(String.format("-- This file has been generated by EobConverter v%s", EobConverter.CONVERTER_VERSION));
                for (Long levelId : levels.keySet()) {
                    exportGrimrockLevel(levels.get(levelId), levelsInfo.get(levelId), out);
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int getFacing(InSquarePositionType inSquarePositionType) {
        switch (inSquarePositionType) {
            default:
                System.out.println("[Error]: Unsupported Grimrock position: " + inSquarePositionType.name());
            case North:
            case NW:
                return 0;
            case East:
            case NE:
                return 1;
            case South:
            case SE:
                return 2;
            case West:
            case SW:
                return 3;
        }
    }

    private void prepareExternalChanges(List<String> externalChangesList) {
        for (String change : externalChangesList) {
            String[] words = change.split(" ");
            externalChanges.put(words[1], words[0]);
            if (words.length > 2) {
                externalElementName.put(words[1], words[2]);
            }
        }
    }


    //--------------------
    //--- Items Export ---
    //--------------------

    private void exportItems(ItemParser itemParser, String fileName, int maxLevel) {
        try {
            PrintWriter out = new PrintWriter(fileName);

            // Get max level
            Set<ItemObject> items = itemParser.getItemSet();
            Map<Long, Set<Item>> itemsByLevel = new TreeMap<Long, Set<Item>>();

            for (int level = 1; level < maxLevel; level++) {
                Set<ItemObject> levelItemObjects = new HashSet<ItemObject>();
                Set<Item> levelItems = new LinkedHashSet<Item>();
                for (ItemObject item : items) {
                    if (item.level == level) {
                        levelItems.add(item.item);
                        levelItemObjects.add(item);
                    }
                }
                items.removeAll(levelItemObjects);
                itemsByLevel.put((long) level, levelItems);
            }

            Set<Item> levelItems = new LinkedHashSet<Item>();
            for (ItemObject item : items) {
                levelItems.add(item.item);
            }
            itemsByLevel.put(0L, levelItems);

            // Export by levels
            for (Map.Entry<Long, Set<Item>> level : itemsByLevel.entrySet()) {
                out.println("---------------");
                out.println("--- Level " + level.getKey() + " ---");
                out.println("---------------");
                out.println("");
                for (Item item : level.getValue()) {
                    createItem(item, out, false);
                    createItem(item, out, true);
                }
            }

            out.close();
        } catch (java.io.IOException e) {
            System.out.println("Error while exporting:");
            e.printStackTrace();
        }
    }

    private void spawnItemOnFloor(ItemObject item, PrintWriter out) {
        String elementType = namePrefix + item.item.getElementType(item.identified);
        String elementName = getNextNameByIndex(itemNameNextIndex, elementType);

        String itemString = String.format("spawn(\"%s\", %d, %d, %d, \"%s\")", elementType, item.x, item.y, getFacing(item.inSquarePosition), elementName);
        if (debug) {
            if (item.item.itemType.unknownType) {
                itemString += String.format(" -- %s type=%s subtype=%s", item.item.getDescription(item.identified), item.item.itemType.itemTypeId, item.item.itemSubType.id);
            }
        }

        out.println(itemString);
    }

    private void spawnItemInAlcove(ItemObject item, PrintWriter out) {
        String elementType = namePrefix + item.item.getElementType(item.identified);

        String itemString = String.format("\t:addItem(spawn(\"%s\"))", elementType);
        if (debug) {
            itemString += String.format(" -- %s ", item.item.getDescription(item.identified));
            if (item.item.itemType.unknownType) {
                itemString += String.format(" type=%s subtype=%s", item.item.itemType.itemTypeId, item.item.itemSubType.id);

            }
        }

        out.println(itemString);
    }

    private void createItem(Item item, PrintWriter out, boolean identified) {
        out.println("cloneObject {");
        out.println(String.format("\tname = \"%s\",%s", namePrefix + item.getElementType(identified), identified ? "" : "  -- " + item.getDescription(identified)));
        out.println(String.format("\tbaseObject = \"%s\",", item.itemType.baseObject));
        out.println(String.format("\tuiName = \"%s\",", item.getDescription(identified)));
/*
        if (item.glowMagic) {
            out.println("\tglitterEffect = \"magic_glow_blue\",");
        }
*/
        out.println("}");
        out.println("");
    }

    private void spawnPossibleItemsIntoAlcove(PrintWriter out, Boolean containCompartment, int level, int x, int y) {
        if (!containCompartment) {
            return;
        }
        for (ItemObject itemObject : itemParser.getItemSet()) {
            if (itemObject.level == level && itemObject.x == x && itemObject.y == y && itemObject.inSquarePosition.equals(InSquarePositionType.Alcove)) {
                spawnItemInAlcove(itemObject, out);
            }
        }
    }

    //----------------------
    //--- Monster Export ---
    //----------------------

    private void exportMonsters(Collection<InfFile> values, String fileName) {
        try {
            PrintWriter out = new PrintWriter(fileName);

            out.println("--========================--");
            out.println("--== Generated monsters ==--");
            out.println("--========================--");
            out.println("");

            Map<String, String> alreadyDefinedMonsterGroups = new HashMap<String, String>();

            for (InfFile infFile : values) {
                out.println("----------------");
                out.println(String.format("--- Level %02d ---", infFile.levelId));
                out.println("----------------");
                out.println("");

                // Sort the monsters by position
                Map<String, List<MonsterObject>> monstersInSquare = new HashMap<String, List<MonsterObject>>();
                for (MonsterObject monsterObject : infFile.monsterObjects) {
                    String square = monsterObject.x + "_" + monsterObject.y;
                    List<MonsterObject> monsters = monstersInSquare.get(square);
                    if (monsters == null) {
                        monsters = new ArrayList<MonsterObject>();
                        monstersInSquare.put(square, monsters);
                    }
                    monsters.add(monsterObject);
                }

                // Create monster groups if necessary
                for (List<MonsterObject> monsterObjects : monstersInSquare.values()) {
                    createMonsterGroup(monsterObjects, alreadyDefinedMonsterGroups, out);
                }
            }

            out.close();
        } catch (java.io.IOException e) {
            System.out.println("Error while exporting:");
            e.printStackTrace();
        }
    }

    private void spawnMonsters(List<MonsterObject> monsters, PrintWriter out) {
        MonsterObject monster = monsters.get(0);
        String monstersPos = monster.levelId + "_" + monster.x + "_" + monster.y;
        String monsterType = monstersByPosition.get(monstersPos);
        int facing = 0;

        if (monsters.size() != 1 && monsters.size() != 2 && monsters.size() != 4) {
            System.out.println(String.format("Unsupported number of monsters: %d [L: %d, X: %d, Y:%d]", monsters.size(), monster.levelId, monster.x, monster.y));
            return;
        }

        String monsterName = getNextNameByIndex(monsterNameNextIndex, monsterType);
        out.println(String.format("spawn(\"%s\", %d, %d, %d, \"%s\")", monsterType, monster.x, monster.y, facing, monsterName));
    }

    private void createMonsterGroup(List<MonsterObject> monsters, Map<String, String> alreadyDefinedMonsterGroups, PrintWriter out) {
        if (monsters.size() < 1) {
            return;
        }

        String monstersPos = monsters.get(0).levelId + "_" + monsters.get(0).x + "_" + monsters.get(0).y;

        List<MonsterGroup> sameMonsters = new ArrayList<MonsterGroup>();

        // Group monsters by the same type
        for (MonsterObject monster : monsters) {
            String pocketItem50Name = monster.pocketItem50 == null ? "" :
                    namePrefix + monster.pocketItem50.item.getElementType(monster.pocketItem50.identified);
            String pocketItemName = monster.pocketItem == null ? "" :
                    namePrefix + monster.pocketItem.item.getElementType(monster.pocketItem.identified);

            MonsterGroup findGroup = null;
            for (MonsterGroup sameMonster : sameMonsters) {
                if (sameMonster.pocketItem50Name.equals(pocketItem50Name) && sameMonster.pocketItemName.equals(pocketItemName)) {
                    findGroup = sameMonster;
                    break;
                }
            }
            if (findGroup == null) {
                findGroup = new MonsterGroup(monster, pocketItem50Name, pocketItemName);
                sameMonsters.add(findGroup);
            }

            findGroup.count++;
        }

        // Prepare monster group Id
        sortMonsterGroups(sameMonsters);
        String monsterGroupId = getMonsterGroupId(monsters.size(), sameMonsters);
        if (alreadyDefinedMonsterGroups.get(monsterGroupId) != null) {
            monstersByPosition.put(monstersPos, alreadyDefinedMonsterGroups.get(monsterGroupId) + (monsters.size() < 2 ? "" : "_group"));
            return;
        }

        // Prepare basic monster name
        final String monsterName;

        // Monster group with the same loot
        if (sameMonsters.size() == 1) {
            MonsterGroup monsterGroup = sameMonsters.get(0);
            if (monsterGroup.pocketItem50Name.length() != 0 || monsterGroup.pocketItemName.length() != 0) {
                monsterName = getNextNameByIndex(monsterGroupNextIndex, monsterGroup.monsterObject.monster.monsterName + monsters.size());

                out.println("cloneObject {");
                out.println(String.format("\tname = \"%s\",", monsterName));
                out.println(String.format("\tbaseObject = \"%s\",", monsterGroup.monsterObject.monster.monsterName));
                out.print("\tlootDrop = {");
                if (monsterGroup.pocketItem50Name.length() > 0) {
                    out.print(String.format("50, \"%s\"", monsterGroup.pocketItem50Name));
                }
                if (monsterGroup.pocketItemName.length() > 0) {
                    if (monsterGroup.pocketItem50Name.length() > 0) {
                        out.print(", ");
                    }
                    out.print(String.format("100, \"%s\"", monsterGroup.pocketItemName));
                }
                out.println("},");
                out.println("}");
                out.println("");

                alreadyDefinedMonsterGroups.put(monsterGroupId, monsterName);
            } else {
                monstersByPosition.put(monstersPos, monsterGroup.monsterObject.monster.monsterName + (monsters.size() < 2 ? "" : "_group"));
                return;
            }

        } else {
            // Group with the different loot
            monsterName = getNextNameByIndex(monsterGroupNextIndex, sameMonsters.get(0).monsterObject.monster.monsterName + monsters.size());
            MonsterGroup monsterGroup = sameMonsters.get(0);

            out.println("cloneObject {");
            out.println(String.format("\tname = \"%s\",", monsterName));
            out.println(String.format("\tbaseObject = \"%s\",", monsterGroup.monsterObject.monster.monsterName));
            out.println("\tlootDrop = {},");
            out.println("\tonDie = function(monster)");
            out.println(String.format("\t\tif findEntity(\"%s_c\") == nil then", monsterName));
            out.println(String.format("\t\t\tspawn(\"counter\", %d, 0, 0, 0, \"%s_c\")", monsterGroup.monsterObject.levelId, monsterName));
            out.println("\t\tend");

            int count = 0;
            for (MonsterGroup sameMonster : sameMonsters) {
                if (count == 0) {
                    out.print("\t\t");
                } else {
                    out.print("\t\telse");
                }
                out.println(String.format("if %s_c:getValue() %% %d < %d then", monsterName, monsters.size(), count + sameMonster.count));
                count += sameMonster.count;

                if (sameMonster.pocketItem50Name.length() > 0) {
                    String elementType = namePrefix + sameMonster.monsterObject.pocketItem50.item.getElementType(sameMonster.monsterObject.pocketItem50.identified);
                    String elementName = elementType + "_" + sameMonster.monsterObject.x + "_" + sameMonster.monsterObject.y + "_";

                    out.println("\t\t\tif math.random(0,99) < 50 then");
                    out.println(String.format("\t\t\t\tspawn(\"%s\", monster.level, monster.x, monster.y, monster.facing, \"%s\"..%s_c:getValue())", elementType, elementName, monsterName));
                    out.println("\t\t\tend");
                }
                if (sameMonster.pocketItemName.length() > 0) {
                    String elementType = namePrefix + sameMonster.monsterObject.pocketItem.item.getElementType(sameMonster.monsterObject.pocketItem.identified);
                    String elementName = getNextNameByIndex(itemNameNextIndex, elementType);

                    out.println(String.format("\t\t\tspawn(\"%s\", monster.level, monster.x, monster.y, monster.facing, \"%s\"..%s_c:getValue())", elementType, elementName, monsterName));
                }
            }
            out.println("\t\tend");

            out.println(String.format("\t\t%s_c:increment()", monsterName));
            out.println("\tend,");
            out.println("}");
            out.println("");
        }

        monstersByPosition.put(monstersPos, monsterName + (monsters.size() < 2 ? "" : "_group"));

        if (monsters.size() < 2) {
            return;
        }

        // Define group
        out.println("defineObject {");
        out.println(String.format("\tname = \"%s_group\",", monsterName));
        out.println("\tclass = \"MonsterGroup\",");
        out.println(String.format("\tmonsterType = \"%s\",", monsterName));
        out.println(String.format("\tcount = %d,", monsters.size()));
        out.println("}");
        out.println("");
    }

    private void sortMonsterGroups(List<MonsterGroup> monsterGroups) {
        Collections.sort(monsterGroups, new Comparator<MonsterGroup>() {
            @Override
            public int compare(MonsterGroup monsterGroup1, MonsterGroup monsterGroup2) {
                int result = monsterGroup1.monsterObject.monster.monsterName.compareTo(monsterGroup2.monsterObject.monster.monsterName);
                if (result != 0) {
                    return result;
                }
                result = monsterGroup1.pocketItem50Name.compareTo(monsterGroup2.pocketItem50Name);
                if (result != 0) {
                    return result;
                }
                return monsterGroup1.pocketItemName.compareTo(monsterGroup2.pocketItemName);
            }
        });
    }

    private String getMonsterGroupId(int groupSize, List<MonsterGroup> monsterGroups) {
        String monsterGroupId = "";

        for (MonsterGroup monsterGroup : monsterGroups) {
            monsterGroupId += (monsterGroupId.length() == 0 ? "" : "|") + monsterGroup.monsterObject.monster.monsterName + groupSize +
                    "|" + monsterGroup.pocketItem50Name + "|" + monsterGroup.pocketItemName;
        }

        return monsterGroupId;
    }

    //---------------------
    //--- Levels Export ---
    //---------------------

    private void exportGrimrockLevel(LevelParser levelParser, InfFile infFile, PrintWriter out) {
        System.out.println("Writing level " + levelParser.levelId + "...");

        GrimrockLevelSettings levelSettings = null;
        for (GrimrockLevelSettings levelInfo : levelsSettings) {
            if (levelParser.levelId == levelInfo.levelId) {
                levelSettings = levelInfo;
            }
        }

        // Write dungeon
        out.println("");
        out.println(String.format("--- level %d ---", levelParser.levelId));
        out.println("");
        if (levelSettings != null) {
            out.println(String.format("mapName(\"%s\")", levelSettings.levelName));
            out.println(String.format("setWallSet(\"%s\")", levelSettings.wallSet));
            out.println(String.format("playStream(\"%s\")", levelSettings.playStream));
        }
        out.println("mapDesc([[");
        for (int y = 0; y < levelParser.height; y++) {
            for (int x = 0; x < levelParser.width; x++) {
                if (levelParser.level[x][y].isUnknownBlock()) {
                    out.print("?");
                } else if (levelParser.level[x][y].isSolidBlock()) {
                    out.print("#");
                } else {
                    out.print(".");
                }
            }
            out.println("");
        }
        out.println("]])");

        // Write walls
        for (int y = 0; y < levelParser.height; y++) {
            for (int x = 0; x < levelParser.width; x++) {
                Square square = levelParser.level[x][y];

                Set<Wall> usedWalls = new HashSet<Wall>();

                // Special walls
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_N"))) {
                    spawnWall(levelParser, out, x, y, square.north, 0, "Wall", "N");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_E"))) {
                    spawnWall(levelParser, out, x, y, square.east, 1, "Wall", "E");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_S"))) {
                    spawnWall(levelParser, out, x, y, square.south, 2, "Wall", "S");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_W"))) {
                    spawnWall(levelParser, out, x, y, square.west, 3, "Wall", "W");
                }

                // Doors
                Wall squareType = square.getDoor(usedWalls);
                if (squareType != null) {
                    spawnWall(levelParser, out, x, y, squareType, square.getDoorFacing(), "Door", square.getDoorFacing() == 1 ? "EW" : "NS");
                }

                // In Square
                squareType = square.getInSquare(usedWalls);
                if (squareType != null) {
                    int facing = 0;
                    if ((x < levelParser.level.length - 1) && !levelParser.level[x + 1][y].isSolidBlock()) {
                        facing = 3;
                    }
                    if (x > 0 && !levelParser.level[x - 1][y].isSolidBlock()) {
                        facing = 1;
                    }
                    if (y > 0 && !levelParser.level[x][y - 1].isSolidBlock()) {
                        facing = 2;
                    }
                    spawnWall(levelParser, out, x, y, squareType, facing, "InSquare", "?");

                }

                if (square.north.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.north) &&
                        (!square.north.wallType.equals(WallType.Wall) || (square.north.wallType.equals(WallType.Wall) && !levelParser.level[x][y - 1].isSolidBlock()))) {
                    if (square.north.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, x, y, square.north, 2, "Wall", "N");
                    } else {
                        spawnWall(levelParser, out, x, y - 1, square.north, 2, "Wall", "S");
                    }
                    spawnPossibleItemsIntoAlcove(out, square.north.containCompartment, levelParser.levelId, x, y);
                }
                if (square.east.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.east) &&
                        (!square.east.wallType.equals(WallType.Wall) || (square.east.wallType.equals(WallType.Wall) && !levelParser.level[x + 1][y].isSolidBlock()))) {
                    if (square.east.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, x, y, square.east, 3, "Wall", "E");
                    } else {
                        spawnWall(levelParser, out, x + 1, y, square.east, 3, "Wall", "W");
                    }
                    spawnPossibleItemsIntoAlcove(out, square.east.containCompartment, levelParser.levelId, x, y);
                }
                if (square.south.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.south) &&
                        (!square.south.wallType.equals(WallType.Wall) || (square.south.wallType.equals(WallType.Wall) && !levelParser.level[x][y + 1].isSolidBlock()))) {
                    if (square.south.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, x, y, square.south, 0, "Wall", "S");
                    } else {
                        spawnWall(levelParser, out, x, y + 1, square.south, 0, "Wall", "N");
                    }
                    spawnPossibleItemsIntoAlcove(out, square.south.containCompartment, levelParser.levelId, x, y);
                }
                if (square.west.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.west) &&
                        (!square.west.wallType.equals(WallType.Wall) || (square.west.wallType.equals(WallType.Wall) && !levelParser.level[x - 1][y].isSolidBlock()))) {
                    if (square.west.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, x, y, square.west, 1, "Wall", "W");
                    } else {
                        spawnWall(levelParser, out, x - 1, y, square.west, 1, "Wall", "E");
                    }
                    spawnPossibleItemsIntoAlcove(out, square.west.containCompartment, levelParser.levelId, x, y);
                }
            }
        }

        // Write items
        for (ItemObject itemObject : itemParser.getItemSet()) {
            if (itemObject.level == levelParser.levelId && !itemObject.inSquarePosition.equals(InSquarePositionType.Alcove)) {
                spawnItemOnFloor(itemObject, out);
            }
        }

        // Write monsters
        Map<String, List<MonsterObject>> monstersInSquare = new HashMap<String, List<MonsterObject>>();
        for (MonsterObject monsterObject : infFile.monsterObjects) {
            String square = monsterObject.x + "_" + monsterObject.y;
            List<MonsterObject> monsters = monstersInSquare.get(square);
            if (monsters == null) {
                monsters = new ArrayList<MonsterObject>();
                monstersInSquare.put(square, monsters);
            }
            monsters.add(monsterObject);
        }
        for (List<MonsterObject> monsterObjects : monstersInSquare.values()) {
            spawnMonsters(monsterObjects, out);
        }
    }


    private void spawnWall(LevelParser levelParser, PrintWriter out, int x, int y, Wall wall, int facing, String text, String direction) {
        if (!wall.elementType.equals("")) {
            String positionName = levelParser.levelId + "_" + x + "_" + y;
            String name = wall.elementName + "_" + positionName;
            if (direction.equals("N") || direction.equals("E") || direction.equals("S") || direction.equals("W")) {
                name += "_" + direction;
                positionName += "_" + direction;
            }

            String externalFilledWall = externalChanges.get(positionName);
            if (externalFilledWall != null && externalFilledWall.equals("F")) {
                if (wall.wallType.isSolid()) {
                    String elementName = externalElementName.get(positionName);
                    out.println("spawn(\"" + (elementName == null ? "???" : elementName) + "\", " + x + ", " + y + ", " + facing + ", \"wall_" + positionName + "\")");
                }
            }


            String externalChange = externalChanges.get(name);
            if (externalChange != null) {
                if (externalChange.equals("R")) {
                    return;
                }

                if (wall.wallType.equals(WallType.DoorPart)) {
                    facing = getFacing(InSquarePositionType.valueByString(externalChange));
                }
            }

            out.println("spawn(\"" + wall.elementType + "\", " + x + ", " + y + ", " + facing + ", \"" + name + "\")");
            if (!wall.properties.equals("")) {
                out.println(wall.properties);
            }
        } else {
            System.out.println(text + " type haven't defined elementType: " + wall.name() + " [" + x + "," + y + "," + direction + "] ");
        }
    }

    //-------------------------------
    //--- Grimrock Level Settings ---
    //-------------------------------

    class GrimrockLevelSettings {
        private Long levelId;
        String levelName;
        String wallSet;
        String playStream;

        public GrimrockLevelSettings(Long levelId, String levelName, String wallSet, String playStream) {
            this.levelId = levelId;
            this.levelName = levelName;
            this.wallSet = wallSet;
            this.playStream = playStream;
        }
    }

    //-----------------------
    //--- Index functions ---
    //-----------------------

    public String getNextNameByIndex(Map<String, Long> indexMap, String indexName) {
        Long index = indexMap.get(indexName);
        if (index == null) {
            index = 1L;
        }
        String result = indexName + "_" + index;
        index++;
        indexMap.put(indexName, index);

        return result;
    }
}
