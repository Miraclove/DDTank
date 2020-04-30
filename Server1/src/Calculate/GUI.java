package Calculate;
/**
 * Class {@code GUI} the in game GUI
 *
 * <p> load all gui component in the screen
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class GUI {
    private float percent;
    /**
     * Get the percentage of force bar
     *
     * @return float,from 0 to 1
     * @author Weizhi
     * @version 1.0
     */
    public float getPercent() {
        return percent;
    }
    /**
     * Set the percentage of force bar
     *
     * @param percent the percentage of force bar, from 0 to 1
     * @author Weizhi
     * @version 1.0
     */
    public void setPercent(float percent) {
        this.percent = percent;
    }
    /**
     * Get the angle of angleInstructor
     *
     * @return float,from 0 to 180
     * @author Weizhi
     * @version 1.0
     */
    public float getAngle() {
        return angle;
    }
    /**
     * Set the angle of angleInstructor
     *
     * @param angle angle of angleInstructor, from 0 to 180
     * @author Weizhi
     * @version 1.0
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }
    /**
     * Set the wind of windInstructor
     *
     * @param windForce angle of angleInstructor, from -5 to 5
     * @author Weizhi
     * @version 1.0
     */
    public float getWindForce() {
        return windForce;
    }
    /**
     * Clear the information of GUI
     *
     * @author Weizhi
     * @version 1.0
     */
    public void setWindForce(float windForce) {
        this.windForce = windForce;
    }

    private float angle;
    private float windForce;
}
