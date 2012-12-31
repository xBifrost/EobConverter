package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 20.12.2012
 * Time: 12:33
 */
public class ReturnCommand extends EobCommand {
    public ReturnCommand(byte[] levelInfData, int pos) {
        super(0xF0, pos, "Return");
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 1);
    }

    public static ReturnCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF0) {
            return new ReturnCommand(levelInfData, pos);
        }
        return null;
    }
}