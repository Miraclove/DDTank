package Calculate;
/**
 * Class {@code Tile} the tile in the playground
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Tile {
    private byte id;

    private String texture;
    /** the tile array */
    public static Tile tiles[] = new Tile[255];//change the tile number to add more
    /** the tile counter */
    public static byte not = 0;
    /** the tile for background */
    public static final Tile test_tile = new Tile("grass");
    /** the tile for solid ground */
    public static final Tile test2 = new Tile("checker").setSolid();
    private boolean solid;
    /**
     * Constructor for Tile
     *
     * <p>Init Data
     * @param texture the texture of tile
     * @author Weizhi
     * @version 1.0
     */
    public Tile(String texture){
        this.id = not;
        not++;
        this.texture = texture;
        this.solid = false;
        if (tiles[id] != null){
            throw new IllegalStateException("Tiles at: ["+id+"] is already being used");
        }
        tiles[id] = this;
    }
    /**
     * Get the id of tiles
     *
     * @return byte, the id of tiles
     * @author Weizhi
     * @version 1.0
     */
    public byte getId() {
        return id;
    }
    /**
     * Set the id of tiles
     *
     * @param id the id of tiles
     * @author Weizhi
     * @version 1.0
     */
    public void setId(byte id) {
        this.id = id;
    }
    /**
     * Get the texture of tiles
     *
     * @return String, the texture of tiles
     * @author Weizhi
     * @version 1.0
     */
    public String getTexture() {
        return texture;
    }
    /**
     * Set the texture of tiles
     *
     * @param texture the id of tiles
     * @author Weizhi
     * @version 1.0
     */
    public void setTexture(String texture) {
        this.texture = texture;
    }
    /**
     * Set the solid condition of tiles
     *
     * @return get soild Tile
     * @author Weizhi
     * @version 1.0
     */
    public Tile setSolid(){
        this.solid = true;
        return this;
    }
    /**
     * Get the solid condition of tiles
     *
     * @return boolean, the solid condition of tiles
     * @author Weizhi
     * @version 1.0
     */
    public boolean isSolid(){
        return solid;
    }


}
