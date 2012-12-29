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
    public final int wallMappingIndex; // This is the index used by the .maz file.
    public final int wallType;         // Index to what backdrop wall type that is being used.
    public final int decorationId;     // Index to and optional overlay decoration image in the DecorationData.decorations array in the [[eob.dat|.dat]] files.
    public final int unknownFlags;

    private WallMappingCommand(byte[] levelInfData, int pos) {
        super(0xFB, pos, "Wall mapping");
        wallMappingIndex = ByteArrayUtility.getByte(levelInfData, pos+1);
        wallType = ByteArrayUtility.getByte(levelInfData, pos+2);
        decorationId = ByteArrayUtility.getByte(levelInfData, pos+3);
        unknownFlags = ByteArrayUtility.getWord(levelInfData, pos+4);
        originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 6);
    }

    public static WallMappingCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFB) {
            return new WallMappingCommand(levelInfData, pos);
        }
        return null;
    }
}