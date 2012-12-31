package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 19:31
 */
public class DamageCommand extends EobCommand {
    public final int whom;
    public final int flag1;
    public final int flag2;
    public final int flag3;

    public DamageCommand(byte[] levelInfData, int pos) {
        super(0xF3, pos, "Damage");

        int whomValue = ByteArrayUtility.getByte(levelInfData, pos + 1);
        whom = whomValue == 0xFF ? -1 : whomValue; // 0xFF -> to all
        flag1 = ByteArrayUtility.getByte(levelInfData, pos + 2);
        flag2 = ByteArrayUtility.getByte(levelInfData, pos + 3);
        flag3 = ByteArrayUtility.getByte(levelInfData, pos + 4);
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 5);
    }

    public static DamageCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF3) {
            return new DamageCommand(levelInfData, pos);
        }
        return null;
    }
}