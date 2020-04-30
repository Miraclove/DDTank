package Rendering.gui;

import Rendering.engine.Input;
import Rendering.render.Camera;
import org.joml.Vector2f;

import java.awt.*;
/**
 * Class {@code AngleInstructor} angle instructor for in game GUI
 *
 *
 * @author Weizhi
 * @since 15/01/2020
 */
public class AngleInstructor implements Component {

    private Panel angleInstructor;
    /**
     * Get visible condition
     *
     * @return Boolean,true : visible, false : invisible
     * @author Weizhi
     * @version 1.0
     */
    public Boolean getVisible() {
        return isVisible;
    }
    /**
     * Set the visible condition
     *
     * @param visible true : visible, false : invisible
     * @author Weizhi
     * @version 1.0
     */
    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    private Boolean isVisible = false;
    /**
     * Get angle condition
     *
     * @return float,from 0 to 180
     * @author Weizhi
     * @version 1.0
     */
    public float getAngle() {
        return angle;
    }
    /**
     * Set the angle condition
     *
     * @param angle angle of weapon launch, from 0 to 180
     * @author Weizhi
     * @version 1.0
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    private float angle = 0 ;
    /**
     * Constructor for AngleInstructor
     *
     * <p>Init Data
     *
     * @param position the position of AngleInstructor
     * @param scale the scale of AngleInstructor
     * @author Weizhi
     * @version 1.0
     */
    public AngleInstructor(Vector2f position, Vector2f scale){
        angleInstructor = new Panel(position,scale);
        angleInstructor.setVisible(true);
        angleInstructor.setText(angle+"",new Font("Comic Sans MS", Font.BOLD, 20));
    }
    /**
     * Render graphics for the AngleInstructor
     *
     * @param camera the camera of the AngleInstructor
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void render(Camera camera) {
        if (isVisible){
            angleInstructor.render(camera);
        }
    }
    /**
     * update information for the AngleInstructor
     *
     * @param input the input interface controller
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void update(Input input) {
        if (isVisible){
//            if (input.isKeyDown(GLFW_KEY_UP)&&angle<180){
//                angle += 0.5;
//            }else if (input.isKeyDown(GLFW_KEY_DOWN)&&angle>0){
//                angle -=0.5;
//            }
        }
        angleInstructor.setText(String.format("%.1f", angle),new Font("Comic Sans MS", Font.BOLD, 30));
        angleInstructor.update(input);

    }
}
