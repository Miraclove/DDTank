package Config;

import java.io.Serializable;
import java.util.HashMap;
/**
 * Class {@code InstantInfo} Contains instant communication info
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class InstantInfo implements Serializable {
    /** the list of entities in the game {@value} */
    public HashMap<String,EntityInfo> entityInfoList;
    /** the weapon information in the game {@value} */
    public WeaponInfo weaponInfo;
    /** the wind information in the game {@value} */
    public float wind;
    /** the camera target in the game {@value} */
    public String cameraTarget;
    /** the remain time in the game {@value} */
    public int remainTime;
    /** the game info in the game. Game over is true. Game not end is false {@value} */
    public boolean isGameOver = false;
    /** the end game info in the game{@value} */
    public String endInfo = "";
    /**
     * Constructor for InstantInfo
     *
     * <p>
     * @param cameraTarget set camera target of game
     * @param entityInfoList set entities information in the game
     * @param remainTime set remain time of the game turn
     * @param wind set the wind force of the game
     * @author Weizhi
     * @version 1.0
     */
    public InstantInfo(HashMap<String,EntityInfo> entityInfoList,float wind,String cameraTarget,int remainTime){
        this.entityInfoList = entityInfoList;
        this.wind = wind;
        this.cameraTarget = cameraTarget;
        this.remainTime = remainTime;
    }
}