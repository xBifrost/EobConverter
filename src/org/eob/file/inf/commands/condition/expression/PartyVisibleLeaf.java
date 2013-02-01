package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyVisibleLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public PartyVisibleLeaf() {
    }

    private PartyVisibleLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 1, "boolean <- party.isVisible", LeafType.Boolean);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xDA) {
            return new PartyVisibleLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}