package org.eob.model;

import org.eob.EobLogger;
import org.eob.enums.DescriptionMergeType;

import java.util.*;

/**
 * User: Bifrost
 * Date: 10/23/12
 * Time: 10:47 PM
 */
public class SubItemType {
    public final long id;
    public final ItemType itemType;
    public final String elementType;
    public final String description;
    public final String info;
    public final DescriptionMergeType descriptionMergeType;

    public static final SubItemType NotSubtype = new SubItemType(0, null, "", "", "");

    public SubItemType(int id, ItemType itemType, String elementType, String description, DescriptionMergeType descriptionMergeType, String info) {
        this.id = id;
        this.itemType = itemType;
        this.elementType = elementType;
        this.description = description;
        this.descriptionMergeType = descriptionMergeType;
        this.info = info;
    }

    public SubItemType(int id, ItemType itemType, String elementType, String description, String info) {
        this(id, itemType, elementType, description, DescriptionMergeType.SuffixWithOf, info);
    }
}
