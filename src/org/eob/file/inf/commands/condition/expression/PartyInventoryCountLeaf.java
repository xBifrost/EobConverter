package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;
import org.eob.model.ItemType;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 16:30
 */
public class PartyInventoryCountLeaf extends ExpressionLeaf {
    public ItemType itemType;
    public int flags;

    /**
     * Constructor of the prototype;
     */
    public PartyInventoryCountLeaf() {
    }

    private PartyInventoryCountLeaf(byte[] originalCommands, int pos, EobGlobalData eobGlobalData) {
        super(originalCommands, pos, 5, "int <- party.inventory.count(type, flags)", LeafType.Number);
        itemType = eobGlobalData.itemTypeDatFile.getById(ByteArrayUtility.getWord(originalCommands, pos + 2));
        flags = ByteArrayUtility.getWord(originalCommands, pos + 4);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xF5F1) {
            return new PartyInventoryCountLeaf(levelInfData, pos, eobGlobalData);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}