package org.eob.enums;

/**
 * User: Bifrost
 * Date: 10/8/12
 * Time: 7:59 PM
 */
public enum WallType {   // WallType
    /**
     * Unknown type of wall
     */
    Unknown(true),
    /**
     * Wall doesn't exist
     */
    Empty(false),
    /**
     * Wall between empty spaces (squares)
     */
    Wall(false),
    /**
     * Part of solid block (square). All sides of the square must be solid.
     */
    SolidPart(true),
    /**
     * Part of the block which filled whole square. All walls of the square must be the same.
     * Some special square can contain only the one Square part (leaders, stairs,...).
     */
    SquarePart(false),
    /**
     * Square contains door.
     */
    DoorPart(false),
    /**
     * Property located on the wall.
     */
    Property(false);


    private boolean solid;

    WallType(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }
}
