package org.eob.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bifrost
 * Date: 10/22/12
 * Time: 7:53 PM
 */
public class Item {
    public final String notIdentifiedName;
    public final String identifiedName;
    public final Boolean glowMagic;
    public final Boolean cursed;
    public final Long imageId;
    public final ItemType itemType;
    public final SubItemType itemSubType;

    public static final List<Item> registeredItems = new ArrayList<Item>();

    private Item(String notIdentifiedName, String identifiedName, Boolean glowMagic, Boolean cursed, Long imageId, ItemType itemType, SubItemType itemSubType) {
        this.notIdentifiedName = notIdentifiedName;
        this.identifiedName = identifiedName;
        this.glowMagic = glowMagic;
        this.cursed = cursed;
        this.imageId = imageId;
        this.itemType = itemType;
        this.itemSubType = itemSubType;
    }

    public static Item getById(Long notIdentifiedNameId, Long identifiedNameId, Boolean glowMagic, Boolean cursed, Long imageId, ItemType itemType, int itemSubType) {
        Item newItem = new Item(ItemName.itemNames.get(notIdentifiedNameId).name, ItemName.itemNames.get(identifiedNameId).name,
                glowMagic, cursed, imageId, itemType, SubItemType.getSubItemById(itemType, itemSubType));

        for (Item registeredItem : registeredItems) {
            if (registeredItem.equals(newItem)) {
                return registeredItem;
            }
        }

        registeredItems.add(newItem);
        return newItem;
    }

    public String getDescription(boolean isIdentified) {
        if (isIdentified) {
            if (identifiedName.equals(notIdentifiedName)) {
                String result = notIdentifiedName;
                if (itemSubType.description.length() != 0) {
                    switch (itemSubType.descriptionMergeType) {
                        case Prefix:
                            result = itemSubType.description + " " + result;
                            break;
                        case Suffix:
                            result += " " + itemSubType.description;
                            break;
                        case SuffixWithOf:
                            result += " " + itemSubType.descriptionMergeType.preposition + " " + itemSubType.description;
                            break;
                        case Replace:
                            result = itemSubType.description;
                            break;
                    }
                }
                return result.trim();
            } else {
                return identifiedName;
            }
        } else {
            return notIdentifiedName;
        }
    }

    public String getElementType(boolean identified) {
        String result = itemType.elementType;
        if (itemSubType.elementType.length() != 0) {
            result += "_" + itemSubType.elementType;
        }
        if (!identified) {
            result += "_u";
        }
        return result.trim();
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (cursed != null ? !cursed.equals(item.cursed) : item.cursed != null) return false;
        if (glowMagic != null ? !glowMagic.equals(item.glowMagic) : item.glowMagic != null) return false;
        if (identifiedName != null ? !identifiedName.equals(item.identifiedName) : item.identifiedName != null)
            return false;
        if (imageId != null ? !imageId.equals(item.imageId) : item.imageId != null) return false;
        if (itemSubType != null ? !itemSubType.equals(item.itemSubType) : item.itemSubType != null) return false;
        if (itemType != null ? !itemType.equals(item.itemType) : item.itemType != null) return false;
        if (notIdentifiedName != null ? !notIdentifiedName.equals(item.notIdentifiedName) : item.notIdentifiedName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = notIdentifiedName != null ? notIdentifiedName.hashCode() : 0;
        result = 31 * result + (identifiedName != null ? identifiedName.hashCode() : 0);
        result = 31 * result + (glowMagic != null ? glowMagic.hashCode() : 0);
        result = 31 * result + (cursed != null ? cursed.hashCode() : 0);
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        result = 31 * result + (itemSubType != null ? itemSubType.hashCode() : 0);
        return result;
    }
}
