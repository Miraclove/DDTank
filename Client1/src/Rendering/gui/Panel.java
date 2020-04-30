package Rendering.gui;

import Rendering.assets.Assets;
import Rendering.engine.Input;
import Rendering.render.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * Class {@code Panel} Panel for display text and image
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */

public class Panel implements Component{
    private Shader shader;
    private Vector2f position;
    /** the scale of the Panel */
    public Vector2f scale;
    private Vector4f color;
    private TileSheet sheet;
    private Text text;
    private Picture picture;
    private boolean isVisible = false;
    private Matrix4f transform = new Matrix4f();
    /**
     * Constructor for Panel
     *
     * <p>Init Data
     * @param position the position of Panel
     * @param scale the scale of Panel
     * @author Weizhi
     * @version 1.0
     */
    public Panel(Vector2f position, Vector2f scale){
        shader = new Shader("control");
        color = new Vector4f(0,0,0,0.8f);
        this.position = position;
        this.scale = scale;
    }

    /**
     * Set the picture of panel
     *
     * @param filename the file path of the image
     * @author Weizhi
     * @version 1.0
     */
    public void setPicture(String filename){
        sheet = new TileSheet(filename, 1);
    }
    /**
     * Set the picture of panel
     *
     * @param bufferedImage buffer of the image
     * @author Weizhi
     * @version 1.0
     */
    public void setPicture(BufferedImage bufferedImage){
        picture = new Picture(bufferedImage);
    }
    /**
     * Set the text of panel
     *
     * @param  str the text
     * @param font the font of the text to render
     * @author Weizhi
     * @version 1.0
     */
    public void setText(String str, Font font){
        text = new Text(str, font, (int)scale.x+20, (int)scale.y);
    }
    /**
     * Render graphics for the Panel
     *
     * @param camera the camera of the Panel
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void render(Camera camera) {
        if (isVisible){
            transform.identity().translate(position.x,position.y,0).scale(scale.x,scale.y,1);
            shader.bind();
            shader.setUniform("projection",camera.getProjection().mul(transform));
            shader.setUniform("color",color);
            if (sheet != null){
                sheet.bindTile(shader,1);
            }
            if (text!=null){
                text.bindTile(shader);
            }
            if (picture!=null){
                picture.bindTile(shader);
            }
            Assets.getModel().render();
        }
    }
    /**
     * update information for the Panel
     *
     * @param input the input interface controller
     * @author Weizhi
     * @version 1.0
     */
    @Override
    public void update(Input input) {
        if (isVisible){
            //        if (input.isKeyDown(GLFW_KEY_SPACE)){
//            if(scale.x<200f){
//                scale.x += 0.5f;
//            }
//        }else{
//            if (scale.x>100f){
//                scale.x -= 0.5f;
//            }
//        }
        }

    }
    /**
     * Get the transform
     *
     *
     * @return Matrix4f, transform of panel
     * @author Weizhi
     * @version 1.0
     */
    public Matrix4f getTransform() {
        return transform;
    }

    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }
    /**
     * Get the position of panel
     *
     *
     * @return Vector2f, the position of panel
     * @author Weizhi
     * @version 1.0
     */
    public Vector2f getPosition() {
        return position;
    }
    /**
     * Set the position of panel
     *
     * @param position the position of the panel
     * @author Weizhi
     * @version 1.0
     */
    public void setPosition(Vector2f position) {
        this.position = position;
    }
    /**
     * Get the scale of panel
     *
     *
     * @return Vector2f, the scale of panel
     * @author Weizhi
     * @version 1.0
     */
    public Vector2f getScale() {
        return scale;
    }
    /**
     * Get the color of panel
     *
     *
     * @return Vector4f, the color config of panel
     * @author Weizhi
     * @version 1.0
     */
    public Vector4f getColor() {
        return color;
    }
    /**
     * Set the color of panel
     *
     * @param color the condig of color
     * @author Weizhi
     * @version 1.0
     */
    public void setColor(Vector4f color){
        this.color = color;
    }
    /**
     * Set the scale of panel
     *
     * @param scale the scale of panel
     * @author Weizhi
     * @version 1.0
     */
    public void setScale(Vector2f scale){
        this.scale = scale;
    }
    /**
     * Get the visible state of panel
     *
     *
     * @return boolean, true : visible, false : invisible
     * @author Weizhi
     * @version 1.0
     */
    public boolean isVisible() {
        return isVisible;
    }
    /**
     * Set the visible state of panel
     *
     * @param visible the visible state,true : visible, false : invisible
     * @author Weizhi
     * @version 1.0
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
