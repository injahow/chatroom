# chatroom

## 1.项目目的

掌握 Java Web 网站设计的过程，进一步熟练 Servlet 技术、数据库、标签库 JSTL、过滤器 Filter 等一般 Java Web 应用技术和 MVC 开发模式，巩固前段时间所学的知识，通过一段时间 JAVA 课程的学习，需要对所学的知识作一个综合的运用。并基于此制作一个在线聊天室系统，主要能实现在网络上的多线程间的通讯。

## 2.项目概括

### 2.1 功能要求

一、主要功能

- 用户登录
- 在线聊天
- 匿名聊天
- 本次聊天在线时间
- 发送表情
- 文字颜色（自己发送的信息可设置特定颜色）
- 聊天背景更换
- 敏感词屏蔽（可加一个敏感词列表进行过滤）

二、扩展功能

- 私聊（留言）
- 在线人员名单

### 2.2 开发环境

windows10、IDEA 2020.1、Jdk1.8、MySql5.6、Tomcat9.0 等。

## 3.系统设计

用户注册：使用账号和密码就可以注册一个用户，用户登录：使用用户账号密码可以登录到投票界面，用户聊天：选择右侧在线人姓名可打开私聊窗口，选择聊天室窗口可进行公共聊天，在对话框上方有选择器包括，匿名聊天，聊天背景，聊天字体颜色，在线时间记录，表情选项等。

## 4.数据库设计

### 4.1 数据库结构

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/sql.png)

### 4.2 数据库内容

用户表
![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/uesr.png)

## 5.项目结构

```bash
│  chatroom.iml
├─src
│  └─com
│      └─chatroom
│          │  Server.java
│          │
│          ├─config
│          │      db.properties
│          │
│          ├─controller
│          │      doLogin.java
│          │      doRegister.java
│          │      onlineList.java
│          │
│          ├─dao
│          │      DaoUser.java
│          │      dbUtil.java
│          │
│          ├─filter
│          │      loginFilter.java
│          │
│          └─model
│                  User.java
│
└─web
    │  index.jsp
    │  login.jsp
    │  online_list.jsp
    │  register.jsp
    │
    ├─css
    │      style.css
    │
    ├─img
    │  ├─background
    │  │      1.jpg
    │  │      comment.png
    │  │
    │  └─huaji
    │          huaji.png
    │
    ├─js
    │      jquery-1.10.2.min.js
    │
    ├─tools
    │  └─owo
    │          OwO.json
    │          OwO.min.css
    │          OwO.min.js
    │
    └─WEB-INF
            web.xml
```

## 6.测试界面

### 1.用户注册

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test1.png)

### 2.用户登陆

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test2.png)

### 3.聊天界面

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test3.png)

### 4.匿名聊天

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test4.png)

### 5.背景图片切换

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test5.png)
![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test6.png)
![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test7.png)

### 6.文字颜色切换

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test8.png)

### 7.发送图片表情

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test9.png)

### 8.私聊消息

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test10.png)
![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test11.png)

### 9.敏感词屏蔽

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test12.png)

### 10.退出聊天室

![](https://cdn.jsdelivr.net/gh/injahow/chatroom/imgs/test13.png)

## 7.程序源码

https://github.com/injahow/chatroom
