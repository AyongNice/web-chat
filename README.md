# web-chat
web-chat 浏览器版本 好友聊天室

### 聊天/群聊
1. 文字发送
2. 图片发送
3. 语音发送
4. 
<img src="https://files.mdnice.com/user/54733/65ae6f4e-8a08-4eb1-8e08-82d84399be3e.png" alt="图片描述" width="340" height="600">
   
 

### 表情包
1. 相当骚气的表情包 让你引临风骚
2. 
<img src="https://files.mdnice.com/user/54733/e62884a8-ff6e-4418-aa8f-83ece535d78d.png" alt="图片描述" width="340" height="600">
 

### 胖友圈
1. emoje文案
2. 精选晒图
3. 好友互动 评论、点赞
4. 
<img src="https://files.mdnice.com/user/54733/f17aa55c-213c-4a77-8476-abf6c3a44ed1.png" alt="图片描述" width="340" height="600">

### 技术栈介绍

前端~ vue2框架 + vantUI组件库 
后端~ spring-boot@3.0 + mysql@8.0 + resist
7.2.5

### 启动须知

1. sql脚本也都在源码中
2. 后端目录结构中 libs 里的文件 要用idea 右键添加为库
3. registerStompEndpoints 方法有注释 细看
4. 用户账号 admin  12345



### 注册自己系统的主邮箱流程


用代码发送邮箱,本质还是 自己系统的邮箱 给用户的邮箱发送, 所以你得注册自己邮箱
我们今天用 163邮箱为示例
如何注册 163邮箱这里就不讲了(不会注册的小伙伴可以网上搜一下)

### 登陆163邮箱 点击 `设置`

![](https://files.mdnice.com/user/54733/5b25b563-ff1c-4e9e-b975-88a31b11d9e3.png)

### 进入设置 切换 `POP3/SMTP/IMAP` 分栏

![](https://files.mdnice.com/user/54733/3973e2ea-c87b-4ec0-a5e1-c0226cbe0554.png)

我们开启 pop3服务 需要自己发短信开启,根据提示开启即可 开启此服务, 意味着我们可以利用代码 来调用 163的服务,让自己的邮箱作为系统邮箱给用户发送验证码、
### API密码
在开启pop3服务 之后。163系统会给你弹框 一个 密钥
⚠️ 密钥只会出现一次,一定要保存好

#### application.yml

yml 随便找个地方另起一行 配置下 
1. host 就是刚次说的163服务地址
2. port 默认写465
3. username 你的邮箱
4. 密码 切记不是邮箱登陆密码, 是你开启`smtp` 给的密钥
<img src="https://files.mdnice.com/user/54733/e867301f-469b-4ff8-ba53-cd37e3fa7b07.png" alt="图片描述" width="340" height="400">



