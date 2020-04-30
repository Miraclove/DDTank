package Database;



import Config.ServerInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 * Class {@code DataBaseConnection} connect database and communicate with database
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class DataBaseConnection {
    // 驱动名及数据库url
    /** the config of jdbc driver {@value} */
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //    static final String DB_URL = "jdbc:mysql://"+ ServerInfo.MYSQL_IP +":" +
//        ServerInfo.MYSQL_PORT + "/" + ServerInfo.DB_NAME +"?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
    /** the config of jdbc url {@value} */
    static final String DB_URL ="jdbc:mysql://"+ ServerInfo.MYSQL_IP+":"+ServerInfo.MYSQL_PORT+"/"+ServerInfo.DB_NAME+"?useSSL=false&serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8";
    // 数据库用户名密码
    /** the config of jdbc user name {@value} */
    static final String USER = ServerInfo.DB_USER_NAME;
    /** the config of jdbc user password {@value} */
    static final String PASS = ServerInfo.DB_USER_PASSWORD;
    // 连接对象
    private Connection conn = null;
    private PreparedStatement prestmt = null;
    /**
     * Connect the database
     *
     * @author Weizhi
     * @version 1.0
     */
    public DataBaseConnection() {
        try {
            // 注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            // 连接数据库
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("DataBase Connected Successfully!");
    }

    // 查询
    /**
     * select in the database
     * @param sql the sql script
     * @author Weizhi
     * @version 1.0
     */
    public ResultSet getFromDataBase(String sql) {
        ResultSet rs = null;
        try {
            prestmt = conn.prepareStatement(sql);
            // 开始查询
            rs = prestmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    // 其他增、删、改操作
    /**
     * update,insert,delete,modify in the database
     * @param sql the sql script
     * @author Weizhi
     * @version 1.0
     */
    public boolean updateDataBase(String sql) {
        try {
            prestmt = conn.prepareStatement(sql);
            prestmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * close the database connection
     * @author Weizhi
     * @version 1.0
     */
    public void close() {
        try {
            if (prestmt != null && !prestmt.isClosed()) {
                prestmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn!=null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("DBConnection closed successfully!");
    }
}