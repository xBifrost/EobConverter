package org.eob.model;

import org.eob.EobLogger;
import org.eob.enums.WallType;

import java.util.Set;

/**
 * User: Bifrost
 * Date: 10/8/12
 * Time: 8:15 PM
 */
public class Square {
    public final int x;
    public final int y;
    public final Wall north;
    public final Wall east;
    public final Wall south;
    public final Wall west;

    public Square(int x, int y, byte northId, byte eastId, byte southId, byte westId, int levelId) {
        this.x = x;
        this.y = y;
        north = getWall(northId, levelId);
        east = getWall(eastId, levelId);
        south = getWall(southId, levelId);
        west = getWall(westId, levelId);

    }

    public boolean isSolidBlock() {
        return north.wallType.isSolid() && east.wallType.isSolid() && south.wallType.isSolid() && west.wallType.isSolid();
    }

    public boolean isUnknownBlock() {
        return north.wallType.equals(WallType.Unknown) || east.wallType.equals(WallType.Unknown) ||
                south.wallType.equals(WallType.Unknown) || west.wallType.equals(WallType.Unknown);
    }

    public Wall getDoor(Set<Wall> usedWalls, boolean debug) {
        if (east.wallType.equals(WallType.DoorPart) && west.wallType.equals(WallType.DoorPart)) {
            usedWalls.add(east);
            usedWalls.add(west);
            if (!east.equals(west) && debug) {
                EobLogger.println("Door haven't the same wall type on both sides! [x:" + x + ", y:" + y + ", E:" + east.name() + ", W:" + west.name() + "] ");
            }
            return east;
        }
        if (north.wallType.equals(WallType.DoorPart) && south.wallType.equals(WallType.DoorPart)) {
            usedWalls.add(north);
            usedWalls.add(south);
            if (!north.equals(south) && debug) {
                EobLogger.println("Door haven't the same wall type on both sides! [x:" + x + ", y:" + y + ", N:" + north.name() + ", S:" + south.name() + "] ");
            }
            return north;
        }
        return null;
    }

    public Wall getInSquare(Set<Wall> usedWalls) {
        if (north.equals(east) && north.equals(south) && north.equals(west) && north.wallType.equals(WallType.SquarePart)) {
            usedWalls.add(north);
            usedWalls.add(east);
            usedWalls.add(south);
            usedWalls.add(west);
            return north;
        }
        return null;
    }

    public int getDoorFacing() {
        if (east.wallType.equals(WallType.DoorPart) && west.wallType.equals(WallType.DoorPart)) {
            return 1;
        }
        return 0;
    }

    private Wall getWall(byte b, int levelId) {
        int wallId = b & 0x000000FF;
        return Wall.getById(wallId, levelId);
    }

    public void checkWalls() {
        if (north.wallType.equals(WallType.Unknown)) {
            EobLogger.println("Unknown type of north wall id: " + north.wallId + " [" + x + "," + y + "]");
        }
        if (east.wallType.equals(WallType.Unknown)) {
            EobLogger.println("Unknown type of east wall id: " + east.wallId + " [" + x + "," + y + "]");
        }
        if (south.wallType.equals(WallType.Unknown)) {
            EobLogger.println("Unknown type of south wall id: " + south.wallId + " [" + x + "," + y + "]");
        }
        if (west.wallType.equals(WallType.Unknown)) {
            EobLogger.println("Unknown type of west wall id: " + west.wallId + " [" + x + "," + y + "]");
        }
    }
}
