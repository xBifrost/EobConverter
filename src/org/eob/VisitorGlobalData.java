package org.eob;

import org.eob.file.inf.EobScriptFunction;

import java.util.Map;

/**
 * User: Bifrost
 * Date: 16.1.2013
 * Time: 22:51
 */
public class VisitorGlobalData {
    public final int levelId;
    public final Map<Integer, Integer> positionMap;
    public final Map<Integer, EobScriptFunction> scriptFunctionMap;
    public final EobGlobalData eobGlobalData;

    public VisitorGlobalData(int levelId, Map<Integer, Integer> positionMap, Map<Integer, EobScriptFunction> scriptFunctionMap, EobGlobalData eobGlobalData) {
        this.levelId = levelId;
        this.positionMap = positionMap;
        this.scriptFunctionMap = scriptFunctionMap;
        this.eobGlobalData = eobGlobalData;
    }
}
