package org.eob;

import org.eob.enums.GameSupportType;
import org.eob.enums.InSquarePositionType;
import org.eob.enums.WallType;
import org.eob.model.Item;
import org.eob.model.ItemObject;
import org.eob.model.Square;
import org.eob.model.Wall;

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

    private static String namePrefix = "eob_";
    private static String DUNGEON_FILE = "dungeon.lua";
    private static String ITEM_FILE = "items.lua";

    private ItemParser itemParser;
    private boolean debug;

    private static String LEVEL_FILE = "level%d.lua";
    private final Map<String, String> externalChanges = new HashMap<String, String>();
    private final Map<String, String> externalElementName = new HashMap<String, String>();

    private final Map<String, Long> itemNameNextIndex = new HashMap<String, Long>();


    public GrimrockExport(List<String> externalChangesList, ItemParser itemParser, boolean debug) {
        this.itemParser = itemParser;
        this.debug = debug;
        prepareExternalChanges(externalChangesList);
    }

    public void addLevel(LevelParser levelParser) {
        levels.put((long) levelParser.levelId, levelParser);
    }

    public void exportIntoGrimrock(boolean addLevelInSeparateFile) {
        exportItems(itemParser, ITEM_FILE, 12);

        if (addLevelInSeparateFile) {
            for (Long levelId : levels.keySet()) {
                try {
                    FileWriter outFile = new FileWriter(String.format(LEVEL_FILE, levelId));
                    PrintWriter out = new PrintWriter(outFile);

                    exportGrimrockLevel(levels.get(levelId), out);

                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                FileWriter outFile = new FileWriter(DUNGEON_FILE);
                PrintWriter out = new PrintWriter(outFile);
                for (Long levelId : levels.keySet()) {
                    exportGrimrockLevel(levels.get(levelId), out);
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
        Long index = itemNameNextIndex.get(elementType);
        if (index == null) {
            index = 1L;
        }
        String elementName = elementType + "_" + index;
        index++;
        itemNameNextIndex.put(elementType, index);

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

        String itemString = String.format("\t:spawn(\"%s\")", elementType);
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
        out.println(String.format("\tname = \"%s\"%s,", item.getElementType(identified), identified ? "" : "  -- " + item.getDescription(identified)));
        out.println(String.format("\tbaseObject = \"%s\",", item.itemType.baseObject));
        out.println(String.format("\tuiName = \"%s\",", item.getDescription(identified)));
        if (item.glowMagic) {
            out.println("\tglitterEffect = \"magic_glow_blue\",");
        }
        out.println("}");
        out.println("");
    }

    //---------------------
    //--- Levels Export ---
    //---------------------

    private void exportGrimrockLevel(LevelParser levelParser, PrintWriter out) {
        System.out.println("Writing level " + levelParser.levelId + "...");

        // Write dungeon
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
                    spawnWall(levelParser, out, y, x, square.north, 0, "Wall", "N");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_E"))) {
                    spawnWall(levelParser, out, y, x, square.east, 1, "Wall", "E");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_S"))) {
                    spawnWall(levelParser, out, y, x, square.south, 2, "Wall", "S");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_W"))) {
                    spawnWall(levelParser, out, y, x, square.west, 3, "Wall", "W");
                }

                // Doors
                Wall squareType = square.getDoor(usedWalls);
                if (squareType != null) {
                    spawnWall(levelParser, out, y, x, squareType, square.getDoorFacing(), "Door", square.getDoorFacing() == 1 ? "EW" : "NS");
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
                    spawnWall(levelParser, out, y, x, squareType, facing, "InSquare", "?");

                }

                if (square.north.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.north) &&
                        (!square.north.wallType.equals(WallType.Wall) || (square.north.wallType.equals(WallType.Wall) && !levelParser.level[x][y - 1].isSolidBlock()))) {
                    if (square.north.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, y, x, square.north, 2, "Wall", "N");
                    } else {
                        spawnWall(levelParser, out, y - 1, x, square.north, 2, "Wall", "S");
                    }
                }
                if (square.east.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.east) &&
                        (!square.east.wallType.equals(WallType.Wall) || (square.east.wallType.equals(WallType.Wall) && !levelParser.level[x + 1][y].isSolidBlock()))) {
                    if (square.east.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, y, x, square.east, 3, "Wall", "E");
                    } else {
                        spawnWall(levelParser, out, y, x + 1, square.east, 3, "Wall", "W");
                    }
                }
                if (square.south.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.south) &&
                        (!square.south.wallType.equals(WallType.Wall) || (square.south.wallType.equals(WallType.Wall) && !levelParser.level[x][y + 1].isSolidBlock()))) {
                    if (square.south.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, y, x, square.south, 0, "Wall", "S");
                    } else {
                        spawnWall(levelParser, out, y + 1, x, square.south, 0, "Wall", "N");
                    }
                }
                if (square.west.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.west) &&
                        (!square.west.wallType.equals(WallType.Wall) || (square.west.wallType.equals(WallType.Wall) && !levelParser.level[x - 1][y].isSolidBlock()))) {
                    if (square.west.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, y, x, square.west, 1, "Wall", "W");
                    } else {
                        spawnWall(levelParser, out, y, x - 1, square.west, 1, "Wall", "E");
                    }
                }
            }
        }

        // Write items
        for (ItemObject itemObject : itemParser.getUsedItemSet()) {
            if (itemObject.level == levelParser.levelId && !itemObject.inSquarePosition.equals(InSquarePositionType.Alcove)) {
                spawnItemOnFloor(itemObject, out);
            }
        }
    }

    private void spawnWall(LevelParser levelParser, PrintWriter out, int y, int x, Wall wall, int facing, String text, String direction) {
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
}
