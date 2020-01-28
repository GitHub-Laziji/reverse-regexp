package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.UninitializedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OptionalNode extends BaseNode {

    private List<Node> children;

    public OptionalNode(String expression) throws RegexpIllegalException {
        super(expression);
    }

    protected OptionalNode(List<String> expressionFragments) {
        super(expressionFragments);
    }

    @Override
    protected boolean test(String expression, List<String> expressionFragments) {
        for (String fragment : expressionFragments) {
            if ("|".equals(fragment)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void init(String expression, List<String> expressionFragments) throws RegexpIllegalException {
        children = new ArrayList<>();
        List<String> subFragments = new ArrayList<>();
        for (String fragment : expressionFragments) {
            if ("|".equals(fragment)) {
                children.add(new OrdinaryNode(subFragments));
                subFragments = new ArrayList<>();
                continue;
            }
            subFragments.add(fragment);
        }
        children.add(new OrdinaryNode(subFragments));
    }

    @Override
    protected String random(String expression, List<String> expressionFragments) throws UninitializedException, RegexpIllegalException {
        return children.get(new Random().nextInt(children.size())).random();
    }
}
