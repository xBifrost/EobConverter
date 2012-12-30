package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 13:58
 */
public class ChangeWallCommand extends EobCommand {
    public final ChangeWallType subtype;
    public final int x;
    public final int y;
    public final int side;
    public final int fromWall;
    public final int toWall;

    public ChangeWallCommand(byte[] levelInfData, int pos) {
        super(0xFE, pos, "Change wall");

        subtype = ChangeWallType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        int len;
        switch (subtype) {
            case CompleteBlock: {
                int position = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                side = -1;
                toWall = ByteArrayUtility.getByte(levelInfData, pos + 4);
                fromWall = ByteArrayUtility.getByte(levelInfData, pos + 5);
                len = 6;
                break;
            }
            case OneWall: {
                int position = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                side = ByteArrayUtility.getByte(levelInfData, pos + 4);
                toWall = ByteArrayUtility.getByte(levelInfData, pos + 5);
                fromWall = ByteArrayUtility.getByte(levelInfData, pos + 6);
                len = 7;
                break;
            }
            case OpenDoor: {
                int position = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                side = -1;
                toWall = -1;
                fromWall = -1;
                len = 4;
                break;
            }
            default: {
                // unknown
                x = -1;
                y = -1;
                side = -1;
                toWall = -1;
                fromWall = -1;
                len = 2;
            }
        }

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + len);
    }

    public static ChangeWallCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFE) {
            return new ChangeWallCommand(levelInfData, pos);
        }
        return null;
    }
}