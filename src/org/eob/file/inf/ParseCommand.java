package org.eob.file.inf;

import org.eob.ByteArrayUtility;
import org.eob.EobLogger;
import org.eob.file.inf.commands.*;
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
            case 0xEC:
                return GraphicsDataCommand.parse(levelInfData, pos);
            case 0xFB:
                return WallMappingCommand.parse(levelInfData, pos);
        }

        return null;
    }

    public EobCommand parseScript(byte[] levelInfData, int pos) {
        int commandId = ByteArrayUtility.getByte(levelInfData, pos);
        switch (commandId) {
            case 0xE6:
                return EncounterCommand.parse(levelInfData, pos);
            case 0xEB:
                return GiveXpCommand.parse(levelInfData, pos);
            case 0xEC:
                return ChangeLevelCommand.parse(levelInfData, pos);
            case 0xEE:
                return ConditionCommand.parse(levelInfData, pos);
            case 0xF1:
                return ScriptEndCommand.parse(levelInfData, pos);
            case 0xF2:
                return JumpCommand.parse(levelInfData, pos);
            case 0xF6:
                return SoundCommand.parse(levelInfData, pos);
            case 0xF7:
                return SetFlagCommand.parse(levelInfData, pos);
            case 0xF8:
                return MessageCommand.parse(levelInfData, pos);
            case 0xFA:
                return new TeleportCommand(levelInfData, pos);
            case 0xFE:
                return new ChangeWallCommand(levelInfData, pos);
            case 0xFF:
                return new SetWallCommand(levelInfData, pos);
        }

        return null;
    }
}
