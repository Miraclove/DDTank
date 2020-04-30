package Config;
/**
 * Class {@code ServerInfo} Contains server information for connection
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public final class ServerInfo {
    /** the config of server IP {@value} */
    public final static String SERVER_IP = "127.0.0.1";
    /** the config of chat port for only text message {@value}*/
    public final static int CHAT_PORT = 23456;
    /** the config of verify as well as ObjectDataStream port {@value} */
    public final static int VERIFY_PORT = 12345;
}