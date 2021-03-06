package org.eob.enums;

import org.eob.EobLogger;

import java.util.Arrays;
import java.util.List;

/**
 * User: Bifrost
 * Date: 10/23/12
 * Time: 10:18 PM
 */
public enum InSquarePositionType {
    Unknown(-1, -1, -1, Arrays.asList("unknown", "u")),
    North(0, 4, 0, Arrays.asList("north", "n")),
    East(1, 5, 1, Arrays.asList("east", "e")),
    South(2, 6, 2, Arrays.asList("south", "s")),
    West(3, 7, 3, Arrays.asList("west", "w")),
    NW(-1, 0, -1, Arrays.asList("northwest", "nw")),
    NE(-1, 1, -1, Arrays.asList("northeast", "ne")),
    SW(-1, 2, -1, Arrays.asList("southwest", "sw")),
    SE(-1, 3, -1, Arrays.asList("southeast", "se")),
    EW(-1, -1, -1, Arrays.asList("eastwest", "ew")),
    NS(-1, -1, -1, Arrays.asList("northsouth", "ns")),
    Center(-1, -1, 4, Arrays.asList("center", "c")),
    Alcove(-1, 8, -1, Arrays.asList("alcove", "a"));

    public final int wallPosition;
    public final int itemPosition;
    public final int monsterPosition;
    public final List<String> possibleNames;

    InSquarePositionType(int wallPosition, int itemPosition, int monsterPosition, List<String> possibleNames) {
        this.wallPosition = wallPosition;
        this.itemPosition = itemPosition;
        this.monsterPosition = monsterPosition;
        this.possibleNames = possibleNames;
    }

    public static InSquarePositionType getItemPositionById(int posId) {
        for (InSquarePositionType inSquarePositionType : values()) {
            if (inSquarePositionType.itemPosition == posId) {
                return inSquarePositionType;
            }
        }

        EobLogger.println("Unsupported In Square Position:" + posId);
        return Unknown;
    }

    public static InSquarePositionType getMonsterPositionById(int posId) {
        for (InSquarePositionType inSquarePositionType : values()) {
            if (inSquarePositionType.monsterPosition == posId) {
                return inSquarePositionType;
            }
        }

        EobLogger.println("Unsupported In Square Position:" + posId);
        return Unknown;
    }

    public static InSquarePositionType valueByString(String value) {
        value = value.toLowerCase();
        for (InSquarePositionType inSquarePositionType : values()) {
            if (inSquarePositionType.possibleNames.contains(value)) {
                return inSquarePositionType;
            }
        }

        EobLogger.println("[ERROR]: Unsupported type of position: " + value);
        return Unknown;
    }

    public InSquarePositionType oppositePosition() {
        switch (this) {
            case Unknown:
                return Unknown;
            case North:
                return South;
            case East:
                return West;
            case South:
                return North;
            case West:
                return East;
            case NW:
                return SE;
            case NE:
                return SW;
            case SW:
                return NE;
            case SE:
                return NW;
            case EW:
                return EW;
            case NS:
                return NS;
            case Center:
                return Center;
            case Alcove:
                return Alcove;
        }
        return Unknown;
    }
}
