package Networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
/**
 * Class {@code ServerListener} wait and listen from client request
 *
 * <p> Record online user id
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class ServerListener {
    // 记录在线用户id
    private static HashMap<String, DataStream> clientUser;
    // 静态初始化块
    static {
        clientUser = new HashMap<String, DataStream>();
    }

    private ServerSocket serverSocket;
    private InetAddress localHostAddress;
    private int serverPort;
    /**
     * Constructor for ServerListener
     *
     * <p>Init Data
     *
     * @param port the port listen to
     * @author Weizhi
     * @version 1.0
     */
    public ServerListener(int port) {
        try {
            // 监听端口
            serverPort = port;

            // 创建监听端口的ServerSocket对象
            serverSocket = new ServerSocket(serverPort);

            // 获取本机地址
            localHostAddress = serverSocket.getInetAddress();

        } catch (IOException e) {
            System.out.println("错误信息 ：" + e.getMessage());
        }
    }

    /**
     * Get the server port
     *
     *
     * @return int, the listen port of server
     * @author Weizhi
     * @version 1.0
     */
    public int getServerPort() {
        return serverPort;
    }
    /**
     * Get the local host name
     *
     *
     * @return String, the local host name
     * @author Weizhi
     * @version 1.0
     */
    public String getLocalHostName() {
        return localHostAddress.getHostName();
    }
    /**
     * Get the local host address
     *
     *
     * @return String, the local host address
     * @author Weizhi
     * @version 1.0
     */
    public String getLocalHostAddress() {
        return localHostAddress.getHostAddress();
    }
    /**
     * Get the server socket
     *
     *
     * @return ServerSocket, the server socket
     * @author Weizhi
     * @version 1.0
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    /**
     * Get the client connection list in the server
     *
     *
     * @return HashMap<String, DataStream>, the client connection list in the server
     * @author Weizhi
     * @version 1.0
     */
    // 获取在线用户表
    public static HashMap<String, DataStream> getClientUser() {
        return clientUser;
    }
    /**
     * Get the client address
     *
     *
     * @return InetAddress, the client address
     * @author Weizhi
     * @version 1.0
     */
    // 获取指定用户IP
    public static InetAddress getUserIP(String userID) {
        return clientUser.get(userID).getClientInetAddress();
    }
}
