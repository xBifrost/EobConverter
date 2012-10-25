package org.eob.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Bifrost
 * Date: 10/23/12
 * Time: 11:44 PM
 */
public class ItemName {
    Long id;
    String name;

    public static Map<Long, ItemName> itemNames = new LinkedHashMap<Long, ItemName>();

    private ItemName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void addItemName(Long id, String name) {
        itemNames.put(id, new ItemName(id, name));
    }
}
