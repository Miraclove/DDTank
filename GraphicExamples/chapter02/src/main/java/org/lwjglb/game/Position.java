package org.lwjglb.game;

public class Position {
    @Override
    public String toString() {
        return "Position{" +
                "xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }

    public float xPosition = 0.0f;
    public float yPosition = 0.0f;
    public Position(){
        xPosition = 0.0f;
        yPosition = 0.0f;
    }
}
