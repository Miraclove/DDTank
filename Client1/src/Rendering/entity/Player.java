package Rendering.entity;

import Config.EntityInfo;
import Network.ChatThread;
import Network.InteractWithServer;
import Rendering.assets.Assets;
import Rendering.engine.Window;
import Rendering.render.*;
import Rendering.test.Main;
import Rendering.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
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
        setAnimation(ANIM_IDLE, new Animation(4, 2, "player/idle",info,false));
        setAnimation(ANIM_WALK, new Animation(4, 2, "player/walking",info,false));

    }
    /**
     * Update for player
     *
     * @param delta loop time of game
     * @param world the world playground of the game
     * @param window the window of the game
     * @param camera the camera of game
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void update(float delta, Window window, Camera camera, World world){

    }
    /**
     * Render Graphics for player
     *
     * @param shader shader of the game
     * @param world the world playground of the game
     * @param camera the camera of game
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void render(Shader shader, Camera camera, World world) {
        Matrix4f target = camera.getProjection();
        target.mul(world.getWorldMatrix());

        shader.bind();
        shader.setUniform("sampler", 0);
        shader.setUniform("projection", transform.getModifiedProjection(
                target,
                new Vector3f(transform.pos.x,transform.pos.y+transform.scale.x,transform.pos.z),
                new Vector3f(transform.scale.x,transform.scale.y*2f,transform.scale.z)));
        animations[use_animation].bind(0);
        Assets.getModel().render();
    }

}
