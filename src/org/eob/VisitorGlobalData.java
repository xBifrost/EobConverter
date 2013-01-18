package org.eob;

import org.eob.file.inf.EobScriptFunction;

import java.util.Map;

/**
 * User: Bifrost
 * Date: 16.1.2013
 * Time: 22:51
 */
public class VisitorGlobalData {
    Map<Integer, Integer> positionMap;
    Map<Integer, EobScriptFunction> scriptFunctionMap;
    EobGlobalData eobGlobalData;

    public VisitorGlobalData(Map<Integer, Integer> positionMap, Map<Integer, EobScriptFunction> scriptFunctionMap, EobGlobalData eobGlobalData) {
        this.positionMap = positionMap;
        this.scriptFunctionMap = scriptFunctionMap;
        this.eobGlobalData = eobGlobalData;
    }
}
