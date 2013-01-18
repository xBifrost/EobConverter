package org.eob.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Bifrost
 * Date: 10/29/12
 * Time: 10:11 PM
 */
public class Monster {
    public final int monsterId;
    public final String monsterName;

    public Monster(int monsterId, String monsterName) {
        this.monsterId = monsterId;
        this.monsterName = monsterName;
    }
}
