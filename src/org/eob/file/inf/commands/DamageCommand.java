package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 19:31
 */
public class DamageCommand extends EobCommand {
    public final int whom;
    public final int rolls;
    public final int sides;
    public final int base;

    public DamageCommand(byte[] levelInfData, int pos) {
        super(0xF3, pos, "Damage");

        int whomValue = ByteArrayUtility.getByte(levelInfData, pos + 1);
        whom = whomValue == 0xFF ? -1 : whomValue; // 0xFF -> to all
        rolls = ByteArrayUtility.getByte(levelInfData, pos + 2);
        sides = ByteArrayUtility.getByte(levelInfData, pos + 3);
        base = ByteArrayUtility.getByte(levelInfData, pos + 4);
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 5);
    }

    public static DamageCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF3) {
            return new DamageCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}