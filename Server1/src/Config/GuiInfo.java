package Config;
/**
 * Class {@code GuiInfo} Contains one GUI info
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class GuiInfo {
    private String time;
    /**
     * Get the wind
     *
     *
     * @return String, wind force, from -5 to 5
     * @author Weizhi
     * @version 1.0
     */
    public String getWind() {
        return wind;
    }

    /**
     * Set the wind force
     *
     * @param wind the wind force from -5 to 5
     * @author Weizhi
     * @version 1.0
     */
    public void setWind(String wind) {
        this.wind = wind;
    }

    private String wind;

    /**
     * Get the remain time
     *
     *
     * @return String,the maximum is turn time
     * @author Weizhi
     * @version 1.0
     */
    public String getTime() {
        return time;
    }

    /**
     * Set the remain time
     *
     * @param time the remain time maximum is the turn time
     * @author Weizhi
     * @version 1.0
     */
    public void setTime(String time) {
        this.time = time;
    }
    /**
     * Get the angle
     *
     *
     * @return String, the angle from 0 to 180
     * @author Weizhi
     * @version 1.0
     */
    public String getAngle() {
        return angle;
    }

    /**
     * Set the angle of GUI
     *
     * @param angle the angle of GUI from 0 to 180
     * @author Weizhi
     * @version 1.0
     */
    public void setAngle(String angle) {
        this.angle = angle;
    }
    /**
     * Get the force
     *
     *
     * @return String, the force from 0 to 1,
     * @author Weizhi
     * @version 1.0
     */
    public String getPercent() {
        return percent;
    }
    /**
     * Set the force of weapon in GUI
     *
     * @param percent the range is from 0 to 1, as float
     * @author Weizhi
     * @version 1.0
     */
    public void setPercent(String percent) {
        this.percent = percent;
    }

    private String angle;
    private String percent;
    /**
     * Constructor for GUIInfo
     *
     * <p>Init Data
     *
     * @param time the remain time of GUI
     * @param angle the current angle of GUI
     * @param percent the current force of weapon in the GUI
     * @param wind the random wind of the GUI
     * @author Weizhi
     * @version 1.0
     */
    public GuiInfo(String angle,String percent){
        this.angle = angle;
        this.percent = percent;
    }
}
