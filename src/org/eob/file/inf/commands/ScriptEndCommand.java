package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobLogger;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 20.12.2012
 * Time: 12:33
 */
public class ScriptEndCommand extends EobCommand {
    public ScriptEndCommand(byte[] levelInfData, int pos) {
        super(0xF1, pos, "End");
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 1);
    }

    public static ScriptEndCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF1) {
            return new ScriptEndCommand(levelInfData, pos);
        }
        return null;
    }
}