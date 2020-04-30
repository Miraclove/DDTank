package Rendering.entity;

import Config.EntityInfo;
import Config.WeaponInfo;
import Rendering.assets.Assets;
import Rendering.engine.Window;
import Rendering.render.Animation;
import Rendering.render.Camera;
import Rendering.render.Shader;
import Rendering.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;
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
    /** The owner of the weapon */
    public int player;
    /** The visible setting = {@value} */
    public boolean isVisible = false;
    /**
     * Constructor for Weapon
     *
     * <p>Init data
     * @param transform the transform of weapon
     * @param info the weapon information of weapon
     * @author Weizhi
     * @version 1.0
     */
    public Weapon(Transform transform, WeaponInfo weaponInfo){
        super(ANIM_SIZE, transform,weaponInfo);
        setAnimation(ANIM_FLYING, new Animation(4, 2, "weapon/"+weaponInfo.getUserName()+"/flying",weaponInfo,true));
        setAnimation(ANIM_BOOM, new Animation(4, 2, "weapon/"+weaponInfo.getUserName()+"/boom",weaponInfo,true));
    }
    /**
     * Update for weapon
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
     * Render Graphics for weapon
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
