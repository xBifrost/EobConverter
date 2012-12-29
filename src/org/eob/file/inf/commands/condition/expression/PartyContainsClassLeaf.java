package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyContainsClassLeaf extends ExpressionLeaf {
    public int classId;

    /**
     * Constructor of the prototype;
     */
    public PartyContainsClassLeaf() {
    }

    private PartyContainsClassLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 3, "boolean <- party.containsClass(byte)");
        classId = ByteArrayUtility.getByte(originalCommands, pos+1);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xDD) {
            return new PartyContainsClassLeaf(levelInfData, pos);
        }
        return null;
    }
}
