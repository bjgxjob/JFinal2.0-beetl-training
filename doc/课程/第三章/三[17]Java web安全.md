## Java Web与安全
1. 服务器漏洞，服务器应用、容器漏洞。如：tomcat、JDK、ssh漏洞等！可关注乌云（WooYun）：http://www.wooyun.org/
2. 弱口令：服务器或者应用采用了比较容易试出来的帐号和密码。采用略微复杂的密码，并可定期修
3. 框架类漏洞，例如：struts2漏洞等。
4. sql注入，由于初学者采用`+`号拼接sql或者未使用预编译sql，而导致的sql注入致使被拖库、删库
5. xss漏洞，盗取cookie或者收集用户信息，敏感cookie采用httponly，使js无法获取它！
6. 上传漏洞，注意jsp，war，sh甚至是beetl的.html模板！*/WEB-INF/为安全目录，建议将模板目录指定到它下面，上传时过滤后缀！

## 开发注意
1. 对于用户千万别直接传递userId执行操作，代码中从cookie、session中获取！
2. 对于用户手机号，邮箱等敏感信息暴露在前台时注意隐藏几位
3. 对于用户操作的订单，博文等，执行更新、删除时一定需要判断该订单、博文是否属于该用户！
4. 前台用户表单的字段js和后台校验、进行xss过滤（特别是富文本编辑器），java可采用jsoup过滤标签和标签属性（css也可被xss）
5. 管理后台限定ip访问或直接使用内网
6. 金钱等敏感信息操作时的日志记录，升级为https提升安全性

## 连接
>乌云WIKI：http://wiki.wooyun.org/WooYun%20WiKi
>乌云知识库：http://drops.wooyun.org/