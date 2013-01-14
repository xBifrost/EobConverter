package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.commands.condition.ConditionNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Bifrost
 * Date: 31.12.2012
 * Time: 19:42
 */
public class ListNode implements ConditionNode {
    public final List<ConditionNode> nodes = new ArrayList<ConditionNode>();

    public int originalPos;
    public byte[] originalCommands;

    public ListNode(byte[] levelInfData, int originalPos, int commandSize) {
        this.originalPos = originalPos;
        this.originalCommands = Arrays.copyOfRange(levelInfData, originalPos, originalPos + commandSize);
    }

    @Override
    public ConditionNode parse(byte[] levelInfData, int originalPos) {
        throw new UnsupportedOperationException("ListNode not supported parse feature.");
    }

    @Override
    public int originalCommandSize() {
        return originalCommands.length;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        for (ConditionNode node : nodes) {
            node.accept(visitor);
        }
        visitor.visit(this);
    }
}
