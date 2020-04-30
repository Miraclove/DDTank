package Logic;
import Rendering.engine.Timer;
import Rendering.engine.Window;
import Rendering.entity.Entity;
import Rendering.gui.GUI;
import Rendering.render.Camera;
import Rendering.world.World;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class EntityController {
    public static int counter = 0;
    public static float originalTime = 0.0f;
    public static Boolean isJump = false;
    public static Entity entity;
    public static float FULLFORCE = 30f;
    public static float Vx;
    public static float Vy;
    public static float wind;
    public static float gravity = -5f;
    public static float Px;
    public static float Py;
    public static boolean isSet = false;
    private static Boolean turn = true;
    public static void setStart(Entity entity,float angle, float percent, float windForce){
        Vx = ((float) Math.cos(Math.toRadians(angle)))*percent*FULLFORCE;
        Vy = ((float) Math.sin(Math.toRadians(angle)))*percent*FULLFORCE;
        wind = windForce;
        originalTime = (float) Timer.getTime();
        Px = entity.transform.pos.x;
        Py = entity.transform.pos.y;
//        System.out.println("percent : "+percent+" angle : "+angle+" Vx :"+Vx+ " Vy : "+ Vy +" Px : "+ Px + " Py "+ Py);
    }

    public static void updateWeaponPosition(Entity entity, Window window, Camera camera, World world){
        float time = (float)Timer.getTime()  - originalTime;
        float Dx = wind*time*time + Vx*time;
        float Dy = gravity*time*time + Vy*time;
//        System.out.println("X : "+(Dx+Px)+"| Y : "+(Dy+Py));
        entity.setPosition(new Vector2f(Dx+Px,Dy+Py));
        world.setCameraTarget(camera,entity);
    }
    public static void updateAIEntity(Entity entity, Window window, Camera camera, World world, GUI gui){
        if (!isSet){
            gui.setPercent(AIEngine.getPercent());
            gui.setAngle(AIEngine.getAngle());
            isSet = true;
        }
        world.setCameraTarget(camera,entity);
    }



    public static void updatePlayer(Entity entity, Window window, Camera camera, World world, GUI gui){
        Vector2f movement = new Vector2f();
        if (window.getInput().isKeyDown(GLFW_KEY_SPACE)) {
            if (turn) {
                gui.setPercent(gui.getPercent() + 0.01f);
                if (gui.getPercent() >= 1) {
                    turn = false;
                }
            } else {
                gui.setPercent(gui.getPercent() - 0.01f);
                if (gui.getPercent() <= 0) {
                    turn = true;
                }
            }
        }
        if (window.getInput().isKeyDown(GLFW_KEY_UP)&&gui.getAngle()<180){
            gui.setAngle(gui.getAngle()+0.5f);
        }else if (window.getInput().isKeyDown(GLFW_KEY_DOWN)&&gui.getAngle()>0){
            gui.setAngle(gui.getAngle()-0.5f);

        }
        if (window.getInput().isKeyDown(GLFW_KEY_LEFT)){
            movement.add(-0.1f,0);
        }
        if (window.getInput().isKeyDown(GLFW_KEY_RIGHT)){
            movement.add(0.1f,0);
        }

        entity.move(movement);
        if (window.getInput().isKeyDown(GLFW_KEY_C)){
            entity.setPosition(new Vector2f(1,-10));
        }

        world.setCameraTarget(camera,entity);
//        if (window.getInput().isKeyDown(GLFW_KEY_UP)){
//            movement.add(0,0.1f);
//        }
//        if (window.getInput().isKeyDown(GLFW_KEY_DOWN)){
//            movement.add(0,-0.1f);
//        }


//                    if (window.getInput().isKeyDown(GLFW.GLFW_KEY_SPACE)) {
//                        isJump = true;
//                    }
//                    if (isJump){
//                        System.out.println(entity.transform.pos.y);
//                        if (vOriginal == 0){
//                            vOriginal = 10;
//                        }
//                        counter = counter + 1;
//                        movement.add(0, (vOriginal-(int)(counter/5)) * delta);
//                    }



//        switch (i){
//            case 0:
//
//                break;
//            case 1:
//                movement = new Vector2f();
//                if (window.getInput().isKeyDown(GLFW_KEY_A)){
//                    movement.add(-0.1f,0);
//                }
//                if (window.getInput().isKeyDown(GLFW_KEY_D)){
//                    movement.add(0.1f,0);
//                }
//                if (window.getInput().isKeyDown(GLFW_KEY_W)){
//                    movement.add(0,0.1f);
//                }
//                if (window.getInput().isKeyDown(GLFW_KEY_S)){
//                    movement.add(0,-0.1f);
//                }
//                entity.move(movement);
//                if (movement.x!=0 || movement.y!=0){
//                    world.setCameraTarget(camera,entity);
//                }
//            case 2:
//                movement = new Vector2f();
//
//                entity.move(movement);
//                if (movement.x!=0 || movement.y!=0){
//                    world.setCameraTarget(camera,entity);
//                }
//        }
    }


}
