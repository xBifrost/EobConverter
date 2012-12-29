package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class MiscTrueLeaf extends ExpressionLeaf {
    /**
     * Constructor of the prototype;
     */
    public MiscTrueLeaf() {
    }

    private MiscTrueLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 1, "boolean/byte <- TRUE/1");
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0x01) {
            return new MiscTrueLeaf(levelInfData, pos);
        }
        return null;
    }
 }
