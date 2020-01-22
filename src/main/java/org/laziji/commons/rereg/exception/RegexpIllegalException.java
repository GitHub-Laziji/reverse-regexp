package org.laziji.commons.rereg.exception;

public class RegexpIllegalException extends Exception {

    public RegexpIllegalException() {
        super();
    }

    public RegexpIllegalException(String regexp, int index) {
        super(String.format("regexp: %s, index: %d", regexp, index));
    }
}
