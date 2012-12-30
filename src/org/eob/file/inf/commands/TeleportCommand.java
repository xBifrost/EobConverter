package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 12:47
 */
public class TeleportCommand extends EobCommand {
    public final TeleportType teleportType;
    public final int sourceX;
    public final int sourceY;
    public final int destX;
    public final int destY;

    public TeleportCommand(byte[] levelInfData, int pos) {
        super(0xFA, pos, "Change level");

        teleportType = TeleportType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        int position = ByteArrayUtility.getWord(levelInfData, pos + 2);
        sourceX = (position) & 0x1f;
        sourceY = (position >> 5) & 0x1f;
        position = ByteArrayUtility.getWord(levelInfData, pos + 4);
        destX = (position) & 0x1f;
        destY = (position >> 5) & 0x1f;
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 6);
    }

    public static TeleportCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFA) {
            return new TeleportCommand(levelInfData, pos);
        }
        return null;
    }
}