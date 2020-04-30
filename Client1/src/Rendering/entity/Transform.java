package Rendering.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
/**
 * Class {@code Transform} Contains the position and scale info
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Transform {

    /** the position information */
    public Vector3f pos;

    /** the scale information */
    public Vector3f scale;
    /**
     * Constructor for Transform
     *
     *<p> Init data
     *
     * @author Weizhi
     * @version 1.0
     */
    public Transform(){
        pos = new Vector3f();
        scale = new Vector3f(1,1,1);
    }
    /**
     * Get the projection
     *
     * @param target the point of the projection
     * @return the target projection
     * @author Weizhi
     * @version 1.0
     */
    public Matrix4f getProjection(Matrix4f target){
        target.translate(pos);
        target.scale(scale);
        return target;
    }
    /**
     * Get the scaled projection
     *
     * @param target the point of the projection
     * @param scale the scale of the projection
     * @return the target projection
     * @author Weizhi
     * @version 1.0
     */
    public Matrix4f getScaledProjection(Matrix4f target,Vector3f scale){
        target.translate(pos);
        target.scale(scale);
        return target;
    }
    /**
     * Get modified projection
     *
     * @param target the point of the projection
     * @param pos the position of the projection
     * @param scale  the scale of the projection
     * @return the target projection
     * @author Weizhi
     * @version 1.0
     */
    public Matrix4f getModifiedProjection(Matrix4f target,Vector3f pos,Vector3f scale){
        target.translate(pos);
        target.scale(scale);
        return target;
    }

}
