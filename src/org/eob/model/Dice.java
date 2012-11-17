package org.eob.model;

/**
 * User: Bifrost
 * Date: 11/15/12
 * Time: 9:46 PM
 */
public class Dice {
    int rolls;
    int sides;
    int base;

    /**
     * Example: 3T6+4 = 3 rolls, 6 sides, 4 base
     * @param rolls Number of dice rolls
     * @param sides Number of dice sides
     * @param base Base value
     */
    public Dice(int rolls, int sides, int base) {
        this.rolls = rolls;
        this.sides = sides;
        this.base = base;
    }
}
