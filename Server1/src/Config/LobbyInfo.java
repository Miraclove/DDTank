package Config;

import java.io.Serializable;
import java.util.Vector;
/**
 * Class {@code LobbyInfo} Contains one lobby info
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class LobbyInfo implements Serializable {
    /**
     * Constructor for LobbyInfo
     *
     * <p>
     * @param gameInfos set groups info in the lobby
     * @author Weizhi
     * @version 1.0
     */
    public LobbyInfo(Vector<String> gameInfos){
        this.gameInfos = gameInfos;
    }
    /** the group info in the lobby {@value} */
    public Vector<String> gameInfos;
}
