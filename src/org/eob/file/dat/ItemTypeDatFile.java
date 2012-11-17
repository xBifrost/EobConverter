package org.eob.file.dat;

import org.eob.ByteArrayUtility;
import org.eob.EobLogger;
import org.eob.enums.ClassType;
import org.eob.enums.ItemSlotType;
import org.eob.model.Dice;
import org.eob.model.ItemType;

import java.util.*;

/**
 * User: Bifrost
 * Date: 11/15/12
 * Time: 8:49 PM
 */
public class ItemTypeDatFile {
    public final byte[] uncompressed;
    public final Map<Long, ItemType> itemTypeList = new LinkedHashMap<Long, ItemType>();

    public ItemTypeDatFile(byte[] itemTypeDatFileBytes) {
        uncompressed = itemTypeDatFileBytes;

        int pos = 0;
        int count = ByteArrayUtility.getWord(itemTypeDatFileBytes, pos);
        pos += 2;

        for (int i = 0; i < count; i++) {
            itemTypeList.put((long) i, new ItemType(
                    i,
                    ItemSlotType.parseItemSlotType(ByteArrayUtility.getWord(itemTypeDatFileBytes, pos)),
                    ItemSlotType.parseItemSlotType(ByteArrayUtility.getWord(itemTypeDatFileBytes, pos + 2)),
                    itemTypeDatFileBytes[pos + 4],
                    ClassType.parseClassType(ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 5)),
                    ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 6),
                    new Dice(ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 7),
                            ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 8),
                            ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 9)),
                    new Dice(ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 10),
                            ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 11),
                            ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 12)),
                    ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 13),
                    ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 14),
                    ByteArrayUtility.getByte(itemTypeDatFileBytes, pos + 15)
            ));
            pos += 16;
        }
    }

    public ItemType getById(long id) {
        ItemType itemType = itemTypeList.get(id);
        if (itemType == null) {
            itemType = new ItemType(id);
            itemTypeList.put(id, itemType);
        }

        return itemType;
    }

    public int getUnknownItemsCount() {
        int count = 0;
        for (ItemType itemType : itemTypeList.values()) {
            if (itemType.unknownType) {
                count++;
            }
        }

        return count;
    }

    public void printItemTypes() {
        for (ItemType itemType : itemTypeList.values()) {
            EobLogger.println(String.format("ItemType %02d: Name=%-25s, Possible Slots: %s, Active: %s, AC: %d, Proffesion: %s, HandCount: %d",
                    itemType.itemTypeId, itemType.description == null ? "" : itemType.description,
                    getSlotSetString(itemType.itemSlotSupported), getSlotSetString(itemType.itemSlotActivate),
                    itemType.addArmorClass, getProfessionString(itemType.professionAllowed), itemType.handCount));
        }
    }

    private String getSlotSetString(Set<ItemSlotType> itemSlotSupported) {
        String result = "";
        for (ItemSlotType itemSlotType : itemSlotSupported) {
//            result += (result.length() == 0 ? "" : ", ");
            switch (itemSlotType) {
                case None:
                    break;
                case Quiver:
                    result += "Qu";
                    break;
                case Armour:
                    result += "Ar";
                    break;
                case Bracers:
                    result += "Br";
                    break;
                case Hand:
                    result += "Ha";
                    break;
                case Boots:
                    result += "Bo";
                    break;
                case Helmet:
                    result += "He";
                    break;
                case Necklace:
                    result += "Ne";
                    break;
                case Belt:
                    result += "Be";
                    break;
                case Ring:
                    result += "Ri";
                    break;
            }
        }
        return result;
    }

    private String getProfessionString(Set<ClassType> professionSupported) {
        String result = "";
        for (ClassType classType : professionSupported) {
//            result += (result.length() == 0 ? "" : ", ");
            switch (classType) {
                case None:
                    break;
                case Fighter:
                    result += "F";
                    break;
                case Mage:
                    result += "M";
                    break;
                case Cleric:
                    result += "C";
                    break;
                case Thief:
                    result += "T";
                    break;
            }
        }
        return result;
    }
}
