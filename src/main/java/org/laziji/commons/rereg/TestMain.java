package org.laziji.commons.rereg;

import org.laziji.commons.rereg.exception.RegexpIllegalException;

public class TestMain {

    public static void main(String[] args) throws RegexpIllegalException {
        RegexpDataGenerator gen = new RegexpDataGenerator("a(ba?\\d{9}){2}|avc|\\d+");
        System.out.println(gen.randomData());
    }
}
