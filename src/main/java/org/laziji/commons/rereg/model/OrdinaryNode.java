package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;
import org.laziji.commons.rereg.exception.UninitializedException;

import java.util.List;

public class OrdinaryNode extends BaseNode {

    private Node proxyNode;

    public OrdinaryNode(String expression) throws RegexpIllegalException, TypeNotMatchException {
        super(expression);
    }

    OrdinaryNode(List<String> expressionFragments) throws RegexpIllegalException, TypeNotMatchException {
        super(expressionFragments);
    }

    @Override
    public void init(String expression, List<String> expressionFragments)
            throws RegexpIllegalException, TypeNotMatchException {
        if (expressionFragments.size() == 0) {
            return;
        }
        Node[] nodes = new Node[]{
                new OptionalNode(expressionFragments, false),
                new SingleNode(expressionFragments, false),
                new RepeatNode(expressionFragments, false),
                new LinkNode(expressionFragments, false)
        };
        for (Node node : nodes) {
            if (node.test()) {
                proxyNode = node;
                proxyNode.init();
                break;
            }
        }
    }

    @Override
    protected String random(String expression, List<String> expressionFragments)
            throws UninitializedException, RegexpIllegalException {
        if (proxyNode == null) {
            return "";
        }
        return proxyNode.random();
    }

}
