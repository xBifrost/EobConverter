package org.eob.external;

import java.util.List;

/**
 * User: Bifrost
 * Date: 21.1.2013
 * Time: 23:13
 */
public abstract class ExternalChangeCommand {
    public final int level;
    public final int x;
    public final int y;

    public ExternalChangeCommand(int level, int x, int y) {
        this.level = level;
        this.x = x;
        this.y = y;
    }

    public abstract String getCommandName();

    public abstract ExternalChangeCommand parse(int rowId, String commandStr);

    public abstract String getCommandAsString();
}
