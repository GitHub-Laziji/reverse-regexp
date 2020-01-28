package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;

public interface Node {

    String getExpression();

    String random();

    boolean test();

    void init() throws RegexpIllegalException,;
}
