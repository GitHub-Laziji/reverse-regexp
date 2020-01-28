package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.UninitializedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LinkNode extends BaseNode {

    private List<Node> children;

    public LinkNode(String expression) throws RegexpIllegalException {
        super(expression);
    }

    public LinkNode(List<String> expressionFragments) {
        super(expressionFragments);
    }

    @Override
    protected boolean test(String expression, List<String> expressionFragments) {
        for (String fragment : expressionFragments) {
            if ("|".equals(fragment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void init(String expression, List<String> expressionFragments) throws RegexpIllegalException {
        children = new ArrayList<>();
        for (int i = 0; i < expressionFragments.size(); i++) {
            Node node;
            if (i + 1 < expressionFragments.size()) {
                node = new RepeatNode(Arrays.asList(expressionFragments.get(i), expressionFragments.get(i + 1)));
                if (node.test()) {
                    node.init();
                    children.add(node);
                    i++;
                    continue;
                }
            }
            node = new SingleNode(Collections.singletonList(expressionFragments.get(i)));
            node.init();
            children.add(node);
        }
    }

    @Override
    protected String random(String expression, List<String> expressionFragments) throws RegexpIllegalException, UninitializedException {
        StringBuilder value = new StringBuilder();
        for (Node node : children) {
            value.append(node.random());
        }
        return value.toString();
    }
}
