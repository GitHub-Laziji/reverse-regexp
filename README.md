# Reverse Regexp
![](https://img.shields.io/github/languages/top/github-laziji/reverse-regexp.svg?style=flat)
![](https://img.shields.io/github/stars/gitHub-laziji/reverse-regexp.svg?style=social)

从正则表达式生成随机数据

## 手动安装或者使用Github Packages
```
//手动安装
git clone https://github.com/GitHub-Laziji/reverse-regexp.git
cd reverse-regexp
mvn install
```

[使用 Maven Github Packages](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages)

然后在项目中引入
```xml
<dependency>
    <groupId>org.laziji.commons</groupId>
    <artifactId>reverse-regexp</artifactId>
    <version>1.0.1</version>
</dependency>
```


## 使用
### 随机字符语法
支持大部分正则表达式的匹配语法
- `\d` 数字, 相当于`[0-9]`
- `\w` 数字、字母加下划线, 相当于`[0-9a-zA-Z_]`
- `\s` 空白字符, 只包含空格和制表符
- `.` 除`\n`和`\r`以外的任意字符, 生成随机字符时只在`ascii`码`0~255`之间生成
- `[a-zA-Z甲乙]` 区间, 不支持`^`语法
- 以及其他字符
### 重复打印语法
与正则表达式的重复匹配语法相同
- `?` 随机生成0个或1个字符
- `*` 随机生成0个以上字符, 默认最多16个
- `+` 随机生成1个以上字符, 默认最多16个
- `{n}` 生成n个字符
- `{n,}` 随机生成n~个字符, 默认最多`max(16,n)`个
- `{n,m}` 随机生成n~m个字符

### 其他语法
- `|` 或语法, 例如`aaa|bbb|ccc`随机生成`aaa`或`bbb`或`ccc`, 概率相等
- `()` 支持括号

### 常用正则
- 邮箱 `\w{6,12}@[a-z0-9]{3}\.(com|cn)`
- 手机号 `1(3|5|7|8)\d{9}`
- 电话 `\d{3}-\d{8}|\d{4}-\d{7}`
- 英文名 `[A-Z][a-z]{4,6}`
- 年龄 `[1-9][0-9]?`
- 网址 `https?://[\w-]+(\.[\w-]+){1,2}(/[\w-]{3,6}){0,2}(\?[\w_]{4,6}=[\w_]{4,6}(&[\w_]{4,6}=[\w_]{4,6}){0,2})?`
- IPv4 `(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}`

```java
public class MainTest {

    @Test
    public void test() throws RegexpIllegalException, UninitializedException, TypeNotMatchException {
        
        random("\\w{6,12}@[a-z0-9]{3}\\.(com|cn)", "邮箱");
        random("1(3|5|7|8)\\d{9}", "手机号");
        random("-?[1-9]\\d*\\.\\d+", "浮点数");
        random("https?://[\\w-]+(\\.[\\w-]+){1,2}(/[\\w-]{3,6}){0,2}(\\?[\\w_]{4,6}=[\\w_]{4,6}(&[\\w_]{4,6}=[\\w_]{4,6}){0,2})?", "网址");
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
```

输出
```
邮箱 \w{6,12}@[a-z0-9]{3}\.(com|cn)
[true] 19cZ8eISNA@9je.com
[true] xpv3wJ@i3h.cn
[true] 6qDUfY@1g9.com
[true] iVnZSMA373@6zd.cn
[true] I5wiX97@ffe.cn
[true] mwqA5sXQ@g8j.cn
[true] HUXiCem1Y0w@j98.cn
[true] 1jOQWsELF@u1o.cn
[true] _Q4QTvxPeMFh@bds.com
[true] 3xFH33Aa@6lh.cn

手机号 1(3|5|7|8)\d{9}
[true] 18263364656
[true] 17539493178
[true] 17452542895
[true] 15190699623
[true] 13441385631
[true] 15450856416
[true] 18651247283
[true] 13835809899
[true] 18595798569
[true] 17115703866

浮点数 -?[1-9]\d*\.\d+
[true] 8148340336.1501586550282701
[true] -3339660539.406
[true] -51.6120243661611419
[true] -731621835440468.9708278
[true] -27438753435.9137579
[true] 393811376.777268751417
[true] 3286498432415.3962664603
[true] -5299652275.9
[true] 216.93676279820770
[true] 34.36843273

网址 https?://[\w-]+(\.[\w-]+){1,2}(/[\w-]{3,6}){0,2}(\?[\w_]{4,6}=[\w_]{4,6}(&[\w_]{4,6}=[\w_]{4,6}){0,2})?
[true] https://a_fl.thx/4_4
[true] http://v3YTuJ0Gu-5z8.JGhpdLe/V2j/ndL-UO
[true] https://FuMG-gafEc.R2FRRtLyX/ahg
[true] https://5phYVK9.wh7vl9z3AAZVg.z-yQSiMTdQw8S9-/WKCd
[true] https://T9dntbI.4Su8vxYhCr6?T85gV=R6TYtm&1c97x=nCTyA5
[true] http://qHyDgqBtYwq6Stg8.I6gb-M_ripkiEafK?ZLxy=Itny&RmsTX=X7KEuW&bwuk2=frcjO6
[true] http://UJ.Yb3foKTJKy-uqKUl.ZXrrFUk9K/Dcymu?W2tasq=oqzS&hYGWD=MF6l&FMHOi=F6ct8T
[true] https://BY_iS.tnlclAvxMkuO.T90G5XETj/cQncGI
[true] http://hG.li8Nzv.uaGokB/CTnrqp
[true] https://huZmN.v-LtoY/Dyl/peq?NUmt=__QDXG&8GBY=wZ8M&AkKZl4=8NZfEt
```