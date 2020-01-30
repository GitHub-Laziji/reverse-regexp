package org.laziji.commons.rereg;

import org.junit.Test;
import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;
import org.laziji.commons.rereg.exception.UninitializedException;
import org.laziji.commons.rereg.model.Node;
import org.laziji.commons.rereg.model.OrdinaryNode;

public class MainTest {

    @Test
    public void test() throws RegexpIllegalException, UninitializedException, TypeNotMatchException {
        //"1(3|5|7|8)\\d{9}"
        //"\\w{6,12}@[a-z0-9]{3,4}.(com|cn)"
        Node node1 = new OrdinaryNode("\\w{6,12}@[a-z0-9]{3}\\.(com|cn)");
        for (int i = 0; i < 10; i++) {
            System.out.println(node1.random());
        }

        Node node2 = new OrdinaryNode("1(3|5|7|8)\\d{9}");
        for (int i = 0; i < 10; i++) {
            System.out.println(node2.random());
        }

    }

}