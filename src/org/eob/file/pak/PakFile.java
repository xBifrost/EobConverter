package org.eob.file.pak;

import org.eob.file.FileUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Bifrost
 * Date: 10/27/12
 * Time: 12:34 AM
 */
public class PakFile {
    private final List<FilePos> files = new ArrayList<FilePos>();

    public class FilePos {
        public int pos;
        public int size;
        public String name;
        public byte[] data;
    }

    public PakFile(String fileName) {
        byte[] pakData = FileUtility.readFile(fileName);
        parse(pakData);
    }

    public List<FilePos> getFiles() {
        return files;
    }

    private void parse(byte[] pakData) {
        Integer headerSize = null;
        FilePos last = null;

        Integer pakPosition = 0;
        do {
            FilePos filePos = new FilePos();
            filePos.pos = getFilePosition(pakData, pakPosition);
            pakPosition += 4;
            filePos.name = getFileName(pakData, pakPosition);
            pakPosition += filePos.name.length() + 1;
            if (headerSize == null) {
                headerSize = filePos.pos;
            }
            if (last != null) {
                last.size = filePos.pos - last.pos;
            }
            files.add(filePos);
            last = filePos;
        } while (pakPosition+4 < headerSize);

        last.size = pakData.length - last.pos;

        for (FilePos file : files) {
            file.data = Arrays.copyOfRange(pakData, file.pos, file.pos + file.size);
        }
    }

    private static int getFilePosition(byte[] pakFile, Integer position) {
        int filePosition = 0;
        for (int i = 0; i < 4; i++) {
            int shift = i * 8;
            filePosition += (pakFile[position + i] & 0x000000FF) << shift;
        }
        return filePosition;
    }

    private static String getFileName(byte[] pakFile, Integer position) {
        String fileName = "";
        while (pakFile[position] != 0) {
            fileName += (char) pakFile[position++];
        }
        return fileName;
    }
}
