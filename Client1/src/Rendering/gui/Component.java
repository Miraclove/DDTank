package Rendering.gui;

import Rendering.engine.Input;
import Rendering.render.Camera;
/**
 * Class {@code Component} the Component for in game GUI
 *
 * <p> use HCI principle for extension
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public interface Component {
    /**
     * Render graphics for the Component
     *
     * @param camera the camera of the Component
     * @author Weizhi
     * @version 1.0
     */
    public void render(Camera camera);
    /**
     * update information for the Component
     *
     * @param input the input interface controller
     * @author Weizhi
     * @version 1.0
     */
    public void update(Input input);
}
