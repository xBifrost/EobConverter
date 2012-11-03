package org.eob.file.cps;

import org.eob.ByteArrayUtility;
import org.eob.file.cps.enums.CpsCompressionType;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 10/28/12
 * Time: 3:48 PM
 */
public class CpsFile {
    private final byte[] uncompressed;

    private class CpsFileHeader {
        public final int fileSize;
        public final CpsCompressionType type;
        public final int uncompressedSize;
        public final int paletteSize;

        private CpsFileHeader(byte[] cpsFileBytes) {
            type = CpsCompressionType.valueByInt(ByteArrayUtility.getWord(cpsFileBytes, 2));
            fileSize = ByteArrayUtility.getWord(cpsFileBytes, 0) + type.fileSizePatch;
            uncompressedSize = ByteArrayUtility.getInteger(cpsFileBytes, 4);
            paletteSize = ByteArrayUtility.getWord(cpsFileBytes, 8);
        }
    }

    public CpsFile(byte[] cpsFileBytes) {
        CpsFileHeader header = new CpsFileHeader(cpsFileBytes);
        switch (header.type) {
            default:
            case Uncompressed:
                throw new UnsupportedOperationException("Compress method Uncompressed is not supported now.");
//                uncompressed = Arrays.copyOfRange(cpsFileBytes, 10, header.fileSize);
//                break;
            case Crunch1:
                throw new UnsupportedOperationException("Compress method Crunch1 is not supported now.");
            case Format2:
                throw new UnsupportedOperationException("Compress method Format2 is not supported now.");
            case Format3:
                throw new UnsupportedOperationException("Compress method Format3 is not supported now.");
            case Format80:
                uncompressed = uncompressByFormat80(header, cpsFileBytes);
                break;
        }
    }

    public byte[] getData() {
        return uncompressed;
    }

    private byte[] uncompressByFormat80(CpsFileHeader header, byte[] cpsFileBytes) {
        byte[] uncompressed = new byte[header.uncompressedSize];
        int srcPos = 10;
        int dstPos = 0;

        do {
            int command = ByteArrayUtility.getByte(cpsFileBytes, srcPos);
            srcPos++;
            if ((command & 0x80) == 0x80) { // Check 7. bit of command
                if ((command & 0x40) == 0x40) { // Check 6. bit of command
                    if (command == 0xFF) {
                        // Very large itself copy command 5Byte: 2.-3.Byte = count, 4.-5.Byte = Position
                        int count = ByteArrayUtility.getWord(cpsFileBytes, srcPos);
                        int from = ByteArrayUtility.getWord(cpsFileBytes, srcPos+2);
                        srcPos += 4;

                        for (int pos = from; pos < from + count; pos++) {
                            uncompressed[dstPos] = uncompressed[pos];
                            dstPos++;
                        }

                    } else if (command == 0xFE) {
                        // Fill command 4Byte: 2.-3.Byte = count, 4.Byte = value
                        int count = ByteArrayUtility.getWord(cpsFileBytes, srcPos);
                        byte value = cpsFileBytes[srcPos+2];
                        srcPos += 3;

                        for (int pos = 0; pos < count; pos++) {
                            uncompressed[dstPos] = value;
                            dstPos++;
                        }

                    } else {
                        // Large itself copy command 1.-6.bit = count-3, 7.-8.bit - value 11, 2.-3.Byte = Position
                        int count = (command & 0x3F) + 3;
                        int from = ByteArrayUtility.getWord(cpsFileBytes, srcPos);
                        srcPos += 2;

                        for (int pos = from; pos < from + count; pos++) {
                            uncompressed[dstPos] = uncompressed[pos];
                            dstPos++;
                        }
                    }

                } else {
                    // Copy command 1Byte: 1.-6.bit = count, 7.-8.bit - value 10
                    int count = command & 0x3F;
                    // End of file marker
                    if (count == 0) {
                        return uncompressed;
                    }

                    for (int pos = 0; pos < count; pos++) {
                        uncompressed[dstPos] = cpsFileBytes[srcPos];
                        dstPos++;
                        srcPos++;
                    }
                }

            } else {
                // Itself copy command 2Byte: 1.-11.bit = relative position, 12.-14.bit = count-3, 15bit - value 0
                // Count is bits 4-6 + 3
                int count = ((command & 0x7F) >> 4) + 3;
                // Position is bits 0-3, with bits 0-7 of next byte
                int relativePos = ((command & 0x0F) << 8) + ByteArrayUtility.getByte(cpsFileBytes, srcPos);
                srcPos++;

                // Starting position = Current pos. - relative position
                int from = dstPos - relativePos;
                for (int pos = from; pos < from + count; pos++) {
                    uncompressed[dstPos] = uncompressed[pos];
                    dstPos++;
                }
            }
        } while (true);
    }
}
