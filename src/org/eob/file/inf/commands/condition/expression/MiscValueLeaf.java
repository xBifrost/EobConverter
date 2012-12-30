package org.eob.file.inf.commands.condition.expression;

import org.eob.ByteArrayUtility;

/**
 * User: Bifrost
 * Date: 12/16/12
 * Time: 11:54 PM
 */
public class MiscValueLeaf extends ExpressionLeaf {
    int value;
    /**
     * Constructor of the prototype;
     */
    public MiscValueLeaf() {
    }

    private MiscValueLeaf(byte[] originalCommands, int pos) {
        super(originalCommands, pos, 1, "byte <- value");
        value = ByteArrayUtility.getByte(originalCommands, pos);
    }

    public ExpressionLeaf parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) > 0x01 && ByteArrayUtility.getByte(levelInfData, pos) < 0x80) {
            return new MiscValueLeaf(levelInfData, pos);
        }
        return null;
    }
 }
