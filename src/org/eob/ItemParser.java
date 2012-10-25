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
    private final boolean debug;

    private List<ItemObject> items = new ArrayList<ItemObject>();
    private Map<Long, ItemObject> usedItems = new LinkedHashMap<Long, ItemObject>();

    public ItemParser(String fileName, boolean debug) {
        this.debug = debug;
        itemsData = FileUtility.readFile(fileName);
    }

    public void parseFile(String debugShowOnlyItemName) {
        System.out.println("Parsing item data");

        int itemsNum = ByteArrayUtility.getWord(itemsData, 0); // first two bytes specifies number of records
        System.out.println("Found " + itemsNum + " items entries.");

        // Get item names
        int offset = 14 * itemsNum + 2;
        int namesNum = ByteArrayUtility.getWord(itemsData, offset);
        offset += 2;
        System.out.println("Found " + namesNum + " item names.");
        for (int i = 0; i < namesNum; i++) {
            byte[] nameBytes = Arrays.copyOfRange(itemsData, offset, offset + 34);
            String name = new String(nameBytes);
            ItemName.addItemName((long) i, name.trim());
            offset += 35; // each name takes 35 bytes
        }

        // Get items
        offset = 2;
        for (int i = 0; i < itemsNum; i++) {
            ItemObject itemObject = new ItemObject(Arrays.copyOfRange(itemsData, offset, offset + 14));
            offset += 14;

            // Ignore empty objects
            if (itemObject.item.getDescription(true).equals("NULL")) {
                continue;
            }

            items.add(itemObject);
            if (itemObject.objectId != 0) {
                usedItems.put(itemObject.objectId, itemObject);
            }
        }

        System.out.println("Found " + items.size() + " items with unique " +
                ItemName.itemNames.size() + " names");

        if (debug) {
            printItems(debugShowOnlyItemName);
        }
    }

    private void printItems(String itemName) {
        for (int pos = 0; pos < items.size(); pos++) {
            if (itemName == null || itemName.equals("") || items.get(pos).item.identifiedName.toLowerCase().contains(itemName.toLowerCase())) {
                System.out.println(String.format("Item %03d: %s", pos, itemToText(pos * 14 + 2, items.get(pos))));
            }
        }
    }

    String itemToText(int pos, ItemObject item) {
        return String.format("Name=%-35s, (l=%02d, x=%02d, y=%02d, c=%d) [G:%s I:%s C:%s] itemId=0x%02x, count=%02d (debug info: offset=0x%04x hex=%s)",
                item.item.getDescription(true), item.level, item.x, item.y, item.inSquarePosition.itemPosition,
                item.item.glowMagic ? "1" : "0", item.identified ? "1" : "0", item.item.cursed ? "1" : "0",
                item.item.itemType.itemTypeId, item.countValue,
                pos, ByteArrayUtility.bytesToHex(item.orig, 0, 14));
    }

    public int getItemsCount() {
        return items.size();
    }

    public Set<ItemObject> getItemSet() {
        return new HashSet<ItemObject>(items);
    }

    public Set<ItemObject> getUsedItemSet() {
        return new HashSet<ItemObject>(usedItems.values());
    }
}