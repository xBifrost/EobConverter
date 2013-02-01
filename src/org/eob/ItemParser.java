package org.eob;

import org.eob.model.ItemName;
import org.eob.model.ItemObject;

import java.util.*;

/**
 * Eye of the Beholder ItemConverter<br/>
 * This goal of this tool is to extract data about available items from EOB files.<br/>
 * <p/>
 * licence: GNU GPL v2 or later
 *
 * @author Tomek "Thomson" Mrugalski, Bifrost
 */
public class ItemParser {
    private final byte[] itemsData;
    private final EobGlobalData eobGlobalData;
    private final Settings settings;

    private Map<Integer, ItemObject> items = new LinkedHashMap<Integer, ItemObject>();

    public ItemParser(byte[] itemsData, EobGlobalData eobGlobalData, Settings settings) {
        this.itemsData = itemsData;
        this.eobGlobalData = eobGlobalData;
        this.settings = settings;
    }

    public void parseFile() {
        if (settings.debug) {
            EobLogger.println("Parsing item data");
        }

        int itemsNum = ByteArrayUtility.getWord(itemsData, 0); // first two bytes specifies number of records
        if (settings.debug) {
            EobLogger.println("Found " + itemsNum + " items entries.");
        }

        // Get item names
        int offset = 14 * itemsNum + 2;
        int namesNum = ByteArrayUtility.getWord(itemsData, offset);
        offset += 2;
        if (settings.debug) {
            EobLogger.println("Found " + namesNum + " item names.");
        }
        for (int i = 0; i < namesNum; i++) {
            byte[] nameBytes = Arrays.copyOfRange(itemsData, offset, offset + 34);
            String name = new String(nameBytes);
            ItemName.addItemName((long) i, name.trim());
            offset += 35; // each name takes 35 bytes
        }

        // Get items
        offset = 2;
        for (int itemIndex = 0; itemIndex < itemsNum; itemIndex++) {
            ItemObject itemObject = new ItemObject(itemIndex, Arrays.copyOfRange(itemsData, offset, offset + 14), eobGlobalData.itemTypeDatFile, eobGlobalData);
            offset += 14;

            // Ignore empty objects
            if (itemObject.item.getDescription(true).equals("NULL")) {
                continue;
            }

            items.put(itemIndex, itemObject);
        }

        if (settings.debug) {
            EobLogger.println("Found " + items.size() + " items with unique " + ItemName.itemNames.size() + " names");
        }

        if (settings.showItems) {
            printItems(settings.showOnlyItemName);
        }
    }

    private void printItems(String itemName) {
        for (Map.Entry<Integer, ItemObject> entry : items.entrySet()) {
            if (itemName == null || itemName.equals("") || entry.getValue().item.identifiedName.toLowerCase().contains(itemName.toLowerCase())) {
                EobLogger.println(String.format("Item %03d: %s", entry.getKey(), itemToText(entry.getKey() * 14 + 2, entry.getValue())));
            }
        }
    }

    String itemToText(int pos, ItemObject item) {
        return String.format("Name=%-35s, (l=%02d, x=%02d, y=%02d, c=%d) [G:%s I:%s C:%s] itemId=0x%02x, count=%02d (debug info: offset=0x%04x hex=%s)",
                item.item.getDescription(true), item.level, item.x, item.y, item.inSquarePosition.itemPosition,
                item.item.glowMagic ? "1" : "0", item.identified ? "1" : "0", item.item.cursed ? "1" : "0",
                item.item.itemType.itemTypeId, item.item.initialCountValue,
                pos, ByteArrayUtility.bytesToHex(item.orig, 0, 14));
    }

    public int getItemsCount() {
        return items.size();
    }

    public Set<ItemObject> getItemSet() {
        return new LinkedHashSet<ItemObject>(items.values());
    }

    public ItemObject getItemByIndex(int itemIndex) {
        return items.get(itemIndex);
    }
}