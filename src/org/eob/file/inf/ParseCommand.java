package org.eob.file.inf;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.commands.GraphicsDataCommand;
import org.eob.file.inf.commands.SetWallCommand;
import org.eob.file.inf.commands.WallMappingCommand;
import org.eob.model.EobCommand;

/**
 * User: Bifrost
 * Date: 12/2/12
 * Time: 1:43 PM
 */
public class ParseCommand {
    public ParseCommand() {
    }

    public EobCommand parse(byte[] levelInfData, int pos) {
        int commandId = ByteArrayUtility.getByte(levelInfData, pos);
        switch (commandId) {
            case 0xFF:
                return new SetWallCommand(levelInfData, pos + 1);
            case 0xEC:
                return GraphicsDataCommand.parse(levelInfData, pos+1);
            case 0xFB:
                return WallMappingCommand.parse(levelInfData, pos + 1);
        }

        return null;
    }
}
