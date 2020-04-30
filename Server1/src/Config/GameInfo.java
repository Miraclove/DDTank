package Config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * Class {@code GameInfo} Contains one game info
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class GameInfo implements Serializable{
    /** entities in the game, and their information {@value} */
    public Map<String,EntityInfo> gameEntity;

    /**
     * Constructor for GameInfo
     *
     * <p>Init Data
     *
     * @author Weizhi
     * @version 1.0
     */
    public GameInfo(){
        gameEntity = new HashMap<>();
    }
    /** the name of the group in the game {@value} */
    public String groupName;
    /** the name of the map in the game {@value} */
    public String playground;
    /** the cur login player in the Client {@value} */
    public String curUser;
}