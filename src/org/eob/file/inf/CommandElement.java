package org.eob.file.inf;

/**
 * User: Bifrost
 * Date: 12.1.2013
 * Time: 22:46
 */
public interface CommandElement {
    void accept(CommandVisitor visitor);
}
