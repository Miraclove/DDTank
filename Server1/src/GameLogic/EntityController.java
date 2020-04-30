package GameLogic;

import Calculate.Entity;
import Calculate.GUI;
import Calculate.Timer;
import Calculate.World;
import Config.EntityInfo;
import org.joml.Vector2f;
/**
 * Class {@code EntityController} Control the movement of entity
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class EntityController {
    private static float originalTime = 0.0f;
    private static float FULLFORCE = 30f;
    private static float Vx;
    private static float Vy;
    private static float wind;
    private static float gravity = -5f;
    private static float Px;
    private static float Py;
    /**
     * set the start information of weapon
     *
     * @param entity the flying entity
     * @param percent the launch force
     * @param angle the launch angle
     * @param time the launch start time
     * @param windForce the current wind force
     * @author Weizhi
     * @version 1.0
     */
    public static void setStart(EntityInfo entity, float time, float angle, float percent, float windForce){
        originalTime = time;
        Vx = ((float) Math.cos(Math.toRadians(angle)))*percent*FULLFORCE;
        Vy = ((float) Math.sin(Math.toRadians(angle)))*percent*FULLFORCE;
        wind = windForce;
        Px = entity.getPosition().x;
        Py = entity.getPosition().y;
//        System.out.println("percent : "+percent+" angle : "+angle+" Vx :"+Vx+ " Vy : "+ Vy +" Px : "+ Px + " Py "+ Py);
    }
    /**
     * update the position of the weapon
     *
     * @param weapon the flying weapon
     * @author Weizhi
     * @version 1.0
     */
    public static void updateWeaponPosition(EntityInfo weapon){
        float time = (float) Timer.getTime()  - originalTime;
        float Dx = wind*time*time + Vx*time;
        float Dy = gravity*time*time + Vy*time;
//        System.out.println("X : "+(Dx+Px)+"| Y : "+(Dy+Py));
        weapon.setPosition(Dx+Px,Dy+Py);
    }

}
