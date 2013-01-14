package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class MiscRollDiceLeaf extends ExpressionLeaf {
    public int rolls;
    public int sides;
    public int base;

    /**
     * Constructor of the prototype;
     */
    public MiscRollDiceLeaf() {
    }

    private MiscRollDiceLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 4, "int <- rollDice(rolls T sides + base)");
        rolls = ByteArrayUtility.getByte(originalCommands, pos + 1);
        sides = ByteArrayUtility.getByte(originalCommands, pos + 2);
        base = ByteArrayUtility.getByte(originalCommands, pos + 3);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xDB) {
            return new MiscRollDiceLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}