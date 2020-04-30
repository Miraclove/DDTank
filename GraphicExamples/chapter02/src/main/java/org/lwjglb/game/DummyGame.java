package org.lwjglb.game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjglb.engine.IGameLogic;
import org.lwjglb.engine.Window;

public class DummyGame implements IGameLogic {

    private int ydirection = 0;
    private int xdirection = 0;
    private boolean isJump = false;
    
    private float color = 0.0f;
    private Position position;
    private PositionController controller;

    private final Renderer renderer;
    
    public DummyGame() {
        renderer = new Renderer();
        position = new Position();
        controller = new PositionController();
    }
    
    @Override
    public void init() throws Exception {
        renderer.init();
    }
    
    @Override
    public void input(Window window) {
        if ( window.isKeyPressed(GLFW_KEY_LEFT) ) {
            xdirection = -1;
        } else if ( window.isKeyPressed(GLFW_KEY_RIGHT) ) {
            xdirection = 1;
        } else {
            xdirection = 0;
        }
        if (window.isKeyPressed(GLFW_KEY_SPACE)){
            isJump = true;
        }
    }

    @Override
    public void update(float interval) {
        if (isJump){
            controller.init(position,0.0f,-1.0f,0.0f,1.0f);
            controller.start();
        }
        if ( controller.getY() < 0.0f ) {
            controller.clear();
            controller.setY(0.0f);
            isJump = false;
        }
        controller.setX(controller.getX()+xdirection * 0.01f);
        if (controller.getX() > 1.0f) {
            controller.setX(1.0f);
        } else if ( controller.getX() < -1.0f ) {
            controller.setX(-1.0f);
        }
        position = controller.nextPosition();

    }

    @Override
    public void render(Window window) {
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

//        window.setClearColor(color, color, color, 0.0f);
        glfwPollEvents();
        renderer.clear();
        glBegin(GL_QUADS);
            glVertex2f(-0.1f+position.xPosition,0.1f+position.yPosition);
            glVertex2f(0.1f+position.xPosition,0.1f+position.yPosition);
            glVertex2f(0.1f+position.xPosition,-0.1f+position.yPosition);
            glVertex2f(-0.1f+position.xPosition,-0.1f+position.yPosition);
        glEnd();
    }
}
