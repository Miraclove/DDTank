package Calculate;
/**
 * Class {@code Timer} Manage Time of game
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Timer {
    /**
     * Get current time
     *
     * @return {@link Double} the time, the unit is second
     * @author Weizhi
     * @version 1.0
     */
    public static double getTime() {
        return (double) System.nanoTime() / (double) 1000000000L;
    }
}
