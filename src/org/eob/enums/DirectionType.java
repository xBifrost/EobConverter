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
    North(0),
    East(1),
    South(2),
    West(3);

    public final int eobDirection;

    DirectionType(int eobDirection) {
        this.eobDirection = eobDirection;
    }

    public static DirectionType getDirectionById(int posId) {
        for (DirectionType directionType : values()) {
            if (directionType.eobDirection == posId) {
                return directionType;
            }
        }

        EobLogger.println("Unsupported direction:" + posId);
        return North;
    }
}
