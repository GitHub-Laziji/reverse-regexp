package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;
import org.laziji.commons.rereg.model.BaseNode;

import java.util.List;

public class SingleNode extends BaseNode {

    public SingleNode(String expression) throws RegexpIllegalException {
        super(expression);
    }

    public SingleNode(List<String> expressionFragments) {
        super(expressionFragments);
    }

    @Override
    protected boolean test(List<String> expressionFragments) {
        return expressionFragments != null && expressionFragments.size() == 1;
    }

    @Override
    protected void init(List<String> expressionFragments) throws RegexpIllegalException {

    }

    @Override
    public String random() {
        return null;
    }
}
