package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

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
        super(originalCommands, pos, 2, "boolean <- party.isVisible");
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xDA) {
            return new PartyVisibleLeaf(levelInfData, pos);
        }
        return null;
    }
 }
