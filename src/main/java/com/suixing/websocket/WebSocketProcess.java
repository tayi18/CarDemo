package com.suixing.websocket;

import com.suixing.entity.UserMsg;
import com.suixing.mapper.UserMsgMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/*
*封装客户端与服务端的Websocket通讯：
1、连接对象的管理  ConcurrentHashMap<Long,WebSocketProcess>
2、事件监听 @OnOpen @OnMessage @OnClose @OnError
3、服务器向（所有/单个）客户端发送信息
*/
@Slf4j
@Component
@ServerEndpoint(value = "/WebSocket/{userId}")
public class WebSocketProcess {
    //持久化每webSocket对象，以key-value 存储到线程安全ConcurrentHashMap
    private static ConcurrentHashMap<Long, WebSocketProcess> concurrentHashMap = new ConcurrentHashMap<>();
    /*
    * 会话对象
    * */
    private Session session;
    @Autowired
    private UserMsgMapper userMsgMapper;
    /*
     * 客户端创建连接时触发
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") long id){
        //每建立一个新的连接，就把当前客户id为key，this为value 存储到map中
        this.session = session;
        concurrentHashMap.put(id,this);
        log.info("Open a websocket. id = {}",id);
    }

    /*客户端创建连接时触发
    * */
    @OnClose
    public void onClose(Session session, @PathParam("userId") long id ){
        //每建立连接，就把当前客户id为key，this为value存储到map中
        this.session = session;
      //  concurrentHashMap.remove(id);
        //log.info("close a wenSocket, concurrentHasMap remove sessionId ={}",id);

    }
    /*
     * 接收到客户端消息是触发
     * */
    @OnMessage
    public void onMessage(String message,@PathParam("userId") String id){
        log.info("receive a message from client id = {} ,msg = {}",id,message);
    }
    /*
     *连接时发生异常时候触发
     **/
    @OnError
    public void onError(Session session,Throwable error){
        log.error("Error while websocket.",error);
    }
    /*
    * 发送消息到指定客户端
    *
    * */
    public void sendMessage(long userId , String message, UserMsg userMsg)throws Exception{
        //根据id，从map中获取存储的webSocket对象
        WebSocketProcess webSocketProcess = concurrentHashMap.get(userId);
        if (!ObjectUtils.isEmpty(webSocketProcess)){
            //判断状态 open才能发送消息
            if (webSocketProcess.session.isOpen()){

                    userMsg.setUserMsgStatus("1");
                    userMsgMapper.insert(userMsg);
                System.out.println("客户端在线发送信息:"+message);
                webSocketProcess.session.getBasicRemote().sendText(message);
            }else {
                userMsgMapper.insert(userMsg);
                log.error("websocket session = {} is closed",userId);
            }
        }else {
            log.error("websocket session = {} id not exit",userId);
        }
    }
    /*
     *发送消息到所有客户端
     * */
    public void sendAllMessage(String msg)throws Exception{
        log.info("online client count = {}",concurrentHashMap.size());
        Set<Map.Entry<Long, WebSocketProcess>> entries = concurrentHashMap.entrySet();
        for (Map.Entry<Long, WebSocketProcess> entry :entries){
            Long cid = entry.getKey();
            WebSocketProcess webSocketProcess = entry.getValue();
            boolean sessionOpen = webSocketProcess.session.isOpen();
            if (sessionOpen){
                webSocketProcess.session.getBasicRemote().sendText(msg); //服务器向该客户端发送消息
            }else {
                log.info("cid={} id closed,ignore send text",cid);
            }
        }
    }
}
