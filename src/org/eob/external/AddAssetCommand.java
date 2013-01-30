package org.eob.external;

import org.eob.EobLogger;
import org.eob.enums.DirectionType;
import org.eob.enums.InSquarePositionType;

/**
 * User: Bifrost
 * Date: 21.1.2013
 * Time: 23:15
 */
public class AddAssetCommand extends ExternalChangeCommand {
    public final InSquarePositionType inSquarePositionType;
    public final DirectionType directionType;
    public final String internalName;

    /**
     * Prototype
     */
    AddAssetCommand() {
        super(0, -1, -1);
        inSquarePositionType = null;
        directionType = null;
        internalName = "";
    }

    public AddAssetCommand(int level, int x, int y, String internalName, InSquarePositionType inSquarePositionType, DirectionType directionType) {
        super(level, x, y);
        this.internalName = internalName;
        this.inSquarePositionType = inSquarePositionType;
        this.directionType = directionType;
    }

    @Override
    public String getCommandName() {
        return "ADDASSET";
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
                return new AddAssetCommand(level, x, y, params[4].trim(), InSquarePositionType.valueByString(params[5].trim()),
                        DirectionType.valueByString(params[6].trim()));
            } catch (NumberFormatException e) {
                EobLogger.println("Command at row " + rowId + " is not correct!");
            }
        }

        return null;
    }

    @Override
    public String getCommandAsString() {
        return String.format("addAsset(%d, %d, %d, %s, %s, %s)", level, x, y,
                internalName, inSquarePositionType.possibleNames.get(1).toUpperCase(), directionType.possibleNames.get(1).toUpperCase());
    }
}
