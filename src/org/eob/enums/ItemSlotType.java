package org.eob.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Bifrost
 * Date: 11/15/12
 * Time: 9:04 PM
 */
public enum ItemSlotType {
    None(0),
    Quiver(1),
    Armour(2),
    Bracers(4),
    Hand(8),
    Boots(16),
    Helmet(32),
    Necklace(64),
    Belt(128),
    Ring(256);

    private int value;

    private ItemSlotType(int value) {
        this.value = value;
    }

    public static Set<ItemSlotType> parseItemSlotType(int inventoryBits) {
        Set<ItemSlotType> result = new HashSet<ItemSlotType>();
        for (ItemSlotType itemSlotType : values()) {
            if (itemSlotType.value > 0 && (inventoryBits & itemSlotType.value) == itemSlotType.value) {
                result.add(itemSlotType);
            }
        }

        return result;
    }
}