package GameLogic;

import Calculate.*;
import Calculate.Timer;
import Config.*;
import Database.DataCheck;
import Networking.ConnectedGroup;
import Networking.InteractWithClient;
import org.joml.Vector2f;

import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Class {@code GameController} Controls the whole game process
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class GameController extends Thread{
    public static boolean isCrash = false;
    public static volatile boolean isGuiUploaded = false;
    public static GuiInfo guiInfo;
    private long startTime;
    private int turnTime = 20;
    private float wind;
    private World world;
    private String groupName;
    private InstantInfo instantInfo;
    private boolean isEnd = false;
    /**
     * Constructor for GameController
     *
     * <p>Init Data
     *
     * @author Weizhi
     * @version 1.0
     */
    public GameController(GameInfo gameInfo){
//        for (int i = 0;i<ConnectedGroup.groups.size();i++){
//            ConnectedGroup.groups.get(i)
//        }
        this.world = new World(gameInfo);
        this.groupName = gameInfo.groupName;

        this.instantInfo = new InstantInfo((HashMap<String, EntityInfo>) gameInfo.gameEntity,0,gameInfo.curUser,10);
        this.instantInfo.isGameOver = false;
        this.instantInfo.weaponInfo = null;
        String target = "";
        for (String key:gameInfo.gameEntity.keySet()){
            target = key;
        }
        System.out.println("Target Set to:" + target);
        this.instantInfo.cameraTarget = target;
        float i = 0f;
        for (String key:instantInfo.entityInfoList.keySet()){
            instantInfo.entityInfoList.get(key).setPosition(10f+i*3f,-28f);
            i = i + 1f;
        }
        ConnectedGroup.groupInstantInfo.put(gameInfo.groupName,instantInfo);
        ConnectedGroup.worldHashMap.put(gameInfo.groupName,world);
    }
    /**
     * process player turn
     *
     * @param userName the user name
     * @author Weizhi
     * @version 1.0
     */
    public void playerTurn(String userName) {
        try {
            //Sync data, set timer, set wind force
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String fromDate = simpleFormat.format(new Date());
            startTime = simpleFormat.parse(fromDate).getTime();
            
            //set camera
            System.out.println("Set camera target to "+userName);
            instantInfo.cameraTarget = userName;
            
            //set wind
            wind = (float) ((Math.random() - 0.5) * 10);
            instantInfo.wind = wind;
            System.out.println("Set wind to "+wind);
            
            //turn time
            System.out.println("Start turn and wait");
            int time = -1;
            while (true) {
                simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String toDate = simpleFormat.format(new Date());
                long to = simpleFormat.parse(toDate).getTime();
                int remainTime = (int) ((to - startTime) / (1000));
                if (time != remainTime){
                    if (turnTime - remainTime != 0){
                        instantInfo.remainTime = turnTime - remainTime;
                    }else {
                        instantInfo.remainTime = turnTime - remainTime;
                        break;
                    }

                }
                time = remainTime;
            }
            System.out.println("End turn and wait for gui upload");
            //get gui info
            double origTime = Timer.getTime();
            while (!isGuiUploaded) {
                if (Timer.getTime() - origTime>2){
                    guiInfo = new GuiInfo("0","0");
                    break;
                }else {
                    Thread.sleep(10);
                }
            }
            isGuiUploaded = false;
            System.out.println("Turn to weapon");
            //turn to weapon

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * process weapon turn
     *
     * @param userName the user name
     * @author Weizhi
     * @version 1.0
     */
    public void weaponTurn(String userName){
        //set weapon
        System.out.println("Start Weapon Turn and send info");
        Position position = new Position(instantInfo.entityInfoList.get(userName).getPosition().x,
                instantInfo.entityInfoList.get(userName).getPosition().y,1,1);
        instantInfo.weaponInfo = DataCheck.getWeaponInfo(instantInfo.entityInfoList.get(userName).getWeapon());
        instantInfo.weaponInfo.setOwnerName(userName);
        instantInfo.weaponInfo.setPosition(position);
        instantInfo.cameraTarget = instantInfo.entityInfoList.get(userName).getWeapon();
        EntityController.setStart(instantInfo.weaponInfo,
                (float)Timer.getTime(),
                Float.parseFloat(guiInfo.getAngle()),
                Float.parseFloat(guiInfo.getPercent()),
                instantInfo.wind);

        //wait for weapon crash
        System.out.println("Wait for weapon crash");
        while (!weaponCrash(instantInfo.weaponInfo,world)){
            EntityController.updateWeaponPosition(instantInfo.weaponInfo);
        }
        System.out.println("Weapon crashed");
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        instantInfo.weaponInfo = null;

        //change turn
    }
    /**
     * calculate weapon condition
     *
     * @param weaponInfo the weapon information
     * @param world the world and game playground
     * @return Boolean, true : weapon crash, false : weapon flying
     * @author Weizhi
     * @version 1.0
     */
    public boolean weaponCrash(WeaponInfo weaponInfo, World world){
        Transform transform = new Transform();
        transform.pos.x = weaponInfo.getPosition().x;
        transform.pos.y = weaponInfo.getPosition().y;
        transform.scale.x = weaponInfo.getPosition().scaleX;
        transform.scale.y = weaponInfo.getPosition().scaleY;
        Weapon weapon = new Weapon(transform,weaponInfo);
        for (String key:instantInfo.entityInfoList.keySet()){
            Collision collision = weapon.getBounding_box().getCollision(
                    new Vector2f(
                            instantInfo.entityInfoList.get(key).getPosition().x,
                           instantInfo.entityInfoList.get(key).getPosition().y
                    ));
            if (collision.isIntersecting&&(!weapon.owner.equals(key))&&(!key.equals("weapon"))){
                EntityInfo entityInfo = instantInfo.entityInfoList.get(key);
                System.out.println("Incident: Hit " + key + " Damage: "+ weaponInfo.getPower() + " "+key +" health:" + (entityInfo.getHealth()-weaponInfo.getPower()));
                if (entityInfo.getHealth()-weaponInfo.getPower()<=0){
                    System.out.println("Game over");
                    instantInfo.endInfo = "Winner is "+ weaponInfo.getOwnerName();
                    instantInfo.isGameOver = true;
                    isEnd = true;
                    DataCheck.recordGame(weaponInfo.getOwnerName(),DataCheck.getGameInfo(groupName));
                    System.out.println("destory");

                }
                entityInfo.setHealth(entityInfo.getHealth()-weaponInfo.getPower());
                return true;
            }
        }
        AABB box1 = world.getTileBoundingBox(
                (int) (((weapon.transform.pos.x / 2) + 0.5f) - (5 / 2)) + 2,
                (int) (((-weapon.transform.pos.y / 2) + 0.5f) - (5 / 2)) + 3);
        AABB box2 = world.getTileBoundingBox(
                (int) (((weapon.transform.pos.x / 2) + 0.5f) - (5 / 2)) + 1,
                (int) (((-weapon.transform.pos.y / 2) + 0.5f) - (5 / 2)) + 2);
        AABB box3 = world.getTileBoundingBox(
                (int) (((weapon.transform.pos.x / 2) + 0.5f) - (5 / 2)) + 3,
                (int) (((-weapon.transform.pos.y / 2) + 0.5f) - (5 / 2)) + 2);
        AABB box4 = world.getTileBoundingBox(
                (int) (((weapon.transform.pos.x / 2) + 0.5f) - (5 / 2)) + 2,
                (int) (((-weapon.transform.pos.y / 2) + 0.5f) - (5 / 2)) + 1);
        if (box1!=null){
            if (box1.getCollision(weapon.getBounding_box()).isIntersecting){
                System.out.println("Incident: Weapon crash!!!");
                return true;

            }
        }
        if (box2!=null) {
            if (box2.getCollision(weapon.getBounding_box()).isIntersecting) {
                System.out.println("Incident: Weapon crash!!!");
                return true;
            }
        }
        if (box3!=null) {
            if (box3.getCollision(weapon.getBounding_box()).isIntersecting) {
                System.out.println("Incident: Weapon crash!!!");
                return true;

            }
        }
        if (box4!=null){
            if (box4.getCollision(weapon.getBounding_box()).isIntersecting){
                System.out.println("Incident: Weapon crash!!!");
                return true;

            }
        }
        return false;
    }
    /**
     * run the game
     *
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void run() {
        Set<String> keySet = instantInfo.entityInfoList.keySet();
        while (true){
            for (String key:keySet){
                if (!isEnd){
                    playerTurn(instantInfo.entityInfoList.get(key).getUserName());
                    weaponTurn(instantInfo.entityInfoList.get(key).getUserName());
                }else {
                    break;
                }
            }
            if (isEnd) break;
        }


    }

}
