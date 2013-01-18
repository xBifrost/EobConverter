package org.eob.file.inf;

/**
 * User: Bifrost
 * Date: 16.1.2013
 * Time: 22:10
 */
public enum TriggerFlagType {
    OnClick(0x00),
    OnEnter(0x08),
    OnLeave(0x10),
    OnPutItem(0x20),
    OnPickUp(0x40),
    OnFlyingObject(0x80);

    public final int triggerFlagTypeId;

    TriggerFlagType(int triggerFlagTypeId) {
        this.triggerFlagTypeId = triggerFlagTypeId;
    }

    public static TriggerFlagType valueOf(int triggerFlagTypeId) {
        for (TriggerFlagType triggerFlagType : values()) {
            if (triggerFlagType.triggerFlagTypeId == triggerFlagTypeId) {
                return triggerFlagType;
            }
        }
        return null;
    }
}
