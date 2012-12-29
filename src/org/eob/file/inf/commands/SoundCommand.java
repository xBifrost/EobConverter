package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 28.12.2012
 * Time: 11:12 PM
 */
public class SoundCommand extends EobCommand {
    public final int soundId;
    public final int soundPosition;

    private SoundCommand(byte[] levelInfData, int pos) {
        super(0xF6, pos, "Sound...");
        soundId = ByteArrayUtility.getByte(levelInfData, pos + 1);
        soundPosition = ByteArrayUtility.getWord(levelInfData, pos + 2);
        originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 4);
    }

    public static SoundCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF6) {
            return new SoundCommand(levelInfData, pos);
        }
        return null;
    }
}