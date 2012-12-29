package org.eob.file.inf.commands.condition;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.commands.condition.expression.ExpressionLeaf;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 12/4/12
 * Time: 10:54 PM
 */
public class TermLeaf implements Term {
    public int originalPos;
    public byte[] originalCommands;

    public ExpressionLeaf expressionLeafLeft;
    public RelationOperator operator;
    public ExpressionLeaf expressionLeafRight;

    private TermLeaf(RelationOperator operator) {
        this.operator = operator;
    }

    /**
     * Constructor of the prototype;
     */
    public TermLeaf() {
    }

    @Override
    public TermLeaf parse(byte[] levelInfData, int pos) {
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
}
