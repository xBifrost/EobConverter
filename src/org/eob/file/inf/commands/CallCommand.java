package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 23:07
 */
public class CallCommand extends EobCommand {
    public final int address;

    public CallCommand(byte[] levelInfData, int pos) {
        super(0xEF, pos, "Call");

        address = ByteArrayUtility.getWord(levelInfData, pos + 1);
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 3);
    }

    public static CallCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xEF) {
            return new CallCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}