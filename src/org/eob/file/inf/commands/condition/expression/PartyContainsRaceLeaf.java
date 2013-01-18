package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.enums.RaceType;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyContainsRaceLeaf extends ExpressionLeaf {
    public RaceType race;

    /**
     * Constructor of the prototype;
     */
    public PartyContainsRaceLeaf() {
    }

    private PartyContainsRaceLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "boolean <- party.containsRace(byte)");
        race = RaceType.getRaceById(ByteArrayUtility.getByte(originalCommands, pos + 1));
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xDD) {
            return new PartyContainsRaceLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}