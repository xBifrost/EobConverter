package org.eob.file.cps.enums;

import org.eob.EobLogger;

/**
 * User: Bifrost
 * Date: 10/28/12
 * Time: 3:53 PM
 */
public enum CpsCompressionType {
    Uncompressed(0, 2),
    Crunch1(1, 0),
    Format2(2, 0),
    Format3(3, 0),
    Format80(4, 2);

    public final int compressionId;
    public final int fileSizePatch;

    CpsCompressionType(int compressionId, int fileSizePatch) {
        this.compressionId = compressionId;
        this.fileSizePatch = fileSizePatch;
    }

    public static CpsCompressionType valueByInt(int intValue) {
        for (CpsCompressionType type : values()) {
            if (type.compressionId == intValue) {
                return type;
            }
        }

        EobLogger.println("Unsupported compressionType: " + intValue);
        return Uncompressed;
    }
}
