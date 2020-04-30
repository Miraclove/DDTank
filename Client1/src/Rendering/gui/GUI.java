package Rendering.gui;

import Rendering.engine.Input;
import Rendering.engine.Window;
import Rendering.render.Camera;
import org.joml.Vector2f;


import java.awt.*;
/**
 * Class {@code GUI} the in game GUI
 *
 * <p> load all gui component in the screen
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class GUI {
    /** the camera of GUI */
    public Camera camera;
    /** the button of GUI */
    public Button button;
    /** the forcePanel of GUI */
    public ProgressBar forcePanel;
    /** the windInstructor of GUI */
    public WindInstructor windInstructor;
    /** the timer of GUI */
    public Panel timer;
    /** the angleInstructor of GUI */
    public AngleInstructor angleInstructor;
    /** the gameOver display of GUI */
    public Panel gameOver;
    /** the miniMap of GUI */
    public Panel miniMap;
    /**
     * Get the condition of button
     *
     *
     * @return int, button condition , 1 is pressed , 0 is release
     * @author Weizhi
     * @version 1.0
     */
    public int getButtonState(){
        return button.getSelectedState();
    }

    /**
     * Set remain time of GUI
     *
     *
     * @return int, button condition , 1 is pressed , 0 is release
     * @author Weizhi
     * @version 1.0
     */
    public void setRemainTime(String time){
        timer.setText(time,new Font("Comic Sans MS", Font.BOLD, 30));
    }

    /**
     * Constructor for EntityInfo
     *
     * <p>Init Data
     *
     * @param window the display window
     * @author Weizhi
     * @version 1.0
     */
    public GUI(Window window) {
        camera = new Camera(window.getWidth(), window.getHeight());
        miniMap = new Panel(new Vector2f(225f,-150f),new Vector2f(80,80));
        miniMap.setVisible(true);
        button = new Button(new Vector2f(225f,-150f),new Vector2f(50,20));
        button.setVisible(true);
        forcePanel = new ProgressBar(new Vector2f(-150f,-150f),new Vector2f(150,20));
        forcePanel.setVisible(true);
        windInstructor = new WindInstructor(new Vector2f(0f,150f),new Vector2f(50,25));
        windInstructor.setVisible(true);
        timer = new Panel(new Vector2f(125f,-150f),new Vector2f(50f,100f));
        timer.setVisible(true);
        timer.setText("24S",new Font("Comic Sans MS", Font.BOLD, 30));
        angleInstructor = new AngleInstructor(new Vector2f(0f,50f),new Vector2f(50,50));
        angleInstructor.setVisible(true);
        gameOver = new Panel(new Vector2f(0f,70f),new Vector2f(150,30));
        gameOver.setText("Game Over!",new Font("Comic Sans MS", Font.BOLD, 30));
        gameOver.setVisible(false);
//        forcePanel.setColor(new Vector4f(29,55,150,0));

  }
    /**
     * resize the scale of camera
     *
     * @param window the display window
     * @author Weizhi
     * @version 1.0
     */
    public void resizeCamera(Window window) {
        camera.setProjection(window.getWidth(), window.getHeight());
    }
    /**
     * update information for the GUI
     *
     * @param input the input interface controller
     * @author Weizhi
     * @version 1.0
     */
    public void update(Input input) {
        forcePanel.update(input);
        button.update(input);
        windInstructor.update(input);
        timer.update(input);
        angleInstructor.update(input);
        gameOver.update(input);
        miniMap.update(input);
    }
    /**
     * Render graphics for the GUI
     *
     * @author Weizhi
     * @version 1.0
     */
    public void render() {
        timer.render(camera);
        angleInstructor.render(camera);
        gameOver.render(camera);
        button.render(camera);
        forcePanel.render(camera);
        windInstructor.render(camera);
        miniMap.render(camera);
    }
    /**
     * Get the percentage of force bar
     *
     * @return float,from 0 to 1
     * @author Weizhi
     * @version 1.0
     */
    public float getPercent(){
        return forcePanel.getPercent();
    }
    /**
     * Get the angle of angleInstructor
     *
     * @return float,from 0 to 180
     * @author Weizhi
     * @version 1.0
     */
    public float getAngle(){
        return angleInstructor.getAngle();
    }
    /**
     * Set the percentage of force bar
     *
     * @param percent the percentage of force bar, from 0 to 1
     * @author Weizhi
     * @version 1.0
     */
    public void setPercent(float percent){
        forcePanel.setPercent(percent);
    }
    /**
     * Set the angle of angleInstructor
     *
     * @param angle angle of angleInstructor, from 0 to 180
     * @author Weizhi
     * @version 1.0
     */
    public void setAngle(float angle){
        angleInstructor.setAngle(angle);
    }
    /**
     * Set the wind of windInstructor
     *
     * @param windForce angle of angleInstructor, from -5 to 5
     * @author Weizhi
     * @version 1.0
     */
    public void setWindForce(float windForce){
        windInstructor.setWindForce(windForce);
    }
    /**
     * Clear the information of GUI
     *
     * @author Weizhi
     * @version 1.0
     */
    public void clear(){
        forcePanel.setPercent(0);
        angleInstructor.setAngle(30);
    }
}

