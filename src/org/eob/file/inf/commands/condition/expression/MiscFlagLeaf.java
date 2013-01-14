package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class MiscFlagLeaf extends ExpressionLeaf {
    public int flagId;

    /**
     * Constructor of the prototype;
     */
    public MiscFlagLeaf() {
    }

    private MiscFlagLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "byte <- misc.flag(Global, flagId)");
        flagId = ByteArrayUtility.getByte(originalCommands, pos+1);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF0) {
            return new MiscFlagLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}