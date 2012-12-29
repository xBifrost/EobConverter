package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 28.12.2012
 * Time: 23:31
 */
public enum FlagType {
    Maze(0xEF),
    Global(0xF0),
    Monster(0xF3),
    Event(0xE4),
    Party(0xD1);

    private int flagTypeId;

    FlagType(int flagTypeId) {
        this.flagTypeId = flagTypeId;
    }

    public static FlagType valueOf(int flagTypeId) {
        for (FlagType flagType : values()) {
            if (flagType.flagTypeId == flagTypeId) {
                return flagType;
            }
        }
        return null;
    }
}
