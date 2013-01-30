package org.eob;

import org.eob.enums.DirectionType;
import org.eob.enums.InSquarePositionType;
import org.eob.model.Couple;
import org.eob.model.Square;
import org.eob.model.Wall;

import java.util.*;

/**
 * User: Bifrost
 * Date: 25.1.2013
 * Time: 14:09
 */
public class LevelUtility {
    private static Map<String, Long> assetObjectIdMap = new HashMap<String, Long>();

    public static Long getAssetObjectId(String internalName) {
        Long id;
        if (assetObjectIdMap.containsKey(internalName)) {
            id = assetObjectIdMap.get(internalName) + 1;
        } else {
            id = 1L;
        }

        assetObjectIdMap.put(internalName, id);
        return id;
    }


    public static Square getSquareBehindWall(Square[][] level, Square square, InSquarePositionType wallPosition) {
        int height = level.length == 0 ? 0 : level[0].length;
        int width = level.length;


        if (wallPosition.equals(InSquarePositionType.North)) {
            if (square.y == 0) {
                return level[square.x][height - 1];
            } else {
                return level[square.x][square.y - 1];
            }
        }
        if (wallPosition.equals(InSquarePositionType.East)) {
            if (square.x == width - 1) {
                return level[0][square.y];
            } else {
                return level[square.x + 1][square.y];
            }
        }
        if (wallPosition.equals(InSquarePositionType.South)) {
            if (square.y == height - 1) {
                return level[square.x][0];
            } else {
                return level[square.x][square.y + 1];
            }
        }
        if (wallPosition.equals(InSquarePositionType.West)) {
            if (square.x == 0) {
                return level[width - 1][square.y];
            } else {
                return level[square.x - 1][square.y];
            }
        }
        EobLogger.println(String.format("[ERROR] Square on position [%d, %d] not contain wall at side %s.", square.x, square.y, wallPosition.name()));
        return null;
    }

    public static DirectionType findDirectionOfEmptyAdjacentSquare(Square[][] level, Square square) {
        int height = level.length == 0 ? 0 : level[0].length;
        int width = level.length;
        if (!level[square.x][square.y == 0 ? height - 1 : square.y - 1].isSolidBlock()) {
            return DirectionType.North;
        }
        if (!level[square.x == width - 1 ? 0 : square.x + 1][square.y].isSolidBlock()) {
            return DirectionType.East;
        }
        if (!level[square.x][square.y == height - 1 ? 0 : square.y + 1].isSolidBlock()) {
            return DirectionType.South;
        }
        if (!level[square.x == 0 ? width - 1 : square.x - 1][square.y].isSolidBlock()) {
            return DirectionType.West;
        }

        EobLogger.println(String.format("[ERROR] Square on position [%d, %d] haven't adjacent empty square!", square.x, square.y));
        return null;
    }

    public static Couple<String, InSquarePositionType> convertDifferentWallsToAsset(List<Couple<Wall, InSquarePositionType>> walls, EobGlobalData eobGlobalData) {
        List<String> wallNames = new ArrayList<String>();
        for (Couple<Wall, InSquarePositionType> wallWithPosition : walls) {
            wallNames.add(wallWithPosition.first.internalName);
        }
        for (Map.Entry<List<String>, String> entry : eobGlobalData.wallConversion.entrySet()) {
            Set<String> names = new HashSet<String>(entry.getKey());
            names.removeAll(wallNames);
            //
            if (names.size() == 0) {
                for (Couple<Wall, InSquarePositionType> wallWithPosition : walls) {
                    if (wallWithPosition.first.internalName.equals(entry.getKey().get(0))) {
                        return new Couple<String, InSquarePositionType>(entry.getValue(), wallWithPosition.second);
                    }
                }
                EobLogger.println("[ERROR] Unexpected error.");
            }
        }

        return null;
    }

    public static DirectionType getDirection(InSquarePositionType inSquarePositionType) {
        switch (inSquarePositionType) {
            case Unknown:
                return DirectionType.Unknown;
            case North:
                return DirectionType.North;
            case East:
                return DirectionType.East;
            case South:
                return DirectionType.South;
            case West:
                return DirectionType.West;
            case NW:
                return DirectionType.North;
            case NE:
                return DirectionType.East;
            case SW:
                return DirectionType.West;
            case SE:
                return DirectionType.South;
            case EW:
                return DirectionType.East;
            case NS:
                return DirectionType.North;
        }
        return DirectionType.North;
    }
}
