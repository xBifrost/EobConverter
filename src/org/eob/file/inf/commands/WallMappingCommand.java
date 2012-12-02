package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 12/2/12
 * Time: 11:12 PM
 */
public class WallMappingCommand extends EobCommand {
    int wallMappingIndex; // This is the index used by the .maz file.
    int wallType;         // Index to what backdrop wall type that is being used.
    int decorationId;     // Index to and optional overlay decoration image in the DecorationData.decorations array in the [[eob.dat|.dat]] files.
    int unknownFlags;

    private WallMappingCommand(byte[] originalCommands, int pos) {
        super(0xFB, originalCommands, pos);
    }

    public static WallMappingCommand parse(byte[] levelInfData, int pos) {
        WallMappingCommand command = new WallMappingCommand(Arrays.copyOfRange(levelInfData, pos, pos + 5), pos);
        command.wallMappingIndex = ByteArrayUtility.getByte(levelInfData, pos);
        command.wallType = ByteArrayUtility.getByte(levelInfData, pos+1);
        command.decorationId = ByteArrayUtility.getByte(levelInfData, pos+2);
        command.unknownFlags = ByteArrayUtility.getWord(levelInfData, pos+3);
        return command;
    }
}