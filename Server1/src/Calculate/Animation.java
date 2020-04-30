package Calculate;

import Calculate.Timer;
/**
 * Class {@code Animation} the animation of the entity
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Animation {
    private int pointer;

    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double fps;
    /**
     * Constructor for Animation
     *
     * <p>Init Data
     *
     * @param amount the amount of frames of the animation
     * @param filename the file path of animation frame
     * @param fps the fps of the animation
     * @author Weizhi
     * @version 1.0
     */
    public Animation(int amount,int fps,String filename){
        this.pointer = 0;
        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = Timer.getTime();
        this.fps = 1.0/(double) fps;
    }


}