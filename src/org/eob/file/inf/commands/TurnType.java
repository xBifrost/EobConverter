package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 12:48
 */
public enum TurnType {
    Unknown(-0x01),
    Party(0xF1),
    Item(0xF5);

    private int turnTypeId;

    TurnType(int turnTypeId) {
        this.turnTypeId = turnTypeId;
    }

    public static TurnType valueOf(int turnTypeId) {
        for (TurnType setWallType : values()) {
            if (setWallType.turnTypeId == turnTypeId) {
                return setWallType;
            }
        }
        return Unknown;
    }
}
