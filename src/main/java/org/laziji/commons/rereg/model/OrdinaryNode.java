package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.RegexpDataGenerator;
import org.laziji.commons.rereg.exception.RegexpIllegalException;

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

    private String findFirst(String expression, int l, int r) throws RegexpIllegalException {
        if (l == r) {
            return null;
        }
        if (expression.charAt(l) == '\\') {
            if (l + 1 >= r) {
                throw new RegexpIllegalException(expression, l + 1);
            }
            return expression.substring(l, l + 2);
        }
        if (expression.charAt(l) == '[') {
            int i = l + 1;
            while (i < r) {
                if (expression.charAt(i) == ']') {
                    return expression.substring(l, i + 1);
                }
                if (expression.charAt(i) == '\\') {
                    i++;
                }
                i++;
            }
            throw new RegexpIllegalException(expression, r);
        }
        if (expression.charAt(l) == '{') {
            int i = l + 1;
            boolean hasDelimiter = false;
            while (i < r) {
                if (expression.charAt(i) == '}') {
                    if (i - l + 1 < 3 || expression.charAt(i - 1) == ',') {
                        throw new RegexpIllegalException(expression, i - 1);
                    }
                    return expression.substring(l, i + 1);
                }
                if (expression.charAt(i) == ',') {
                    if (hasDelimiter) {
                        throw new RegexpIllegalException(expression, i);
                    }
                    hasDelimiter = true;
                    i++;
                    continue;
                }
                if (expression.charAt(i) < '0' || expression.charAt(i) >'9'){
                    throw new RegexpIllegalException(expression, i);
                }
                i++;
            }
            throw new RegexpIllegalException(expression, r);
        }
        if (expression.charAt(l) == '(') {
            int i = l + 1;
            while (true) {
                String result = findFirst(expression, i, r);
                if (result == null || result.length() == 0 || result.length() + i >= r) {
                    throw new RegexpIllegalException(expression, i);
                }
                i += result.length();
                if (expression.charAt(i) == ')') {
                    return expression.substring(l, i + 1);
                }
            }
        }
        return expression.substring(l, l + 1);
    }
}
