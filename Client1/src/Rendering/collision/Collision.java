package Rendering.collision;

import org.joml.Vector2f;
/**
 * Class {@code Collision} spatial relations of boxes
 *
 * <p>
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Collision {
    /** the distance between boxes */
    public Vector2f distance;
    /** the intersecting condition between boxes */
    public boolean isIntersecting;
    /**
     * Constructor for Collision
     *
     * <p>Contains spatial relations of boxes
     *
     * @param distance the distance between two boxes
     * @param isIntersecting the intersecting condition between boxes
     * @author Weizhi
     * @version 1.0
     */
    public Collision(Vector2f distance,boolean isIntersecting){
        this.distance = distance;
        this.isIntersecting = isIntersecting;
    }
}
