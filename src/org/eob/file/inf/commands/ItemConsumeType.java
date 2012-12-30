package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 30.12.2012
 * Time: 20:25
 */
public enum ItemConsumeType {
    AtMousePointer(0xFF),
    AtPositionWithAnyType(0xFE),
    AtPosition(-0x01);

    private int ItemConsumeTypeId;

    ItemConsumeType(int ItemConsumeTypeId) {
        this.ItemConsumeTypeId = ItemConsumeTypeId;
    }

    public static ItemConsumeType valueOf(int ItemConsumeTypeId) {
        for (ItemConsumeType itemConsumeType : values()) {
            if (itemConsumeType.ItemConsumeTypeId == ItemConsumeTypeId) {
                return itemConsumeType;
            }
        }
        return AtPosition;
    }
}
