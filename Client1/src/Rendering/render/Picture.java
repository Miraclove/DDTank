package Rendering.render;

import org.joml.Matrix4f;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * Class {@code EntityInfo} render texture as picture
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Picture {
    private Texture texture;

    private Matrix4f scale;
    private Matrix4f translation;

    /**
     * Constructor for Picture
     *
     * <p>Init Data
     *
     * @param bufferedImage the buffer of the image
     * @author Weizhi
     * @version 1.0
     */
    public Picture(BufferedImage bufferedImage) {
        this.texture = new Texture(bufferedImage);
        scale = new Matrix4f().scale(1.0f);
        translation = new Matrix4f();
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
    public void bindTile(Shader shader) {
        int x = 0;
        int y = 0;
        bindTile(shader, x, y);
    }
}
