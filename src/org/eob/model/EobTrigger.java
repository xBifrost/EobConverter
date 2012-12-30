package org.eob.model;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 22:02
 */
public class EobTrigger {
    private static Map<Integer, Integer> collapseTriggerFlags = new HashMap<Integer, Integer>();

    public final int x;
    public final int y;
    public final int flags;
    public final int address;

    static {
        collapseTriggerFlags.put(0x00, 0);
        collapseTriggerFlags.put(0x08, 1);
        collapseTriggerFlags.put(0x18, 2);
        collapseTriggerFlags.put(0x20, 3);
        collapseTriggerFlags.put(0x28, 4);
        collapseTriggerFlags.put(0x40, 5);
        collapseTriggerFlags.put(0x48, 6);
        collapseTriggerFlags.put(0x60, 7);
        collapseTriggerFlags.put(0x78, 8);
        collapseTriggerFlags.put(0x80, 9);
        collapseTriggerFlags.put(0x88, 10);
        collapseTriggerFlags.put(0xa8, 11);
    }

    public EobTrigger(int position, int flags, int address) {
        this.x = (position) & 0x1f;
        this.y = (position >> 5) & 0x1f;
        this.flags = collapseTriggerFlags.get(flags);
        this.address = address;
    }
}
