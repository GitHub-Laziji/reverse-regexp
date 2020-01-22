package org.laziji.commons.rereg;

import org.laziji.commons.rereg.exception.RegexpIllegalException;

import java.util.regex.Pattern;

public class TestMain {

    public static void main(String[] args) throws RegexpIllegalException {
//        Pattern.compile("[a-z]+@[a-z]{1,3}.(com|cn)").matcher("c_smtp@163.com");
        RegexpDataGenerator gen = new RegexpDataGenerator("\\w+@\\d{1,3}.(com|cn)");
        System.out.println(gen.randomData());
    }
}
