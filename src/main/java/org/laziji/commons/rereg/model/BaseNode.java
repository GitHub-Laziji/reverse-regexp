package org.laziji.commons.rereg.model;

public abstract class BaseNode implements Node {

    private String expression;

    public BaseNode(String expression) {
        this.expression = expression;
    }

    public abstract void init(String expression);

    @Override
    public String getExpression() {
        return expression;
    }

}
