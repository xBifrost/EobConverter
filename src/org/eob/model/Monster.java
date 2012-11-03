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

    private static final Map<Monster, String> registeredMonsters = new LinkedHashMap<Monster, String>();

    public Monster(int monsterId, String monsterName) {
        this.monsterId = monsterId;
        this.monsterName = monsterName;
    }

    public static Monster createMonster(String internalName, int monsterType, String monsterName) {
        Monster monster = new Monster(monsterType, monsterName);
        registeredMonsters.put(monster, internalName);
        return monster;
    }

    public static Monster getById(int monsterId) {
        for (Monster monster : registeredMonsters.keySet()) {
            if (monster.monsterId == monsterId) {
                return monster;
            }
        }

        return createMonster("unknown", monsterId, "");
    }

}
