package org.eob.model;

import org.eob.enums.InSquarePositionType;

/**
 * User: Bifrost
 * Date: 10/23/12
 * Time: 10:12 PM
 */
public class ItemObject {
    /**
     * Byte 00 - Id of not identified name string
     * Byte 01 - Id of identified name string (Value 01 -> Use Byte 00)
     * Byte 02 - Bit 0x80 - Glow magic, Bit 0x40 - Is identified, Bit 0x20 - Is cursed, 0x1F - count value (wands, ...)
     * Byte 03 - Image Id
     * Byte 04 - Item type (Axe, Sword, Mage scroll, ...)
     * Byte 05 - Sub position (in square) EoB1: 0-3 > Floor NW, NE, SW, SE; 4-7 -> Wall N, E, S, W; 8 -> Alcove,
     * Byte 06-07 - Position in maze: x + y*32
     * Byte 08-09 - Object ID
     * Byte 10-11 - Object ID
     * Byte 12 - maze level
     * Byte 13 - Item sub type (scrolls, potions, wand,...)
     */
    final public byte[] orig;

    final public Item item;
    final public Boolean identified;
    final public Long countValue;
    final public InSquarePositionType inSquarePosition;
    final public int x;
    final public int y;
    final public long objectId;
    final public int level;

    public ItemObject(byte[] orig) {
        this.orig = orig;

        item = Item.getById(
                (long) (orig[0] & 0xFF),
                orig[1] != 1 ? (long) (orig[1] & 0xFF) : (long) (orig[0] & 0xFF),
                (orig[2] & 0x80) == 0x80,
                (orig[2] & 0x20) == 0x20,
                (long) (orig[3] & 0xFF),
                ItemType.getById((long) (orig[4] & 0xFF)),
                orig[13]
        );

        identified = (orig[2] & 0x40) == 0x40;
        countValue = (long) (orig[2] & 0x1F);
        inSquarePosition = InSquarePositionType.getItemPositionById(orig[5] & 0xFF);

        int coords = (orig[6] & 0xff) + (orig[7] & 0xff) * 256;
        x = (coords >> 5) & 0x1f;
        y = (coords) & 0x1f;

        if (orig[8] != orig[10] || orig[9] != orig[11]) {
            System.out.println("Different values of objectIds: (" + ((orig[8] & 0xFF) + 0x0100 * (orig[9] & 0xFF)) + "," + ((orig[10] & 0xFF) + 0x0100 * (orig[11] & 0xFF)) + ")");
        }
        objectId = (orig[8] & 0xFF) + 0x0100 * (orig[9] & 0xFF);
        level = orig[12] & 0xFF;
    }
}
