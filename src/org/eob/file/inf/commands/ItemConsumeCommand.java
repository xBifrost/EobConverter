package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;
import org.eob.model.Item;
import org.eob.model.ItemType;

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
    public final ItemType itemType;


    public ItemConsumeCommand(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        super(0xED, pos, "Item consume");

        int value = ByteArrayUtility.getByte(levelInfData, pos + 1);
        itemConsumeType = ItemConsumeType.valueOf(value);

        switch (itemConsumeType) {
            case AtMousePointer: {
                x = -1;
                y = -1;
                itemType = eobGlobalData.itemTypeDatFile.getById(0xFF);
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 2);
                break;
            }
            case AtPositionWithAnyType:{
                int position = ByteArrayUtility.getWord(levelInfData, pos + 1);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                itemType = eobGlobalData.itemTypeDatFile.getById(0xFF);
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 4);
                break;
            }
            default:
            case AtPosition:
                int position = ByteArrayUtility.getWord(levelInfData, pos + 1);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 4);
                itemType = eobGlobalData.itemTypeDatFile.getById(value);
        }
    }

    public static ItemConsumeCommand parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xED) {
            return new ItemConsumeCommand(levelInfData, pos, eobGlobalData);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}