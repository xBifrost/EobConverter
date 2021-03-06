package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.enums.ClassType;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class PartyContainsClassLeaf extends ExpressionLeaf {
    public ClassType classType;

    /**
     * Constructor of the prototype;
     */
    public PartyContainsClassLeaf() {
    }

    private PartyContainsClassLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "boolean <- party.containsClass(byte)", LeafType.Boolean);
        classType = ClassType.getClassById(ByteArrayUtility.getByte(originalCommands, pos+1));
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xDC) {
            return new PartyContainsClassLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}