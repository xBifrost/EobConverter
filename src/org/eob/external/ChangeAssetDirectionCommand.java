package org.eob.external;

import org.eob.EobLogger;
import org.eob.enums.DirectionType;

import java.util.Set;

/**
 * User: Bifrost
 * Date: 21.1.2013
 * Time: 23:15
 */
public class ChangeAssetDirectionCommand extends ExternalChangeCommand implements AssetCommand {
    public final String assetName;
    public final DirectionType newDirectionType;
    public Set<String> internalNames = null;
    public Long assetId = null;

    /**
     * Prototype
     */
    ChangeAssetDirectionCommand() {
        super(0, -1, -1);
        assetName = "";
        newDirectionType = null;
    }

    public ChangeAssetDirectionCommand(int level, int x, int y, String assetName, DirectionType newDirectionType) {
        super(level, x, y);
        this.assetName = assetName;
        this.newDirectionType = newDirectionType;
    }

    @Override
    public String getCommandName() {
        return "CHANGEDIRECTION";
    }

    public ExternalChangeCommand parse(int rowId, String commandStr) {
        if (commandStr.toUpperCase().startsWith(getCommandName())) {
            String[] params = commandStr.split("[,()]");
            if (params.length != 6) {
                EobLogger.println("Command at row " + rowId + " is not correct!");
                return null;
            }
            try {
                int level = Integer.parseInt(params[1].trim());
                int x = Integer.parseInt(params[2].trim());
                int y = Integer.parseInt(params[3].trim());
                return new ChangeAssetDirectionCommand(level, x, y, params[4].trim(), DirectionType.valueByString(params[5].trim()));
            } catch (NumberFormatException e) {
                EobLogger.println("Command at row " + rowId + " is not correct!");
            }
        }

        return null;
    }

    @Override
    public String getCommandAsString() {
        return String.format("changeDirection(%d, %d, %d, %s, %s)", level, x, y, assetName, newDirectionType.possibleNames.get(1).toUpperCase());
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