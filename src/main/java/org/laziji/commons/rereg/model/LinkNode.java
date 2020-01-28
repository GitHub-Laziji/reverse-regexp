package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;

import java.util.List;

public class LinkNode extends BaseNode {
    public LinkNode(String expression) throws RegexpIllegalException {
        super(expression);
    }

    public LinkNode(List<String> expressionFragments){
        super(expressionFragments);
    }

    @Override
    protected void init(List<String> expressionFragments) throws RegexpIllegalException {

    }

    @Override
    public String random() {
        return null;
    }
}
