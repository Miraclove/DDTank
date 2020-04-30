package Calculate;

import Config.GameInfo;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
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
    public  HashMap<String,Entity> entityList;
    private int height;
    private Matrix4f world;

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
     * <p>Init Data
     *
     * @param gameInfo the game information of the world
     * @author Weizhi
     * @version 1.0
     */
    public World(GameInfo gameInfo){
        try {
            BufferedImage tile_sheet = ImageIO.read(new File("./res/map/" + gameInfo.playground + "/worm.png"));
            BufferedImage entity_sheet = ImageIO.read(new File("./res/map/" + gameInfo.playground + "/entities.png"));

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
            float Px = 10;
            for (String key:gameInfo.gameEntity.keySet()){
                transform = new Transform();
                transform.pos.x = Px;
                Px = Px + 20;
                transform.pos.y = -30.0f;
                //set players
                Player player = new Player(transform,gameInfo.gameEntity.get(key));
                player.setEntityType(Entity.PLAYER);
                entityList.put(key,player);
                System.out.print("Y1: "+transform.pos.y);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Set the camera target of world
     *
     * @param entity the entity to set target
     * @author Weizhi
     * @version 1.0
     */
    public void setCameraTarget(Entity entity){
        //TODO
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
