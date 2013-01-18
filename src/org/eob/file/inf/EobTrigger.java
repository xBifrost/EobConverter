package org.eob.file.inf;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 22:02
 */
public class EobTrigger {
    public final int triggerId;
    public final int x;
    public final int y;
    public final TriggerFlagsType flags;
    public final int addressStart;
    public int addressEnd = -1;

    public EobTrigger(int triggerId, int position, int flags, int addressStart) {
        this.triggerId = triggerId;
        this.x = (position) & 0x1f;
        this.y = (position >> 5) & 0x1f;
        this.flags = TriggerFlagsType.valueOf(flags);
        this.addressStart = addressStart;
    }
}
