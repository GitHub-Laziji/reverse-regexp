# reverse-regexp

从正则表达式生成随机数据

## 安装
```
git clone https://github.com/GitHub-Laziji/reverse-regexp
cd reverse-regexp
mvn install
```

```xml
<dependency>
    <groupId>org.laziji.commons</groupId>
    <artifactId>reverse-regexp</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 使用
```java
public class MainTest {

    @Test
    public void test() throws RegexpIllegalException, UninitializedException, TypeNotMatchException {
        //"1(3|5|7|8)\\d{9}"
        //"\\w{6,12}@[a-z0-9]{3,4}.(com|cn)"
        Node node1 = new OrdinaryNode("\\w{6,12}@[a-z0-9]{3,4}.(com|cn)");
        for (int i = 0; i < 10; i++) {
            System.out.println(node1.random());
        }

        Node node2 = new OrdinaryNode("1(3|5|7|8)\\d{9}");
        for (int i = 0; i < 10; i++) {
            System.out.println(node2.random());
        }
    }
}
```

输出
```
e8q9RcpqA@v86.cn
4j9JY3eAwcK@taed.cn
DxMk9WAC@nezq.cn
a_DkgsX53@65oi.cn
9FdnaW@xwc0.cn
i7OC4jKkd@qg5.cn
04w3Ey3XDk@01h0.com
cxm1hm0eypdr@nrk.com
S6gLYGH@4ku6.com
uIEVMkv@wow7.com
15888816167
13570739660
18777512187
17696928416
18070326829
15867303803
17520644809
15822003765
15656061323
18611800570
```