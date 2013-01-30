package org.eob.external;

import org.eob.EobLogger;
import org.eob.enums.InSquarePositionType;

import java.util.Set;

/**
 * User: Bifrost
 * Date: 21.1.2013
 * Time: 23:15
 */
public class RemoveAssetCommand extends ExternalChangeCommand implements AssetCommand {
    public final String assetName;
    public Set<String> internalNames = null;
    public Long assetId = null;

    /**
     * Prototype
     */
    RemoveAssetCommand() {
        super(0, -1, -1);
        assetName = "";
    }

    public RemoveAssetCommand(int level, int x, int y, String assetName) {
        super(level, x, y);
        this.assetName = assetName;
    }

    @Override
    public String getCommandName() {
        return "REMOVEASSET";
    }

    public ExternalChangeCommand parse(int rowId, String commandStr) {
        if (commandStr.toUpperCase().startsWith(getCommandName())) {
            String[] params = commandStr.split("[,()]");
            if (params.length != 5) {
                EobLogger.println("Command at row " + rowId + " is not correct!");
                return null;
            }
            try {
                final int level = Integer.parseInt(params[1].trim());
                final int x = Integer.parseInt(params[2].trim());
                final int y = Integer.parseInt(params[3].trim());
                return new RemoveAssetCommand(level, x, y, params[4].trim());
            } catch (NumberFormatException e) {
                EobLogger.println("Command at row " + rowId + " is not correct!");
            }
        }

        return null;
    }

    @Override
    public String getCommandAsString() {
        return String.format("removeAsset(%d, %d, %d, %s)", level, x, y, assetName);
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
