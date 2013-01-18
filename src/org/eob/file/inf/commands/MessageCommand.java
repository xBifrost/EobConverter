package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.file.inf.CommandVisitor;
import org.eob.file.inf.EobCommand;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 28.12.2012
 * Time: 11:12 PM
 */
public class MessageCommand extends EobCommand {
    public final String message;
    public final VgaColorType vgaColor;

    private MessageCommand(byte[] levelInfData, int pos) {
        super(0xF8, pos, "Message...");
        int subPosition = 1;
        String messageText = "";

        while (levelInfData[pos + subPosition] != 0) {
            messageText += (char) levelInfData[pos + subPosition];
            subPosition++;
        }
        message = messageText;
        subPosition++;
        int colorId = ByteArrayUtility.getByte(levelInfData, pos + subPosition);
        subPosition += 2;

//        int[] vgaColors = {0, 6, 5, 3, 2, 4, 9, 15, 11, 14, 13, 3, 10, 4, 7, 1};
        int[] vgaColors = {0, 7, 3, 2, 7, 14, 12, 7, 7, 7, 7, 7, 7, 7, 7, 15};
        vgaColor = VgaColorType.valueOf(vgaColors[colorId]);
        originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + subPosition);
    }

    public static MessageCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xF8) {
            return new MessageCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}