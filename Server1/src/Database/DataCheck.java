package Database;

import Calculate.Weapon;
import Config.EntityInfo;
import Config.GameInfo;
import Config.LobbyInfo;
import Config.WeaponInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 * Class {@code DataCheck} All the SQL scripts and check in database
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class DataCheck {
    private static int matchCounter = 1;
    /**
     * check whether login is successful
     *
     * @param userName the user name
     * @param userPass the user password
     * @return Boolean,true : successful, false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    // login check
    public static boolean isLoginSuccess(String userName, String userPass) {
        boolean isSuccess = false;
        try {
            // check whether the user exist
            DataBaseConnection conn = new DataBaseConnection();
            String sql = "select * from user where name = '" + userName +
                    "' and passwd = '" + userPass +"'";

            // if exist then return true
            isSuccess = conn.getFromDataBase(sql).next();

            // close the database connection
            conn.close();
        } catch (SQLException e) {
            System.out.println("check identification fail: " + e.getMessage());
        }
        return isSuccess;
    }
    /**
     * check whether group exist
     *
     * @param groupName the group name
     * @return Boolean,true : exist , false : not exist
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean isGroupExist(String groupName){
        boolean isSuccess = false;
        try {
            // check whether the user exist
            DataBaseConnection conn = new DataBaseConnection();
            String sql = "select * from lobby  where lobby.name =  '" + groupName + "'";

            // if exist then return true
            isSuccess = conn.getFromDataBase(sql).next();

            // close the database connection
            conn.close();
        } catch (SQLException e) {
            System.out.println("check identification fail: " + e.getMessage());
        }
        return isSuccess;
    }
    /**
     * create a group
     *
     * @param groupName the group name
     * @param userName the user name
     * @return Boolean,true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean createGroup(String groupName,String userName,String mapName){
        System.out.println("createGroup");
        DataBaseConnection dataCon = new DataBaseConnection();

        String sql = "insert into lobby(name,number,map_id) values ('"+groupName+"',4,(select map.id from map where map.name = '"+mapName+"'))";
        String sql2 = "insert into lobby_user(lobby_id,player_id) values ((select lobby.id from lobby where lobby.name = '"+groupName+"'),(select user.id from user where user.name = '"+userName+"'))";

        if(dataCon.updateDataBase(sql)&&dataCon.updateDataBase(sql2)){
            dataCon.close();
            return true;
        }
        dataCon.close();
        return false;
    }
    /**
     * join a group
     *
     * @param groupName the group name
     * @param userName the user name
     * @return Boolean,true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean joinGroup(String groupName,String userName){
        System.out.println("joinGroup");
        DataBaseConnection dataCon = new DataBaseConnection();

        String sql2 = "insert into lobby_user(lobby_id,player_id) values ((select lobby.id from lobby where lobby.name = '"+groupName+"' and lobby.isStart = 0),(select user.id from user where user.name = '"+userName+"'))";

        if(dataCon.updateDataBase(sql2)){
            dataCon.close();
            return true;
        }
        dataCon.close();
        return false;
    }
    /**
     * get lobby information
     *
     * @return LobbyInfo, contains all the group
     * @author Weizhi
     * @version 1.0
     */
    public static LobbyInfo getLobbyInfo(){
        Vector<String> lobbyInfo = new Vector<>();
        try {
            // check whether the user exist
            DataBaseConnection dataCon = new DataBaseConnection();
            // 最终结果Vector数组

            String sql = "select lobby.name, map.name as map_name from lobby,map where lobby.isStart = 0 and lobby.map_id = map.id";
            ResultSet resultSet = dataCon.getFromDataBase(sql);
            try {
                while (resultSet.next()) {
                    lobbyInfo.add(resultSet.getString("name") +" | " +resultSet.getString("map_name"));
                }
            } catch (SQLException e) {
                System.out.println("获取群信息失败 " + e.getMessage());
            }
            resultSet.close();


            // close the database connection
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("check identification fail: " + e.getMessage());
        }
        return new LobbyInfo(lobbyInfo);
    }
    /**
     * get Game information
     *
     * @param groupName the group name
     * @return GameInfo, contains game information
     * @author Weizhi
     * @version 1.0
     */
    public static GameInfo getGameInfo(String groupName){
        System.out.println("Get Game Info: "+groupName);
        GameInfo gameInfo = new GameInfo();
        try {
            // check whether the user exist
            DataBaseConnection dataCon = new DataBaseConnection();
            // 最终结果Vector数组

            String sql = "select user.name , lobby_user.is_ready, weapon.name as 'weapon' from lobby_user, user,lobby,weapon where lobby.id = lobby_user.lobby_id and lobby_user.player_id = user.id and user.weapon_id = weapon.id and lobby.name = '"+groupName+"'";
            ResultSet resultSet = dataCon.getFromDataBase(sql);
            try {
                while (resultSet.next()) {
                    String userName = resultSet.getString("name");
                    EntityInfo entityInfo = new EntityInfo(userName);
                    boolean isReady = resultSet.getBoolean("is_ready");
                    entityInfo.setReady(isReady);
                    String weapon = resultSet.getString("weapon");
                    entityInfo.setWeapon(weapon);
                    entityInfo.setPosition(10,-30);
                    gameInfo.gameEntity.put(userName,entityInfo);
                }
            } catch (SQLException e) {
                System.out.println("获取群信息失败 " + e.getMessage());
            }
            resultSet.close();
            dataCon.close();

            DataBaseConnection con = new DataBaseConnection();
            sql = "select map.name from lobby, map where lobby.map_id = map.id and lobby.name = '"+groupName+"'";
            ResultSet result = con.getFromDataBase(sql);
            String playground = "bridge";
            System.out.println("Play ground: "+playground);
            gameInfo.playground = playground;
            gameInfo.groupName = groupName;
            result.close();
            // close the database connection
            con.close();
        } catch (SQLException e) {
            System.out.println("check identification fail: " + e.getMessage());
        }
        return gameInfo;
    }
    /**
     * leave a group
     *
     * @param userName the user name
     * @return Boolean,true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean leaveGroup(String userName){
        boolean isSuccess = false;
        DataBaseConnection conn = new DataBaseConnection();
        String sql = "delete from lobby_user where lobby_user.player_id = (select user.id from user where user.name = '"+userName+"')";
        // if exist then return true
        isSuccess = conn.updateDataBase(sql);
        // close the database connection
        conn.close();
        return isSuccess;
    }
    /**
     * set user condition to ready
     *
     * @param userName the user name
     * @return Boolean,true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean setReady(String userName){
        boolean isSuccess = false;
        DataBaseConnection conn = new DataBaseConnection();
        String sql = "update lobby_user set is_ready = 1 where player_id = (select user.id from user where user.name = '"+userName+"')";
        // if exist then return true
        isSuccess = conn.updateDataBase(sql);
        // close the database connection
        conn.close();
        return isSuccess;
    }
    /**
     * set user condition to Unready
     *
     * @param userName the user name
     * @return Boolean,true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean setUnready(String userName){
        boolean isSuccess = false;
        DataBaseConnection conn = new DataBaseConnection();
        String sql = "update lobby_user set is_ready = 0 where player_id = (select user.id from user where user.name = '"+userName+"')";
        // if exist then return true
        isSuccess = conn.updateDataBase(sql);
        // close the database connection
        conn.close();
        return isSuccess;
    }
    /**
     * Register user to database
     *
     * @param userName the user name
     * @param password the user password
     * @return Boolean,true : successful , false : unsuccessful
     * @author Demon
     * @version 1.0
     */
    public static Boolean registerUser(String userName,String password){
        System.out.println("registerUser");
        DataBaseConnection dataCon = new DataBaseConnection();
        String sql2 = "insert into user(name,passwd) values ('"+userName+"','"+password+"')";
        if(dataCon.updateDataBase(sql2)){
            dataCon.close();
            return true;
        }
        dataCon.close();
        return false;
    }
    /**
     * Get weapon info from database
     *
     * @param weapon the weapon name
     * @return WeaponInfo, contains weapon information
     * @author Weizhi
     * @version 1.0
     */
    public static WeaponInfo getWeaponInfo(String weapon){
        DataBaseConnection dataCon = new DataBaseConnection();
        // 最终结果WeaponInfo
        WeaponInfo weaponInfo = null;
        try {

            String sql = "select * from weapon where weapon.name = '"+weapon+"'";
            ResultSet resultSet = dataCon.getFromDataBase(sql);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                int power = resultSet.getInt("power");
                int startAngle = resultSet.getInt("angle_start");
                int endAngle = resultSet.getInt("angle_end");
                weaponInfo = new WeaponInfo(name,"",power);
                weaponInfo.setStartAngle(startAngle);
                weaponInfo.setEndAngle(endAngle);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        dataCon.close();
        return weaponInfo;
    }
    /**
     * Get weapon list from database
     *
     * @return WeaponInfo, contains weapon information
     * @author Weizhi
     * @version 1.0
     */
    public static Vector<String> getWeaponList(){
        DataBaseConnection dataCon = new DataBaseConnection();
        // 最终结果WeaponInfo
        Vector<String> weaponList = new Vector<>();
        try {
            String sql = "select * from weapon";
            ResultSet resultSet = dataCon.getFromDataBase(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int power = resultSet.getInt("power");
                int startAngle = resultSet.getInt("angle_start");
                int endAngle = resultSet.getInt("angle_end");
                String weapon = name +" | Power: "+ power +" | Angle: "+startAngle+"-"+endAngle;
                weaponList.add(weapon);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        dataCon.close();
        return weaponList;
    }
    /**
     * Get map list from database
     *
     * @return map, contains map information
     * @author Weizhi
     * @version 1.0
     */
    public static Vector<String> getMapList(){
        DataBaseConnection dataCon = new DataBaseConnection();
        // 最终结果WeaponInfo
        Vector<String> mapList = new Vector<>();
        try {
            String sql = "select * from map";
            ResultSet resultSet = dataCon.getFromDataBase(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                mapList.add(name);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        dataCon.close();
        return mapList;
    }
    /**
     * Set user weapon to database
     *
     * @param userName the name of user
     * @param weapon the weapon of user
     * @return Boolean, true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean setUserWeapon(String userName,String weapon) {
        boolean isSuccess = false;
        DataBaseConnection conn = new DataBaseConnection();
        String sql = "update user set user.weapon_id = (select weapon.id from weapon where weapon.name = '"+weapon+"') where user.name = '"+userName+"';";
        // if exist then return true
        isSuccess = conn.updateDataBase(sql);
        // close the database connection
        conn.close();
        return isSuccess;
    }
    /**
     * set start flag to true to database
     *
     * @param groupName set target game match
     * @return Boolean, true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean setGameStart(String groupName) {
        boolean isSuccess = false;
        DataBaseConnection conn = new DataBaseConnection();
        String sql = "update lobby set lobby.isStart = 1 where lobby.name = '"+groupName+"';";
        // if exist then return true
        isSuccess = conn.updateDataBase(sql);
        // close the database connection
        conn.close();
        return isSuccess;
    }
    /**
     * set end flag to true to database
     *
     * @param groupName set target game match
     * @return Boolean, true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean setGameEnd(String groupName) {
        boolean isSuccess = false;
        DataBaseConnection conn = new DataBaseConnection();
        String sql = "update lobby set lobby.isStart = 0 where lobby.name = '"+groupName+"';";
        // if exist then return true
        isSuccess = conn.updateDataBase(sql);
        // close the database connection
        conn.close();
        return isSuccess;
    }
    /**
     * Get group member list from database
     *
     *
     * @param groupName the target game match
     * @return map, contains map information
     * @author Weizhi
     * @version 1.0
     */
    public static Vector<String> getGroupMember(String groupName){
        DataBaseConnection dataCon = new DataBaseConnection();
        // 最终结果WeaponInfo
        Vector<String> mapList = new Vector<>();
        try {
            String sql = "select user.name, weapon.name as weapon_name, lobby_user.is_ready from user,weapon,lobby_user,lobby where user.id = lobby_user.player_id and user.weapon_id = weapon.id and lobby.id = lobby_user.lobby_id and lobby.name = '"+groupName+"'";
            ResultSet resultSet = dataCon.getFromDataBase(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String weaponName = resultSet.getString("weapon_name");
                boolean isReady = resultSet.getBoolean("is_ready");
                String condition;
                if (isReady){
                    condition = "Ready";
                }else {
                    condition = "Unready";
                }
                mapList.add(name+ " | "+weaponName+" | "+condition);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        dataCon.close();
        return mapList;
    }
    /**
     * Get match history list from database
     *
     * @param userName the target user
     * @return map, contains map information
     * @author Weizhi
     * @version 1.0
     */
    public static Vector<String> getMatchHistory(String userName) {
        DataBaseConnection dataCon = new DataBaseConnection();
        // 最终结果WeaponInfo
        Vector<String> matchList = new Vector<>();
        try {
            String sql = "select game_match.end_time, user.name , map.name as map_name from game_match, map , user where game_match.winner_id = user.id and game_match.map_id = map.id and game_match.id in (select match_user.match_id from match_user, user where user.id = match_user.user_id and user.name = '"+userName+"');";
            ResultSet resultSet = dataCon.getFromDataBase(sql);
            while (resultSet.next()) {
                String time = resultSet.getString("end_time");
                String winner = resultSet.getString("name");
                String map = resultSet.getString("map_name");
                matchList.add("End Time: "+time+ " | Winner: "+winner+" | Map: "+map);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        dataCon.close();
        return matchList;

    }
    /**
     * Set game record to database
     *
     * @param gameInfo the target game information
     * @param winner the target winner
     * @return Boolean, true : successful , false : unsuccessful
     * @author Weizhi
     * @version 1.0
     */
    public static Boolean recordGame(String winner,GameInfo gameInfo){
        System.out.println("recordGame");
        DataBaseConnection dataCon = new DataBaseConnection();
        String sql2 = "insert into game_match(id,end_time,winner_id,map_id) values("+matchCounter+",now(),(select user.id from user where user.name = '"+winner+"'),(select map.id from map where map.name = '"+gameInfo.playground+"'));";

        if(dataCon.updateDataBase(sql2)){
            for (String key : gameInfo.gameEntity.keySet()){
                String sql = "insert into match_user(match_id,user_id) values ("+matchCounter+",(select user.id from user where user.name = '"+key+"'));";
                dataCon.updateDataBase(sql);
            }
            matchCounter++;
            dataCon.close();
            return true;
        }
        dataCon.close();
        return false;
    }
}
