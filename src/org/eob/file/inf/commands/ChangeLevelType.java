package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 29.12.2012
 * Time: 18:59
 */
public enum ChangeLevelType {
    ChangeLevel(0xE5),
    InLevel(-0x01); // Unknown id

    private int changeLevelTypeId;

    ChangeLevelType(int changeLevelTypeId) {
        this.changeLevelTypeId = changeLevelTypeId;
    }

    public static ChangeLevelType valueOf(int changeLevelTypeId) {
        for (ChangeLevelType setWallType : values()) {
            if (setWallType.changeLevelTypeId == changeLevelTypeId) {
                return setWallType;
            }
        }
        return InLevel;
    }
}
