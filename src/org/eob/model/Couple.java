package org.eob.model;

/**
 * User: Bifrost
 * Date: 25.1.2013
 * Time: 20:19
 */
public class Couple<F, S> {
    public final F first;
    public final S second;

    public Couple(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
