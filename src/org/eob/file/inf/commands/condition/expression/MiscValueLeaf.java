package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class MiscValueLeaf extends ExpressionLeaf {
    public Integer parentValue = null;
    public int value;
    /**
     * Constructor of the prototype;
     */
    public MiscValueLeaf() {
    }

    protected MiscValueLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 1, "byte <- value", LeafType.UndefinedYet);
        value = ByteArrayUtility.getByte(originalCommands, pos);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) >= 0x00 && ByteArrayUtility.getByte(levelInfData, pos) < 0x80) {
            return new MiscValueLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
