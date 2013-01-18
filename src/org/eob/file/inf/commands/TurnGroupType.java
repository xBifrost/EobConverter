package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 12:48
 */
public enum TurnGroupType {
    Unknown(-0x01),
    Party(0xF1),
    Item(0xF5);

    private int turnGroupTypeId;

    TurnGroupType(int turnGroupTypeId) {
        this.turnGroupTypeId = turnGroupTypeId;
    }

    public static TurnGroupType valueOf(int turnGroupTypeId) {
        for (TurnGroupType turnGroupType : values()) {
            if (turnGroupType.turnGroupTypeId == turnGroupTypeId) {
                return turnGroupType;
            }
        }
        return Unknown;
    }
}
