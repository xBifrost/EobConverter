package org.eob.file.inf.commands.condition;

import org.eob.file.inf.CommandElement;

/**
 * User: Bifrost
 * Date: 12/23/12
 * Time: 10:08 PM
 */
public interface ConditionNode extends CommandElement {
    ConditionNode parse(byte[] levelInfData, int pos);

    int originalCommandSize();
}
