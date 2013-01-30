package org.eob.external;

import org.eob.EobLogger;

import java.io.InputStream;
import java.util.*;

/**
 * User: Bifrost
 * Date: 21.1.2013
 * Time: 23:27
 */
public class ExternalChangesParser {
    static final private Set<ExternalChangeCommand> registeredCommands = new LinkedHashSet<ExternalChangeCommand>();

    static {
        registerCommand(new ChangeAssetDirectionCommand());
        registerCommand(new RemoveAssetCommand());
        registerCommand(new AddAssetCommand());
        registerCommand(new CommentCommand());
        registerCommand(new ChangeAssetPositionCommand());
    }

    public static void registerCommand(ExternalChangeCommand command) {
        registeredCommands.add(command);
    }


    public List<ExternalChangeCommand> parseFile(InputStream inputStream) {
        EobLogger.println("Parsing external data");

        final List<ExternalChangeCommand> result = new ArrayList<ExternalChangeCommand>();
        try {
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            try {
                int rowPos = 0;
                while (scanner.hasNextLine()) {
                    rowPos++;
                    final String commandStr = scanner.nextLine().trim();
                    if (commandStr.length() == 0) {
                        continue;
                    }

                    boolean executed = false;
                    for (ExternalChangeCommand externalChangeCommand : registeredCommands) {
                        final ExternalChangeCommand changeCommand = externalChangeCommand.parse(rowPos, commandStr);
                        if (changeCommand != null) {
                            if (!(changeCommand instanceof CommentCommand)) {
                                result.add(changeCommand);
                            }
                            executed = true;
                        }
                    }

                    if (!executed) {
                        EobLogger.println("Command at row " + rowPos + " was supported. Row: " + commandStr);
                    }
                }
            } finally {
                scanner.close();
            }
        } catch (Exception e) {
            EobLogger.println("[Error] " + e.getMessage());
        }

        EobLogger.println("External changes count: " + result.size());
        return result;
    }

}
