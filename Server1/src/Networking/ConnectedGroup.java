package Networking;

import Calculate.World;
import Config.EntityInfo;
import Config.InstantInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Class {@code ConnectedGroup} Contains groups information
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class ConnectedGroup {
    /** the instant information for group */
    public static HashMap<String,InstantInfo> groupInstantInfo;
    public static HashMap<String, World> worldHashMap;
    static {
        groupInstantInfo = new HashMap<>();
        worldHashMap = new HashMap<>();

    }
}
