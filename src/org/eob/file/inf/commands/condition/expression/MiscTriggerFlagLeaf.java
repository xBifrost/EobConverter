package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class MiscTriggerFlagLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public MiscTriggerFlagLeaf() {
    }

    private MiscTriggerFlagLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 1, "byte <- trigger.flag");
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xE0) {
            return new MiscTriggerFlagLeaf(levelInfData, pos);
        }
        return null;
    }
 }
