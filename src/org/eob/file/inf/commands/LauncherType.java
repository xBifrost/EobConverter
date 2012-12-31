package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 12:48
 */
public enum LauncherType {
    Spell(0xDF),
    Item(0xEC);

    private int launcherTypeId;

    LauncherType(int launcherTypeId) {
        this.launcherTypeId = launcherTypeId;
    }

    public static LauncherType valueOf(int launcherTypeId) {
        for (LauncherType setWallType : values()) {
            if (setWallType.launcherTypeId == launcherTypeId) {
                return setWallType;
            }
        }
        return Item;
    }
}
