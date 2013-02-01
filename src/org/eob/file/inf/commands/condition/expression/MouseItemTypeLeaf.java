package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.file.inf.CommandVisitor;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class MouseItemTypeLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public MouseItemTypeLeaf() {
    }

    private MouseItemTypeLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 2, "object <- mouse.item.type", LeafType.MouseItemType);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getWord(levelInfData, pos) == 0xF5E7) {
            return new MouseItemTypeLeaf(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}