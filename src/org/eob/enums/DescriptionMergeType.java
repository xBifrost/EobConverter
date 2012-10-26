package org.eob.enums;

/**
 * User: Bifrost
 * Date: 10/26/12
 * Time: 1:05 PM
 */
public enum DescriptionMergeType {
    Prefix(""),
    Suffix(""),
    SuffixWithOf("of"),
    Replace("");

    public final String preposition;

    private DescriptionMergeType(String preposition) {
        this.preposition = preposition;
    }
}
