package Rendering.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
/**
 * Class {@code Window} shows the window of the game
 *
 * <p> Basde on LWJGL
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Window {
    private long window;

    private int width, height;
    private boolean fullscreen;
    private boolean hasResized;
    private GLFWWindowSizeCallback windowSizeCallback;

    private Input input;
    /**
     * Set catch error
     *
     * @author Weizhi
     * @version 1.0
     */
    public static void setCallbacks() {
        glfwSetErrorCallback(new GLFWErrorCallbackI() {
            @Override
            public void invoke(int error, long description) {
                throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
            }
        });
    }

    private void setLocalCallbacks() {
        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long argWindow, int argWidth, int argHeight) {
                width = argWidth;
                height = argHeight;
                hasResized = true;
            }
        };

        glfwSetWindowSizeCallback(window, windowSizeCallback);
    }
    /**
     * Set for LWJGL and create window
     *
     * @param title the name of the window
     * @author Weizhi
     * @version 1.0
     */
    public void createWindow(String title) {
        window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);

        if (window == 0) throw new IllegalStateException("Failed to create window!");

        if (!fullscreen) {
            GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);
        }

        glfwShowWindow(window);

        glfwMakeContextCurrent(window);

        input = new Input(window);
        setLocalCallbacks();
    }
    /**
     * Clear the window
     *
     * @author Weizhi
     * @version 1.0
     */
    public void cleanUp() {
        glfwFreeCallbacks(window);
    }
    /**
     * Close the window
     *
     * @author Weizhi
     * @version 1.0
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }
    /**
     * swap OpenGL buffer for new frame
     *
     * @author Weizhi
     * @version 1.0
     */
    public void swapBuffers() {
        glfwSwapBuffers(window);
    }
    /**
     * Set the size of the window
     *
     *
     * @param width the width of the window
     * @param height the height of the window
     * @author Weizhi
     * @version 1.0
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Set the full screen feature
     *
     * @param fullscreen set true : fullscreen, false: not fullscreen
     * @author Weizhi
     * @version 1.0
     */
    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
    /**
     * update the window
     *
     * @author Weizhi
     * @version 1.0
     */
    public void update() {
        hasResized = false;
        input.update();
        glfwPollEvents();
    }
    /**
     * get width of the window
     * @return {@link Integer} the width of window
     * @author Weizhi
     * @version 1.0
     */
    public int getWidth() {
        return width;
    }
    /**
     * get height of the window
     * @return {@link Integer} the height of window
     * @author Weizhi
     * @version 1.0
     */
    public int getHeight() {
        return height;
    }
    /**
     * get resized condition
     * @return {@link Boolean} true : size changed, false : size not change
     * @author Weizhi
     * @version 1.0
     */
    public boolean hasResized() {
        return hasResized;
    }
    /**
     * get fullscreen condition
     * @return {@link Boolean} true : fullscreen, false : not fullscreen
     * @author Weizhi
     * @version 1.0
     */
    public boolean isFullscreen() {
        return fullscreen;
    }
    /**
     * get window id in LWJGL
     * @return {@link Long} the id of window
     * @author Weizhi
     * @version 1.0
     */
    public long getWindow() {
        return window;
    }
    /**
     * get input of the window
     * @return {@link Input} the user input controller
     * @author Weizhi
     * @version 1.0
     */
    public Input getInput() {
        return input;
    }
}
