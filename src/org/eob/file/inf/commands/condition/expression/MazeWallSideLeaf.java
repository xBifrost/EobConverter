package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.enums.DirectionType;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 14:27
 */
public class MazeWallSideLeaf extends ExpressionLeaf {
    public DirectionType side;
    public int x;
    public int y;

    /**
     * Constructor of the prototype;
     */
    public MazeWallSideLeaf() {
    }

    private MazeWallSideLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 4, "byte <- maze.wallSide(side, x, y)", LeafType.WallType);
        side = DirectionType.getDirectionById(ByteArrayUtility.getByte(originalCommands, pos + 1));
        int position = ByteArrayUtility.getWord(originalCommands, pos + 2);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xE9) {
            return new MazeWallSideLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}