package org.eob.model;

import org.eob.EobGlobalData;
import org.eob.LevelUtility;
import org.eob.enums.DirectionType;
import org.eob.enums.InSquarePositionType;

/**
 * User: Bifrost
 * Date: 25.1.2013
 * Time: 17:45
 */
public class Asset {
    public final Long id;
    public final String internalName;
    public final boolean containCompartment;
    public int x;
    public int y;
    public int originalX;
    public int originalY;
    public InSquarePositionType inSquarePositionType;
    public DirectionType directionType;

    public Asset(String internalName, int x, int y, InSquarePositionType inSquarePositionType, DirectionType directionType, boolean containCompartment, EobGlobalData eobGlobalData) {
        this.internalName = internalName;
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;
        this.inSquarePositionType = inSquarePositionType;
        this.directionType = directionType;
        this.containCompartment = containCompartment;
        id = LevelUtility.getAssetObjectId(eobGlobalData.assetNamesMap.get(internalName));
    }

    public Asset(String internalName, int x, int y, int originalX, int originalY, InSquarePositionType inSquarePositionType, DirectionType directionType, boolean containCompartment, EobGlobalData eobGlobalData) {
        this.internalName = internalName;
        this.x = x;
        this.y = y;
        this.originalX = originalX;
        this.originalY = originalY;
        this.inSquarePositionType = inSquarePositionType;
        this.directionType = directionType;
        this.containCompartment = containCompartment;
        id = LevelUtility.getAssetObjectId(eobGlobalData.assetNamesMap.get(internalName));
    }
}
