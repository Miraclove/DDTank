package Calculate;

import Config.EntityInfo;
import Config.WeaponInfo;
/**
 * Class {@code Player} Weapon in the game
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Weapon extends Entity{
    /** The animation state of weapon is flying = {@value} */
    public static final int ANIM_FLYING = 0;
    /** The animation state of weapon is boom = {@value} */
    public static final int ANIM_BOOM = 1;
    /** The animation state of weapon is size = {@value} */
    public static final int ANIM_SIZE = 2;
    /** The owner of the weapon */
    public String owner;
    /**
     * Constructor for Weapon
     *
     * <p>Init data
     * @param transform the transform of weapon
     * @param info the weapon information of weapon
     * @author Weizhi
     * @version 1.0
     */
    public Weapon(Transform transform, WeaponInfo info){
        super(ANIM_SIZE, transform,info);
        this.owner = info.getOwnerName();
        setAnimation(ANIM_FLYING, new Animation(4, 2, "walk"));
        setAnimation(ANIM_BOOM, new Animation(4, 2, "walk"));

    }
}