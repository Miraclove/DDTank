package Rendering.entity;

import Config.EntityInfo;
import Rendering.assets.Assets;
import Rendering.engine.Window;
import Rendering.render.Animation;
import Rendering.render.Camera;
import Rendering.render.Shader;
import Rendering.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;
/**
 * Class {@code AIPlayer} the AI player entity in game
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class AIPlayer extends Player{
    //    private Texture texture;
    /**
     * Constructor for AIPlayer
     *
     * <p>
     * @param info the player information
     * @param transform the player position and scale
     * @author Weizhi
     * @version 1.0
     */
    public AIPlayer(Transform transform, EntityInfo info){
        super(transform,info);
        setAnimation(ANIM_IDLE, new Animation(4, 2, "player/idle",info,false));
        setAnimation(ANIM_WALK, new Animation(4, 2, "player/walking",info,false));

    }
}