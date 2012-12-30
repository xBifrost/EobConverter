package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 19:31
 */
public class OpenDoorCommand extends EobCommand {
    public final int x;
    public final int y;

    public OpenDoorCommand(byte[] levelInfData, int pos) {
        super(0xFD, pos, "Open door");

        int position = ByteArrayUtility.getWord(levelInfData, pos + 1);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 3);
    }

    public static OpenDoorCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFD) {
            return new OpenDoorCommand(levelInfData, pos);
        }
        return null;
    }
}