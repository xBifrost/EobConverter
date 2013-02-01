package org.eob.model;

import org.eob.EobGlobalData;
import org.eob.EobLogger;
import org.eob.LevelUtility;
import org.eob.Settings;
import org.eob.enums.DirectionType;
import org.eob.enums.GameSupportType;
import org.eob.enums.InSquarePositionType;
import org.eob.enums.WallType;
import org.eob.external.*;

import java.util.*;

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

    public final Map<InSquarePositionType, Wall> originalWalls = new HashMap<InSquarePositionType, Wall>();
    public final Set<Asset> assets = new HashSet<Asset>();

    public Square(int x, int y, byte northId, byte eastId, byte southId, byte westId, int levelId, EobGlobalData eobGlobalData) {
        this.x = x;
        this.y = y;
        north = getWall(northId, levelId, eobGlobalData);
        east = getWall(eastId, levelId, eobGlobalData);
        south = getWall(southId, levelId, eobGlobalData);
        west = getWall(westId, levelId, eobGlobalData);

        originalWalls.put(InSquarePositionType.North, getWall(northId, levelId, eobGlobalData));
        originalWalls.put(InSquarePositionType.East, getWall(eastId, levelId, eobGlobalData));
        originalWalls.put(InSquarePositionType.South, getWall(southId, levelId, eobGlobalData));
        originalWalls.put(InSquarePositionType.West, getWall(westId, levelId, eobGlobalData));
    }

    public void createAssets(Square[][] level, EobGlobalData eobGlobalData, Settings settings) {
        Set<Wall> usedWalls = new HashSet<Wall>();
        Wall north = originalWalls.get(InSquarePositionType.North);
        Wall east = originalWalls.get(InSquarePositionType.East);
        Wall south = originalWalls.get(InSquarePositionType.South);
        Wall west = originalWalls.get(InSquarePositionType.West);

        // Check doors
        if (east.wallType.equals(WallType.DoorPart) && west.wallType.equals(WallType.DoorPart)) {
            usedWalls.add(east);
            usedWalls.add(west);
            if (east.equals(west)) {
                assets.add(new Asset(east.internalName, x, y, InSquarePositionType.EW, DirectionType.East, east.containCompartment, eobGlobalData));
            } else {
                @SuppressWarnings("unchecked")
                final List<Couple<Wall, InSquarePositionType>> walls = Arrays.asList(
                        new Couple<Wall, InSquarePositionType>(east, InSquarePositionType.East),
                        new Couple<Wall, InSquarePositionType>(west, InSquarePositionType.West));
                Couple<String, InSquarePositionType> assetNameDir = LevelUtility.convertDifferentWallsToAsset(walls, eobGlobalData);
                if (assetNameDir == null) {
                    if (settings.debugWalls) {
                        EobLogger.println("Door haven't conversion of different wall types! [x:" + x + ", y:" + y + ", E:" + east.internalName + ", W:" + west.internalName + "] ");
                    }
                    assets.add(new Asset(east.internalName, x, y, InSquarePositionType.EW, DirectionType.East, east.containCompartment, eobGlobalData));
                } else {
                    assets.add(new Asset(assetNameDir.first, x, y, InSquarePositionType.EW, LevelUtility.getDirection(assetNameDir.second), east.containCompartment, eobGlobalData));
                }
            }

        } else if (north.wallType.equals(WallType.DoorPart) && south.wallType.equals(WallType.DoorPart)) {
            usedWalls.add(north);
            usedWalls.add(south);

            usedWalls.add(north);
            usedWalls.add(south);
            if (north.equals(south)) {
                assets.add(new Asset(north.internalName, x, y, InSquarePositionType.EW, DirectionType.North, north.containCompartment, eobGlobalData));
            } else {
                @SuppressWarnings("unchecked")
                final List<Couple<Wall, InSquarePositionType>> walls = Arrays.asList(
                        new Couple<Wall, InSquarePositionType>(north, InSquarePositionType.North),
                        new Couple<Wall, InSquarePositionType>(south, InSquarePositionType.South));
                Couple<String, InSquarePositionType> assetNameDir = LevelUtility.convertDifferentWallsToAsset(walls, eobGlobalData);
                if (assetNameDir == null) {
                    if (settings.debugWalls) {
                        EobLogger.println("Door haven't conversion of different wall types! [x:" + x + ", y:" + y + ", N:" + north.internalName + ", S:" + south.internalName + "] ");
                    }
                    assets.add(new Asset(north.internalName, x, y, InSquarePositionType.EW, DirectionType.North, north.containCompartment, eobGlobalData));
                } else {
                    assets.add(new Asset(assetNameDir.first, x, y, InSquarePositionType.EW, LevelUtility.getDirection(assetNameDir.second), north.containCompartment, eobGlobalData));
                }
            }
        }

        // In Square
        Wall inSquare = getInSquare(usedWalls);
        if (inSquare != null) {
            assets.add(new Asset(inSquare.internalName, x, y, InSquarePositionType.Center,
                    LevelUtility.findDirectionOfEmptyAdjacentSquare(level, this).turnBack(), inSquare.containCompartment, eobGlobalData));
        }

        // Unused walls -> Asset
        for (Map.Entry<InSquarePositionType, Wall> wallEntry : originalWalls.entrySet()) {
            Wall wall = wallEntry.getValue();
            if (!wall.gameSupportType.contains(GameSupportType.Assets) || usedWalls.contains(wall)) {
                continue;
            }
            Square adjacentSquare = LevelUtility.getSquareBehindWall(level, this, wallEntry.getKey());
            if (wall.wallType.equals(WallType.Wall)) {
                // Discard wall where one side of the wall is solid block
                if (adjacentSquare.isSolidBlock()) {
                    continue;
                }
                Wall adjacentWall = adjacentSquare.originalWalls.get(wallEntry.getKey().oppositePosition());
                // Discard one of the same adjacent walls
                if (wall.equals(adjacentWall) && (wallEntry.getKey().equals(InSquarePositionType.South) || wallEntry.getKey().equals(InSquarePositionType.West))) {
                    continue;
                }
            }
            if (wall.wallType.equals(WallType.SquarePart)) {
                assets.add(new Asset(wall.internalName, x, y, wallEntry.getKey(), LevelUtility.getDirection(wallEntry.getKey()).turnBack(), wall.containCompartment,eobGlobalData));
            } else {
                adjacentSquare.assets.add(new Asset(wall.internalName, adjacentSquare.x, adjacentSquare.y, x, y,
                        wallEntry.getKey().oppositePosition(), LevelUtility.getDirection(wallEntry.getKey()).turnBack(), wall.containCompartment, eobGlobalData));
            }
        }
    }

    public void externalAssetChange(int levelId, Square[][] level, EobGlobalData eobGlobalData, Settings settings) {
        for (ExternalChangeCommand changeCommand : eobGlobalData.externalChangeCommands) {
            Set<Asset> assets = level[changeCommand.x][changeCommand.y].assets;
            if (changeCommand.level != levelId || changeCommand.x != x || changeCommand.y != y) {
                continue;
            }

            boolean unusedCommand = true;
            if (changeCommand instanceof ChangeAssetDirectionCommand) {
                ChangeAssetDirectionCommand changeDirectionCommand = (ChangeAssetDirectionCommand) changeCommand;

                for (Asset asset : assets) {
                    if (changeDirectionCommand.internalNames.contains(asset.internalName) &&
                            (changeDirectionCommand.assetId == -1 || asset.id.equals(changeDirectionCommand.assetId))) {
                        if (asset.directionType.equals(changeDirectionCommand.newDirectionType)) {
                            EobLogger.println(String.format("[Warning] direction of asset %s_%s at position [%d, %d] was not changed.",
                                    eobGlobalData.assetNamesMap.get(asset.internalName), asset.id == -1 ? "?" : asset.id.toString(),
                                    asset.x, asset.y));
                        }
                        asset.directionType = changeDirectionCommand.newDirectionType;
                        unusedCommand = false;
                    }
                }

            } else if (changeCommand instanceof ChangeAssetPositionCommand) {
                ChangeAssetPositionCommand changePositionCommand = (ChangeAssetPositionCommand) changeCommand;

                Set<Asset> toMove = new LinkedHashSet<Asset>();
                for (Asset asset : assets) {
                    if (changePositionCommand.internalNames.contains(asset.internalName) &&
                            (changePositionCommand.assetId == -1 || asset.id.equals(changePositionCommand.assetId))) {
                        asset.x = changePositionCommand.newX;
                        asset.y = changePositionCommand.newY;
                        toMove.add(asset);
                        unusedCommand = false;
                    }
                }

                if (toMove.size() == 0) {
                    EobLogger.println(String.format("[Warning] Any asset with name %s at position [%s, %s] was not found!",
                            eobGlobalData.assetNamesMap.get(changePositionCommand.assetName),
                            changePositionCommand.x, changePositionCommand.y));
                } else {
                    assets.removeAll(toMove);
                    level[changePositionCommand.newX][changePositionCommand.newY].assets.addAll(toMove);
                }

            } else if (changeCommand instanceof RemoveAssetCommand) {
                RemoveAssetCommand removeAssetCommand = (RemoveAssetCommand) changeCommand;

                Set<Asset> toRemove = new HashSet<Asset>();
                for (Asset asset : assets) {
                    if (removeAssetCommand.internalNames.contains(asset.internalName) &&
                            (removeAssetCommand.assetId == -1 || asset.id.equals(removeAssetCommand.assetId))) {
                        toRemove.add(asset);
                        unusedCommand = false;
                    }
                }
                assets.removeAll(toRemove);

            } else if (changeCommand instanceof AddAssetCommand) {
                AddAssetCommand addAssetCommand = (AddAssetCommand) changeCommand;
                assets.add(new Asset(addAssetCommand.internalName, addAssetCommand.x, addAssetCommand.y,
                        addAssetCommand.inSquarePositionType, addAssetCommand.directionType, false, eobGlobalData));
                unusedCommand = false;

            } else {
                EobLogger.println("[ERROR] unsupported external change command: " + changeCommand.getClass().getSimpleName());
            }
            if (unusedCommand) {
                EobLogger.println(String.format("[WARNING] External change %s was not applied.", changeCommand.getCommandAsString()));
            }
        }
    }

    public boolean isSolidBlock() {
        return originalWalls.get(InSquarePositionType.North).wallType.isSolid() &&
                originalWalls.get(InSquarePositionType.East).wallType.isSolid() &&
                originalWalls.get(InSquarePositionType.South).wallType.isSolid() &&
                originalWalls.get(InSquarePositionType.West).wallType.isSolid();
    }

    public boolean isUnknownBlock() {
        return originalWalls.get(InSquarePositionType.North).wallType.equals(WallType.Unknown) ||
                originalWalls.get(InSquarePositionType.East).wallType.equals(WallType.Unknown) ||
                originalWalls.get(InSquarePositionType.South).wallType.equals(WallType.Unknown) ||
                originalWalls.get(InSquarePositionType.West).wallType.equals(WallType.Unknown);
    }

    @Deprecated
    public Wall getDoor(Set<Wall> usedWalls, boolean debug) {
        if (east.wallType.equals(WallType.DoorPart) && west.wallType.equals(WallType.DoorPart)) {
            usedWalls.add(east);
            usedWalls.add(west);
            if (!east.equals(west) && debug) {
                EobLogger.println("Door haven't the same wall type on both sides! [x:" + x + ", y:" + y + ", E:" + east.internalName + ", W:" + west.internalName + "] ");
            }
            return east;
        }
        if (north.wallType.equals(WallType.DoorPart) && south.wallType.equals(WallType.DoorPart)) {
            usedWalls.add(north);
            usedWalls.add(south);
            if (!north.equals(south) && debug) {
                EobLogger.println("Door haven't the same wall type on both sides! [x:" + x + ", y:" + y + ", N:" + north.internalName + ", S:" + south.internalName + "] ");
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

    private Wall getWall(byte b, int levelId, EobGlobalData eobGlobalData) {
        int wallId = b & 0x000000FF;
        return eobGlobalData.getWallById(wallId, levelId);
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
