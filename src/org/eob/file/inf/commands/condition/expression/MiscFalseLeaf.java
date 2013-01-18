package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class MiscFalseLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public MiscFalseLeaf() {
    }

    private MiscFalseLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 1, "boolean/byte <- FALSE/0");
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0x00) {
            return new MiscFalseLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}