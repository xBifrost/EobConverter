package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyContainsRaceLeaf extends ExpressionLeaf {
    public int raceId;

    /**
     * Constructor of the prototype;
     */
    public PartyContainsRaceLeaf() {
    }

    private PartyContainsRaceLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "boolean <- party.containsRace(byte)");
        raceId = ByteArrayUtility.getByte(originalCommands, pos+1);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xDD) {
            return new PartyContainsRaceLeaf(levelInfData, pos);
        }
        return null;
    }
}
