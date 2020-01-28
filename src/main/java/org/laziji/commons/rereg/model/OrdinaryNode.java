package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;

import java.util.ArrayList;
import java.util.List;

public class OrdinaryNode extends BaseNode {

    private Node proxyNode;

    public OrdinaryNode(String expression) throws RegexpIllegalException, TypeNotMatchException {
        super(expression);
    }


    @Override
    public void init(List<String> expressionFragments) throws RegexpIllegalException {
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
    public String random() {
        if (proxyNode == null) {
            return "";
        }
        return proxyNode.random();
    }

}
