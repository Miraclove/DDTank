package Network;

import Config.*;
import org.apache.xpath.operations.Bool;
import org.lwjgl.system.CallbackI;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
/**
 * Class {@code InteractWithServer} Use ObjectDataStream to transport information to the server.
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public final class InteractWithServer {
    /**
     * Post Object to the server, and get respond as Object
     *
     * <p> Establish a connection to the server authentication port and use the object stream to transfer data
     *
     * @param obj the Object sending to server
     * @return {@link Object} get the object from the server which contains information
     * @author Weizhi
     * @version 1.0
     */
    // 与服务器验证端口建立连接，并使用对象流传输数据
    private static Object postToServer(Object obj) {
        Object result = null;
        try {
            // 建立连接
            Socket sc = new Socket(ServerInfo.SERVER_IP, ServerInfo.VERIFY_PORT);

            // 创建对象输入输出流
            ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(sc.getInputStream());

            // 写对象到服务端
            out.writeObject(obj);

            // 获取返回信息
            result = in.readObject();

            // 关闭流
            sc.close();
            in.close();
            out.close();
        } catch (ClassNotFoundException | IOException e) {
            JOptionPane.showMessageDialog(null, "在与服务器验证交互中出现了异常，请重新登录", "错误", JOptionPane.WARNING_MESSAGE);
            System.out.println("在与服务器验证交互中出现了异常：" + e.getMessage());
            System.exit(0);
        }
        return result;
    }
    /**
     * Verify that the login username or password is correct and send the constructed identity information to the server
     *
     * @param userId the user name of the Client user
     * @param userPassword the user password of the Client user
     * @return {@link Boolean} return true for correct, false for incorrect
     * @author Weizhi
     * @version 1.0
     */
    // 验证登录用户名或密码是否正确，发送构造的身份信息到服务器
    public static Boolean isLogin(String userId, String userPassword) {
        // 构造身份信息
        ChatVerify userInfo = new ChatVerify(userId, userPassword);

        // 返回服务器产生的结果
        return (Boolean) postToServer(userInfo);
    }
    /**
     * Check whether all users required have logined in
     *
     * @return {@link Boolean} return true for correct, false for incorrect
     * @author Weizhi
     * @version 1.0
     */
    public static boolean getAllLogin() {
        String fieldString = "isAllLogin";
        return (boolean) postToServer(fieldString);
    }
    /**
     * Send player position
     * @param userName  the Client user name
     * @param positionX the position x of the entity in map
     * @param positionY the position y of the entity in map
     * @param message other message
     * @author Weizhi
     * @version 1.0
     */
    public static void sendPlayerInfo(String userName,String groupName,String positionX,String positionY, String message){
        message = userName+"```"+groupName+"```" + positionX + "```" + positionY + "```" + message;
        ChatThread.getDataStream().send("updatePlayer",message);
    }
    /**
     * Send GUI information
     * @param angle angle of weapon
     * @param percent the force of weapon
     * @param message other message
     * @author Weizhi
     * @version 1.0
     */
    public static void sendGuiInfo(String angle, String percent, String message){
        message =  angle + "```" + percent + "```" + message;
        ChatThread.getDataStream().send("updateGuiInfo",message);
    }
    /**
     * Send weapon crash info
     * @param message other message
     * @author Weizhi
     * @version 1.0
     */
    public static void sendWeapon(String message){
        ChatThread.getDataStream().send("updateWeapon",message);
    }
    /**
     * Get instant information from server
     *
     * @return {@link InstantInfo} real time instant info
     * @author Weizhi
     * @version 1.0
     */
    public static InstantInfo getInstantInfo(String groupName){
        InstantInfo instantInfo = null;
        String fieldString = "getInstantInfo"+groupName;
        instantInfo = (InstantInfo) postToServer(fieldString);
        return instantInfo;
    }
    /**
     * Check whether the group exists
     *
     * @return {@link Boolean} return true or false
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean isExistGroup(String groupName){
        Boolean isExistGroup = false;
        String fieldString = "isExistGroup" + groupName;
        isExistGroup = (Boolean) postToServer(fieldString);
        return isExistGroup;
    }
    /**
     * Create group in the lobby
     *
     * @param userName the Client user name
     * @param groupName the Group name of lobby
     * @return {@link Boolean} return whether process is successful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean createGroup(String groupName, String userName,String gameMap){
        Boolean isSuccess;
        String fieldString = "createGroup" + groupName +"```" +userName+"```" +gameMap;
        isSuccess = (Boolean)postToServer(fieldString);
        return isSuccess;
    }
    /**
     * Join group in the lobby
     *
     * @param userName the Client user name
     * @param groupName the Group name of lobby
     * @return {@link Boolean} return whether process is successful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean joinGroup(String groupName,String userName){
        Boolean isSuccess;
        String fieldString = "joinGroup" + groupName +"```" +userName;
        isSuccess = (Boolean)postToServer(fieldString);
        return isSuccess;
    }
    /**
     * get lobby information from server
     *
     * @return {@link LobbyInfo} the lobby information
     * @author Weizhi
     * @version 1.0
     */
    public static LobbyInfo getLobbyInfo(){
        LobbyInfo result;
        String fieldString = "getLobbyInfo";
        result = (LobbyInfo) postToServer(fieldString);
        return result;
    }
    /**
     * get Game information from server
     * @param groupName the group name of lobby
     * @return {@link GameInfo} the Game information
     * @author Weizhi
     * @version 1.0
     */
    public static Vector<String> getGroupMember(String groupName){
        Vector<String> result;
        String fieldString = "getGroupMember" + groupName;
        result = (Vector<String>) postToServer(fieldString);
        return result;
    }
    /**
     * Leave Lobby
     * @param userName the Client user name
     * @return {@link Boolean} whether the process is successful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean leaveGroup(String userName){
        Boolean isSuccess;
        String fieldString = "leaveGroup" + userName;
        isSuccess = (Boolean) postToServer(fieldString);
        return isSuccess;
    }
    /**
     * Set user state to ready
     * @param userName the Client user name
     * @return {@link Boolean} whether the process is successful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean setReady(String userName){
        Boolean isSuccess;
        String fieldString = "setReady" + userName;
        isSuccess = (Boolean) postToServer(fieldString);
        return isSuccess;
    }
    /**
     * Set user state to Unready
     * @param userName the Client user name
     * @return {@link Boolean} whether the process is successful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean setUnready(String userName){
        Boolean isSuccess;
        String fieldString = "setUnready" + userName;
        isSuccess = (Boolean) postToServer(fieldString);
        return isSuccess;
    }
    /**
     * Send start game request
     * @param groupName the group name of the starting game
     * @return {@link Boolean} whether the process is successful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean startGame(String groupName){
        Boolean isSuccess;
        String fieldString = "startGame" + groupName;
        isSuccess = (Boolean) postToServer(fieldString);
        return isSuccess;
    }
    /**
     * Get game information of the starting game
     * @param groupName the group name of the starting game
     * @return {@link GameInfo} get the information of the game to start
     * @author Weizhi
     * @version 1.0
     */
    public static GameInfo getInitGameInfo(String groupName){
        GameInfo gameInfo;
        String fieldString = "getInitGameInfo" + groupName;
        gameInfo = (GameInfo) postToServer(fieldString);
        return gameInfo;
    }
    /**
     * Register user to the server
     *
     * @param userName the user name of the Client user
     * @param password the user password of the Client user
     * @return {@link Boolean} return true for correct, false for incorrect
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean registerUser(String userName,String password){
        Boolean isSuccess;
        String fieldString = "registerUser" +userName +"```" +password;
        isSuccess = (Boolean) postToServer(fieldString);
        return isSuccess;
    }

    public static Vector<String> getWeaponList() {
        Vector<String> weaponList;
        String fieldString = "getWeaponList";
        weaponList = (Vector<String>) postToServer(fieldString);
        return weaponList;
    }

    public static boolean setUserWeapon(String userName,String weapon) {
        Boolean isSuccess;
        String fieldString = "setUserWeapon" +userName +"```" +weapon;
        isSuccess = (Boolean) postToServer(fieldString);
        return isSuccess;
    }

    public static Vector<String> getMapList() {
        Vector<String> weaponList;
        String fieldString = "getMapList";
        weaponList = (Vector<String>) postToServer(fieldString);
        return weaponList;
    }

    public static Boolean setGameEnd(String groupName) {
        Boolean isSuccess;
        String fieldString = "setGameEnd" +groupName;
        isSuccess = (Boolean) postToServer(fieldString);
        return isSuccess;
    }

    public static Vector<String> getMatchHistory(String curUsername) {
        Vector<String> matchList;
        String fieldString = "getMatchHistory" + curUsername;
        matchList = (Vector<String>) postToServer(fieldString);
        return matchList;
    }

//    // 通过ID获取用户的信息，包括个人资料以及好友列表群列表
//    public static User getUserInfo(String userId) {
//        User userInfo = null;
//        String fieldString = "getUserInfo" + userId;
//        userInfo = (User) postToServer(fieldString);
//        return userInfo;
//    }
//    //注册
//    public static String register(String userName, String password) {
//        String userID = null;
//        String fieldString = "register" + userName + "```" + password;
//        userID = (String) postToServer(fieldString);
//        return userID;
//    }
//    //得到搜索好友列表
//    public static Vector<UserInfo.FriendsOrGroups> getFriendsOrGroup(String fid, String uID){
//        Vector<UserInfo.FriendsOrGroups> friendsOrGroups=null;
//        String fieldString = "getFriendsOrGroups" + fid +  "```" + uID;
//        friendsOrGroups = (Vector<UserInfo.FriendsOrGroups>) postToServer(fieldString);
//        return friendsOrGroups;
//    }
//
//
//    //申请好友信息
//    public static PureInfo getFriendInfo(String fid){
//        PureInfo pureInfo=null;
//        String fieldString ="getFriendInfo" + fid;
//        pureInfo =(PureInfo)postToServer(fieldString);
//        return pureInfo;
//    }
//    //提交修改信息
//    public static Boolean modifyUserInfo(PureInfo pureInfo){
//        Boolean isSuccess = false;
//        isSuccess = (Boolean)postToServer(pureInfo);
//        return isSuccess;
//    }
//    //创建群聊
//    public static UserInfo.FriendsOrGroups createGroup(String uid,String gName){
//        UserInfo.FriendsOrGroups friendsOrGroups = null;
//        String fieldString = "createGroup"+uid+"```"+gName;
//        friendsOrGroups=(UserInfo.FriendsOrGroups)postToServer(fieldString);
//        return friendsOrGroups;
//    }
//    //删除群成员
//    public static Boolean deleteGroupMember(String gid,String uid){
//        Boolean isSuccess = false;
//        String fieldString = "deleteGroupMember"+gid+"```"+uid;
//        isSuccess = (Boolean)postToServer(fieldString);
//        return isSuccess;
//    }
//    //设置为管理员
//    public static Boolean setAdmin(String gid,String uid){
//        Boolean isSuccess=false;
//        String fieldString = "setAdmin"+gid+"```"+uid;
//        isSuccess = (Boolean)postToServer(fieldString);
//        return isSuccess;
//    }
//
//    //添加好友
//    public static Boolean addFriends(String mid,String fid,boolean isFriend){
//        boolean isSuccess = false;
//        String fieldString = "";
//        if (isFriend){
//            fieldString = "addFriends" + mid + "```" + fid + "```"+ "0";
//        }else {
//            fieldString = "addFriends" + mid + "```" + fid + "```"+ "1";
//        }
//        isSuccess = (boolean)postToServer(fieldString);
//        return isSuccess;
//    }
//    //删除好友
//    public static Boolean deleteFriends(String mid,String fid,boolean isFriend){
//        boolean isSuccess = false;
//        String fieldString = "";
//        if (isFriend){
//            fieldString = "deleteFriends" + mid + "```" + fid + "```"+ "0";
//        }else {
//            fieldString = "deleteFriends" + mid + "```" + fid + "```"+ "1";
//        }
//        isSuccess = (boolean)postToServer(fieldString);
//        return isSuccess;
//    }
//
//    // 通过交互双方id获取历史聊天记录
//    public static Vector<String> getChatRecord(String fromid, String toId,Boolean isGroup) {
//        String sendString;
//        if (isGroup){
//            sendString = "getChatRecord" + fromid + "```" + toId + "```" + "1";
//        }else {
//            sendString = "getChatRecord" + fromid + "```" + toId + "```" + "0";
//        }
//        return (Vector<String>) postToServer(sendString);
//    }
//    // 通过群ID向服务器发送请求，获取群所有成员ID
//    public static  Vector<UserInfo.FriendsOrGroups> getGroupMembers(String groupId) {
//        Vector<UserInfo.FriendsOrGroups> friendsOrGroups=null;
//        String fieldString = "getGroupMembers" + groupId;
//        friendsOrGroups = (Vector<UserInfo.FriendsOrGroups>) postToServer(fieldString);
//        return friendsOrGroups;
//    }
//    //获得用户IP
//    public static InetAddress getUserIP(String userID){
//        String fieldString = "getUserIP" + userID;
//        return (InetAddress)postToServer(fieldString);
//    }
//
//    // 向服务器发送请求更改个性签名
//    public static void setMyTrades(String myId, String content) {
//        postToServer("setMyTrades```" + myId + "```" + content);
//    }


}