package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.enums.DirectionType;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 31.12.2012
 * Time: 12:49
 */
public class TurnCommand extends EobCommand {
    public final TurnType turnType;
    public final DirectionType directionType;

    public TurnCommand(byte[] levelInfData, int pos) {
        super(0xE8, pos, "Turn");

        turnType = TurnType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        directionType = DirectionType.getDirectionById(ByteArrayUtility.getByte(levelInfData, pos + 2));
        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 3);
    }

    public static TurnCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xE8) {
            return new TurnCommand(levelInfData, pos);
        }
        return null;
    }
}