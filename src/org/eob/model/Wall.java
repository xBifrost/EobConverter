package org.eob.model;

import org.eob.EobLogger;
import org.eob.enums.WallType;
import org.eob.enums.GameSupportType;

import java.lang.reflect.Field;
import java.util.*;

/**
 * User: Bifrost
 * Date: 10/22/12
 * Time: 7:54 PM
 */
@SuppressWarnings("UnusedDeclaration")
public class Wall {
    public final Long wallId;
    public final String internalName;
    public final Set<GameSupportType> gameSupportType;
    public final WallType wallType;
    public final Boolean containCompartment;
    public final Set<Long> level;

    public Wall(Long wallId, String internalName, WallType wallType, boolean containCompartment, List<GameSupportType> gameSupportType, Set<Long> level) {
        this.wallId = wallId;
        this.internalName = internalName;
        this.wallType = wallType;
        this.containCompartment = containCompartment;
        this.gameSupportType = new HashSet<GameSupportType>(gameSupportType);
        this.level = level;
    }

    public static Set<Long> levels(int... levels) {
        HashSet<Long> result = new HashSet<Long>();
        for (int level : levels) {
            result.add((long) level);
        }
        return result;
    }
}
