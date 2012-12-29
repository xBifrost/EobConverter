package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 29.12.2012
 * Time: 15:11
 */
public enum SetWallType {
    CompleteBlock(0xF7),
    OneWall(0xE9),
    PartyFacing(0xED);

    private int setWallTypeId;

    SetWallType(int setWallTypeId) {
        this.setWallTypeId = setWallTypeId;
    }

    public static SetWallType valueOf(int setWallTypeId) {
        for (SetWallType setWallType : values()) {
            if (setWallType.setWallTypeId == setWallTypeId) {
                return setWallType;
            }
        }
        return null;
    }
}
