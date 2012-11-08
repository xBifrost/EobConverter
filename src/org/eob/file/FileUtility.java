package org.eob.file;

import org.eob.EobLogger;

import java.io.*;

/**
 * User: Bifrost
 * Date: 10/24/12
 * Time: 10:08 AM
 */
public class FileUtility {
    public static byte[] readFile(String inputFileName, boolean debug) {
        if (debug) {
            EobLogger.println("Reading in binary file with name: " + inputFileName + " ...");
        }
        File file = new File(inputFileName);
        if (debug) {
            EobLogger.println("File size: " + file.length());
        }
        byte[] result = new byte[(int) file.length()];
        try {
            InputStream input = null;
            try {
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                while (totalBytesRead < result.length) {
                    int bytesRemaining = result.length - totalBytesRead;
                    //input.read() returns -1, 0, or more :
                    int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
                    if (bytesRead > 0) {
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }
                if (debug) {
                    EobLogger.println("Num bytes read: " + totalBytesRead);
                }
            } finally {
                if (debug) {
                    EobLogger.println("Closing input stream.");
                }
                if (input != null) {
                    input.close();
                }
            }
        } catch (FileNotFoundException ex) {
            EobLogger.println("File not found: " + inputFileName);
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    public static void writeFile(String outputFileName, byte[] data, boolean debug) {
        if (debug) {
            EobLogger.println("Writing binary file with name: " + outputFileName + " ...");
        }
        try {
            OutputStream output = null;
            try {
                output = new BufferedOutputStream(new FileOutputStream(outputFileName));
                output.write(data, 0, data.length);
            } finally {
                if (output != null) {
                    output.close();
                }
            }
        } catch (FileNotFoundException ex) {
            EobLogger.println("File not found.");
        } catch (IOException ex) {
            EobLogger.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
