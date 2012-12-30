package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobLogger;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * - negative jump is not correct
 * User: Bifrost
 * Date: 20.12.2012
 * Time: 12:33
 */
public class JumpCommand extends EobCommand {
    public final int address;

    public JumpCommand(byte[] levelInfData, int pos) {
        super(0xF2, pos, "Jump");

        address = ByteArrayUtility.getWord(levelInfData, pos + 1);
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 3);

        if (pos > address) {
            EobLogger.println("[Error] Jump command is negative! Command position: " + pos);
        }
    }

    public static JumpCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF2) {
            return new JumpCommand(levelInfData, pos);
        }
        return null;
    }
}