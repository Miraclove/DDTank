package Rendering.render;

import org.joml.Matrix4f;
/**
 * Class {@code TileSheet} render tile sheet
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class TileSheet {
    private Texture texture;

    private Matrix4f scale;
    private Matrix4f translation;

    private int amountOfTiles;
    /**
     * Constructor for TileSheet
     *
     * <p>Init Data
     *
     * @param texture set the file path of the tile
     * @param amountOfTiles the amount of the tiles
     * @author Weizhi
     * @version 1.0
     */
    public TileSheet(String texture, int amountOfTiles) {
        this.texture = new Texture("./res/sheets/" + texture);

        scale = new Matrix4f().scale(1.0f / (float)amountOfTiles);
        translation = new Matrix4f();

        this.amountOfTiles = amountOfTiles;
    }
    /**
     * bind the tile for render
     *
     * @param shader the shader to render
     * @param x the x block to render
     * @param y the y block to render
     * @author Weizhi
     * @version 1.0
     */
    public void bindTile(Shader shader, int x, int y) {
        scale.translate(x, y, 0, translation);

        shader.setUniform("sampler", 0);
        shader.setUniform("texModifier", translation);
        texture.bind(0);
    }
    /**
     * bind the tile for render
     *
     * @param shader the shader to render
     * @author Weizhi
     * @version 1.0
     */
    public void bindTile(Shader shader, int tile) {
        int x = tile % amountOfTiles;
        int y = tile / amountOfTiles;
        bindTile(shader, x, y);
    }
}
