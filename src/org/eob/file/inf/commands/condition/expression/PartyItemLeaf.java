package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyItemLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public PartyItemLeaf() {
    }

    private PartyItemLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "object <- party.item");
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xF5E7) {
            return new PartyItemLeaf(levelInfData, pos);
        }
        return null;
    }
 }
