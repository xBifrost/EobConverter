package org.eob;

import javax.swing.*;

/**
 * User: Bifrost
 * Date: 11/8/12
 * Time: 9:49 PM
 */
public class EobLogger {
    public static JTextArea output = null;

    public static void println(String text) {
        if (output == null) {
            System.out.println(text);
        } else {
            output.append(text + "\n");
        }
    }

    public static void print(String text) {
        if (output == null) {
            System.out.print(text);
        } else {
            output.append(text);
        }
    }

}
