package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 14:18
 */
public class MazeWallNumberLeaf extends ExpressionLeaf {
    public int x;
    public int y;

    /**
     * Constructor of the prototype;
     */
    public MazeWallNumberLeaf() {
    }

    private MazeWallNumberLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 3, "byte <- maze.wallNumber(x, y)");
        int position = ByteArrayUtility.getWord(originalCommands, pos + 1);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF7) {
            return new MazeWallNumberLeaf(levelInfData, pos);
        }
        return null;
    }
}
