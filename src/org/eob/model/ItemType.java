package org.eob.model;

import org.eob.EobLogger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Bifrost
 * Date: 10/23/12
 * Time: 10:11 PM
 */
public class ItemType {
    private final static String DEFAULT_BASE_OBJECT = "rock";

    public final boolean unknownType;
    public final long itemTypeId;
    public final String elementType;
    public final String description;
    public final String baseObject;

    private static final Map<Long, ItemType> registeredItemTypes = new LinkedHashMap<Long, ItemType>();
    private static int unknownItemsCount = 0;

    public ItemType(long itemTypeId, String elementType, String description, String baseObject) {
        this(itemTypeId, elementType, description, baseObject, false);
    }
    public ItemType(long itemTypeId, String elementType, String description, String baseObject, boolean unknownType) {
        this.itemTypeId = itemTypeId;
        this.elementType = elementType;
        this.description = description;
        this.baseObject = baseObject;
        this.unknownType = unknownType;
        registeredItemTypes.put(itemTypeId, this);
    }

    public static ItemType getById(long itemType) {
        ItemType result = registeredItemTypes.get(itemType);
        if (result == null) {
            unknownItemsCount++;
            result = new ItemType(itemType, "unknown_" + itemType, "Unknown " + itemType, DEFAULT_BASE_OBJECT , true);
            EobLogger.println("Unknown item type: " + itemType);
        }

        return result;
    }

    public static int getItemsCount() {
        return registeredItemTypes.size();
    }

    public static int getUnknownItemsCount() {
        return unknownItemsCount;
    }
}
