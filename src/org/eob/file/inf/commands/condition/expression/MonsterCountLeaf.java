package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.commands.condition.ConditionNode;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 14:39
 */
public class MonsterCountLeaf extends ExpressionLeaf {
    public int monsterTypeId;
    public int x;
    public int y;

    /**
     * Constructor of the prototype;
     */
    public MonsterCountLeaf() {
    }

    private MonsterCountLeaf(byte[] levelInfData, int originalPos, int commandSize) {
        super(levelInfData, originalPos, commandSize, commandSize == 4 ?
                "byte <- monster.countAt(x, y)" : "byte <- monster.countByType(type)");

        if (commandSize == 4) {
            monsterTypeId = ByteArrayUtility.getByte(levelInfData, originalPos + 1);
            int position = ByteArrayUtility.getWord(levelInfData, originalPos + 2);
            x = (position) & 0x1f;
            y = (position >> 5) & 0x1f;
        } else {
            expressionId = -1;
            monsterTypeId = ByteArrayUtility.getByte(levelInfData, originalPos);
            x = -1;
            y = -1;
        }
    }

    public ConditionNode parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF3) {
            if (ByteArrayUtility.getByte(levelInfData, pos + 1) == 0xFF) {
                return new MonsterCountLeaf(levelInfData, pos, 4);
            }

            List<ExpressionLeaf> nodes = new ArrayList<ExpressionLeaf>();
            do {
                nodes.add(new MonsterCountLeaf(levelInfData, pos + nodes.size() + 1, 1));
                nodes.add(new MiscValueLeaf(levelInfData, pos + nodes.size() + 1));
            } while (ByteArrayUtility.getByte(levelInfData, pos + nodes.size() + 1) != 0x00);

            ListNode listNode = new ListNode(levelInfData, pos, nodes.size() + 2);
            listNode.nodes.addAll(nodes);
            for (ExpressionLeaf node : nodes) {
                node.parent = listNode;
            }
            return listNode;
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
