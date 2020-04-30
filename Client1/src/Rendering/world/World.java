package Rendering.world;

import Config.EntityInfo;
import Config.GameInfo;
import Config.WeaponInfo;
import Rendering.collision.AABB;
import Rendering.engine.Window;
import Rendering.entity.Entity;
import Rendering.entity.Player;
import Rendering.entity.Transform;
import Rendering.entity.Weapon;
import Rendering.render.Camera;
import Rendering.render.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class {@code World} The world as well as playground of the game
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class World {
    private int viewX;
    private int viewY;
    private byte[] tiles;
    private int width;
    private AABB[] bounding_boxes;
    /** the entity in the game */
    public static HashMap<String,Entity> entityList;
    /** the weapon in the game */
    public static Weapon weapon;
    private int height;
    private Matrix4f world;
    /** the Client user in the game */
    public String curPlayer;

    /**
     * Get the scale of world
     *
     *
     * @return int, the scale of world
     * @author Weizhi
     * @version 1.0
     */
    public int getScale() {
        return scale;
    }
    /**
     * Set the scale of world
     *
     * @param scale the scale of world
     * @author Weizhi
     * @version 1.0
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    private int scale;
    private final int view = 24;
//    public World(){
//        width = 64;
//        height = 64;
//        scale = 16;
//        tiles = new byte[width*height];
//        bounding_boxes = new AABB[width*height];
//
//        world = new Matrix4f().setTranslation(new Vector3f(0));
//        world.scale(scale);
//
////        world.scale(16);
//
//    }
    /**
     * Constructor for World
     *
     * <p>Init Data for multi player online game
     *
     * @param world the text of the world
     * @param camera the camera of the world
     * @param gameInfo the game information of the world
     * @author Weizhi
     * @version 1.0
     */
    public World(Camera camera, GameInfo gameInfo){
        try {
            BufferedImage tile_sheet = ImageIO.read(new File("./res/map/" + gameInfo.playground + "/worm.png"));
            BufferedImage entity_sheet = ImageIO.read(new File("./res/map/" + gameInfo.playground + "/entities.png"));
            curPlayer = gameInfo.curUser;

            width = tile_sheet.getWidth();
            height = tile_sheet.getHeight();
            scale = 16;

            this.world = new Matrix4f().setTranslation(new Vector3f(0));
            this.world.scale(scale);

            int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);
            int[] colorEntitySheet = entity_sheet.getRGB(0, 0, width, height, null, 0, width);

            tiles = new byte[width * height];
            bounding_boxes = new AABB[width * height];
            entityList = new HashMap<>();

            Transform transform;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int red = (colorTileSheet[x + y * width] >> 16) & 0xFF;
                    int entity_index = (colorEntitySheet[x + y * width] >> 16) & 0xFF;
                    int entity_alpha = (colorEntitySheet[x + y * width] >> 24) & 0xFF;

                    Tile t;
                    try {
                        t = Tile.tiles[red];
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        t = null;
                    }

                    if (t != null) setTile(t, x, y);

//                    if (entity_alpha > 0) {
//                        transform = new Transform();
//                        transform.pos.x = x * 2;
//                        transform.pos.y = -y * 2;
//                        switch (entity_index) {
//                            case 1 :							// Player
//                                EntityInfo entityInfo = new EntityInfo("Jack");
//                                Player firstPlayer = new Player(transform,entityInfo);
//                                firstPlayer.setEntityType(Entity.PLAYER);
//                                entityList.add(firstPlayer);
//                                System.out.print("Y1: "+transform.pos.y);
//                                camera.getPosition().set(transform.pos.mul(-scale, new Vector3f()));
//                                break;
//                            case 2:
////                                if (mode == 0){
//                                entityInfo = new EntityInfo("Mike");
//                                    Player secondPlayer = new Player(transform,entityInfo);
//                                System.out.print("Y1: "+transform.pos.y);
//                                    secondPlayer.setEntityType(Entity.PLAYER);
//                                    entityList.add(secondPlayer);
////                                }else if (mode == 1){
////                                    Player secondPlayer = new Player(transform);
////                                    secondPlayer.setEntityType(Entity.AIPLAYER);
////                                    entityList.add(secondPlayer);
////                                }
//                                //camera.getPosition().set(transform.pos.mul(-scale, new Vector3f()));
//                                break;
//                            case 3:
//                                Weapon weapon = new Weapon(transform);
//                                weapon.setEntityType(Entity.WEAPON);
//                                entityList.add(weapon);
//                                break;
//                            default :
//                                break;
//                        }
//                    }
                }
            }
            float Px = 10f;
            for (String key:gameInfo.gameEntity.keySet()){
                transform = new Transform();
                transform.pos.x = Px;
                Px = Px + 3f;
                transform.pos.y = -28.0f;
                Player player = new Player(transform,gameInfo.gameEntity.get(key));
                player.setEntityType(Entity.PLAYER);
                entityList.put(key,player);
                System.out.print("Y1: "+transform.pos.y);
                camera.getPosition().set(transform.pos.mul(-scale, new Vector3f()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Constructor for World
     *
     * <p>Init Data for local game
     *
     * @param world the text of the world
     * @param camera the camera of the world
     * @param mode the mode of the game
     * @author Weizhi
     * @version 1.0
     */
    public World(String world, Camera camera,int mode,String curPlayer){
        try {
            BufferedImage tile_sheet = ImageIO.read(new File("./res/map/bridge/worm.png"));
            BufferedImage entity_sheet = ImageIO.read(new File("./res/map/bridge/entities.png"));

            width = tile_sheet.getWidth();
            height = tile_sheet.getHeight();
            scale = 16;

            this.world = new Matrix4f().setTranslation(new Vector3f(0));
            this.world.scale(scale);

            int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);
            int[] colorEntitySheet = entity_sheet.getRGB(0, 0, width, height, null, 0, width);

            tiles = new byte[width * height];
            bounding_boxes = new AABB[width * height];
            entityList = new HashMap<>();

            Transform transform;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int red = (colorTileSheet[x + y * width] >> 16) & 0xFF;
                    int entity_index = (colorEntitySheet[x + y * width] >> 16) & 0xFF;
                    int entity_alpha = (colorEntitySheet[x + y * width] >> 24) & 0xFF;

                    Tile t;
                    try {
                        t = Tile.tiles[red];
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        t = null;
                    }

                    if (t != null) setTile(t, x, y);

                    if (entity_alpha > 0) {
                        transform = new Transform();
                        transform.pos.x = x * 2;
                        transform.pos.y = -y * 2;
                        switch (entity_index) {
                            case 1 :							// Player
                                Player firstPlayer = new Player(transform,new EntityInfo("you"));
                                firstPlayer.setEntityType(Entity.PLAYER);
                                entityList.put("you",firstPlayer);
                                camera.getPosition().set(transform.pos.mul(-scale, new Vector3f()));
                                break;
                            case 2:
                                if (mode == 0){
                                    Player secondPlayer = new Player(transform,new EntityInfo("bot"));
                                    secondPlayer.setEntityType(Entity.PLAYER);
                                    entityList.put("bot",secondPlayer);
                                }else if (mode == 1){
                                    Player secondPlayer = new Player(transform,new EntityInfo("bot"));
                                    secondPlayer.setEntityType(Entity.AIPLAYER);
                                    entityList.put("bot",secondPlayer);
                                }
                                //camera.getPosition().set(transform.pos.mul(-scale, new Vector3f()));
                                break;
                            default :
                                break;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Get the matrix of world
     *
     *
     * @return Matrix4f, the matrix of world
     * @author Weizhi
     * @version 1.0
     */
    public Matrix4f getWorldMatrix() {
        return world;
    }
    /**
     * Set the matrix of world
     *
     * @param world the matrix of world
     * @author Weizhi
     * @version 1.0
     */
    public void setWorldMatrix(Matrix4f world) {
        this.world = world;
    }
    /**
     * calculate the view of world
     *
     * @param window the display window of world
     * @author Weizhi
     * @version 1.0
     */
    public void calculateView(Window window) {
        viewX = (window.getWidth() / (scale * 2)) + 4;
        viewY = (window.getHeight() / (scale * 2)) + 4;
    }
    /**
     * Set the camera target of world
     *
     * @param camera the camera of world
     * @param entity the entity to set target
     * @author Weizhi
     * @version 1.0
     */
    public void setCameraTarget(Camera camera, Entity entity){
        camera.getPosition().lerp(entity.transform.pos.mul(-this.getScale(),new Vector3f()), 0.1f);
    }
    /**
     * correct the position of camera
     *
     * @param camera the camera of world
     * @param window the display window of world
     * @author Weizhi
     * @version 1.0
     */
    public void correctCamera(Camera camera, Window window){
        Vector3f pos = camera.getPosition();
        int w = -width * scale * 2;
        int h = height * scale * 2;

        if (pos.x > -(window.getWidth() / 2) + scale) {
            pos.x = -(window.getWidth() / 2) + scale;
        }
        if (pos.x < w + (window.getWidth() / 2) + scale) {
            pos.x = w + (window.getWidth() / 2) + scale;
        }

        if (pos.y < (window.getHeight() / 2) - scale) {
            pos.y = (window.getHeight() / 2) - scale;
        }
        if (pos.y > h - (window.getHeight() / 2) - scale) {
            pos.y = h - (window.getHeight() / 2) - scale;
        }
    }

    /**
     * render the tile of the world
     *
     * @param render render the tiles
     * @param shader the shader for rendering
     * @param camera the camera of world
     * @author Weizhi
     * @version 1.0
     */
    public void render(TileRenderer render, Shader shader, Camera camera){
//        for (int i= 0;i<height;i++){
//            for (int j =0;j<width;j++){
//                render.renderTile(tiles[j+i*width],j,-i,shader,world,camera);
//            }
//        }
        int posX = (int) camera.getPosition().x / (scale * 2);
        int posY = (int) camera.getPosition().y / (scale * 2);

        for (int i = 0; i < viewX; i++) {
            for (int j = 0; j < viewY; j++) {
                Tile t = getTile(i - posX - (viewX / 2) + 1, j + posY - (viewY / 2));
                if (t != null) render.renderTile(t, i - posX - (viewX / 2) + 1, -j - posY + (viewY / 2), shader, world, camera);
            }
        }
        for (String key:entityList.keySet()){
            entityList.get(key).render(shader, camera, this);
        }
        if (weapon != null){
            weapon.render(shader, camera, this);
        }
    }
    /**
     * update information of the world
     *
     * @param delta the loop time of process
     * @param camera the camera of the world
     * @param window the display window of world
     * @author Weizhi
     * @version 1.0
     */
    public void update(float delta,Window window,Camera camera){
        for (String key:entityList.keySet()){
            entityList.get(key).update(delta,window,camera,this);
        }
//        EntityController.updatePlayer(entityList,delta,window,camera,this);
        for (String key:entityList.keySet()){
            entityList.get(key).collideWithTiles(this);
//            for (int j = i + 1; j < entityList.size(); j++) {
//                entityList.get(key).collideWithEntity(entityList.get());
//            }
//            entityList.get(key).collideWithTiles(this);
        }
    }
    /**
     * Set the tile of world
     *
     * @param tile the tile of world
     * @param y the y position of the tile
     * @param x the x position of the tile
     * @author Weizhi
     * @version 1.0
     */
    public void setTile(Tile tile, int x,int y){
        tiles[x+y*width] = tile.getId();
        if (tile.isSolid()){
            bounding_boxes[x+y*width] = new AABB(new Vector2f(x*2,-y*2),new Vector2f(1,1));

        }else {
            bounding_boxes[x+y*width] = null;
        }
    }
    /**
     * Get the tile AABB of world
     *
     * @param x the x position tile
     * @param y the y position tile
     * @return AABB, the tile AABB box
     * @author Weizhi
     * @version 1.0
     */
    public AABB getTileBoundingBox(int x, int y){
        try{
            return bounding_boxes[x+y*width];
        }catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    /**
     * Get the tile of world
     *
     * @param x the x position of tile
     * @param y the y position of tile
     * @return Tile, the scale of world
     * @author Weizhi
     * @version 1.0
     */
    public Tile getTile(int x, int y){
        try{
            return Tile.tiles[tiles[x+y*width]];
        }catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

}
