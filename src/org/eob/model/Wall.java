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

    public Wall(Long wallId, WallType wallType, boolean containCompartment, Set<Long> level, String elementType, String elementName, String properties) {
        this.wallId = wallId;
        this.wallType = wallType;
        this.containCompartment = containCompartment;
        this.gameSupportType = new HashSet<GameSupportType>(Arrays.asList(GameSupportType.Eob1, GameSupportType.Grimrock));
        this.level = level;
        this.elementType = elementType;
        this.elementName = elementName;
        this.properties = properties;
        registeredWalls.put(this, null);
    }

    public Wall(Long wallId, WallType wallType, boolean containCompartment, Set<Long> level, String elementType, String elementName) {
        this(wallId, wallType, containCompartment, level, elementType, elementName, "");
    }

    public Wall(Long wallId, WallType wallType, List<GameSupportType> gameSupportType) {
        this.wallId = wallId;
        this.wallType = wallType;
        this.containCompartment = false;
        this.gameSupportType = new HashSet<GameSupportType>(gameSupportType);
        this.level = null;
        this.elementType = "";
        this.elementName = "";
        this.properties = "";
        registeredWalls.put(this, null);
    }

    public static Wall getById(long wallId, int levelId) {
        for (Wall wall : registeredWalls.keySet()) {
            if (wall.wallId == wallId) {
                if (wall.level == null || wall.level.contains((long) levelId)) {
                    return wall;
                }
            }
        }

        Wall wall = new Wall(wallId, WallType.Unknown, false, null, "", "");
        registeredWalls.put(wall, "unknown");
        return wall;
    }

    public String name() {
        String name = registeredWalls.get(this);
        if (name == null) {
            for (Field field : Wall.class.getFields()) {
                if (field.getType().equals(Wall.class)) {
                    try {
                        registeredWalls.put((Wall) field.get(null), field.getName());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            name = registeredWalls.get(this);
        }

        return name;
    }

    public static Set<Long> levels(int... levels) {
        HashSet<Long> result = new HashSet<Long>();
        for (int level : levels) {
            result.add((long) level);
        }
        return result;
    }
}
