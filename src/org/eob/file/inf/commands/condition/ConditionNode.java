package org.eob.file.inf.commands.condition;

/**
 * User: Bifrost
 * Date: 12/23/12
 * Time: 10:08 PM
 */
public interface ConditionNode {
    ConditionNode parse(byte[] levelInfData, int pos);

    int originalCommandSize();
}
