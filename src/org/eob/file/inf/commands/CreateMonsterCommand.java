package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.enums.DirectionType;
import org.eob.enums.InSquarePositionType;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 13:58
 */
public class CreateMonsterCommand extends EobCommand {
    public final int unknown;
    public final int moveTime;
    public final int x;
    public final int y;
    public final InSquarePositionType inSquarePositionType;
    public final DirectionType direction;
    public final int monsterTypeId;
    public final int imageId; // Image monster (0 - monster1 from level*.inf file, 1 - monster2 from level*.inf file)
    public final int phase; // animation phase?
    public final boolean pause; // Monster freeze?
    public final int pocketItem50Id; // 50% chance to drop this item
    public final int pocketItemId; // 100% chance to drop this item

    public CreateMonsterCommand(byte[] levelInfData, int pos) {
        super(0xFB, pos, "Create monster");

        unknown = ByteArrayUtility.getByte(levelInfData, pos + 1);
        moveTime = ByteArrayUtility.getByte(levelInfData, pos + 2);
        int position = ByteArrayUtility.getWord(levelInfData, pos + 3);
        x = (position) & 0x1f;
        y = (position >> 5) & 0x1f;
        inSquarePositionType = InSquarePositionType.getItemPositionById(ByteArrayUtility.getByte(levelInfData, pos + 5));
        direction = DirectionType.getDirectionById(ByteArrayUtility.getByte(levelInfData, pos + 6));
        monsterTypeId = ByteArrayUtility.getByte(levelInfData, pos + 7);
        imageId = ByteArrayUtility.getByte(levelInfData, pos + 8);
        phase = ByteArrayUtility.getByte(levelInfData, pos + 9);
        pause = ByteArrayUtility.getByte(levelInfData, pos + 10) != 0;
        pocketItemId = ByteArrayUtility.getWord(levelInfData, pos + 11);
        pocketItem50Id = ByteArrayUtility.getWord(levelInfData, pos + 13);

        this.originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 15);
    }

    public static CreateMonsterCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xFB) {
            return new CreateMonsterCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}