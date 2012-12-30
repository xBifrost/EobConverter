package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 19:31
 */
public class CloseDoorCommand extends EobCommand {
    public final int x;
    public final int y;

    public CloseDoorCommand(byte[] levelInfData, int pos) {
        super(0xFC, pos, "Close door");

        int position = ByteArrayUtility.getWord(levelInfData, pos + 1);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 3);
    }

    public static CloseDoorCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFC) {
            return new CloseDoorCommand(levelInfData, pos);
        }
        return null;
    }
}