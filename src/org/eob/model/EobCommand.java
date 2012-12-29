package org.eob.model;

/**
 * User: Bifrost
 * Date: 12/2/12
 * Time: 1:15 PM
 */
public class EobCommand {
    public final int commandId;
    public final int originalPos;
    public byte[] originalCommands;
    public String description;

    public EobCommand(int commandId, int originalPos) {
        this.commandId = commandId;
        this.originalPos = originalPos;
    }

    public EobCommand(int commandId, int originalPos, String description) {
        this.commandId = commandId;
        this.originalPos = originalPos;
        this.description = description;
    }
}
