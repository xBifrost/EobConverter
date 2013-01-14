package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 20:22
 */
public class ItemConsumeCommand extends EobCommand {
    public final ItemConsumeType itemConsumeType;
    public final int x;
    public final int y;
    public final int itemTypeId;


    public ItemConsumeCommand(byte[] levelInfData, int pos) {
        super(0xED, pos, "Item consume");

        int value = ByteArrayUtility.getByte(levelInfData, pos + 1);
        itemConsumeType = ItemConsumeType.valueOf(value);

        switch (itemConsumeType) {
            case AtMousePointer: {
                x = -1;
                y = -1;
                itemTypeId = -1;
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 2);
                break;
            }
            case AtPositionWithAnyType:{
                int position = ByteArrayUtility.getWord(levelInfData, pos + 1);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                itemTypeId = -1;
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 4);
                break;
            }
            default:
            case AtPosition:
                int position = ByteArrayUtility.getWord(levelInfData, pos + 1);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 4);
                itemTypeId = value;
        }
    }

    public static ItemConsumeCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xED) {
            return new ItemConsumeCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}