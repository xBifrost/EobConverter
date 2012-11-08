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

    private static final Map<Wall, String> registeredWalls = new LinkedHashMap<Wall, String>();

    private Wall(Long wallId, String internalName, WallType wallType, boolean containCompartment, List<GameSupportType> gameSupportType, Set<Long> level) {
        this.wallId = wallId;
        this.internalName = internalName;
        this.wallType = wallType;
        this.containCompartment = containCompartment;
        this.gameSupportType = new HashSet<GameSupportType>(gameSupportType);
        this.level = level;
    }

    public static Wall createWall(String internalName, Long wallId, WallType wallType, boolean containCompartment, Set<Long> level) {
        Wall key = new Wall(wallId, internalName, wallType, containCompartment, Arrays.asList(GameSupportType.values()), level);
        registeredWalls.put(key, internalName);
        return key;
    }

    public static Wall createWall(String internalName, Long wallId, WallType wallType, List<GameSupportType> gameSupportType) {
        Wall key = new Wall(wallId, internalName, wallType, false, gameSupportType, null);
        registeredWalls.put(key, internalName);
        return key;
    }

    public static Wall getById(long wallId, int levelId) {
        for (Wall wall : registeredWalls.keySet()) {
            if (wall.wallId == wallId) {
                if (wall.level == null || wall.level.contains((long) levelId)) {
                    return wall;
                }
            }
        }

        return createWall("unknown", wallId, WallType.Unknown, false, null);
    }

    public void registerNames() {
        for (Field field : Wall.class.getFields()) {
            if (field.getType().equals(Wall.class)) {
                try {
                    registeredWalls.put((Wall) field.get(null), field.getName());
                } catch (Exception e) {
                    EobLogger.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public String name() {
        return registeredWalls.get(this);
    }

    public static Set<Long> levels(int... levels) {
        HashSet<Long> result = new HashSet<Long>();
        for (int level : levels) {
            result.add((long) level);
        }
        return result;
    }
}
