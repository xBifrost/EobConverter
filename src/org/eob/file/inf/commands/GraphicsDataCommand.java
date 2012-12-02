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
    public String graphicsDataName = ""; // Points to a .cps file containing wall graphics data.
    public String graphicsDataExt = "";
    public String rectanglesDataName = ""; // Points to a .dat file containing rectangular data that point into the graphics data.


    private GraphicsDataCommand(byte[] originalCommands, int pos) {
        super(0xEC, originalCommands, pos);
    }

    public static GraphicsDataCommand parse(byte[] levelInfData, int pos) {
        GraphicsDataCommand command = new GraphicsDataCommand(Arrays.copyOfRange(levelInfData, pos, pos + 24), pos);
        int commandPos = 0;
        while (command.originalCommands[commandPos] != 0) {
            command.graphicsDataName += (char) command.originalCommands[commandPos];
            commandPos++;
        }
        commandPos++;
        while (command.originalCommands[commandPos] != 0) {
            command.graphicsDataExt += (char) command.originalCommands[commandPos];
            commandPos++;
        }
        commandPos = 12;
        while (command.originalCommands[commandPos] != 0) {
            command.rectanglesDataName += (char) command.originalCommands[commandPos];
            commandPos++;
        }

        return command;
    }
}
