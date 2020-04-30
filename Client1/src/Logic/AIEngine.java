package Logic;
/**
 * Class {@code AIEngine} is the AI engine to generate random move
 * of the Player to compete with the real Player
 *
 * <p> Basically, just computer controls character
 *
 * @author Weizhi
 * @since 05/02/2020
 */

public class AIEngine {
    /**
     * Generate the random number from {@code min} to {@code max}
     *
     * @param min the min value of the random number
     * @param max the max value of teh random number
     * @return {@link Float},the random number from {@code min} to {@code max}
     * @author Weizhi
     * @version 1.0
     */
    public static float getRandomNumber(double max,double min){
        return (float) (Math.random()%(max - min) + min);
    }
    /**
     * Generate the random force
     *
     * @return {@link Float},the random force
     * @author Weizhi
     * @version 1.0
     */
    public static float getPercent(){
        return getRandomNumber(0.8,0.3);
    }
    /**
     * Generate the random angle
     *
     * @return {@link Float},the random angle
     * @author Weizhi
     * @version 1.0
     */
    public static float getAngle(){
        return getRandomNumber(0.8,0.3)*100f;
    }
}
