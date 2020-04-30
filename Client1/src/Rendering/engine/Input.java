package Rendering.engine;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;
/**
 * Class {@code Input} Manage Input of the user
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Input {
    private long window;

    private boolean keys[];

    private static Vector2f mousePos = new Vector2f();
    private static double[] x = new double[1], y = new double[1];
    private static int[] winWidth = new int[1], winHeight = new int[1];

    /**
     * Constructor for Input
     *
     * @param window the id of the LWJGL window
     * @author Weizhi
     * @version 1.0
     */
    public Input(long window){
        this.window = window;
        this.keys = new boolean[GLFW_KEY_LAST];
        for (int i = 0;i<GLFW_KEY_LAST;i++){
            keys[i] = false;
        }
    }
    /**
     * Get the key down condition
     *
     * @param key the detect key
     * @return {@link Boolean} whether the key is down
     * @author Weizhi
     * @version 1.0
     */
    public boolean isKeyDown(int key){
        return glfwGetKey(window,key)==1;
    }
    /**
     * Get the mouse down condition
     *
     * @param button the detect key
     * @return {@link Boolean} whether the key is down
     * @author Weizhi
     * @version 1.0
     */
    public boolean isMouseButtonDown(int button){
        return glfwGetMouseButton(window,button)==1;
    }
    /**
     * Get the mouse press condition
     *
     * @param key the detect key
     * @return {@link Boolean} whether the key is down
     * @author Weizhi
     * @version 1.0
     */
    public boolean isKeyPressed(int key){
        return (isKeyDown(key)&&!keys[key]);
    }
    /**
     * Get the key released condition
     *
     * @param key the detect key
     * @return {@link Boolean} whether the key is down
     * @author Weizhi
     * @version 1.0
     */
    public boolean isKeyReleased(int key){
        return (!isKeyDown(key)&&keys[key]);
    }
    /**
     * Get the mouse position
     *
     * @return Vector2f, the position of the key
     * @author Weizhi
     * @version 1.0
     */
    public Vector2f getMousePosition() {
        glfwGetCursorPos(window, x, y);

        glfwGetWindowSize(window, winWidth, winHeight);

        mousePos.set((float) x[0] - (winWidth[0] / 2.0f), -((float) y[0] - (winHeight[0] / 2.0f)));

        return mousePos;
    }
    /**
     * update key condition
     *
     * @author Weizhi
     * @version 1.0
     */
    public void update(){
        for (int i = 32;i<GLFW_KEY_LAST;i++){
            keys[i] = isKeyDown(i);
        }

    }

}
