package org.eob.model;

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
    public final Set<GameSupportType> gameSupportType;
    public final WallType wallType;
    public final Boolean containCompartment;
    public final Set<Long> level;
    public final String elementType;
    public final String elementName;
    public final String properties;

    private static final Map<Wall, String> registeredWalls = new LinkedHashMap<Wall, String>();

    private Wall(Long wallId, WallType wallType, boolean containCompartment, List<GameSupportType> gameSupportType, Set<Long> level, String elementType, String elementName, String properties) {
        this.wallId = wallId;
        this.wallType = wallType;
        this.containCompartment = containCompartment;
        this.gameSupportType = new HashSet<GameSupportType>(gameSupportType);
        this.level = level;
        this.elementType = elementType;
        this.elementName = elementName;
        this.properties = properties;
    }

    public static Wall createWall(String internalName, Long wallId, WallType wallType, boolean containCompartment, Set<Long> level, String elementType, String elementName, String properties) {
        Wall key = new Wall(wallId, wallType, containCompartment, Arrays.asList(GameSupportType.values()), level, elementType, elementName, properties);
        registeredWalls.put(key, internalName);
        return key;
    }

    public static Wall createWall(String internalName, Long wallId, WallType wallType, boolean containCompartment, Set<Long> level, String elementType, String elementName) {
        Wall key = new Wall(wallId, wallType, containCompartment, Arrays.asList(GameSupportType.values()), level, elementType, elementName, "");
        registeredWalls.put(key, internalName);
        return key;
    }

    public static Wall createWall(String internalName, Long wallId, WallType wallType, List<GameSupportType> gameSupportType) {
        Wall key = new Wall(wallId, wallType, false, gameSupportType, null, "", "", "");
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

        return createWall("unknown", wallId, WallType.Unknown, false, null, "", "");
    }

    public void registerNames() {
        for (Field field : Wall.class.getFields()) {
            if (field.getType().equals(Wall.class)) {
                try {
                    registeredWalls.put((Wall) field.get(null), field.getName());
                } catch (Exception e) {
                    System.out.println(e);
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
