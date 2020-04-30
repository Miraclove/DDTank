package org.lwjglb.game;

public class PositionController {
    public static float ACC_GRAVITY = -2.0f;
    public static float VEL_JUMP = 2.0f;
    private float counter = 0.0f;
    private boolean isStart = false;

    private float xAcceleration = 0.0f;
    private float yAcceleration = 0.0f;
    private float xVelocity = 0.0f;
    private float yVelocity = 0.0f;
    private Position position;
    public PositionController(){
        position = new Position();
    }
    public void start(){
        isStart = true;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public void setX(float x){
        this.position.xPosition = x;
    }
    public void setY(float y){
        this.position.yPosition = y;
    }
    public float getX(){
        return position.xPosition;
    }
    public float getY(){
        return position.yPosition;
    }

    public void clear(){
        isStart = false;
        counter = 0.0f;
        xAcceleration = 0.0f;
        yAcceleration = 0.0f;
        xVelocity = 0.0f;
        yVelocity = 0.0f;
    }

    public void init(Position position,float xAcceleration,float yAcceleration,float xVelocity,float yVelocity){
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.position = position;
    }

    public Position nextPosition(){
        if (isStart){
            counter = counter + 0.02f;
            position.xPosition = position.xPosition + xAcceleration*counter*counter + xVelocity*counter;
            position.yPosition = yAcceleration*counter*counter + yVelocity*counter;
        }
        return position;
    }

    public void move(float x,float y){
        position.xPosition = position.xPosition + x;
        position.yPosition = position.yPosition + y;
    }

}
