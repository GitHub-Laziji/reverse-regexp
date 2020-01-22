package org.laziji.commons.rereg;


public class RegexpDataGenerator {

    private static final int MAX_REPEAT = 9;

    private String regexp;

    private char[] regexpChs;

    public RegexpDataGenerator(String regexp) {
        this.regexp = regexp;
        this.regexpChs = regexp.toCharArray();
    }


    private int[] findFirst(int l, int r) {
        if(l==r){
            return null;
        }
        if(regexpChs[l]=='\\'){

        }
        return null;
    }


}
