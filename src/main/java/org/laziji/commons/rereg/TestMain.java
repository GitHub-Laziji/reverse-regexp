package org.laziji.commons.rereg;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.UninitializedException;
import org.laziji.commons.rereg.model.Node;
import org.laziji.commons.rereg.model.OrdinaryNode;

public class TestMain {

    public static void main(String[] args) throws RegexpIllegalException, UninitializedException {
//        for (int i = 0; i < 100; i++) {
//            System.out.println(new RegexpDataGenerator("\\w{9,18}@[a-z0-9]{2,3}.(com|cn)").random());
//        }
//        for (int i = 0; i < 10; i++) {
//            System.out.println(new RegexpDataGenerator("1(3|5|7|8)\\d{9}").random());
//        }
        Node node = new OrdinaryNode("\\d{2}\\w{9,18}@\\w{2,3}.(com|cn)");
        for (int i = 0; i < 10; i++) {
            System.out.println(node.random());
        }
    }
}
