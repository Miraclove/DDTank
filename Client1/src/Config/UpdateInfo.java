package Config;

import java.io.Serializable;
/**
 * Class {@code UpdateInfo} Contains one game info
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class UpdateInfo implements Serializable {
    /** updated gui information {@value} */
    public GuiInfo guiInfo;
    /** updated entity information {@value} */
    public EntityInfo entityInfo;
    /**
     * Constructor for UpdateInfo
     *
     * <p>
     * @param entityInfo set updated one entity information
     * @param guiInfo set updated GUI information
     * @author Weizhi
     * @version 1.0
     */
    public UpdateInfo(GuiInfo guiInfo,EntityInfo entityInfo){
        this.guiInfo = guiInfo;
        this.entityInfo = entityInfo;
    }
}
