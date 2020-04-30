package Calculate;


import Config.EntityInfo;
/**
 * Class {@code Player} Player in the game
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Player extends Entity{
    //    private Texture texture;
    /** The animation state of player is idle = {@value} */
    public static final int ANIM_IDLE = 0;
    /** The animation state of player is walk = {@value} */
    public static final int ANIM_WALK = 1;
    /** The animation state of player is size = {@value} */
    public static final int ANIM_SIZE = 2;
    /**
     * Constructor for Player
     *
     * <p>
     * @param info the player information
     * @param transform the player position and scale
     * @author Weizhi
     * @version 1.0
     */
    public Player(Transform transform, EntityInfo info){
        super(ANIM_SIZE, transform,info);
        setAnimation(ANIM_IDLE, new Animation(4, 2, "player/idle"));
        setAnimation(ANIM_WALK, new Animation(4, 2, "player/walking"));

    }
}
