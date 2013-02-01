package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 29.12.2012
 * Time: 15:11
 */
public enum ChangeWallType {
    CompleteBlock(0xF7),
    OneWall(0xE9),
    Door(0xEA),
    PartyFacing(0xED);

    private int wallTypeId;

    ChangeWallType(int wallTypeId) {
        this.wallTypeId = wallTypeId;
    }

    public static ChangeWallType valueOf(int wallTypeId) {
        for (ChangeWallType changeWallType : values()) {
            if (changeWallType.wallTypeId == wallTypeId) {
                return changeWallType;
            }
        }
        return null;
    }
}
