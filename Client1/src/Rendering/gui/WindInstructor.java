package Rendering.gui;

import Rendering.engine.Input;
import Rendering.render.Camera;
import org.joml.Vector2f;

import java.awt.*;
/**
 * Class {@code WindInstructor} WindInstructor for in Game GUI
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class WindInstructor implements Component{

    private Panel windImage;
    private Panel numberInstructor;
    private boolean isVisible = false;

    private float windForce;

    /**
     * Constructor for WindInstructor
     *
     * <p>Init Data
     *
     * @param position the position of WindInstructor
     * @param scale the scale of WindInstructor
     * @author Weizhi
     * @version 1.0
     */
    public WindInstructor(Vector2f position, Vector2f scale){
        windImage = new Panel(new Vector2f(position.x-scale.x*0.5f,position.y),new Vector2f(scale.x*0.5f,scale.y));
        windImage.setPicture("windleft.png");
        numberInstructor = new Panel(new Vector2f(position.x+scale.x*0.5f,position.y),new Vector2f(scale.x*0.5f,scale.y));
        numberInstructor.setText("2.0",new Font("Comic Sans MS", Font.BOLD, 10));
    }
    /**
     * Set the wind force
     *
     * @param windForce the wind force, from -5 to 5
     * @author Weizhi
     * @version 1.0
     */
    public void setWindForce(float windForce){
        if (windForce>0){
            windImage.setPicture("windright.png");
            numberInstructor.setText(String.format("%.2f", windForce)+"",new Font("Comic Sans MS", Font.BOLD, 10));
        }else {
            windImage.setPicture("windleft.png");
            numberInstructor.setText(String.format("%.2f", -windForce),new Font("Comic Sans MS", Font.BOLD, 10));
        }

    }


    /**
     * Render graphics for the Wind force
     *
     * @param camera the camera of the wind force bar
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void render(Camera camera) {
        if (isVisible){
            windImage.render(camera);
            numberInstructor.render(camera);
        }

    }
    /**
     * update information for the wind force bar
     *
     * @param input the input interface controller
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void update(Input input) {
        if (isVisible){

        }
    }
    /**
     * Get the visible state of wind instructor
     *
     *
     * @return boolean, the visible state of the panel
     * @author Weizhi
     * @version 1.0
     */
    public boolean isVisible() {
        return isVisible;
    }
    /**
     * Set the visible state
     *
     * @param visible the visible state of the panel
     * @author Weizhi
     * @version 1.0
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
        windImage.setVisible(visible);
        numberInstructor.setVisible(visible);
    }
}
