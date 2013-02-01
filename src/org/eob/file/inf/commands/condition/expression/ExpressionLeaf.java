package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.commands.condition.ConditionNode;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 12/3/12
 * Time: 10:50 PM
 */
public abstract class ExpressionLeaf implements ConditionNode {
    public int originalPos;
    public byte[] originalCommands;
    public ConditionNode parent = null;

    public int expressionId;
    public String description;
    public LeafType leafType;

    /**
     * Constructor of the prototype;
     */
    public ExpressionLeaf() {
    }

    public ExpressionLeaf(byte[] levelInfData, int originalPos, int commandSize, String description, LeafType leafType) {
        this.originalPos = originalPos;
        this.originalCommands = Arrays.copyOfRange(levelInfData, originalPos, originalPos + commandSize);
        this.expressionId = ByteArrayUtility.getByte(levelInfData, originalPos + 1);
        this.description = description;
        this.leafType = leafType;
    }

    @Override
    public int originalCommandSize() {
        return originalCommands.length;
    }
}
