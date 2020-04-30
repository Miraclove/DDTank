package Networking;

import Config.ServerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Class {@code WaitForConnection} wait for data connection from client
 *
 * <p> use DataStream to transport Data, only text data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class WaitForConnection implements Runnable {
    // listen to the port to see connections
    private static ServerSocket serverSocket;
    // init
    static {
        serverSocket = (new ServerListener(ServerInfo.VERIFY_PORT)).getServerSocket();
    }

    // wait for user, start a new thread for the user
    /**
     * start waiting for client
     *
     *<p>wait for user, start a new thread for the user
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void run() {
        try {
            while (true) {
                // wait user connection
                Socket userSocket = serverSocket.accept();
//                System.out.println(userSocket.getInetAddress()+" user connected");
                // deal with user connection
                new Thread(new Verification(userSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("verified port error ：" + e.getMessage());
        }
    }
}