package org.eob.file.inf.commands;

import org.eob.ByteArrayUtility;
import org.eob.model.EobCommand;

import java.util.Arrays;

/**
 * Load overlay image picture + overlay image rectangle data
 * <p/>
 * User: Bifrost
 * Date: 12/2/12
 * Time: 2:18 PM
 */
public class GraphicsDataCommand extends EobCommand {
    public final String graphicsDataName; // Points to a .cps file containing wall graphics data.
    public final String graphicsDataExt;
    public final String rectanglesDataName; // Points to a .dat file containing rectangular data that point into the graphics data.

    private GraphicsDataCommand(byte[] levelInfData, int pos) {
        super(0xEC, pos, "Graphics data");

        int commandPos = 1;
        String text = "";
        while (levelInfData[pos + commandPos] != 0) {
            text += (char) levelInfData[pos + commandPos];
            commandPos++;
        }
        graphicsDataName = text;

        commandPos++;
        text = "";
        while (levelInfData[pos + commandPos] != 0) {
            text += (char) levelInfData[pos + commandPos];
            commandPos++;
        }
        graphicsDataExt = text;

        commandPos = 13;
        text = "";
        while (levelInfData[pos + commandPos] != 0) {
            text += (char) levelInfData[pos + commandPos];
            commandPos++;
        }
        rectanglesDataName = text;
        originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 25);
    }

    public static GraphicsDataCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xEC) {
            return new GraphicsDataCommand(levelInfData, pos);
        }
        return null;
    }
}
