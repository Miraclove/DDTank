package Networking;



import Config.EntityInfo;
import Config.GameInfo;
import Config.ServerInfo;
import GameLogic.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;

/**
 * Class {@code InteractWithClient} send text message to client
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */

public final class InteractWithClient implements Runnable {
    // 监听聊天端口
    private static ServerSocket interactSocket;
    // 静态初始化块
    static {
        interactSocket = (new ServerListener(ServerInfo.CHAT_PORT)).getServerSocket();
    }

    private String userId;
    /// 该用户连接所生成的Socket对象
    private Socket userSocket;
    /**
     * Constructor for InteractWithClient
     *
     * <p>Init Data
     *
     * @param userId the user name of client
     * @author Weizhi
     * @version 1.0
     */
    // 初始化用户ID
    public InteractWithClient(String userId) {
        this.userId = userId;
    }
    /**
     * get connection with client
     *
     * <p>Wait for the client to connect and create a chat data stream object for it after successful connection
     * @author Weizhi
     * @version 1.0
     */
    // 等待客户端连接，成功连接之后为其创建一个聊天数据流对象
    @Override
    public void run() {
        // 先与客户端建立聊天端口的连接
        try {
            userSocket = interactSocket.accept();
        } catch (IOException e) {
            System.out.println("未能与客户端成功建立连接：" + e.getMessage());
            return;
        }

        // 成功接入之后建立数据流
        DataStream dataStream = new DataStream(userSocket, userId);

        // 加入到在线人员映射里面
        ServerListener.getClientUser().put(userId, dataStream);

        System.out.println("用户 " + userId + " 已成功登录 ,Info: " + userSocket.getInetAddress());
        System.out.println("当前在线人数： " + ServerListener.getClientUser().size());

        dataStream.run();
    }

    /**
     * Send start game message to client
     *
     * @param message other message
     * @author Weizhi
     * @version 1.0
     */
    public static void startGame(GameInfo gameInfo, String message){
        Set<String> userSet = gameInfo.gameEntity.keySet();
        new Thread(new GameController(gameInfo)).start();
        try {
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
        for (String key: userSet) {
            if (ServerListener.getClientUser().containsKey(key)) {
                ServerListener.getClientUser().get(key).send("startGame"+"```"+gameInfo.groupName+"```"+message);
            } else {
                // 群成员不在线情况
            }
        }
    }
}
