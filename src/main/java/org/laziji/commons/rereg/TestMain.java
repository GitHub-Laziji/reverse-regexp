package org.laziji.commons.rereg;

import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.UninitializedException;
import org.laziji.commons.rereg.model.Node;
import org.laziji.commons.rereg.model.OrdinaryNode;

public class TestMain {

    public static void main(String[] args) throws RegexpIllegalException, UninitializedException {
        //"1(3|5|7|8)\\d{9}"
        //"\\w{9,18}@[a-z0-9]{2,3}.(com|cn)"
        Node node = new OrdinaryNode("\\w{9,18}@[a-z0-9]{2,3}.(com|cn)");
        for (int i = 0; i < 10; i++) {
            System.out.println(node.random());
        }
    }
}
