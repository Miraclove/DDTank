package Rendering.gui;

import Rendering.engine.Input;
import Rendering.render.Camera;
import org.joml.Vector2f;
/**
 * Class {@code ProgressBar} the progress bar for force bar
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class ProgressBar implements Component{
    private Panel forcePanel;
    private Panel backGround;
    private Vector2f position;
    private Vector2f scale;
    private Boolean isVisible = false;
    private Boolean turn = true;

    /**
     * Constructor for ProgressBar
     *
     * <p>Init Data
     * @param position the position of Panel
     * @param scale the scale of Panel
     * @author Weizhi
     * @version 1.0
     */
    public ProgressBar(Vector2f position, Vector2f scale){
        this.position = position;
        this.scale = scale;
        forcePanel = new Panel(new Vector2f(position.x-scale.x+scale.x/24f,position.y),new Vector2f(0f,scale.y*0.8f));
        forcePanel.setPicture("forcebar.png");
        backGround = new Panel(position,scale);
        backGround.setPicture("background.png");
    }
    /**
     * Set the percentage of progressbar
     *
     * @param percent the percentage of bar, from 0 to 1
     * @author Weizhi
     * @version 1.0
     */
    public void setPercent(float percent){
        if (percent>1){
            forcePanel.scale.x = 2f*scale.x/1.04f;
        }else if (percent<0){
            forcePanel.scale.x = 0;
        }else {
            forcePanel.scale.x = percent * 2f*scale.x/1.04f;
        }
    }
    /**
     * Get the percentage of progressbar
     *
     *
     * @return float, the percentage of bar, from 0 to 1
     * @author Weizhi
     * @version 1.0
     */
    public float getPercent(){
        return forcePanel.scale.x/(2f*scale.x/1.04f);
    }

    /**
     * Render graphics for the ProgressBar
     *
     * @param camera the camera of the ProgressBar
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void render(Camera camera) {
        if (isVisible){
            backGround.render(camera);
            forcePanel.render(camera);
        }

    }
    /**
     * update information for the ProgressBar
     *
     * @param input the input interface controller
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void update(Input input) {
        if (isVisible){
//            if (input.isKeyDown(GLFW_KEY_SPACE)){
//                if (turn){
//                    forcePanel.scale.x += 2f;
//                    if (forcePanel.scale.x>2f*scale.x/1.04f){
//                        turn = false;
//                    }
//                }else {
//                    forcePanel.scale.x -= 2f;
//                    if (forcePanel.scale.x<0){
//                        turn = true;
//                    }
//                }
//            }else{
//
//            }
//            forcePanel.update(input);
//            backGround.update(input);
        }

    }
    /**
     * Get the visible state of progressbar
     *
     *
     * @return Boolean, the visible state of progressbar
     * @author Weizhi
     * @version 1.0
     */
    public Boolean getVisible() {
        return isVisible;
    }
    /**
     * Set the visible state of progressbar
     *
     * @param visible the visible state of progressbar
     * @author Weizhi
     * @version 1.0
     */
    public void setVisible(Boolean visible) {
        isVisible = visible;
        forcePanel.setVisible(visible);
        backGround.setVisible(visible);
    }

}
