package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyItemValueLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public PartyItemValueLeaf() {
    }

    private PartyItemValueLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "object <- party.item.value");
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xF6E7) {
            return new PartyItemValueLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}