package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 31.12.2012
 * Time: 00:38
 */
public class ClearFlagCommand extends EobCommand {
    public final FlagType flagType;
    public final int flag;
    public final int monsterId;

    private ClearFlagCommand(byte[] levelInfData, int pos) {
        super(0xF5, pos);
        flagType = FlagType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        switch (flagType) {
            case Maze: {
                description = "Maze.flag &= byte";
                monsterId = -1;
                flag = ByteArrayUtility.getByte(levelInfData, pos + 2);
                originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 3);
                break;
            }
            case Global: {
                description = "Global.flag = byte";
                monsterId = -1;
                flag = ByteArrayUtility.getByte(levelInfData, pos + 2);
                originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 3);
                break;
            }
            case Monster: {
                description = "Monster.id = byte, Monster.flag = byte";
                monsterId = ByteArrayUtility.getByte(levelInfData, pos + 2);
                flag = ByteArrayUtility.getByte(levelInfData, pos + 3);
                originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 4);
                break;
            }
            case Event: {
                description = "Event (clear flag)";
                monsterId = -1;
                flag = -1;
                originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 2);
                break;
            }
            case Party: {
                description = "Party(FUNC_SETVAL, PARTY_SAVEREST, 0) (clear flag)";
                monsterId = -1;
                flag = -1;
                originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 2);
                break;
            }
            default:
                description = "Unsupported flag type";
                monsterId = -1;
                flag = -1;
        }
    }

    public static ClearFlagCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF5) {
            return new ClearFlagCommand(levelInfData, pos);
        }
        return null;
    }
}