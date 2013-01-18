package org.eob.file.inf;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.commands.*;

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

    public EobCommand parseScript(int level, byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        int commandId = ByteArrayUtility.getByte(levelInfData, pos);
        switch (commandId) {
            case 0xE6:
                return EncounterCommand.parse(levelInfData, pos);
            case 0xE7:
                return IdentifyAllItemsCommand.parse(levelInfData, pos);
            case 0xE8:
                return TurnCommand.parse(levelInfData, pos);
            case 0xE9:
                return LauncherCommand.parse(levelInfData, pos, eobGlobalData);
            case 0xEA:
                return NewItemCommand.parse(levelInfData, pos, eobGlobalData);
            case 0xEB:
                return GiveXpCommand.parse(levelInfData, pos);
            case 0xEC:
                return ChangeLevelCommand.parse(levelInfData, pos);
            case 0xED:
                return ItemConsumeCommand.parse(levelInfData, pos, eobGlobalData);
            case 0xEE:
                return ConditionCommand.parse(levelInfData, pos, eobGlobalData);
            case 0xEF:
                return CallCommand.parse(levelInfData, pos);
            case 0xF0:
                return ReturnCommand.parse(levelInfData, pos);
            case 0xF1:
                return ScriptEndCommand.parse(levelInfData, pos);
            case 0xF2:
                return JumpCommand.parse(levelInfData, pos);
            case 0xF3:
                return DamageCommand.parse(levelInfData, pos);
            case 0xF5:
                return ClearFlagCommand.parse(levelInfData, pos);
            case 0xF6:
                return SoundCommand.parse(levelInfData, pos);
            case 0xF7:
                return SetFlagCommand.parse(levelInfData, pos);
            case 0xF8:
                return MessageCommand.parse(levelInfData, pos);
            case 0xF9:
                return StealSmallItemsCommand.parse(levelInfData, pos);
            case 0xFA:
                return TeleportCommand.parse(levelInfData, pos);
            case 0xFB:
                return CreateMonsterCommand.parse(levelInfData, pos, eobGlobalData);
            case 0xFC:
                return CloseDoorCommand.parse(levelInfData, pos);
            case 0xFD:
                return OpenDoorCommand.parse(levelInfData, pos);
            case 0xFE:
                return ChangeWallCommand.parse(level, levelInfData, pos, eobGlobalData);
            case 0xFF:
                return SetWallCommand.parse(level, levelInfData, pos, eobGlobalData);
        }

        return null;
    }
}
