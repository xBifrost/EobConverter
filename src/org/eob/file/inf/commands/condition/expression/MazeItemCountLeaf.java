package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * If itemTypeId == -1 then return count of the all items.
 * <p/>
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 14:39
 */
public class MazeItemCountLeaf extends ExpressionLeaf {
    public int itemTypeId;
    public int x;
    public int y;

    /**
     * Constructor of the prototype;
     */
    public MazeItemCountLeaf() {
    }

    private MazeItemCountLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 4, "byte <- maze.itemCount(x, y, itemId)");
        itemTypeId = ByteArrayUtility.getByte(originalCommands, pos + 1);
        int position = ByteArrayUtility.getWord(originalCommands, pos + 2);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;

        if (itemTypeId == 0xFF) {
            itemTypeId = -1;
        }
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF5) {
            return new MazeItemCountLeaf(levelInfData, pos);
        }
        return null;
    }
}
