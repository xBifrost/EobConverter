package org.eob.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Bifrost
 * Date: 11/15/12
 * Time: 9:03 PM
 */
public enum ClassType {
    None(0),
    Fighter(1),
    Mage(2),
    Cleric(4),
    Thief(8);

    private int classValue;

    private ClassType(int classValue) {
        this.classValue = classValue;
    }

    public static Set<ClassType> parseClassType(int classBits) {
        Set<ClassType> result = new HashSet<ClassType>();
        for (ClassType classType : values()) {
            if (classType.classValue > 0 && (classBits & classType.classValue) == classType.classValue) {
                result.add(classType);
            }
        }

        return result;
    }
}
