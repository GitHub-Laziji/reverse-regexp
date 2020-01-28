package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.UninitializedException;

import java.util.List;

public class OrdinaryNode extends BaseNode {

    private Node proxyNode;

    public OrdinaryNode(String expression) throws RegexpIllegalException {
        super(expression);
        init();
    }

    protected OrdinaryNode(List<String> expressionFragments) throws RegexpIllegalException {
        super(expressionFragments);
        init();
    }

    @Override
    public void init(String expression, List<String> expressionFragments) throws RegexpIllegalException {
        if (expressionFragments.size() == 0) {
            return;
        }
        Node[] nodes = new Node[]{
                new OptionalNode(expressionFragments),
                new SingleNode(expressionFragments),
                new RepeatNode(expressionFragments),
                new LinkNode(expressionFragments)
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
    protected String random(String expression, List<String> expressionFragments) throws UninitializedException, RegexpIllegalException {
        if (proxyNode == null) {
            return "";
        }
        return proxyNode.random();
    }

}
