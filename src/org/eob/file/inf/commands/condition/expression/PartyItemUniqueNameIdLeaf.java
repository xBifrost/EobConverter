package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyItemUniqueNameIdLeaf extends ExpressionLeaf {
    public int nameId;

    /**
     * Constructor of the prototype;
     */
    public PartyItemUniqueNameIdLeaf() {
    }

    private PartyItemUniqueNameIdLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 3, "boolean <- party.item.uniqueNameId == byte");
        nameId = ByteArrayUtility.getByte(originalCommands, pos+2);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xE7D0) {
            return new PartyItemUniqueNameIdLeaf(levelInfData, pos);
        }
        return null;
    }
 }