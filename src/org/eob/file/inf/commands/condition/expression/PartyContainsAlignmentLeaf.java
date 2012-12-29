package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

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
        super(originalCommands, pos, 3, "boolean <- party.containsAlignment(byte)");
        alignmentId = ByteArrayUtility.getByte(originalCommands, pos+1);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xDD) {
            return new PartyContainsAlignmentLeaf(levelInfData, pos);
        }
        return null;
    }
}
