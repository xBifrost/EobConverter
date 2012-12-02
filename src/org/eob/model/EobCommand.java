package org.eob.model;

/**
 * User: Bifrost
 * Date: 12/2/12
 * Time: 1:15 PM
 */
public class EobCommand {
    public final int commandId;
    public final int originalPos;
    public final byte[] originalCommands;

    public EobCommand(int commandId, byte[] originalCommands, int originalPos) {
        this.commandId = commandId;
        this.originalCommands = originalCommands;
        this.originalPos = originalPos;
    }
}
