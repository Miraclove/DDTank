package Networking;



import Calculate.World;
import Config.EntityInfo;
import Config.GuiInfo;
import Config.Position;
import Database.DataCheck;
import GameLogic.GameController;
import org.joml.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Vector;
/**
 * Class {@code DataStream} DataStream for communication with server by text
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public final class DataStream implements Runnable{
    private DataInputStream in;
    private DataOutputStream out;
    private String userId;
    private InetAddress clientInetAddress;

    // 利用与客户端连接的Socket对象创建数据输入输出流
    /**
     * Constructor for DataStream
     *
     * <p>Create a data input and output stream using the Socket object connected to the client
     *
     * @param clientSocket the user client to connect the server
     * @param userId the user name of the current client user
     * @author Weizhi
     * @version 1.0
     */
    DataStream(Socket clientSocket, String userId) {
        this.clientInetAddress = clientSocket.getInetAddress();
        this.userId = userId;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("创建聊天数据传输流失败：" + e.getMessage());
        }
//        sendToAllFriends("OnlineSituation```在线```" + userId);
    }

//    // 通知该用户的所有好友(一般为上线信息或离线信息)
//    private void sendToAllFriends(String message) {
//        System.out.println(message);
//        //当前连接用户好友列表
//        Vector<String> friends;
//        friends = DataCheck.getFriendMember(userId);
//        for (int i = 0; i < friends.size(); i++) {
//            if (ServerListener.getClientUser().containsKey(friends.get(i))) {
//                ServerListener.getClientUser().get(friends.get(i)).send(message);
//            }
//        }
//    }
    /**
     * Auto generated by Runnable
     *
     *<p> Receive requests from clients
     *
     * @author Weizhi
     * @version 1.0
     */
    // 接收客户端发送来的请求
    @Override
    public void run() {
        String scMessage;
        try {
            while (true) {
                scMessage = in.readUTF();
                // 处理消息
                execute(scMessage);
            }
        } catch (IOException e) {
            // 离线断开连接出错
            // 离线后删除用户在线信息
            ServerListener.getClientUser().remove(userId);

            // 通知所有好友离线情况
//            sendToAllFriends("OnlineSituation```离线```" + userId);
            System.out.println("用户" + userId + "的离线，剩余在线人数 ：" + ServerListener.getClientUser().size());
        }
    }
//
    /**
     * receive message from clients and process
     *
     * <p>
     * Process the information sent by the client and forward it to other users or groups
     * @param message the message from client
     * @author Weizhi
     * @version 1.0
     */
    // 处理客户端所发送来的信息，将它转发给其他用户或群
    private void execute(String message) {
        System.out.println("Receive from "+ userId+" : "+message);
        String res[] = message.split("```", 2);
        String type = res[0];
        String msg = res[1];
        if (res.length == 2) {
            if (type.equals("updatePlayer")){
                String temp[] = msg.split("```", 5);
                String target = temp[0];
                String groupName = temp[1];
                String x = temp[2];
                String y = temp[3];
                ConnectedGroup.groupInstantInfo.get(groupName).entityInfoList.get(target).setPosition(Float.parseFloat(x),Float.parseFloat(y));
                ConnectedGroup.worldHashMap.get(groupName).entityList.get(target).setPosition(new Vector2f(Float.parseFloat(x),Float.parseFloat(y)));
                // 发给除了自己的其他人
//                for (int i = 0; i < groupM.size(); i++) {
//                    if (!groupM.get(i).getUserName().equals(userId)
//                            && ServerListener.getClientUser().containsKey(groupM.get(i).getUserName())) {
//                        ServerListener.getClientUser().get(groupM.get(i).getUserName()).send(message);
//                    } else {
//
//                        // 群成员不在线情况
//                    }
//                }
            }else if (type.equals("updateGuiInfo")){
                String temp[] = msg.split("```", 3);
                GuiInfo guiInfo = new GuiInfo(temp[0],temp[1]);
                GameController.guiInfo = guiInfo;
                GameController.isGuiUploaded = true;
            }else if(type.equals("updateWeapon")){
                GameController.isCrash = true;
            }
        } else {
            System.out.println("收到的信息不规范：" + message);
        }
//        if (res.length == 4) {
//            String res[] = scMessage.split("```", 5);
//            // 从服务端发送的内容解码之后长度为5，代表该消息为聊天内容
//
//            message = new Date().toString() + "```" + message;

//            if (type.equals("toFriend")) {
////                // 发送给好友的消息
////                if (ServerListener.getClientUser().containsKey(toId)) {
////                    ServerListener.getClientUser().get(toId).send(message);
////                    System.out.println(message);
////                } else {
////                    // 好友不在线的情况
////                }
////                // 存入数据库
////                // message, 时间```标识```fromid```toid```content
////                String info[] = message.split("```");
//            } else if (type.equals("toGroup")) {
//                ServerListener.getClientUser().get("Mike").send(message);
//                ServerListener.getClientUser().get("Jack").send(message);
//                // 群聊消息
////                Vector<GroupMember> groupM = DataCheck.getGroupMember(toId);
//                // 判断是否是群成员
////                boolean isMember = false;
////                for (int i = 0; i < groupM.size(); i++) {
////                    if (groupM.get(i).getId().equals(userId)) {
////                        isMember = true;
////                        break;
////                    }
////                }
//                // 不是群成员发不了消息（有可能被踢出群了）
////                if (!isMember) return;
////
////                 //发给除了自己的其他人
////                for (int i = 0; i < ServerListener.getClientUser().size(); i++) {
//
////                    if (ServerListener.getClientUser().containsKey(groupM.get(i).getUserId())) {
////
////                    } else {
////                        // 群成员不在线情况
////                    }
////                }
////                // 保存消息
////                // message, 时间```标识```fromid```toid即群id```content
////                String info[] = message.split("```");
////                DataCheck.saveMessage(info[0], info[4], info[2], info[3], true);
//            }

    }
    /**
     * Send message to client
     *
     *<p>Send message to client, specific encoding of sent content
     *
     * @param message the text that will send to server
     * @author Weizhi
     * @version 1.0
     */
    // 发送消息到连接的客户端
    public void send(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println(userId + "发送到客户端失败：" + e.getMessage());
        }
    }
    /**
     * get internet address of connected client
     *
     * @return InetAddress, internet address
     * @author Weizhi
     * @version 1.0
     */
    public InetAddress getClientInetAddress() {
        System.out.println("In DataStream.getClientInetAddress：" + clientInetAddress);
        return clientInetAddress;
    }

}