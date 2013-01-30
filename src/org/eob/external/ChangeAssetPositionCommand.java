package org.eob.external;

import org.eob.EobLogger;
import org.eob.enums.DirectionType;

import java.util.Set;

/**
 * User: Bifrost
 * Date: 29.1.2013
 * Time: 23:15
 */
public class ChangeAssetPositionCommand extends ExternalChangeCommand implements AssetCommand {
    public final String assetName;
    public final int newX;
    public final int newY;
    public Set<String> internalNames = null;
    public Long assetId = null;

    /**
     * Prototype
     */
    ChangeAssetPositionCommand() {
        super(0, -1, -1);
        assetName = "";
        newX = -1;
        newY = -1;
    }

    public ChangeAssetPositionCommand(int level, int x, int y, String assetName, int newX, int newY) {
        super(level, x, y);
        this.assetName = assetName;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public String getCommandName() {
        return "CHANGEPOSITION";
    }

    public ExternalChangeCommand parse(int rowId, String commandStr) {
        if (commandStr.toUpperCase().startsWith(getCommandName())) {
            String[] params = commandStr.split("[,()]");
            if (params.length != 7) {
                EobLogger.println("Command at row " + rowId + " is not correct!");
                return null;
            }
            try {
                int level = Integer.parseInt(params[1].trim());
                int x = Integer.parseInt(params[2].trim());
                int y = Integer.parseInt(params[3].trim());
                int newX = Integer.parseInt(params[5].trim());
                int newY = Integer.parseInt(params[6].trim());
                return new ChangeAssetPositionCommand(level, x, y, params[4].trim(), newX, newY);
            } catch (NumberFormatException e) {
                EobLogger.println("Command at row " + rowId + " is not correct!");
            }
        }

        return null;
    }

    @Override
    public String getCommandAsString() {
        return String.format("changePosition(%d, %d, %d, %s, %d, %d)", level, x, y, assetName, newX, newY);
    }

    @Override
    public String getOriginalName() {
        return assetName;
    }

    @Override
    public Set<String> getInternalNames() {
        return internalNames;
    }

    @Override
    public void setInternalNames(Set<String> internalNames) {
        this.internalNames = internalNames;
    }

    @Override
    public Long getAssetId() {
        return assetId;
    }

    @Override
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }
}