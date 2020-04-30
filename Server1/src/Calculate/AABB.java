package Calculate;

import org.joml.Vector2f;
/**
 * Class {@code AABB} Basic calculate component
 *
 * <p> store the position information and calculate the distance and interact state of two entity
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class AABB {
    /**
     * Constructor for AABB
     *
     * <p>Init basic center and scale of AABB
     *
     * @param center the center of AABB box
     * @param half_extent the scale of AABB box
     * @author Weizhi
     * @version 1.0
     */
    public AABB(Vector2f center,Vector2f half_extent){
        this.center = center;
        this.half_extent = half_extent;
    }
    /**
     * Get the center of AABB
     *
     *
     * @return Vector2f, the center of AABB
     * @author Weizhi
     * @version 1.0
     */
    public Vector2f getCenter() {
        return center;
    }

    /**
     * Set the center of AABB
     *
     * @param center the center of AABB
     * @author Weizhi
     * @version 1.0
     */
    public void setCenter(Vector2f center) {
        this.center = center;
    }

    private Vector2f center,half_extent;

    /**
     * get the relation between two AABB entities
     *
     * @param box2 the target box to detect
     * @return {@link Collision} the spatial relations of two boxes
     * @author Weizhi
     * @version 1.0
     */
    public Collision getCollision(AABB box2){
        Vector2f distance = box2.center.sub(center,new Vector2f());
        distance.x = Math.abs(distance.x);
        distance.y = Math.abs(distance.y);
        distance.sub(half_extent.add(box2.half_extent,new Vector2f()));
        return new Collision(distance,distance.x<0 && distance.y<0);
    }
    /**
     * get the relation between a AABB entity and a point
     *
     * @param point the target point to detect
     * @return {@link Collision} the spatial relations of a AABB entity and a point
     * @author Weizhi
     * @version 1.0
     */
    public Collision getCollision(Vector2f point) {
        Vector2f distance = point.sub(center);
        distance.x = Math.abs(distance.x);
        distance.y = Math.abs(distance.y);

        distance.sub(half_extent);

        return new Collision(distance, distance.x < 0 && distance.y < 0);
    }

    /**
     * Set boxes to right position
     *
     * @param box2 the target box to detect
     * @param data the spatial relation
     * @author Weizhi
     * @version 1.0
     */
    public void correctPosition(AABB box2,Collision data){
        Vector2f correction = box2.center.sub(center,new Vector2f());
        if (data.distance.x > data.distance.y){
            if(correction.x>0){
                center.add(data.distance.x,0);
            }else {
                center.add(-data.distance.x,0);
            }

        }else {
            if (correction.y>0){
                center.add(0,data.distance.y);
            }else {
                center.add(0,-data.distance.y);
            }

        }
    }
    /**
     * Get the scale of AABB
     *
     *
     * @return Vector2f, the scale of AABB
     * @author Weizhi
     * @version 1.0
     */
    public Vector2f getHalfExtent() {
        return half_extent;
    }
}
