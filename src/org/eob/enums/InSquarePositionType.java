package org.eob.enums;

import java.util.Arrays;
import java.util.List;

/**
 * User: Bifrost
 * Date: 10/23/12
 * Time: 10:18 PM
 */
public enum InSquarePositionType {
    Unknown(-1, -1, Arrays.asList("unknown", "u")),
    North(0, 4, Arrays.asList("north", "n")),
    East(1, 5, Arrays.asList("east", "e")),
    South(2, 6, Arrays.asList("south", "s")),
    West(3, 7, Arrays.asList("west", "w")),
    NW(-1, 0, Arrays.asList("northwest", "nw")),
    NE(-1, 1, Arrays.asList("northeast", "ne")),
    SW(-1, 2, Arrays.asList("southwest", "sw")),
    SE(-1, 3, Arrays.asList("southeast", "se")),
    Alcove(-1, 8, Arrays.asList("alcove", "a"));

    public final int wallPosition;
    public final int itemPosition;
    public final List<String> possibleNames;

    InSquarePositionType(int wallPosition, int itemPosition, List<String> possibleNames) {
        this.wallPosition = wallPosition;
        this.itemPosition = itemPosition;
        this.possibleNames = possibleNames;
    }

    public static InSquarePositionType getItemPositionById(int posId) {
        for (InSquarePositionType inSquarePositionType : values()) {
            if (inSquarePositionType.itemPosition == posId) {
                return inSquarePositionType;
            }
        }

        System.out.println("Unsupported In Square Position:" + posId);
        return Unknown;
    }

    public static InSquarePositionType valueByString(String value) {
        value = value.toLowerCase();
        for (InSquarePositionType inSquarePositionType : values()) {
            if (inSquarePositionType.possibleNames.contains(value)) {
                return inSquarePositionType;
            }
        }

        System.out.println("[ERROR]: Unsupported type of position: " + value);
        return Unknown;
    }
}
