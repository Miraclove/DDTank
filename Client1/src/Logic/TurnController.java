package Logic;


import Config.EntityInfo;
import Config.WeaponInfo;
import Rendering.collision.AABB;
import Rendering.collision.Collision;
import Rendering.engine.Window;
import Rendering.entity.Entity;
import Rendering.entity.Transform;
import Rendering.entity.Weapon;
import Rendering.gui.GUI;
import Rendering.render.Camera;
import Rendering.world.World;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class TurnController {
    private long startTime;
    private HashMap<String,Entity> entityList;
    private String curTurn;
    private boolean isEnd;
    private boolean isHit = false;
    private boolean isWeaponTurn;
    private boolean isCrash = false;
    private int turnTime = 10;
    public TurnController(HashMap<String,Entity> entityList){
        this.entityList = entityList;
        curTurn = "you";
        isEnd = true;

    }

    public void turn(Entity entity, GUI gui, Window window, Camera camera, World world){
        try{
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String toDate = simpleFormat.format(new Date());
            long to = simpleFormat.parse(toDate).getTime();
            int remainTime = (int) ((to - startTime)/(1000));
            gui.setRemainTime(turnTime - remainTime+"s");
            if (entity.getEntityType() == Entity.PLAYER){
                EntityController.updatePlayer(entity,window,camera,world,gui);
            }else if(entity.getEntityType() == Entity.AIPLAYER){
                EntityController.updateAIEntity(entity,window,camera,world,gui);
            }
            if (turnTime - remainTime == 0&&!isEnd){
                System.out.println("Statue: Weapon Turn");
                isEnd = true;
                isWeaponTurn = true;
                EntityController.isSet = false;
                Transform transform = new Transform();
                transform.pos.x = entity.transform.pos.x;
                transform.pos.y = entity.transform.pos.y + 1 ;
                Weapon weapon = new Weapon(transform,new WeaponInfo("bomb",curTurn,0));
                weapon.owner = entity.getEntityInfo().getUserName();
                entityList.put("weapon",weapon);
                float wind = (float)((Math.random()-0.5) * 10);
                gui.setWindForce(wind);
                EntityController.setStart(weapon,gui.getAngle(),gui.getPercent(),wind);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void weaponTurn(Weapon weapon, Window window, Camera camera, World world){
        EntityController.updateWeaponPosition(weapon,window,camera,world);
        System.out.println("updateWeapon");
        if (isCrash||isHit){
            System.out.println("Incident: Weapon delete!");
            entityList.remove("weapon");
            isCrash = false;
            isHit = false;
            isWeaponTurn = false;
        }else {
            for (String key:entityList.keySet()){
                Collision collision = weapon.getBounding_box().getCollision(entityList.get(key).getBounding_box());
                if (collision.isIntersecting&&(!key.equals(weapon.owner))&&(!key.equals("weapon"))){
                    System.out.println("Incident: Hit "+key+" and Weapon crash!!!");
                    isHit = true;
                    break;
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
                    isCrash = true;
                    System.out.println("Incident: Weapon crash!!!");

                }
            }
            if (box2!=null) {
                if (box2.getCollision(weapon.getBounding_box()).isIntersecting) {
                    isCrash = true;
                    System.out.println("Incident: Weapon crash!!!");
                }
            }
            if (box3!=null) {
                if (box3.getCollision(weapon.getBounding_box()).isIntersecting) {
                    isCrash = true;
                    System.out.println("Incident: Weapon crash!!!");

                }
            }
            if (box4!=null){
                if (box4.getCollision(weapon.getBounding_box()).isIntersecting){
                    isCrash = true;
                    System.out.println("Incident: Weapon crash!!!");

                }
            }
        }
    }

    public void run(GUI gui, Window window, Camera camera, World world){
        if (!isWeaponTurn){
//            System.out.println("Statue: Player Turn For Player "+curTurn);
            turn(entityList.get(curTurn),gui,window,camera,world);
        }

        if (isEnd){
            if (isWeaponTurn){
                weaponTurn((Weapon) entityList.get("weapon"),window,camera,world);
//                isWeaponTurn = false;
            }else {
                try {
                    if (curTurn.equals("you")){
                        curTurn = "bot";
                    }else {
                        curTurn = "you";
                    }
                    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String fromDate = simpleFormat.format(new Date());
                    startTime = simpleFormat.parse(fromDate).getTime();
                    gui.clear();
                    isEnd = false;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
