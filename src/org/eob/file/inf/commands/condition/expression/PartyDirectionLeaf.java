package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyDirectionLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public PartyDirectionLeaf() {
    }

    private PartyDirectionLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 1, "byte <- party.direction");
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xED) {
            return new PartyDirectionLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}