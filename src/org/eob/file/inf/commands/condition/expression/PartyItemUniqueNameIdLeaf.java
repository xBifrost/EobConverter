package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

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
        super(originalCommands, pos, 3, "boolean <- party.item.uniqueNameId == byte", LeafType.ItemUniqueName);
        nameId = ByteArrayUtility.getByte(originalCommands, pos+2);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xD0E7) {
            return new PartyItemUniqueNameIdLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}