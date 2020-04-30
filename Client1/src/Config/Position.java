package Config;

import java.io.Serializable;
/**
 * Class {@code Position} Contains position information
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Position implements Serializable {
    /** the x position of entity {@value} */
    public float x;
    /** the y position of entity {@value} */
    public float y;
    /** the x scale of entity {@value} */
    public float scaleX;
    /** the y scale of entity {@value} */
    public float scaleY;
    /**
     * Constructor for Position
     *
     * <p>
     * @param x the position x of entity
     * @param y the position y of entity
     * @param scaleX the scale x of entity
     * @param scaleY the scale y of entity
     * @author Weizhi
     * @version 1.0
     */
    public Position(float x,float y,float scaleX,float scaleY){
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;

    }

}