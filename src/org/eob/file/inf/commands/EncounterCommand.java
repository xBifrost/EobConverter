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
public class EncounterCommand extends EobCommand {
    public final int encounterId;

    private EncounterCommand(byte[] levelInfData, int pos) {
        super(0xE6, pos, "Encounter");
        encounterId = ByteArrayUtility.getByte(levelInfData, pos + 1);
        originalCommands = Arrays.copyOfRange(levelInfData, pos, pos + 2);
    }

    public static EncounterCommand parse(byte[] levelInfData, int pos) {
        if (ByteArrayUtility.getByte(levelInfData, pos) == 0xE6) {
            return new EncounterCommand(levelInfData, pos);
        }
        return null;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}