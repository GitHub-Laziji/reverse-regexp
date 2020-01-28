package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseNode implements Node {

    private String expression;
    private List<String> expressionFragments;
    private boolean initialized;

    public BaseNode(String expression) throws RegexpIllegalException {
        this.expression = expression;
        this.expressionFragments = spliceExpression(expression);
    }

    public BaseNode(List<String> expressionFragments) {
        this.expressionFragments = expressionFragments;
    }

    @Override
    public boolean test() {
        return test(expressionFragments);
    }

    @Override
    public void init() throws RegexpIllegalException {
        if (!initialized) {
            init(expressionFragments);
            initialized = true;
        }
    }

    protected void init(List<String> expressionFragments) throws RegexpIllegalException {

    }

    protected boolean test(List<String> expressionFragments) {
        return true;
    }

    @Override
    public String getExpression() {
        return expression;
    }


    private List<String> spliceExpression(String expression) throws RegexpIllegalException {
        int l = 0;
        int r = expression.length();
        List<String> fragments = new ArrayList<>();
        while (true) {
            String result = findFirst(expression, l, r);
            if (result == null || result.isEmpty()) {
                break;
            }
            fragments.add(result);
            l += result.length();
        }
        return fragments;
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
                if (expression.charAt(i) < '0' || expression.charAt(i) > '9') {
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
