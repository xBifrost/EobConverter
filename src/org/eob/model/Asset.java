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
    public int x;
    public int y;
    public InSquarePositionType inSquarePositionType;
    public DirectionType directionType;

    public Asset(String internalName, int x, int y, InSquarePositionType inSquarePositionType, DirectionType directionType, EobGlobalData eobGlobalData) {
        this.internalName = internalName;
        this.x = x;
        this.y = y;
        this.inSquarePositionType = inSquarePositionType;
        this.directionType = directionType;
        id = LevelUtility.getAssetObjectId(eobGlobalData.assetNamesMap.get(internalName));
    }
}
