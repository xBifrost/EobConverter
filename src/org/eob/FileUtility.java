package org.eob;

import java.io.*;

/**
 * User: Bifrost
 * Date: 10/24/12
 * Time: 10:08 AM
 */
public class FileUtility {
    public static byte[] readFile(String aInputFileName) {
        System.out.println("Reading in binary file named : " + aInputFileName);
        File file = new File(aInputFileName);
        System.out.println("File size: " + file.length());
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
                System.out.println("Num bytes read: " + totalBytesRead);
            } finally {
                System.out.println("Closing input stream.");
                if (input != null) {
                    input.close();
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return result;
    }
}
