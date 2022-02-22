<%--
  Created by IntelliJ IDEA.
  User: injah
  Date: 2020/11/21
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>User Chat</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="tools/owo/OwO.min.css">
</head>
<body id="body" class="back-cover" style="background-image:url(/img/background/1.jpg);">
    <div class="container">
        <div class="main">
            <div id="console-container">
                <div id="console_list" style="width:100%;">
                    <div class="show" id="console0"/>
                </div>
            </div>
            <div style="background-color: rgba(255,255,255,0.8)">
                <div style="height:50px">
                    <input type="checkbox" id="no_from" />匿名发言
                    <span>&nbsp;|&nbsp;&nbsp;背景图片：</span>
                    <select onchange="setBackground()" id="background_img">
                        <option value="1.jpg">1.jpg</option>
                        <option value="2.jpg">2.jpg</option>
                        <option value="3.jpg">3.jpg</option>
                        <option value="4.jpg">4.jpg</option>
                    </select>
                    <span>&nbsp;|&nbsp;&nbsp;文字颜色：</span>
                    <select onchange="setFontColor()" id="font_color">
                        <option value="black">black</option>
                        <option value="red">red</option>
                        <option value="blue">blue</option>
                        <option value="yellow">yellow</option>
                    </select>
                    <span>&nbsp;|&nbsp;&nbsp;在线时间：</span>
                    <span id="online_time"></span>
                    <div class="OwO"></div>
                </div>
            </div>
            <div style="background-color:rgba(255,255,255,0.8);height:120px;">
                <textarea placeholder="|´・ω・)ノ还不快点说点什么呀poi~" id="chat" class="messagebox" style="resize:none;width:100%;height:120px;background:url(/img/background/comment.png) right bottom no-repeat"></textarea>
                <input id="send" type="button" style="height:30px;width:100%" value="发送" />
            </div>
        </div>

        <div class="left">
            <div class="left-content" id="user_msg_list">
                <a href="javascript:void(0)" onclick="open_user_msg('0')") style="color: red">聊天室</a>
            </div>
        </div>

        <div class="right">
            <div id="table"></div>
        </div>
    </div>
</body>
<script src="tools/owo/OwO.min.js"></script>
<script src="js/jquery-1.10.2.min.js"></script>
<script>
    // 前端处理敏感词
    function checkMsg(msg){
        const blacklist = ['script','iframe','kill', '杀', 'die', '死' ]//......
        const strategy = '*'
        if(!msg || !strategy) return
        const r = blacklist.map(function(item){
            return '('+ item +')'
        }).join('|')
        const regex = new RegExp(r, 'g')
        return msg.replace(regex, strategy)
    }
    // 设置背景图片
    function setBackground(){
        const x = document.getElementById('background_img')
        const _value = x.options[x.selectedIndex].value;
        document.getElementById('body').setAttribute('style','background-image:url(img/background/'+_value+')')
    }
    function setFontColor(msg) {
        const x = document.getElementById('font_color')
        const _value = x.options[x.selectedIndex].value;
        return '<span style="color:'+ _value +'">'+msg+'</span>';
    }
    // 消息显示对象
    let Console = {}
    Console.log = (function(msg, account='0') {
        // 更新在线列表
        init_online_list()
        let message = msg.replace('\n', '<br>')
        message = checkMsg(message)
        let console = document.getElementById('console'+account)
        // 设置消息提醒
        if(account!=='0'){
            const p = document.createElement('p')
            p.style.wordWrap = 'break-word'
            p.innerHTML = '<strong>[系统提示：接收到用户'+account+'发来的信息!]</strong>'
            document.getElementById('console0').appendChild(p)
        }
        if(console===null){
            // 不存在
            const console_list = $('#console_list')[0]
            const div = document.createElement('div')
            div.setAttribute('id','console'+account)
            div.setAttribute('class','no-show')
            console_list.appendChild(div)
            console = document.getElementById('console'+account)
        }
        const p = document.createElement('p')
        p.style.wordWrap = 'break-word'
        p.innerHTML = message
        console.appendChild(p)
        while (console.childNodes.length > 25) {
            console.removeChild(console.firstChild)
        }
        console.scrollTop = console.scrollHeight
    });
    let Chat = {};
    Chat.socket = null;
    Chat.connect = (function(host) {
        if ('WebSocket' in window) {
            Chat.socket = new WebSocket(host);
        } else if ('MozWebSocket' in window) {
            Chat.socket = new MozWebSocket(host);
        } else {
            Console.log('Error: 浏览器不支持WebSocket');
            return;
        }
        Chat.socket.onopen = function () {
            Console.log('Info: WebSocket 链接已打开');
            document.getElementById('send').onclick = function(event) {
                Chat.sendMessage();
            };
        };
        Chat.socket.onclose = function () {
            document.getElementById('send').onclick = null;
            Console.log('Info: WebSocket 关闭.');
        };
        // 从服务器接收消息
        Chat.socket.onmessage = function (message) {
            const res = message.data
            if (res.indexOf('&&')==-1) {
                Console.log(res);
            }else{
                let _data = res.split('&&')
                let msg = ''
                for(let i=0;i<_data.length;i++){
                    if (i == 0) continue
                    msg += _data[i]
                }
                Console.log(msg, _data[0])
            }
        };
    });
    // 连接服务器
    Chat.initialize = function() {
        Chat.connect('ws://' + window.location.host + '/server/${account.id}');
        //Chat.connect('ws://10.83.5.131:8080/server/${account.id}');
    };
    // 发送消息给服务器
    Chat.sendMessage = function() {
        const from = document.getElementById('no_from').checked ? '0':'1'
        const to = showAcc
        const msg = document.getElementById('chat').value
        if (msg==='') {
            alert('请输入信息内容！')
            return
        }
        const message = from + '&&' + to + '&&' + setFontColor(msg)
        if (message != '') {
            Chat.socket.send(message)
            document.getElementById('chat').value = ''
        }
    };

    Chat.initialize()

    const OwO_demo = new OwO({
        logo: 'OωO表情',
        container: document.getElementsByClassName('OwO')[0],
        target: document.getElementsByClassName('messagebox')[0],
        api: '/tools/owo/OwO.json',
        position: 'up',
        width: '100%',
        maxHeight: '250px'
    });
    //在线时长
    const login_time = <%=session.getAttribute("login_time")%>
    setInterval(function(){
        const seconds = parseInt((new Date().getTime() - login_time)/1000)
        const currentTime = seconds;
        const min = '0' + parseInt(currentTime/60);
        let sec = currentTime % 60;
        if (sec < 10) {
            sec ='0'+sec;
        }
        let _return = sec + '秒'
        if (min != '0' ) _return = min + '分' + _return
        $('#online_time').text(_return)
    },1000)

    function console_display(){
        userMsgList.forEach((arr)=>{
            const acc = arr[0]
            if(acc === showAcc){
                $('#console'+acc).attr('class','show')
            }else{
                $('#console'+acc).attr('class','no-show')
            }
        })
    }

    function list_display(){
        const user_msg_list = $('#user_msg_list')[0]
        user_msg_list.innerHTML=''
        userMsgList.forEach((arr)=>{
            const a = document.createElement('a')
            a.setAttribute('href','javascript:void(0)')
            a.setAttribute('onclick','open_user_msg("'+arr[0]+'")')
            if(arr[0]===showAcc){
                a.setAttribute('style','color:red;')
            }else{
                a.setAttribute('style','color:black;')
            }
            a.innerHTML= arr[1]
            user_msg_list.appendChild(a)
        })
        console_display()
    }

    function add_user(account,name) {
        let arr = [account,name]
        let f = 0
        userMsgList.forEach((i)=>{
            if (i.toString() === arr.toString()){
                f=1
            }
        })
        if(f==0){
            // 不存在
            userMsgList.push(arr)
            const console_list = $('#console_list')[0]
            const div = document.createElement('div')
            div.setAttribute('id','console'+account)
            console_list.appendChild(div)
        }
        showAcc = account
        list_display()
    }

    function open_user_msg(account) {
        showAcc = account
        list_display()
    }

    function init_online_list() {
        $.ajax({
            type: 'get',
            url: 'online_list',
            async: true,
            cache: false,
            dataType: 'html',
            success: function (data) {
                $("#table").html(data);
            }
        })
    }
    init_online_list()
    const userMsgList = []
    let showAcc = '0'
    userMsgList.push(['0','聊天室'])

</script>
</html>