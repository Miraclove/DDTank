package Logic;

import Config.EntityInfo;
import Config.GameInfo;
import Config.InstantInfo;
import Config.WeaponInfo;
import Network.InteractWithServer;
import Rendering.collision.AABB;
import Rendering.engine.Timer;
import Rendering.engine.Window;
import Rendering.entity.Entity;
import Rendering.entity.Transform;
import Rendering.entity.Weapon;
import Rendering.gui.GUI;
import Rendering.render.Camera;
import Rendering.test.Main;
import Rendering.world.World;
import org.joml.Vector2f;


import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;
/**
 * Class {@code GameController} Controls the Game and
 * get instant information from server and
 * update rendering pictures
 *
 * <p> Run in a loop, and update 30 times per seconds
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class GameController {
    private boolean isSent = false;
    private Boolean turn = true;
    private InstantInfo instantInfo;
    private double time = 0;
    private GameInfo gameInfo;
    private float garvity = 0.05f;
    private float startV = 2f;
    private boolean isJump = false;
    public GameController(GameInfo gameInfo){
        this.gameInfo = gameInfo;
    }
    //init weapon info
    private void setWeapon(){
        Transform transform = new Transform();
        transform.pos.x = instantInfo.entityInfoList.get(instantInfo.weaponInfo.getOwnerName()).getPosition().x;
        transform.pos.y = instantInfo.entityInfoList.get(instantInfo.weaponInfo.getOwnerName()).getPosition().y + 1 ;
        World.weapon = new Weapon(transform,new WeaponInfo(instantInfo.weaponInfo.getUserName(),instantInfo.weaponInfo.getOwnerName(),instantInfo.weaponInfo.getPower()));
    }
    //get input from windows
    private void getInput(Window window,GUI gui,World world){
        if (Main.gameInfo.curUser.equals(instantInfo.cameraTarget)){
            Entity entity = World.entityList.get(instantInfo.cameraTarget);
            //entity move
            Vector2f movement = new Vector2f();
            if (window.getInput().isKeyDown(GLFW_KEY_LEFT)){
                movement.add(-0.1f,0);
            }
            if (window.getInput().isKeyDown(GLFW_KEY_RIGHT)){
                movement.add(0.1f,0);
            }
            if (window.getInput().isKeyDown(GLFW_KEY_C)){
                System.out.println("Jump!");
                isJump = true;
                startV = 2f;
            }
            entity.move(movement);
            if (isJump){
                if (isTouchGroud(entity,world)){
                    System.out.println("Touch the ground!");
                    isJump = false;
                    startV = 0f;
                }else {
                    System.out.println("Above the ground!");
                    movement.add(0f,startV - garvity);
                    startV = startV - garvity;
                }
            }
            entity.collideWithTiles(world);
            //get force
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
            //get angle
            if (window.getInput().isKeyDown(GLFW_KEY_UP)&&gui.getAngle()<180){
                gui.setAngle(gui.getAngle()+0.5f);
            }else if (window.getInput().isKeyDown(GLFW_KEY_DOWN)&&gui.getAngle()>0){
                gui.setAngle(gui.getAngle()-0.5f);

            }
            //send to server
            if (movement.x!=0||movement.y!=0){
                InteractWithServer.sendPlayerInfo(instantInfo.cameraTarget,gameInfo.groupName,entity.transform.pos.x+"",entity.transform.pos.y+"","10");
            }
        }

    }

    /**
     * update Information from server and update rendering information
     *
     * <p>place in a loop, and run for 30 times per seconds so the Logic is different
     *
     *
     * @param camera set {@link Camera} camera for update camera display
     * @param gui set {@link GUI} gui for update GUI display
     * @param window set {@link Window} window for update window display
     * @param world set {@link World} world for update world display
     * @author Weizhi
     * @version 1.0
     */
    public void updateInfo(World world, GUI gui, Camera camera, Window window){
        instantInfo = InteractWithServer.getInstantInfo(gameInfo.groupName);
        //set mini Map
//        setMiniMap(instantInfo,gui);
//        System.out.println("Set mini Map");
        //isGameOver
        if (instantInfo.isGameOver){
            try {
                if (time == 0){
                    gui.gameOver.setText(instantInfo.endInfo,new Font("Comic Sans MS", Font.BOLD, 30));
                    gui.gameOver.setVisible(true);
                    gui.windInstructor.setVisible(false);
                    gui.angleInstructor.setVisible(false);
                    time = Timer.getTime();
                }else {
                    if (Timer.getTime() - time >= 5){
                        glfwSetWindowShouldClose(window.getWindow(), true);
                        InteractWithServer.setGameEnd(gameInfo.groupName);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            //detect GUI
            if (instantInfo.remainTime == 0 && world.curPlayer.equals(instantInfo.cameraTarget)&&!isSent){
                InteractWithServer.sendGuiInfo(gui.getAngle()+"",gui.getPercent()+"","");
                isSent = true;
            }
            if (!world.curPlayer.equals(instantInfo.cameraTarget)){
                isSent = false;
            }
            //detect weapon
            if (instantInfo.weaponInfo!=null){
                //update for weapon
                if (World.weapon!=null){
                    World.weapon.setPosition(
                            new Vector2f(instantInfo.weaponInfo.getPosition().x,
                                    instantInfo.weaponInfo.getPosition().y));
                }else {
                    //init
                    setWeapon();
                }
            }else {
                //delete weapon
                if (World.weapon!=null){
                    World.weapon = null;
                }
            }
            //set position for everyone
            for (String key:instantInfo.entityInfoList.keySet()){
                if (!key.equals(world.curPlayer)){
                    World.entityList.get(key).setPosition(new Vector2f(instantInfo.entityInfoList.get(key).getPosition().x,
                            instantInfo.entityInfoList.get(key).getPosition().y));
                    World.entityList.get(key).setEntityInfo(instantInfo.entityInfoList.get(key));
                }
            }
            //set wind
            gui.setWindForce(instantInfo.wind);
            //set camera target
            if (instantInfo.weaponInfo != null){
                world.setCameraTarget(camera,World.weapon);
            }else {
                world.setCameraTarget(camera, World.entityList.get(instantInfo.cameraTarget));
            }
            //set timer
            gui.setRemainTime(instantInfo.remainTime+"");
            //update input
            getInput(window,gui,world);
        }

    }
    private boolean isTouchGroud(Entity entity,World world){
        AABB box1 = world.getTileBoundingBox(
                (int) (((entity.transform.pos.x / 2) + 0.5f) - (5 / 2)) + 2,
                (int) (((-entity.transform.pos.y / 2) + 0.5f) - (5 / 2)) + 3);
        if (box1!=null){
            if (box1.getCollision(entity.getBounding_box()).isIntersecting){
                System.out.println("Touch Ground!!!");
                return true;
            }
        }
        return false;
    }
    //set mini map for the game
    private void setMiniMap(InstantInfo instantInfo,GUI gui){
//        BufferedImage image = markImageByIcon("./res/icon/player.png","./res/map/test/worm.jpg",instantInfo);
//        BufferedImage image = getBufferedImageFromFile("./res/map/test/worm.jpg");
//        for (String key:instantInfo.entityInfoList.keySet()){
//            System.out.println("Set for player "+key);
//            EntityInfo entityInfo = instantInfo.entityInfoList.get(key);
//            float x = entityInfo.getPosition().x*10f;
//            float y = entityInfo.getPosition().y*10f;
//            image = addImageByIcon(image,"./res/icon/player.png",(int)x,(int)y);
//        }
//        gui.miniMap.setPicture(image);
    }
}
