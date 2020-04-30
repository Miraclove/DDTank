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

    /** the config of CHAT_PORT {@value} */
    public final static int CHAT_PORT = 23456;

    /** the config of VERIFY_PORT {@value} */
    public final static int VERIFY_PORT = 12345;

    /** the config of MYSQL_IP {@value} */
    public final static String MYSQL_IP = "127.0.0.1";

    /** the config of MYSQL_PORT : Mysql port number {@value} */
    public final static int MYSQL_PORT = 3306;

    /** the config of Mysql database name {@value} */
    public final static String DB_NAME = "battlezone";

    /** the config of DB_USER_NAME : Mysql user name {@value} */
    public final static String DB_USER_NAME = "root";

    /** the config of DB_USER_PASSWORD : Mysql user password {@value} */
    public final static String DB_USER_PASSWORD = "123456";
}
