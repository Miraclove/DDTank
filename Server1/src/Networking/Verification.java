package Networking;

import Config.*;
import Database.DataCheck;
import GameLogic.GameController;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
/**
 * Class {@code Verification} Deal with ObjectDataStream and process client request
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Verification implements Runnable {
    private Socket userSocket;

    public static Thread gameThread = null;
    /**
     * Constructor for Verification
     *
     * <p>Init Data
     *
     * @param userSocket the socket with the client
     * @author Weizhi
     * @version 1.0
     */
    Verification(Socket userSocket) {
        this.userSocket = userSocket;
    }
    /**
     * process the Object from the Client and send back the result Object
     *
     * @param obj the object from client
     * @return the precessed object
     * @author Weizhi
     * @version 1.0
     */
    private Object process(Object obj) {
        // 获取对象类型
        String objName = obj.getClass().getSimpleName();
//        System.out.println("The current process object is " + objName);

        // 返回结果对象
        Object result = null;

        switch (objName) {
            case "ChatVerify":
                // 登陆验证
                ChatVerify loginVerify = (ChatVerify) obj;
                if(ServerListener.getClientUser().containsKey(loginVerify.getUserId())) {
                    // 重复登陆
                    result = "Repeat_login";
                } else {
                    result = DataCheck.isLoginSuccess(loginVerify.getUserId(), loginVerify.getUserPassword());

                    // 为登录成功的用户创建聊天线程
                    if (result != null && (Boolean) result == true) {
//                        Position position = new Position(30f+ConnectedGroup.groups.size()*10,-28f,1,1);
//                        EntityInfo entityInfo = new EntityInfo(loginVerify.getUserId());
//                        entityInfo.setPosition(position);
//                        ConnectedGroup.groups.add(entityInfo);
                        System.out.println("Login success, create a new thread for the user");
                        new Thread(new InteractWithClient(loginVerify.getUserId())).start();

                    }
                }
                break;
            case "PureInfo":
//                // 修改资料
//                result = DataCheck.editInfo( (PureInfo)obj );
                break;
            case "String":
                // 获取字符串内容
                String field = obj.toString();

//                System.out.println(field);
                // 如果为获取信息
                if (field.startsWith("getInstantInfo")){
                    field = field.replace("getInstantInfo", "");
                    String info[] = field.split("```");
                    result = ConnectedGroup.groupInstantInfo.get(info[0]);
                }else if (field.startsWith("isExistGroup")){
                    field = field.replace("isExistGroup", "");
                    String info[] = field.split("```");
                    result = DataCheck.isGroupExist(info[0]);
                }else if (field.startsWith("createGroup")){
                    field = field.replace("createGroup", "");
                    String info[] = field.split("```");
                    result = DataCheck.createGroup(info[0],info[1],info[2]);
                }else if (field.startsWith("joinGroup")){
                    field = field.replace("joinGroup", "");
                    String info[] = field.split("```");
                    result = DataCheck.joinGroup(info[0],info[1]);
                }else if (field.startsWith("getLobbyInfo")){
                    result = DataCheck.getLobbyInfo();
                }else if (field.startsWith("getGroupMember")){
                    field = field.replace("getGroupMember", "");
                    String info[] = field.split("```");
                    result = DataCheck.getGroupMember(info[0]);
                }else if (field.startsWith("leaveGroup")){
                    field = field.replace("leaveGroup", "");
                    String info[] = field.split("```");
                    result = DataCheck.leaveGroup(info[0]);
                }else if (field.startsWith("setReady")){
                    field = field.replace("setReady", "");
                    String info[] = field.split("```");
                    result = DataCheck.setReady(info[0]);
                }else if (field.startsWith("setUnready")){
                    field = field.replace("setUnready", "");
                    String info[] = field.split("```");
                    result = DataCheck.setUnready(info[0]);
                }else if (field.startsWith("startGame")){
                    field = field.replace("startGame", "");
                    String info[] = field.split("```");
                    DataCheck.setGameStart(info[0]);
                    InteractWithClient.startGame(DataCheck.getGameInfo(info[0]),"");
                    result = true;
                }else if (field.startsWith("getInitGameInfo")){
                    field = field.replace("getInitGameInfo", "");
                    String info[] = field.split("```");
                    result = DataCheck.getGameInfo(info[0]);
                }else if (field.startsWith("registerUser")){
                    field = field.replace("registerUser", "");
                    String info[] = field.split("```");
                    result = DataCheck.registerUser(info[0],info[1]);
                }else if (field.startsWith("getWeaponList")){
                    field = field.replace("getWeaponList","");
                    String info[] = field.split("```");
                    result = DataCheck.getWeaponList();
                }else if (field.startsWith("getMapList")){
                    field = field.replace("getMapList","");
                    String info[] = field.split("```");
                    result = DataCheck.getMapList();
                }else if (field.startsWith("setUserWeapon")){
                    field = field.replace("setUserWeapon","");
                    String info[] = field.split("```");
                    result = DataCheck.setUserWeapon(info[0],info[1]);
                }else if (field.startsWith("setGameStart")){
                    field = field.replace("setGameStart","");
                    String info[] = field.split("```");
                    result = DataCheck.setGameStart(info[0]);
                }else if (field.startsWith("setGameEnd")){
                    field = field.replace("setGameEnd","");
                    String info[] = field.split("```");
                    result = DataCheck.setGameEnd(info[0]);
                }else if (field.startsWith("getMatchHistory")){
                    field = field.replace("getMatchHistory","");
                    String info[] = field.split("```");
                    result = DataCheck.getMatchHistory(info[0]);
                }

//
//                } else if (field.startsWith("getGroupMembers")) {
//                    // 获取群成员
//                    field = field.replace("getGroupMembers", "");
//                    result = DataCheck.getGroupMember(field);
//
//                } else if (field.startsWith("getFriendsOrGroups")) {
//                    // 搜索好友 TODO
//                    System.out.println("in search");
//                    field = field.replace("getFriendsOrGroups", "");
//                    String info[] = field.split("```");
//                    result = DataCheck.getSearchResult(info[0], info[1]);
//                } else if (field.startsWith("addFriends")) {
//                    // 添加好友或群，自身id```目标id```好友或群
//                    field = field.replace("addFriends", "");
//                    String info[] = field.split("```");
//                    if(info[2].equals("0"))
//                        // 0加好友1加群
//                        result = DataCheck.addFriend(info[0], info[1]);
//                    else
//                        result = DataCheck.addGroup(info[0], info[1]);
//
//                } else if (field.startsWith("deleteFriends")) {
//                    // 删除好友或退出群
//                    field = field.replace("deleteFriends", "");
//                    String info[] = field.split("```");
//                    if(info[2].equals("0"))
//                        // 0加好友1加群
//                        result = DataCheck.deleteFriend(info[0], info[1]);
//                    else
//                        result = DataCheck.exitGroup(info[0], info[1]);
//
//                } else if (field.startsWith("register")) {
//                    // 注册
//                    field = field.replace("register", "");
//                    String info[] = field.split("```");
//                    result = DataCheck.register(info[0], info[1]);
//
//                } else if(field.startsWith("getFriendInfo")) {
//                    // 获取指定用户简要资料
//                    field = field.replace("getFriendInfo", "");
//                    result = DataCheck.getPureInfo(field);
//
//                } else if(field.startsWith("createGroup")) {
//                    // 创建群聊, 用户id```群名
//                    field = field.replace("createGroup", "");
//                    String info[] = field.split("```");
//                    result = DataCheck.createGroup(info[0], info[1]);
//                } else if (field.startsWith("deleteGroupMember")) {
//                    // 踢出群成员
//                    field = field.replace("deleteGroupMember", "");
//                    String info[] = field.split("```");
//                    result = DataCheck.deleteGrouopMember(info[0], info[1]);
//                } else if (field.startsWith("getUserIP")) {
//                    // 获取指定用户的网络地址信息, 返回InetAddress对象
//                    field = field.replace("getUserIP", "");
//                    return ServerListener.getUserIP(field);
//                } else if (field.startsWith("getChatRecord")) {
//                    // 获取聊天历史记录
//                    field = field.replace("getChatRecord", "");
//                    String info[] = field.split("```");
//                    // uid```fid```好友0群1
//                    boolean isGroup = info[2].equals("1") ? true : false;
//                    return DataCheck.getChatRecord(info[0], info[1], isGroup);
//                }
                break;
        }

        return result;
    }
    /**
     * Receive object from user socket, process and return result
     *
     * @author Weizhi
     * @version 1.0
     */
    // 接收用户套接字传过来的对象，处理，回传结果
    @Override
    public void run() {
        try {
            // 对象输入输出流
            ObjectInputStream in = new ObjectInputStream(userSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(userSocket.getOutputStream());

            // 接收一个对象流
            Object obj = in.readObject();

            // 获取处理结果
            Object result = process(obj);

            // 返回给客户端这个处理结果
            out.writeObject(result);

            // 关闭所有流
            in.close();
            out.close();
            userSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }
    }
}