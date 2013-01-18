package org.eob.file.inf.commands.condition;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 12/4/12
 * Time: 10:54 PM
 */
public class TermLeaf implements Term {
    public int originalPos;
    public byte[] originalCommands;

    public ConditionNode nodeLeft;
    public RelationOperator operator;
    public ConditionNode nodeRight;

    private TermLeaf(RelationOperator operator) {
        this.operator = operator;
    }

    /**
     * Constructor of the prototype;
     */
    public TermLeaf() {
    }

    @Override
    public TermLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        int command = ByteArrayUtility.getByte(levelInfData, pos);
        for (RelationOperator relationOperator : RelationOperator.values()) {
            if (relationOperator.eobCommandId == command) {
                TermLeaf termLeaf = new TermLeaf(relationOperator);
                termLeaf.originalPos = pos;
                termLeaf.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 1);
                return termLeaf;
            }
        }

        return null;
    }

    @Override
    public int originalCommandSize() {
        return originalCommands.length;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        nodeLeft.accept(visitor);
        nodeRight.accept(visitor);
        visitor.visit(this);
    }
}