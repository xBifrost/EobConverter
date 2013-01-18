package org.eob.enums;

import org.eob.EobLogger;

/**
 * User: Bifrost
 * Date: 18.1.2013
 * Time: 23:00
 */
public enum RaceType {
    Unknown(-1),
    Human(0),
    Elf(1),
    HalfElf(2),
    Dwarf(3),
    Gnome(4),
    Halfling(5);

    public final int eobRace;

    RaceType(int eobRace) {
        this.eobRace = eobRace;
    }

    public static RaceType getRaceById(int eobRace) {
        for (RaceType raceType : values()) {
            if (raceType.eobRace == eobRace) {
                return raceType;
            }
        }

        EobLogger.println("Unsupported race:" + eobRace);
        return Unknown;
    }
}
