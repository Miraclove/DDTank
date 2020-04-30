package Rendering.engine;
/**
 * Class {@code Timer} Manage Time of game
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Timer {

    private double lastLoopTime;
    /**
     * Init the Time, set original time
     *
     *
     * @author Weizhi
     * @version 1.0
     */
    public void init() {
        lastLoopTime = getTime();
    }
    /**
     * Get current time
     *
     * @return {@link Double} the time, the unit is second
     * @author Weizhi
     * @version 1.0
     */
    public static double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }
    /**
     * Get process time
     *
     * @return {@link Float} the time, the unit is second
     * @author Weizhi
     * @version 1.0
     */
    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }
    /**
     * Get last loop precess time
     *
     * @return {@link Double} the time, the unit is second
     * @author Weizhi
     * @version 1.0
     */
    public double getLastLoopTime() {
        return lastLoopTime;
    }
}