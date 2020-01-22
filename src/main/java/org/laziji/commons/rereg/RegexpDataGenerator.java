package org.laziji.commons.rereg;


import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegexpDataGenerator {

    private static final int MAX_REPEAT = 9;

    private Random random = new Random();

    private String regexp;
    private char[] regexpChs;

    public RegexpDataGenerator(String regexp) {
        this.regexp = regexp;
        this.regexpChs = regexp.toCharArray();
    }

    public String randomData() throws RegexpIllegalException {
        return randomData(new Snippet(0, regexpChs.length));
    }

    private String randomData(Snippet snippet) throws RegexpIllegalException {
        int l = snippet.getLeft();
        int r = snippet.getRight();
        List<Snippet> fullSnippets = new ArrayList<>();
        while (true) {
            Snippet result = findFirst(l, r);
            if (result == null || result.isEmpty()) {
                break;
            }
            fullSnippets.add(result);
            l = result.getRight();
        }
        if (fullSnippets.size() == 0) {
            return "";
        }

        List<List<Snippet>> subSnippetsList = new ArrayList<>();
        subSnippetsList.add(new ArrayList<>());
        for (Snippet result : fullSnippets) {
            if (result.isSingle() && regexpChs[result.getLeft()] == '|') {
                subSnippetsList.add(new ArrayList<>());
                continue;
            }
            subSnippetsList.get(subSnippetsList.size() - 1).add(result);
        }

        List<Snippet> subSnippets = subSnippetsList.get(random.nextInt(subSnippetsList.size()));
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < subSnippets.size(); i++) {
            Snippet result = subSnippets.get(i);
            int minRepeat = 1;
            int maxRepeat = 1;
            if (subSnippets.size() > i + 1) {
                Snippet nextResult = subSnippets.get(i + 1);
                if (nextResult.isSingle()) {
                    switch (regexpChs[nextResult.getLeft()]) {
                        case '?':
                            minRepeat = 0;
                            i++;
                            break;
                        case '+':
                            maxRepeat = MAX_REPEAT;
                            i++;
                            break;
                        case '*':
                            minRepeat = 0;
                            maxRepeat = MAX_REPEAT;
                            i++;
                            break;
                    }
                } else if (regexpChs[nextResult.getLeft()] == '{') {
                    String[] ranges = StringUtils
                            .charsToString(regexpChs, nextResult.getLeft() + 1, nextResult.getRight() - 1)
                            .split(",");
                    minRepeat = maxRepeat = Integer.parseInt(ranges[0]);
                    if (ranges.length > 1) {
                        maxRepeat = Integer.parseInt(ranges[1]);
                    }
                    i++;
                }
            }
            int repeat = random.nextInt(maxRepeat - minRepeat + 1) + minRepeat;
            while (repeat-- > 0) {
                value.append(randomDataBySingleSnippet(result));
            }
        }
        return value.toString();
    }

    private String randomDataBySingleSnippet(Snippet snippet) throws RegexpIllegalException {
        if (snippet.isEmpty()) {
            return "";
        }
        if (snippet.getLength() == 2 && regexpChs[snippet.getLeft()] == '\\') {
            if (regexpChs[snippet.getLeft() + 1] == 'd') {
                return randomRangeChar(new Range('0', '9'));
            }
            if (regexpChs[snippet.getLeft() + 1] == 'w') {
                return randomRangeChar(new Range('a', 'z'), new Range('A', 'Z'), new Range('0', '9'), new Range('_'));
            }
            return regexpChs[snippet.getLeft() + 1] + "";
        }
        if (regexpChs[snippet.getLeft()] == '[') {
            List<Range> ranges = new ArrayList<>();
            int i = snippet.getLeft() + 1;
            Character preChar = null;
            while (i < snippet.getRight() - 1) {
                if (regexpChs[i] == '-') {
                    throw new RegexpIllegalException(this.regexp, i);
                }
                if (regexpChs[i] == '\\') {
                    if (i + 1 >= snippet.getRight() - 1) {
                        throw new RegexpIllegalException(this.regexp, i);
                    }
                    if (regexpChs[i + 1] == 'd') {
                        ranges.add(new Range('0', '9'));
                    } else if (regexpChs[i + 1] == 'w') {
                        ranges.add(new Range('0', '9'));
                        ranges.add(new Range('A', 'Z'));
                        ranges.add(new Range('0', '9'));
                        ranges.add(new Range('_'));
                    } else {
                        if (preChar != null) {
                            ranges.add(new Range(preChar, regexpChs[i + 1]));
                            preChar = null;
                        } else if (i + 2 < snippet.getRight() - 1 && regexpChs[i + 2] == '-') {
                            preChar = regexpChs[i + 1];
                            i++;
                        } else {
                            ranges.add(new Range(regexpChs[i + 1]));
                        }
                    }
                    i++;
                } else if (preChar != null) {
                    ranges.add(new Range(preChar, regexpChs[i]));
                    preChar = null;
                } else if (i + 1 < snippet.getRight() - 1 && regexpChs[i + 1] == '-') {
                    preChar = regexpChs[i];
                    i++;
                } else {
                    ranges.add(new Range(regexpChs[i]));
                }
                i++;
            }
            return randomRangeChar(ranges.toArray(new Range[0]));
        }
        if (regexpChs[snippet.getLeft()] == '(') {
            return randomData(new Snippet(snippet.getLeft() + 1, snippet.getRight() - 1));
        }
        return StringUtils.charsToString(regexpChs, snippet.getLeft(), snippet.getRight());
    }

    private String randomRangeChar(Range... ranges) {
        return ranges[random.nextInt(ranges.length)].random() + "";
    }


    private Snippet findFirst(int l, int r) throws RegexpIllegalException {
        if (l == r) {
            return null;
        }
        if (regexpChs[l] == '\\') {
            if (l + 1 >= r) {
                throw new RegexpIllegalException(this.regexp, l + 1);
            }
            return new Snippet(l, l + 2);
        }
        if (regexpChs[l] == '[') {
            int i = l + 1;
            while (i < r) {
                if (regexpChs[i] == ']') {
                    return new Snippet(l, i + 1);
                }
                if (regexpChs[i] == '\\') {
                    i++;
                }
                i++;
            }
            throw new RegexpIllegalException(this.regexp, r);
        }
        if (regexpChs[l] == '{') {
            int i = l + 1;
            boolean hasDelimiter = false;
            while (i < r) {
                if (regexpChs[i] == '}') {
                    if (i - l + 1 < 3 || regexpChs[i - 1] == ',') {
                        throw new RegexpIllegalException(this.regexp, i - 1);
                    }
                    return new Snippet(l, i + 1);
                }
                if (regexpChs[i] == ',') {
                    if (hasDelimiter) {
                        throw new RegexpIllegalException(this.regexp, i);
                    }
                    hasDelimiter = true;
                    i++;
                    continue;
                }
                if (regexpChs[i] < '0' || regexpChs[i] > '9') {
                    throw new RegexpIllegalException(this.regexp, i);
                }
                i++;
            }
            throw new RegexpIllegalException(this.regexp, r);
        }
        if (regexpChs[l] == '(') {
            int i = l + 1;
            while (true) {
                Snippet result = findFirst(i, r);
                if (result == null || result.getRight() >= r) {
                    throw new RegexpIllegalException(this.regexp, i);
                }
                i = result.getRight();
                if (regexpChs[i] == ')') {
                    return new Snippet(l, i + 1);
                }
            }
        }
        return new Snippet(l, l + 1);
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

    private static class Snippet {

        private int left;
        private int right;

        private Snippet(int left, int right) {
            this.left = left;
            this.right = right;
        }

        private int getLength() {
            return right - left;
        }

        private boolean isSingle() {
            return right - left == 1;
        }

        private boolean isEmpty() {
            return right <= left;
        }

        private int getLeft() {
            return left;
        }

        private void setLeft(int left) {
            this.left = left;
        }

        private int getRight() {
            return right;
        }

        private void setRight(int right) {
            this.right = right;
        }
    }

}