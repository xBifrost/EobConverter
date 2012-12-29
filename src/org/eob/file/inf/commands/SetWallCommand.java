package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: thomson
 * Date: 6.12.2012
 * Time: 00:04
 */
public class SetWallCommand extends EobCommand {
    public final SetWallType subtype;
    public final int x;
    public final int y;
    public final int wallMappingIndex;
    public final int direction;
    public final int partyFacing;

    public SetWallCommand(byte[] levelInfData, int pos) {
        super(0xFF, pos, "Set wall");

        subtype = SetWallType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        int len;
        switch (subtype) {
            case CompleteBlock: {
                int coords = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (coords) & 0x1f;
                y = (coords >> 5) & 0x1f;
                wallMappingIndex = ByteArrayUtility.getByte(levelInfData, pos + 4);
                direction = -1;
                partyFacing = -1;
                len = 4; // subtype(1) + coords(2) + wallindex (1)
                break;
            }
            case OneWall: {
                int coords = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (coords) & 0x1f;
                y = (coords >> 5) & 0x1f;
                wallMappingIndex = ByteArrayUtility.getByte(levelInfData, pos + 4);
                direction = ByteArrayUtility.getByte(levelInfData, pos + 4);
                partyFacing = -1;
                len = 5; // subtype(1) + coords(2) + wallindex (1)
                break;
            }
            case PartyFacing: {
                x= -1;
                y = -1;
                wallMappingIndex = -1;
                direction = -1;
                partyFacing = ByteArrayUtility.getByte(levelInfData, pos + 2);
                len = 2;
                break;
            }
            default: {
                // unknown
                x= -1;
                y = -1;
                wallMappingIndex = -1;
                direction = -1;
                partyFacing = -1;
                len = 1;
            }
        }

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + len + 1);
    }

    public static SetWallCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFF) {
            return new SetWallCommand(levelInfData, pos);
        }
        return null;
    }
}