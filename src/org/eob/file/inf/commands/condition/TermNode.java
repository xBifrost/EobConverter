package org.eob.file.inf.commands.condition;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 12/4/12
 * Time: 10:54 PM
 */
public class TermNode implements Term{
    public int originalPos;
    public byte[] originalCommands;

    public Term termLeft;
    public ConditionalOperator operator;
    public Term termRight;

    public TermNode(ConditionalOperator operator) {
        this.operator = operator;
    }

    /**
     * Constructor of the prototype;
     */
    public TermNode() {
    }

    @Override
    public TermNode parse(byte[] levelInfData, int pos) {
        int command = ByteArrayUtility.getByte(levelInfData, pos);
        for (ConditionalOperator conditionalOperator : ConditionalOperator.values()) {
            if (conditionalOperator.eobCommandId == command) {
                TermNode termNode = new TermNode(conditionalOperator);
                termNode.originalPos = pos;
                termNode.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 1);
                return termNode;
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
        termLeft.accept(visitor);
        termRight.accept(visitor);
        visitor.visit(this);
    }
}