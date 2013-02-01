package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyContainsAlignmentLeaf extends ExpressionLeaf {
    public int alignmentId;

    /**
     * Constructor of the prototype;
     */
    public PartyContainsAlignmentLeaf() {
    }

    private PartyContainsAlignmentLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "boolean <- party.containsAlignment(byte)", LeafType.Boolean);
        alignmentId = ByteArrayUtility.getByte(originalCommands, pos + 1);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xDD) {
            return new PartyContainsAlignmentLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}