package Rendering.world;

import Rendering.render.Camera;
import Rendering.render.Model;
import Rendering.render.Shader;
import Rendering.render.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;
/**
 * Class {@code TileRenderer} the renderer to render tiles
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class TileRenderer {
    private HashMap<String, Texture> tile_textures;
    private Model model;
    /**
     * Constructor for TileRenderer
     *
     * <p>Init Data
     *
     * @author Weizhi
     * @version 1.0
     */
    public TileRenderer(){
        tile_textures = new HashMap<String,Texture>();
        float[] vertices = new float[]{
                -1f,1f,0,//top left 0
                1f,1f,0,//top right 1
                1f,-1f,0,//bottom right 2
                -1f,-1f,0,//bottom left 3
        };

        float[] texture = new float[]{
                0,0,
                1,0,
                1,1,
                0,1
        };
        int[] indices = new int[]{
                0,1,2,
                2,3,0
        };
        model = new Model(vertices,texture,indices);
        for (int i = 0;i<Tile.tiles.length;i++){
            if (Tile.tiles[i]!=null){
                if (!tile_textures.containsKey(Tile.tiles[i].getTexture())){
                    String tex = Tile.tiles[i].getTexture();
                    tile_textures.put(tex,new Texture("./res/player/"+tex+".png"));
                }
            }

        }
    }
    /**
     * render tiles in th playground
     *
     * @param x the x position of tile
     * @param y the y position of tile
     * @param shader the shader of the game
     * @param camera the camera of the game
     * @param world the world of the game
     * @param tile the tile for render
     * @author Weizhi
     * @version 1.0
     */
    public void renderTile(Tile tile, int x, int y, Shader shader, Matrix4f world, Camera camera){
        shader.bind();
        if (tile_textures.containsKey(tile.getTexture())) tile_textures.get(tile.getTexture()).bind(0);

        Matrix4f tile_pos = new Matrix4f().translate(new Vector3f(x * 2, y * 2, 0));
        Matrix4f target = new Matrix4f();

        camera.getProjection().mul(world, target);
        target.mul(tile_pos);

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);

        model.render();
    }
}
