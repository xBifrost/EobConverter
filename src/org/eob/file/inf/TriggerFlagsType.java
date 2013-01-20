package org.eob.file.inf;

/**
 * Bits:
 *  4. Enter
 *  5. Leave
 *  6. Put item
 *  7. PickUp item
 *  8. Flying object
 *
 * User: Bifrost
 * Date: 16.1.2013
 * Time: 0:37
 */
public enum TriggerFlagsType {
    OnClick(TriggerFlagType.OnClick),
    OnEnter(TriggerFlagType.OnEnter),
    OnEnterLeave(TriggerFlagType.OnEnter, TriggerFlagType.OnLeave),
    OnPutItem(TriggerFlagType.OnPutItem),
    OnEnterOrPutItem(TriggerFlagType.OnEnter, TriggerFlagType.OnPutItem),
    OnPickUp(TriggerFlagType.OnPickUp),
    OnEnterOrPickUp(TriggerFlagType.OnEnter, TriggerFlagType.OnPickUp),
    OnPutOrPickUpItem(TriggerFlagType.OnPutItem, TriggerFlagType.OnPickUp),
    OnEnterLeavePutPickUp(TriggerFlagType.OnEnter, TriggerFlagType.OnLeave, TriggerFlagType.OnPutItem, TriggerFlagType.OnPickUp),
    OnFlyingObject(TriggerFlagType.OnFlyingObject),
    OnEnterOrFlyingObject(TriggerFlagType.OnEnter, TriggerFlagType.OnFlyingObject),
    OnEnterOrPutItemOrFlyingObject(TriggerFlagType.OnEnter, TriggerFlagType.OnPutItem, TriggerFlagType.OnFlyingObject);

    public final int triggerFlagsTypeId;


    TriggerFlagsType(TriggerFlagType... triggerFlagTypes) {
        int triggerFlagsTypeId = 0;
        for (TriggerFlagType triggerFlagType : triggerFlagTypes) {
            triggerFlagsTypeId |= triggerFlagType.triggerFlagTypeId;
        }


        this.triggerFlagsTypeId = triggerFlagsTypeId;
    }

    public static TriggerFlagsType valueOf(int triggerFlagsTypeId) {
        for (TriggerFlagsType triggerFlagsType : values()) {
            if (triggerFlagsType.triggerFlagsTypeId == triggerFlagsTypeId) {
                return triggerFlagsType;
            }
        }
        return null;
    }
}
