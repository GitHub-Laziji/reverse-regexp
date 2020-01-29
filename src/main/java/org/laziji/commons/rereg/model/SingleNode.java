package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;
import org.laziji.commons.rereg.exception.UninitializedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SingleNode extends BaseNode {

    private Node node;

    private List<Interval> intervals;

    SingleNode(List<String> expressionFragments) throws RegexpIllegalException, TypeNotMatchException {
        super(expressionFragments);
    }

    SingleNode(List<String> expressionFragments, boolean initialize)
            throws RegexpIllegalException, TypeNotMatchException {
        super(expressionFragments, initialize);
    }


    @Override
    protected boolean test(String expression, List<String> expressionFragments) {
        return expressionFragments != null && expressionFragments.size() == 1;
    }

    @Override
    protected void init(String expression, List<String> expressionFragments)
            throws RegexpIllegalException, TypeNotMatchException {
        if (expression.startsWith("(")) {
            node = new OrdinaryNode(expression.substring(1, expression.length() - 1));
            return;
        }
        intervals = new ArrayList<>();
        if (expression.startsWith("[")) {
            int i = 1;
            Character preChar = null;
            while (i < expression.length() - 1) {
                if (expression.charAt(i) == '-') {
                    throw new RegexpIllegalException(expression, i);
                }
                if (expression.charAt(i) == '\\') {
                    if (i + 1 >= expression.length() - 1) {
                        throw new RegexpIllegalException(expression, i);
                    }
                    if (expression.charAt(i + 1) == 'd') {
                        intervals.add(new Interval('0', '9'));
                    } else if (expression.charAt(i + 1) == 'w') {
                        intervals.add(new Interval('0', '9'));
                        intervals.add(new Interval('A', 'Z'));
                        intervals.add(new Interval('0', '9'));
                        intervals.add(new Interval('_'));
                    } else {
                        if (preChar != null) {
                            intervals.add(new Interval(preChar, expression.charAt(i + 1)));
                            preChar = null;
                        } else if (i + 2 < expression.length() - 1 && expression.charAt(i + 2) == '-') {
                            preChar = expression.charAt(i + 1);
                            i++;
                        } else {
                            intervals.add(new Interval(expression.charAt(i + 1)));
                        }
                    }
                    i++;
                } else if (preChar != null) {
                    intervals.add(new Interval(preChar, expression.charAt(i)));
                    preChar = null;
                } else if (i + 1 < expression.length() - 1 && expression.charAt(i + 1) == '-') {
                    preChar = expression.charAt(i);
                    i++;
                } else {
                    intervals.add(new Interval(expression.charAt(i)));
                }
                i++;
            }
        } else if ("\\d".equals(expression)) {
            intervals.add(new Interval('0', '9'));
        } else if ("\\w".equals(expression)) {
            intervals.add(new Interval('a', 'z'));
            intervals.add(new Interval('A', 'Z'));
            intervals.add(new Interval('0', '9'));
            intervals.add(new Interval('_'));
        } else if (expression.startsWith("\\")) {
            intervals.add(new Interval(expression.charAt(1)));
        }
    }

    @Override
    protected String random(String expression, List<String> expressionFragments)
            throws RegexpIllegalException, UninitializedException {
        if (node != null) {
            return node.random();
        }
        if (intervals != null && intervals.size() > 0) {
            Character value = randomCharFromInterval(intervals.toArray(new Interval[0]));
            return value == null ? "" : value.toString();
        }
        return expression;
    }

    private Character randomCharFromInterval(Interval... intervals) {
        int count = 0;
        for (Interval interval : intervals) {
            count += interval.end + 1 - interval.start;
        }
        int randomValue = new Random().nextInt(count);
        for (Interval interval : intervals) {
            if (randomValue < interval.end + 1 - interval.start) {
                return (char) (interval.start + randomValue);
            }
            randomValue -= interval.end + 1 - interval.start;
        }
        return null;
    }

    private static class Interval {
        private char start;
        private char end;

        private Interval(char start) {
            this.start = this.end = start;
        }

        private Interval(char start, char end) {
            this.start = start;
            this.end = end;
        }
    }
}
