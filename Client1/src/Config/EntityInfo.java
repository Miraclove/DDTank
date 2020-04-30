package Config;

import java.io.Serializable;
/**
 * Class {@code EntityInfo} Contains one entity info
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class EntityInfo implements Serializable {

    private String userName;

    /**
     * Constructor for EntityInfo
     *
     * <p>Init Data
     *
     * @param userName the user name of the player
     * @author Weizhi
     * @version 1.0
     */
    public EntityInfo(String userName) {
        this.userName = userName;
    }

    /**
     * Get the position of user
     *
     *
     * @return Position, user position, contains x potion as well as y position
     * @author Weizhi
     * @version 1.0
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Set the position of user
     *
     * @param position the position of the entity in the map
     * @author Weizhi
     * @version 1.0
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Get the health of user
     *
     *
     * @return int, user health, the maximum is 100
     * @author Weizhi
     * @version 1.0
     */
    public int getHealth() {
        return health;
    }
    /**
     * Set the position of user
     *
     * @param x the x position of user
     * @param y the y position of user
     * @author Weizhi
     * @version 1.0
     */
    public void setPosition(float x,float y){
        position.x = x;
        position.y = y;
    }
    /**
     * Set the health of user
     *
     * @param health the health of the entity, the maximum is 100
     * @author Weizhi
     * @version 1.0
     */
    public void setHealth(int health) {
        this.health = health;
    }

    private Position position;
    /**
     * Get the user name of user
     *
     *
     * @return String, user name
     * @author Weizhi
     * @version 1.0
     */
    public String getUserName() {
        return userName;
    }

    private int health = 100;
    /**
     * Get the the ready condition of user
     *
     *
     * @return Boolean, true is Ready , false is unReady
     * @author Weizhi
     * @version 1.0
     */
    public boolean isReady() {
        return isReady;
    }
    /**
     * Set the ready condition of user
     *
     * @param ready the ready condition, true is ready, false is unReady
     * @author Weizhi
     * @version 1.0
     */
    public void setReady(boolean ready) {
        isReady = ready;
    }

    private boolean isReady = false;
    private String weapon;
    /**
     * Get the the weapon of user
     *
     *
     * @return Boolean, true is Ready , false is unReady
     * @author Weizhi
     * @version 1.0
     */
    public String getWeapon() {
        return weapon;
    }
    /**
     * Set the the weapon of user
     *
     *
     * @param weapon the ready condition, true is ready, false is unReady
     * @author Weizhi
     * @version 1.0
     */
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
}