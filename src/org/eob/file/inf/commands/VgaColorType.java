package org.eob.file.inf.commands;

/**
 * User: Bifrost
 * Date: 29.12.2012
 * Time: 00:36
 */
public enum VgaColorType {
    Black(0, 0x00, 0x00, 0x00),
    Blue(1, 0x00, 0x00, 0x80),
    Green(2, 0x00, 0x80, 0x00),
    Cyan(3, 0x00, 0x80, 0x80),
    Red(4, 0x80, 0x00, 0x00),
    Magenta(5, 0x80, 0x00, 0x80),
    Brown(6, 0x80, 0x80, 0x00),
    LightGray(7, 0xC0, 0xC0, 0xC0),
    DarkGray(8, 0x80, 0x80, 0x80),
    LightBlue(9, 0x00, 0x00, 0xFF),
    LightGreen(10, 0x00, 0xFF, 0x00),
    LightCyan(11, 0x00, 0xFF, 0xFF),
    LightRed(12, 0xFF, 0x00, 0x00),
    LightMagenta(13, 0xFF, 0x00, 0xFF),
    Yellow(14, 0xFF, 0xFF, 0x00),
    White(15, 0xFF, 0xFF, 0xFF);

    public final int vgaColorId;
    public final int red;
    public final int green;
    public final int blue;

    VgaColorType(int vgaColorId, int red, int green, int blue) {
        this.vgaColorId = vgaColorId;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static VgaColorType valueOf(int colorId) {
        for (VgaColorType vgaColorType : values()) {
            if (vgaColorType.vgaColorId == colorId) {
                return vgaColorType;
            }
        }

        return null;
    }
}
