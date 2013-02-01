package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 14:18
 */
public class MazeWallTypeLeaf extends ExpressionLeaf {
    public int x;
    public int y;

    /**
     * Constructor of the prototype;
     */
    public MazeWallTypeLeaf() {
    }

    private MazeWallTypeLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 3, "wallType(byte) <- maze.wallType(x, y)", LeafType.WallType);
        int position = ByteArrayUtility.getWord(originalCommands, pos + 1);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF7) {
            return new MazeWallTypeLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}