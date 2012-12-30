package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobLogger;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * - negative jump is not correct
 * User: Bifrost
 * Date: 20.12.2012
 * Time: 12:33
 */
public class GiveXpCommand extends EobCommand {
    public GiveXpType giveXpType;
    public final int experience;

    public GiveXpCommand(byte[] levelInfData, int pos) {
        super(0xEB, pos, "Give Experience");

        giveXpType = GiveXpType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        switch (giveXpType) {
            case Party:
                experience = ByteArrayUtility.getWord(levelInfData, pos + 2);
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 4);
                break;
            default:
            case Unknown:
                experience = 0;
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 2);
                break;
        }
    }

    public static GiveXpCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xEB) {
            return new GiveXpCommand(levelInfData, pos);
        }
        return null;
    }
}