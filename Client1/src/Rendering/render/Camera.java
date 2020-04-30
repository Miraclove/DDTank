package Rendering.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
/**
 * Class {@code Camera} the camera of game
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Camera {
    private  Vector3f position;
    private  Matrix4f projection;
    /**
     * Constructor for Camera
     *
     * <p>Init Data
     *
     * @param width the width of camera
     * @param height the height of camera
     * @author Weizhi
     * @version 1.0
     */
    public Camera(int width,int height){
        position = new Vector3f(0,0,0);
        projection = new Matrix4f().setOrtho2D(-width/2,width/2,-height/2,height/2);
    }
    /**
     * Set the position of camera
     *
     * @param position the position of the entity in the map
     * @author Weizhi
     * @version 1.0
     */
    public void setPosition(Vector3f position){
        this.position = position;
    }
    /**
     * Add the position of camera
     *
     * @param position the position of the entity in the map
     * @author Weizhi
     * @version 1.0
     */
    public void addPosition(Vector3f position){
        this.position.add(position);
    }
    /**
     * Get the position of camera
     *
     *
     * @return Vector3f,the position of camera
     * @author Weizhi
     * @version 1.0
     */
    public Vector3f getPosition(){
        return position;
    }
    /**
     * Get the projection of camera
     *
     *
     * @return Matrix4f, the projection of camera
     * @author Weizhi
     * @version 1.0
     */
    public Matrix4f getProjection() {
        return projection.translate(position, new Matrix4f());
    }
    /**
     * Get the untransformed projection of camera
     *
     *
     * @return Matrix4f, the untransformed projection of camera
     * @author Weizhi
     * @version 1.0
     */
    public Matrix4f getUntransformedProjection() {
        return projection;
    }
    /**
     * Set the projection of camera
     *
     * @param width the width of projection
     * @param height the height of projection
     * @author Weizhi
     * @version 1.0
     */
    public void setProjection(int width, int height) {
        projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2);
    }
}
