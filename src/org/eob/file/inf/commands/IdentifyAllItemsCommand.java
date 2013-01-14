package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 31.12.2012
 * Time: 13:19
 */
public class IdentifyAllItemsCommand extends EobCommand {
    public final int x;
    public final int y;

    public IdentifyAllItemsCommand(byte[] levelInfData, int pos) {
        super(0xE7, pos, "Identify all items");

        int position = ByteArrayUtility.getWord(levelInfData, pos + 1);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 3);
    }

    public static IdentifyAllItemsCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xE7) {
            return new IdentifyAllItemsCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}