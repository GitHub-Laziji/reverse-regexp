# reverse-regexp

从正则表达式生成随机数据

## 安装
```
git clone https://github.com/GitHub-Laziji/reverse-regexp.git
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
### 随机字符语法
支持大部分正则表达式的匹配语法
- `\d`数字, 相当于`[0-9]`
- `\w`数字 字母加下划线, 相当于`[0-9a-zA-Z_]`
- `\s`空白字符, 只包含空格以及制表符
- `.`除`\n`和`\r`以外的任意字符, 生成随机数据时只在ascii码0~255之间生成
- `[a-zA-Z甲乙]`区间, 不支持`^`语法
- 以及其他字符
### 重复打印语法
与正则表达式的重复匹配语法相同
- `?`随机生成0个或1个字符
- `*`生成0个以上字符, 默认最多16个
- `+`生成1个以上字符, 默认最多16个
- `{n}`生成n个字符
- `{n,}`生成n~个字符, 默认最多`max(16,n)`个
- `{n,m}`生成n~m个字符

### 其他语法
- `|`或语法, 例如`aaa|bbb|ccc`随机生成`aaa`或`bbb`或`ccc`,概率相等
- `()`支持括号

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