package Config;
/**
 * Class {@code WeaponInfo} Contains one weapon information
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class WeaponInfo extends EntityInfo{
    /**
     * Get the owner name of weapon
     *
     *
     * @return String,the owner name of weapon
     * @author Weizhi
     * @version 1.0
     */
    public String getOwnerName() {
        return ownerName;
    }
    /**
     * Set the owner name of weapon
     *
     *
     * @param ownerName the owner name of weapon
     * @author Weizhi
     * @version 1.0
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    private String ownerName;
    /**
     * Get the power of weapon
     *
     *
     * @return int, the power of weapon, from 0 to 100
     * @author Weizhi
     * @version 1.0
     */
    public int getPower() {
        return power;
    }
    /**
     * Set the power of weapon
     *
     * @param power the power of weapon, from 0 to 100
     * @author Weizhi
     * @version 1.0
     */
    public void setPower(int power) {
        this.power = power;
    }

    private int power;
    /**
     * Constructor for EntityInfo
     *
     * <p>
     *
     * @param userName the user name of the player
     * @param ownerName the owner name of the weapon
     * @param power the power of the weapon,  from 0 to 100
     * @author Weizhi
     * @version 1.0
     */
    public WeaponInfo(String userName,String ownerName,int power){
        super(userName);
        this.ownerName = ownerName;
        this.power = power;
    }
    private int startAngle;
    private int endAngle;
    /**
     * Get the start angle of weapon
     *
     *
     * @return int, the start angle of weapon, from 0 to 90
     * @author Weizhi
     * @version 1.0
     */
    public int getStartAngle() {
        return startAngle;
    }
    /**
     * Set the start angle of weapon
     *
     *
     * @param startAngle int, the start angle of weapon, from 0 to 90
     * @author Weizhi
     * @version 1.0
     */
    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }
    /**
     * Get the end angle of weapon
     *
     *
     * @return  int, the end angle of weapon, from 0 to 90
     * @author Weizhi
     * @version 1.0
     */
    public int getEndAngle() {
        return endAngle;
    }
    /**
     * Set the end angle of weapon
     *
     *
     * @param endAngle  the end angle of weapon, from 0 to 90
     * @author Weizhi
     * @version 1.0
     */
    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }
}
