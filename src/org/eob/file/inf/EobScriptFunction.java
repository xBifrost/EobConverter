package org.eob.file.inf;

/**
 * User: Bifrost
 * Date: 14.1.2013
 * Time: 18:09
 */
public class EobScriptFunction {
    public final String functionName;
    public final int addressStart;
    public final int addressEnd;

    public EobScriptFunction(String functionName, int addressStart, int addressEnd) {
        this.functionName = functionName;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
    }
}
