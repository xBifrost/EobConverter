package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.enums.DirectionType;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;
import org.eob.model.Wall;

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
    public final DirectionType side;
    public final Wall fromWall;
    public final Wall toWall;

    public ChangeWallCommand(int level, byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        super(0xFE, pos, "Change wall");

        subtype = ChangeWallType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        int len;
        switch (subtype) {
            case CompleteBlock: {
                int position = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                side = DirectionType.Unknown;
                toWall = eobGlobalData.getWallById(ByteArrayUtility.getByte(levelInfData, pos + 4), level);
                fromWall = eobGlobalData.getWallById(ByteArrayUtility.getByte(levelInfData, pos + 5), level);
                len = 6;
                break;
            }
            case OneWall: {
                int position = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                side = DirectionType.getDirectionById(ByteArrayUtility.getByte(levelInfData, pos + 4));
                toWall = eobGlobalData.getWallById(ByteArrayUtility.getByte(levelInfData, pos + 5), level);
                fromWall = eobGlobalData.getWallById(ByteArrayUtility.getByte(levelInfData, pos + 6), level);
                len = 7;
                break;
            }
            case Door: {
                int position = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                side = DirectionType.Unknown;
                toWall = null;
                fromWall = null;
                len = 4;
                break;
            }
            default: {
                // unknown
                x = -1;
                y = -1;
                side = DirectionType.Unknown;
                toWall = null;
                fromWall = null;
                len = 2;
            }
        }

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + len);
    }

    public static ChangeWallCommand parse(int level, byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFE) {
            return new ChangeWallCommand(level, levelInfData, pos, eobGlobalData);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}