package org.eob.export;

import org.eob.model.MonsterObject;

/**
 * User: Bifrost
 * Date: 11/2/12
 * Time: 11:20 PM
 */
public class MonsterGroup {
    public final MonsterObject monsterObject;
    public final String pocketItem50Name;
    public final String pocketItemName;
    public int count = 0;

    public MonsterGroup(MonsterObject monsterObject, String pocketItem50Name, String pocketItemName) {
        this.monsterObject = monsterObject;
        this.pocketItem50Name = pocketItem50Name;
        this.pocketItemName = pocketItemName;
    }
}
