package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 12:48
 */
public enum TeleportType {
    Unknown(-0x01),
    Party(0xE8),
    Monster(0xF3),
    Item(0xF5);

    private int teleportTypeId;

    TeleportType(int teleportTypeId) {
        this.teleportTypeId = teleportTypeId;
    }

    public static TeleportType valueOf(int teleportTypeId) {
        for (TeleportType setWallType : values()) {
            if (setWallType.teleportTypeId == teleportTypeId) {
                return setWallType;
            }
        }
        return Unknown;
    }
}
