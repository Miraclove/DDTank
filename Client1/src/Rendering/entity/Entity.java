package Rendering.entity;

import Config.EntityInfo;
import Rendering.assets.Assets;
import Rendering.collision.AABB;
import Rendering.collision.Collision;
import Rendering.engine.Window;
import Rendering.gui.Panel;
import Rendering.render.Animation;
import Rendering.render.Camera;
import Rendering.render.Shader;
import Rendering.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;
/**
 * Class {@code Entity} entity in the game
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public abstract class Entity {

    protected AABB bounding_box;
    // private Texture texture;
    protected Animation[] animations;
    protected int use_animation;
    private int entityType;
    /** The character is player = {@value} */
    public static int PLAYER = 1;
    /** The character is AIPlayer = {@value} */
    public static int AIPLAYER = 2;
    /** The character is weapon = {@value} */
    public static int WEAPON = 3;
    /** The position and scale {@link Transform} */
    public Transform transform;
    /**
     * Get the entity info
     *
     * @return {@link EntityInfo} the entity information
     * @author Weizhi
     * @version 1.0
     */
    public EntityInfo getEntityInfo() {
        return entityInfo;
    }
    /**
     * Set the entity info
     *
     * @param entityInfo the entity information
     * @author Weizhi
     * @version 1.0
     */
    public void setEntityInfo(EntityInfo entityInfo) {
        this.entityInfo = entityInfo;
    }

    private EntityInfo entityInfo;
    /**
     * Constructor for EntityInfo
     *
     * <p>Init date for Entity
     *
     * @param max_animations the maximum animations that can use
     * @param transform the position of the entity
     * @param entityInfo the entity info of the entity
     * @author Weizhi
     * @version 1.0
     */
    public Entity(int max_animations, Transform transform,EntityInfo entityInfo) {
        this.animations = new Animation[max_animations];
        this.entityInfo = entityInfo;

        this.transform = transform;
        this.use_animation = 0;
        bounding_box = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(transform.scale.x, transform.scale.y));
    }
    /**
     * Set the animation
     *
     * @param index the slot using
     * @param animation the animation of entity
     * @author Weizhi
     * @version 1.0
     */
    protected void setAnimation(int index, Animation animation) {
        animations[index] = animation;
    }
    /**
     * use the animation
     *
     * @param index the slot using
     * @author Weizhi
     * @version 1.0
     */
    public void useAnimation(int index) {
        this.use_animation = index;
    }
    /**
     * move the entity
     *
     * @param direction the move distance of the entity
     * @author Weizhi
     * @version 1.0
     */
    public void move(Vector2f direction) {
        transform.pos.add(new Vector3f(direction, 0));
        bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
    }
    /**
     * set the position of the entity
     *
     * @param position the position of entity
     * @author Weizhi
     * @version 1.0
     */
    public void setPosition(Vector2f position){
        transform.pos.set(position.x,position.y,0);
        bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
    }
    /**
     * calculate the correct position of entity
     *
     * @param world the word playground
     * @author Weizhi
     * @version 1.0
     */
    public void collideWithTiles(World world) {
        AABB[] boxes = new AABB[25];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boxes[i + j * 5] = world.getTileBoundingBox(
                        (int) (((transform.pos.x / 2) + 0.5f) - (5 / 2)) + i,
                        (int) (((-transform.pos.y / 2) + 0.5f) - (5 / 2)) + j);
            }
        }

        AABB box = null;
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i] != null) {
                if (box == null) box = boxes[i];

                Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

                if (length1.lengthSquared() > length2.lengthSquared()) {
                    box = boxes[i];
                }
            }
        }
        if (box != null) {
            Collision data = bounding_box.getCollision(box);
            if (data.isIntersecting) {
                bounding_box.correctPosition(box, data);
                transform.pos.set(bounding_box.getCenter(), 0);
            }

            for (int i = 0; i < boxes.length; i++) {
                if (boxes[i] != null) {
                    if (box == null) box = boxes[i];

                    Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                    Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

                    if (length1.lengthSquared() > length2.lengthSquared()) {
                        box = boxes[i];
                    }
                }
            }

            data = bounding_box.getCollision(box);
            if (data.isIntersecting) {
                bounding_box.correctPosition(box, data);
                transform.pos.set(bounding_box.getCenter(), 0);
            }
        }
    }
    /**
     * Update for entity
     *
     * @param delta loop time of game
     * @param world the world playground of the game
     * @param window the window of the game
     * @param camera the camera of game
     * @author Weizhi
     * @version 1.0
     */
    public abstract void update(float delta, Window window, Camera camera, World world);
    /**
     * Render Graphics for entity
     *
     * @param shader shader of the game
     * @param world the world playground of the game
     * @param camera the camera of game
     * @author Weizhi
     * @version 1.0
     */
    public abstract void render(Shader shader, Camera camera, World world);
    /**
     * calculate for entity spatial relations
     *
     * @param entity the target calculate entity
     * @author Weizhi
     * @version 1.0
     */
    public void collideWithEntity(Entity entity) {
        Collision collision = bounding_box.getCollision(entity.bounding_box);

        if (collision.isIntersecting) {
            collision.distance.x /= 2;
            collision.distance.y /= 2;

            bounding_box.correctPosition(entity.bounding_box, collision);
            transform.pos.set(bounding_box.getCenter().x, bounding_box.getCenter().y, 0);

            entity.bounding_box.correctPosition(bounding_box, collision);
            entity.transform.pos.set(entity.bounding_box.getCenter().x, entity.bounding_box.getCenter().y, 0);
        }
    }
    /**
     * get the AABB box of entity
     *
     * @return {@link AABB} the AABB of entity
     * @author Weizhi
     * @version 1.0
     */
    public AABB getBounding_box() {
        return bounding_box;
    }
    /**
     * set the AABB box of entity
     *
     * @param bounding_box the AABB of entity
     * @author Weizhi
     * @version 1.0
     */
    public void setBounding_box(AABB bounding_box) {
        this.bounding_box = bounding_box;
    }
    /**
     * get entity type
     *
     * @return {@link Integer} the entity type
     * @author Weizhi
     * @version 1.0
     */
    public int getEntityType() {
        return entityType;
    }
    /**
     * set entity type
     *
     * @param entityType the entity type
     * @author Weizhi
     * @version 1.0
     */
    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }
}
