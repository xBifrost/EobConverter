package org.eob.file.inf.commands.condition;

/**
 * User: Bifrost
 * Date: 12/3/12
 * Time: 10:51 PM
 */
public enum RelationOperator {
    Equal(0xFF),
    NotEqual(0xFE),
    Less(0xFD),
    LessOrEqual(0xFC),
    Greater(0xFB),
    GreaterOrEqual(0xFA);

    public final int eobCommandId;

    RelationOperator(int eobCommandId) {
        this.eobCommandId = eobCommandId;
    }
}
