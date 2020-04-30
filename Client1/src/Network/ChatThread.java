package Network;

import Config.ServerInfo;

import java.io.IOException;
import java.net.Socket;
/**
 * Class {@code ChatThread} Create the Chatting thread to get text message from the server
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public final class ChatThread implements Runnable {
    // userId : 用户IDChat
    private String userId;
    //dataStream : 聊天数据流传输对象，主要通过它发送消息或者接收消息
    private static DataStream dataStream;
    //     初始化用户ID
    /**
     * Constructor for ChatThread
     *
     * <p>
     *
     * @param userId the user name of the player
     * @author Weizhi
     * @version 1.0
     */
    public ChatThread(String userId) {
        this.userId = userId;
    }
    //    创建与服务器聊天端口的通讯，并创建线程开始(接收/发送)数据
    /**
     * The auto generated run by Runnable
     *
     *<p> Create DataStream for text communication with server
     *
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void run() {
        Socket myHost = null;
        try {
            // 创建与服务端的连接
            myHost = new Socket(ServerInfo.SERVER_IP, ServerInfo.CHAT_PORT);
            // 聊天数据信息流，用来接收或者发送信息
            dataStream = new DataStream(myHost, userId);
            new Thread(dataStream).start();
        } catch (IOException e) {
            System.out.println("创建与服务端的连接出错：" + e.getMessage());

        }
    }

    //     返回与服务器聊天端口创建的信息数据流对象
    public static DataStream getDataStream() {
        return dataStream;
    }
}