package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 13:50
 */
public enum GiveXpType {
    Unknown(-0x01),
    Party(0xE2);

    private int giveXpTypeId;

    GiveXpType(int giveXpTypeId) {
        this.giveXpTypeId = giveXpTypeId;
    }

    public static GiveXpType valueOf(int giveXpTypeId) {
        for (GiveXpType setWallType : values()) {
            if (setWallType.giveXpTypeId == giveXpTypeId) {
                return setWallType;
            }
        }
        return Unknown;
    }
}
