package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.enums.DirectionType;
import org.eob.enums.InSquarePositionType;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 31.12.2012
 * Time: 13:06
 */
public class LauncherCommand extends EobCommand {
    public final LauncherType launcherType;
    public final int itemId;
    public final int spellId;
    public final int x;
    public final int y;
    public final DirectionType direction;
    public final InSquarePositionType inSquarePositionType;

    public LauncherCommand(byte[] levelInfData, int pos) {
        super(0xE9, pos, "Launch spell/item");

        launcherType = LauncherType.valueOf(ByteArrayUtility.getByte(levelInfData, pos + 1));
        if (launcherType == LauncherType.Spell) {
            spellId = ByteArrayUtility.getWord(levelInfData, pos + 2);
            itemId = -1;
        } else {
            spellId = -1;
            itemId = ByteArrayUtility.getWord(levelInfData, pos + 2);
        }
        int position = ByteArrayUtility.getWord(levelInfData, pos + 4);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
        inSquarePositionType = InSquarePositionType.getItemPositionById(ByteArrayUtility.getByte(levelInfData, pos + 6));
        direction = DirectionType.getDirectionById(ByteArrayUtility.getByte(levelInfData, pos + 7));

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 8);
    }

    public static LauncherCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xE9) {
            return new LauncherCommand(levelInfData, pos);
        }
        return null;
    }
}