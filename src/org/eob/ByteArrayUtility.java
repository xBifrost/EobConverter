package org.eob;

/**
 * User: Bifrost
 * Date: 10/24/12
 * Time: 12:27 PM
 */
public class ByteArrayUtility {
    public static Integer getWord(byte[] pakFile, Integer position) {
        int filePosition = 0;
        for (int i = 0; i < 2; i++) {
            int shift = i * 8;
            filePosition += (pakFile[position + i] & 0x000000FF) << shift;
        }
        return filePosition;
    }

    public static String bytesToHex(byte[] bytes, int from, int size) {
        from = Math.max(0, from);
        size = Math.min(bytes.length - from, size);
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[2*size];
        int v;
        for (int j = 0; j < size; j++) {
            v = bytes[from+j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
