package org.eob.external;

/**
 * User: Bifrost
 * Date: 29.1.2013
 * Time: 22:10
 */
public class CommentCommand extends ExternalChangeCommand {
    /**
     * Prototype
     */
    CommentCommand() {
        super(0, -1, -1);
    }

    @Override
    public String getCommandName() {
        return "//";
    }

    public ExternalChangeCommand parse(int rowId, String commandStr) {
        if (commandStr.toUpperCase().startsWith(getCommandName())) {
            return new CommentCommand();
        }

        return null;
    }

    @Override
    public String getCommandAsString() {
        return "// Comment";
    }
}

