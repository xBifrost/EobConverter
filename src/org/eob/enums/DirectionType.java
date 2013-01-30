package org.eob.enums;

import org.eob.EobLogger;

import java.util.Arrays;
import java.util.List;

/**
 * User: Bifrost
 * Date: 10/29/12
 * Time: 9:36 PM
 */
public enum DirectionType {
    Unknown(-1, Arrays.asList("unknown", "u")),
    North(0, Arrays.asList("north", "n")),
    East(1, Arrays.asList("east", "e")),
    South(2, Arrays.asList("south", "s")),
    West(3, Arrays.asList("west", "w")),
    Unchanged(0xFF, Arrays.asList("Unchanged", "un"));

    public final int eobDirection;
    public final List<String> possibleNames;

    DirectionType(int eobDirection, List<String> possibleNames) {
        this.eobDirection = eobDirection;
        this.possibleNames = possibleNames;
    }

    public static DirectionType getDirectionById(int posId) {
        for (DirectionType directionType : values()) {
            if (directionType.eobDirection == posId) {
                return directionType;
            }
        }

        EobLogger.println("Unsupported direction:" + posId);
        return Unknown;
    }

    public static DirectionType valueByString(String value) {
        value = value.toLowerCase();
        for (DirectionType directionType : values()) {
            if (directionType.possibleNames.contains(value)) {
                return directionType;
            }
        }

        EobLogger.println("[ERROR]: Unsupported type of direction: " + value);
        return Unknown;
    }

    public DirectionType turnBack() {
        switch (this) {
            case North:
                return South;
            case East:
                return West;
            case South:
                return North;
            case West:
                return East;
        }
        return this;
    }
}
