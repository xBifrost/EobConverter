package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;
import org.eob.model.ItemType;

/**
 * If itemType == -1 then return count of the all items.
 * <p/>
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 14:39
 */
public class MazeItemCountLeaf extends ExpressionLeaf {
    public ItemType itemType;
    public int x;
    public int y;

    /**
     * Constructor of the prototype;
     */
    public MazeItemCountLeaf() {
    }

    private MazeItemCountLeaf(byte[] originalCommands, int pos, EobGlobalData eobGlobalData) {
        super(originalCommands, pos, 4, "byte <- maze.itemCount(x, y, itemId)");
        int itemTypeId = ByteArrayUtility.getByte(originalCommands, pos + 1);
        itemType = eobGlobalData.itemTypeDatFile.getById(itemTypeId);
        int position = ByteArrayUtility.getWord(originalCommands, pos + 2);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF5) {
            return new MazeItemCountLeaf(levelInfData, pos, eobGlobalData);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}