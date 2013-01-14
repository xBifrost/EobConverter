package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.enums.InSquarePositionType;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 31.12.2012
 * Time: 13:26
 */
public class StealSmallItemsCommand extends EobCommand {
    public final int whom;
    public final int x;
    public final int y;
    public final InSquarePositionType inSquarePositionType;

    public StealSmallItemsCommand(byte[] levelInfData, int pos) {
        super(0xF9, pos, "Steal small items");

        int whomValue = ByteArrayUtility.getByte(levelInfData, pos + 1);
        whom = whomValue == 0xFF ? -1 : whomValue; // 0xFF -> to all
        int position = ByteArrayUtility.getWord(levelInfData, pos + 2);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
        inSquarePositionType = InSquarePositionType.getItemPositionById(ByteArrayUtility.getByte(levelInfData, pos + 4));

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 5);
    }

    public static StealSmallItemsCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF9) {
            return new StealSmallItemsCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}