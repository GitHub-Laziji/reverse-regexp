package org.laziji.commons.rereg;

import org.laziji.commons.rereg.exception.RegexpIllegalException;

public class TestMain {

    public static void main(String[] args) throws RegexpIllegalException {
        RegexpDataGenerator gen = new RegexpDataGenerator("[a-zA-Z0-9]{5,9}@[a-zA-Z0-9]{2,3}.(com|cn)");
        for (int i = 0; i < 100; i++) {
            System.out.println(gen.randomData());
        }

    }
}
