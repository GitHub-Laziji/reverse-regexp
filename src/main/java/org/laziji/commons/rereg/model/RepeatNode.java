package org.laziji.commons.rereg.model;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.UninitializedException;

import java.util.List;
import java.util.Random;

public class RepeatNode extends BaseNode {

    private static final int MAX_REPEAT = 9;

    private Node node;
    private int minRepeat = 1;
    private int maxRepeat = 1;

    public RepeatNode(String expression) throws RegexpIllegalException {
        super(expression);
    }

    public RepeatNode(List<String> expressionFragments) {
        super(expressionFragments);
    }

    @Override
    protected boolean test(String expression, List<String> expressionFragments) {
        if (expressionFragments.size() == 2) {
            String token = expressionFragments.get(1);
            return token != null
                    && ("+".equals(token) || "?".equals(token) || "*".equals(token) || token.startsWith("{"));
        }
        return false;
    }

    @Override
    protected void init(String expression, List<String> expressionFragments) throws RegexpIllegalException {
        node = new SingleNode(expressionFragments.get(0));
        node.init();
        String token = expressionFragments.get(1);
        if ("+".equals(token)) {
            maxRepeat = MAX_REPEAT;
        } else if ("?".equals(token)) {
            minRepeat = 0;
        } else if ("*".equals(token)) {
            minRepeat = 0;
            maxRepeat = MAX_REPEAT;
        } else if (token.startsWith("{")) {
            String[] numbers = token.substring(1, token.length() - 1).split(",");
            minRepeat = maxRepeat = Integer.parseInt(numbers[0]);
            if (numbers.length > 1) {
                maxRepeat = Integer.parseInt(numbers[1]);
            }
        }
    }

    @Override
    protected String random(String expression, List<String> expressionFragments) throws RegexpIllegalException, UninitializedException {
        int repeat = new Random().nextInt(maxRepeat - minRepeat + 1) + minRepeat;
        StringBuilder value = new StringBuilder();
        while (repeat-- > 0) {
            value.append(node.random());
        }
        return value.toString();
    }
}
