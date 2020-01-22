package org.laziji.commons.rereg;

import org.laziji.commons.rereg.exception.RegexpIllegalException;

public class TestMain {

    public static void main(String[] args) throws RegexpIllegalException {
        for (int i = 0; i < 10; i++) {
            System.out.println(new RegexpDataGenerator("[a-zA-Z0-9]{5,9}@[a-z0-9]{2,3}.(com|cn)").randomData());
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(new RegexpDataGenerator("1(3|5|7|8)\\d{9}").randomData());
        }

    }
}
