package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.enums.DirectionType;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 00:04
 */
public class ChangeLevelCommand extends EobCommand {
    public final ChangeLevelType changeLevelType;
    public final int level;
    public final int x;
    public final int y;
    public final DirectionType direction;

    public ChangeLevelCommand(byte[] levelInfData, int pos) {
        super(0xEC, pos, "Change level");

        changeLevelType = ChangeLevelType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        switch (changeLevelType) {
            case ChangeLevel: {
                level = ByteArrayUtility.getByte(levelInfData, pos + 2);
                int position = ByteArrayUtility.getWord(levelInfData, pos + 3);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                direction = DirectionType.getDirectionById(ByteArrayUtility.getByte(levelInfData, pos + 5));
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 6);
                break;
            }
            default:
            case InLevel: {
                level = -1;
                direction = DirectionType.getDirectionById(ByteArrayUtility.getByte(levelInfData, pos + 2));
                int position = ByteArrayUtility.getWord(levelInfData, pos + 3);
                x = (position) & 0x1f;
                y = (position >> 5) & 0x1f;
                this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 5);
                break;
            }
        }
    }

    public static ChangeLevelCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xEC) {
            return new ChangeLevelCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}