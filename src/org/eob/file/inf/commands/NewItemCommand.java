package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.enums.InSquarePositionType;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;
import org.eob.model.ItemObject;
import org.eob.model.ItemType;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 19:45
 */
public class NewItemCommand extends EobCommand {
    public final ItemObject itemObject;
    public final int x;
    public final int y;
    public final InSquarePositionType inSquarePosition;


    public NewItemCommand(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        super(0xEA, pos, "New item");

        itemObject = eobGlobalData.itemParser.getItemByIndex(ByteArrayUtility.getWord(levelInfData, pos + 1));
        int position = ByteArrayUtility.getWord(levelInfData, pos + 3);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
        inSquarePosition = InSquarePositionType.getItemPositionById(ByteArrayUtility.getByte(levelInfData, pos + 5));
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 6);
    }

    public static NewItemCommand parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xEA) {
            return new NewItemCommand(levelInfData, pos, eobGlobalData);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}