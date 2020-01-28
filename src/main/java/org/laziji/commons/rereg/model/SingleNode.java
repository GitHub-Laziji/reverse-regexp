package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.RegexpDataGenerator;
import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;
import org.laziji.commons.rereg.exception.UninitializedException;
import org.laziji.commons.rereg.model.BaseNode;

import java.util.List;
import java.util.Random;

public class SingleNode extends BaseNode {

    private Node node;

    public SingleNode(String expression) throws RegexpIllegalException {
        super(expression);
    }

    public SingleNode(List<String> expressionFragments) {
        super(expressionFragments);
    }

    @Override
    protected boolean test(String expression, List<String> expressionFragments) {
        return expressionFragments != null && expressionFragments.size() == 1;
    }

    @Override
    protected void init(String expression, List<String> expressionFragments) throws RegexpIllegalException {
        if (expression.startsWith("(")) {
            node = new OrdinaryNode(expression.substring(1, expression.length() - 1));
        }
    }

    @Override
    protected String random(String expression, List<String> expressionFragments) throws RegexpIllegalException, UninitializedException {
        if (node != null) {
            return node.random();
        }
        if (expression == null || expression.isEmpty()) {
            return "";
        }
        if ("\\d".equals(expression)) {
            return randomByRangeList(new Range('0', '9'));
        }
        if ("\\w".equals(expression)) {
            return randomByRangeList(new Range('a', 'z'), new Range('A', 'Z'), new Range('0', '9'), new Range('_'));
        }
        if (expression.startsWith("\\")) {
            return expression.substring(1);
        }
        return expression;
    }

    private String randomByRangeList(Range... ranges) {
        int count = 0;
        for (Range range : ranges) {
            count += range.end + 1 - range.start;
        }
        int randomValue = new Random().nextInt(count);
        for (Range range : ranges) {
            if (randomValue < range.end + 1 - range.start) {
                return (char) (range.start + randomValue) + "";
            }
            randomValue -= range.end + 1 - range.start;
        }
        return "";
    }

    private static class Range {
        private char start;
        private char end;

        private Range(char start) {
            this.start = this.end = start;
        }

        private Range(char start, char end) {
            this.start = start;
            this.end = end;
        }

        private char random() {
            return (char) (new Random().nextInt(this.end + 1 - this.start) + this.start);
        }

        private char getStart() {
            return start;
        }

        private void setStart(char start) {
            this.start = start;
        }

        private char getEnd() {
            return end;
        }

        private void setEnd(char end) {
            this.end = end;
        }
    }
}
