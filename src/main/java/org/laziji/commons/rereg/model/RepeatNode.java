package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;

import java.util.List;

public class RepeatNode extends BaseNode {

    public RepeatNode(String expression) throws RegexpIllegalException {
        super(expression);
    }

    public RepeatNode(List<String> expressionFragments) {
        super(expressionFragments);
    }

    @Override
    protected boolean test(List<String> expressionFragments) {
        if (expressionFragments.size() == 2) {
            String lastToken = expressionFragments.get(1);
            return lastToken != null
                    && ("+".equals(lastToken) || "*".equals(lastToken) || "?".equals(lastToken) || lastToken.startsWith("{"));
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
