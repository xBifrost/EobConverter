package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 16:59
 */
public class PartyPositionCheckLeaf extends ExpressionLeaf {
    public int x;
    public int y;

    /**
     * Constructor of the prototype;
     */
    public PartyPositionCheckLeaf() {
    }

    private PartyPositionCheckLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 3, "boolean <- party.atPosition(x, y)", LeafType.Boolean);
        int position = ByteArrayUtility.getWord(originalCommands, pos + 1);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF1 && ByteArrayUtility.getWord(levelInfData, pos) != 0xF5F1) {
            return new PartyPositionCheckLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}