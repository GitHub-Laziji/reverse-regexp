package org.laziji.commons.rereg;

import org.junit.Test;
import org.laziji.commons.rereg.exception.RegexpIllegalException;
import org.laziji.commons.rereg.exception.TypeNotMatchException;
import org.laziji.commons.rereg.exception.UninitializedException;
import org.laziji.commons.rereg.model.Node;
import org.laziji.commons.rereg.model.OrdinaryNode;

import java.util.regex.Pattern;

public class MainTest {

    @Test
    public void test() throws RegexpIllegalException, UninitializedException, TypeNotMatchException {

        random("\\w{6,12}@[a-z0-9]{3}\\.(com|cn)", "邮箱");
        random("1(3|5|7|8)\\d{9}", "手机号");
        random("-?[1-9]\\d*\\.\\d+", "浮点数");
        random("https?://[\\w-]+(\\.[\\w-]+){1,2}(/[\\w-]{3,6}){0,2}(\\?[\\w_]{4,6}=[\\w_]{4,6}(&[\\w_]{4,6}=[\\w_]{4,6}){0,2})?", "网址");
        random("[A-Z][a-z]{4,6}", "英文名");
        random("[1-9][0-9]?", "年龄");
        random("(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}", "IP");
        random("\\d{3}-\\d{8}|\\d{4}-\\d{7}","电话");
    }

    private void random(String expression, String title)
            throws RegexpIllegalException, TypeNotMatchException, UninitializedException {

        System.out.println(title + " " + expression);
        Node node = new OrdinaryNode(expression);
        Pattern pattern = Pattern.compile(node.getExpression());
        for (int i = 0; i < 10; i++) {
            String data = node.random();
            System.out.println("[" + pattern.matcher(data).matches() + "] " + data);
        }
        System.out.println();
    }

}