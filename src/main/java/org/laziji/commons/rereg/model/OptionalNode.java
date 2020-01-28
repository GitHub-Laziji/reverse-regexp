package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;

import java.util.List;

public class OptionalNode extends BaseNode {

    public OptionalNode(String expression) throws RegexpIllegalException {
        super(expression);
    }

    public OptionalNode(List<String> expressionFragments) {
        super(expressionFragments);
    }

    @Override
    protected boolean test(List<String> expressionFragments) {
        for (String snippet : expressionFragments) {
            if ("|".equals(snippet)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void init(List<String> expressionFragments) throws RegexpIllegalException {

    }

    @Override
    public String random() {
        return null;
    }
}
