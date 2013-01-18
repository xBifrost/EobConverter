package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.EobGlobalData;
import org.eob.enums.DirectionType;
import org.eob.enums.InSquarePositionType;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;
import org.eob.model.Wall;

import java.util.Arrays;

/**
 * User: thomson
 * Date: 6.12.2012
 * Time: 00:04
 */
public class SetWallCommand extends EobCommand {
    public final ChangeWallType subtype;
    public final int x;
    public final int y;
    public final Wall wall;
    public final DirectionType direction;
    public final int partyFacing;

    public SetWallCommand(int level, byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        super(0xFF, pos, "Set wall");

        subtype = ChangeWallType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        int len;
        switch (subtype) {
            case CompleteBlock: {
                int coords = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (coords) & 0x1f;
                y = (coords >> 5) & 0x1f;
                direction = DirectionType.Unknown;
                wall = eobGlobalData.getWallById(ByteArrayUtility.getByte(levelInfData, pos + 4), level);
                partyFacing = -1;
                len = 4; // subtype(1) + coords(2) + wallindex (1)
                break;
            }
            case OneWall: {
                int coords = ByteArrayUtility.getWord(levelInfData, pos + 2);
                x = (coords) & 0x1f;
                y = (coords >> 5) & 0x1f;
                direction =  DirectionType.getDirectionById(ByteArrayUtility.getByte(levelInfData, pos + 4));
                wall = eobGlobalData.getWallById(ByteArrayUtility.getByte(levelInfData, pos + 5), level);
                partyFacing = -1;
                len = 5; // subtype(1) + coords(2) + wallindex (1)
                break;
            }
            case PartyFacing: {
                x= -1;
                y = -1;
                direction = DirectionType.Unknown;
                wall = null;
                partyFacing = ByteArrayUtility.getByte(levelInfData, pos + 2);
                len = 2;
                break;
            }
            default: {
                // unknown
                x= -1;
                y = -1;
                direction = DirectionType.Unknown;
                wall = null;
                partyFacing = -1;
                len = 1;
            }
        }

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + len + 1);
    }

    public static SetWallCommand parse(int level, byte[] levelInfData, int pos, EobGlobalData eobGlobalData) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFF) {
            return new SetWallCommand(level, levelInfData, pos, eobGlobalData);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}