package org.eob.enums;

import org.eob.EobLogger;

/**
 * User: Bifrost
 * Date: 18.1.2013
 * Time: 19:51
 */
public enum TurnType {
    Unknown(-1),
    Right(1),
    Back(2),
    Left(3);

    public final int eobTurn;

    TurnType(int eobTurn) {
        this.eobTurn = eobTurn;
    }

    public static TurnType getTurnById(int turnId) {
        for (TurnType turnType : values()) {
            if (turnType.eobTurn == turnId) {
                return turnType;
            }
        }

        EobLogger.println("Unsupported turn:" + turnId);
        return Unknown;
    }
}
