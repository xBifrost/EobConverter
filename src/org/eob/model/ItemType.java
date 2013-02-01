package org.eob.model;

import org.eob.enums.ClassType;
import org.eob.enums.ItemSlotType;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Bifrost
 * Date: 10/23/12
 * Time: 10:11 PM
 */
public class ItemType {
    public final boolean unknownType;
    public final long itemTypeId;
    public final Set<ItemSlotType> itemSlotSupported = new HashSet<ItemSlotType>();
    public final Set<ItemSlotType> itemSlotActivate = new HashSet<ItemSlotType>();
    public final int addArmorClass;
    public final Set<ClassType> professionAllowed = new HashSet<ClassType>();
    public final int handCount;
    public final Dice damageVsSmall;
    public final Dice damageVsBig;
    public final int unknown1;
    public final int unknown2;
    public final int unknown3;

    public String elementType;
    public String description;

    public ItemType(long itemTypeId) {
        this.unknownType = true;
        this.itemTypeId = itemTypeId;
        this.elementType = "unknown_" + itemTypeId;
        this.description = "Unknown " + itemTypeId;
        this.addArmorClass = 0;
        this.handCount = 0;
        this.damageVsSmall = null;
        this.damageVsBig = null;
        this.unknown1 = 0;
        this.unknown2 = 0;
        this.unknown3 = 0;
    }

    public ItemType(long itemTypeId, String elementType, String description) {
        this.unknownType = false;
        this.itemTypeId = itemTypeId;
        this.elementType = elementType;
        this.description = description;
        this.addArmorClass = 0;
        this.handCount = 0;
        this.damageVsSmall = null;
        this.damageVsBig = null;
        this.unknown1 = 0;
        this.unknown2 = 0;
        this.unknown3 = 0;
    }

    public ItemType(long itemTypeId, Set<ItemSlotType> itemSlotSupported, Set<ItemSlotType> itemSlotActivate, int addArmorClass, Set<ClassType> professionAllowed, int handCount, Dice damageVsSmall, Dice damageVsBig, int unknown1, int unknown2, int unknown3) {
        this.unknownType = false;
        this.itemTypeId = itemTypeId;
        this.itemSlotSupported.addAll(itemSlotSupported);
        this.itemSlotActivate.addAll(itemSlotActivate);
        this.addArmorClass = addArmorClass;
        this.professionAllowed.addAll(professionAllowed);
        this.handCount = handCount;
        this.damageVsSmall = damageVsSmall;
        this.damageVsBig = damageVsBig;
        this.unknown1 = unknown1;
        this.unknown2 = unknown2;
        this.unknown3 = unknown3;
    }

    public ItemType setExternalData(String elementType, String description) {
        this.elementType = elementType;
        this.description = description;
        return this;
    }
}
