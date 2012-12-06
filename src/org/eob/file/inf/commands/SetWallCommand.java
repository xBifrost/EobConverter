package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: thomson
 * Date: 12/6/12
 * Time: 12:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class SetWallCommand extends EobCommand {
    final private int COMPLETE_BLOCK = 0xf7;
    final private static int ONE_WALL = 0xe9;
    final private static int PARTY_FACING = 0xed;

    int wallMappingIndex;
    int x;
    int y;
    int direction;
    int partyFacing;

    public SetWallCommand(byte[] levelInfData, int pos) {
        super(0xFF, levelInfData, pos);

        int subtype = ByteArrayUtility.getByte(levelInfData, pos);
        int len = 0;
        switch (subtype) {
            case COMPLETE_BLOCK: {
                int coords = ByteArrayUtility.getWord(levelInfData, pos + 1);
                x = (coords) & 0x1f;
                y = (coords >> 5) & 0x1f;
                wallMappingIndex = ByteArrayUtility.getByte(levelInfData, pos + 3);
                len = 4; // subtype(1) + coords(2) + wallindex (1)
                break;
            }
            case ONE_WALL: {
                int coords = ByteArrayUtility.getWord(levelInfData, pos + 1);
                x = (coords) & 0x1f;
                y = (coords >> 5) & 0x1f;
                wallMappingIndex = ByteArrayUtility.getByte(levelInfData, pos + 3);
                direction = ByteArrayUtility.getByte(levelInfData, pos + 4);
                len = 5; // subtype(1) + coords(2) + wallindex (1)
                break;
            }
            case PARTY_FACING: {
                partyFacing = ByteArrayUtility.getByte(levelInfData, pos + 1);
                len = 2;
                break;
            }
            default: {
                // unknown
                len = 1;
            }
        }

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + len);
    }
}