package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyItemTypeLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public PartyItemTypeLeaf() {
    }

    private PartyItemTypeLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "typeId <- party.item.type");
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xE7E1) {
            return new PartyItemTypeLeaf(levelInfData, pos);
        }
        return null;
    }
 }
