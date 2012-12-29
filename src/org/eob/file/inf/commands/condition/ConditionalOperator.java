package org.eob.file.inf.commands.condition;

/**
 * User: Bifrost
 * Date: 12/3/12
 * Time: 10:49 PM
 */
public enum ConditionalOperator {
    And(0xF9),
    Or(0xF8);

    public final int eobCommandId;

    ConditionalOperator(int eobCommandId) {
        this.eobCommandId = eobCommandId;
    }
}
