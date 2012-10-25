package org.eob.model;

import java.util.*;

/**
 * User: Bifrost
 * Date: 10/23/12
 * Time: 10:47 PM
 */
public class SubItemType {
    private static final String defaultPreposition = "of";
    public final long id;
    public final ItemType itemType;
    public final String elementType;
    public final String description;
    public final String info;
    public final String preposition;

    private static Map<ItemType, Map<Long, SubItemType>> subItems = new HashMap<ItemType, Map<Long, SubItemType>>();

    static final SubItemType NotSubtype = new SubItemType(0, null, "", "", "");

    public SubItemType(int id, ItemType itemType, String elementType, String description, String preposition, String info) {
        this.id = id;
        this.itemType = itemType;
        this.elementType = elementType;
        this.description = description;
        this.preposition = preposition;
        this.info = info;

        if (itemType == null) {
            return;
        }

        Map<Long, SubItemType> subItemTypeMap = subItems.get(itemType);
        if (subItemTypeMap == null) {
            subItemTypeMap = new LinkedHashMap<Long, SubItemType>();
            subItems.put(itemType, subItemTypeMap);
        }
        subItemTypeMap.put((long) id, this);
    }

    public SubItemType(int id, ItemType itemType, String elementType, String description, String info) {
        this(id, itemType, elementType, description, defaultPreposition, info);
    }

    public static SubItemType getSubItemById(ItemType itemType, int id) {
        if (id == 0) {
            return NotSubtype;
        }

        Map<Long, SubItemType> typeMap = subItems.get(itemType);
        SubItemType subItemType = typeMap != null ? typeMap.get((long) id) : null;
        if (subItemType == null) {
            subItemType = new SubItemType(id, itemType, "unknown_" + id, "Unknown " + id, "");
            System.out.println("Unknown sub type of item type. (Item type: " + itemType.elementType + ", SubType: " + id + ")");
        }
        return subItemType;
    }
}
