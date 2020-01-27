package org.laziji.commons.rereg.model;

public class OrdinaryNode extends BaseNode {

    private Node proxyNode;

    public OrdinaryNode(String expression) {
        super(expression);
    }

    @Override
    public void init(String expression) {

    }

    @Override
    public String random() {
        return proxyNode.random();
    }
}
