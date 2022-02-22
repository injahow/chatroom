package com.chatroom;

import com.chatroom.dao.DaoUser;
import com.chatroom.model.User;

import java.io.IOException;
import java.util.*;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/server/{id}")
public class Server {
    public Session session;
    public String name;
    private int uid;
    private String account;
    // account, list<this>
    private static final HashMap<String, ArrayList<Server>> connectMap = new HashMap<String, ArrayList<Server>>();

    @OnOpen
    public void start(Session session, @PathParam("id") String id) throws Exception {
        if (id == null)
            return;
        this.session = session;
        DaoUser daoUser = new DaoUser();
        User user = daoUser.getUserInfo(id);
        if (user == null)
            return;
        this.name = user.getName();
        this.account = user.getAccount();
        this.uid = user.getId();
        daoUser.userStart(uid);
        ArrayList<Server> newList = (ArrayList) connectMap.get(account);
        boolean is_null = newList == null;
        if (is_null) {
            newList = new ArrayList<Server>();
        }
        newList.add(this);
        connectMap.put(account, newList);
        if (is_null || newList.size() == 1) {
            String message = String.format("[%s %s]", name, "加入聊天室！");
            sendMessageAll(message);
        }
    }

    @OnClose
    public void end() throws Exception {
        ArrayList<Server> newList = connectMap.get(account);
        boolean isLast = newList.size() == 1;
        int del_i = 0;
        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).session == session) {
                del_i = i;
                break;
            }
        }
        newList.remove(del_i);
        if (isLast) {
            String message = String.format("[%s %s]", name, "离开聊天室！");
            sendMessageAll(message);
            DaoUser daoUser = new DaoUser();
            daoUser.userQuit(uid);
        }
    }

    @OnMessage
    public void print(String data) throws IOException {
        String[] data_arr = data.split("&&", 3);
        String from = data_arr[0];
        String tarUser = data_arr[1];
        String msg = data_arr[2];
        // 服务端处理敏感词
        String[] blacklist = { "kill", "杀", "die", "死", "document" };
        for (int i = 0; i < blacklist.length; i++) {
            msg = msg.replace(blacklist[i], "*");
            ;
        }
        // from&&to
        if (!tarUser.equals("0")) {
            sendMessageTo(tarUser, "【" + name + "】:<br>" + msg);
        } else {
            String from_name = from.equals("0") ? "匿名" + uid : name;
            sendMessageAll("【" + from_name + "】:<br>" + msg);
        }
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        System.out.println("WebSocket 服务端错误 " + t);
    }

    /**
     * 私聊
     */
    private void sendMessageTo(String tarUserAccount, String msg) throws IOException {
        Server client = null;
        // 目标用户
        ArrayList<Server> tarUserServerList = null;
        tarUserServerList = connectMap.get(tarUserAccount);

        // from && msg
        if (tarUserAccount != account) {
            session.getBasicRemote().sendText(tarUserAccount + "&&" + msg);
        }
        if (tarUserServerList == null) {
            session.getBasicRemote().sendText(tarUserAccount + "用户 " + tarUserAccount + " 不在线");
        } else {
            for (int i = 0; i < tarUserServerList.size(); i++) {
                Session tarUserSession = tarUserServerList.get(i).session;
                tarUserSession.getBasicRemote().sendText(account + "&&" + msg);
            }
        }
    }

    /**
     * 消息群发
     * 
     * @param msg
     */
    public void sendMessageAll(String msg) {
        // 遍历所有客户端
        Server client = null;
        for (String _account : connectMap.keySet()) {
            try {
                ArrayList<Server> clientList = (ArrayList) connectMap.get(_account);
                for (int i = 0; i < clientList.size(); i++) {
                    client = (Server) clientList.get(i);
                    synchronized (client) {
                        // 发送消息
                        client.session.getBasicRemote().sendText(msg);
                    }
                }

            } catch (IOException e) {
                System.out.println("错误，向客户端 " + client + " 发送消息出现错误。");
                connectMap.remove(account, client);
                try {
                    client.session.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                String message = String.format("[%s %s]", client.name, "已经被断开了连接。");
                sendMessageAll(message);
            }
        }
    }

}