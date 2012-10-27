package org.eob.file;

import org.eob.file.pak.PakFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Bifrost
 * Date: 10/27/12
 * Time: 9:03 PM
 */
public class EobFiles {
    private Map<String, FileData> files = new HashMap<String, FileData>();

    public EobFiles(String eobPath) {
        File directory = new File(eobPath);
        File[] filesInDir = directory.listFiles();
        if (filesInDir == null) {
            return;
        }

        for (File file : filesInDir) {
            if (file.isFile()) {
                if (file.getName().toLowerCase().endsWith(".pak")) {
                    PakFile pakFile = new PakFile(file.getPath());
                    for (PakFile.FilePos filePos : pakFile.getFiles()) {
                        FileData fileData = new FileData();
                        fileData.name = filePos.name;
                        fileData.path = file.getPath() + " : " + filePos.name;
                        fileData.data = filePos.data;
                        files.put(filePos.name.toLowerCase(), fileData);
                    }
                } else {
                    FileData fileData = new FileData();
                    fileData.name = file.getName();
                    fileData.path = file.getPath();
                    files.put(file.getName().toLowerCase(), fileData);
                }
            }
        }
    }

    public byte[] getFile(String name) {
        FileData fileData = files.get(name.toLowerCase());
        if (fileData == null) {
            return null;
        }
        if (fileData.data != null) {
            return fileData.data;
        }

        return FileUtility.readFile(fileData.path);
    }
}
