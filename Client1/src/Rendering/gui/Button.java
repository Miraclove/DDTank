package Rendering.gui;

import Rendering.assets.Assets;
import Rendering.collision.AABB;
import Rendering.collision.Collision;
import Rendering.engine.Input;
import Rendering.render.Camera;
import Rendering.render.Shader;
import Rendering.render.TileSheet;
import org.joml.Matrix4f;
import org.joml.Vector2f;

/**
 * Class {@code Button} button for in game GUI
 *
 *
 * @author Weizhi
 * @since 15/01/2020
 */
public class Button implements Component {
    private Shader shader;
    private TileSheet sheet;
    /** the state of button is idle =  {@value} */
    public static final int STATE_IDLE = 0;
    /** the state of button is selected =  {@value} */
    public static final int STATE_SELECTED = 1;
    /** the state of button is clicked =  {@value} */
    public static final int STATE_CLICKED = 2;

    private boolean isVisible = false;

    private AABB boundingBox;
    /**
     * Get selected state of button
     *
     * @return int, idle = 0, selected = 1, clicked = 2
     * @author Weizhi
     * @version 1.0
     */
    public int getSelectedState() {
        return selectedState;
    }

    private int selectedState;

    private static Matrix4f transform = new Matrix4f();
    /**
     * Constructor for Button
     *
     * <p>Init Data
     *
     * @param position the position of Button
     * @param scale the scale of Button
     * @author Weizhi
     * @version 1.0
     */
    public Button(Vector2f position, Vector2f scale) {
        sheet = new TileSheet("gui.png", 9);
        shader = new Shader("gui");
        this.boundingBox = new AABB(position, scale);
        selectedState = STATE_IDLE;
    }
    /**
     * update information for the Button
     *
     * @param input the input interface controller
     * @author Weizhi
     * @version 1.0
     */
    public void update(Input input) {
        if (isVisible){
            Collision data = boundingBox.getCollision(input.getMousePosition());
            if (data.isIntersecting) {
                selectedState = STATE_SELECTED;
                if (input.isMouseButtonDown(0)) {
                    selectedState = STATE_CLICKED;
                }
            }
            else selectedState = STATE_IDLE;
        }

    }
    /**
     * Render graphics for the Button
     *
     * @param camera the camera of the Button
     * @author Weizhi
     * @version 1.0
     */
    public void render(Camera camera) {
        if (isVisible){
            shader.bind();
            Vector2f position = boundingBox.getCenter(),
                    scale = boundingBox.getHalfExtent();

            transform.identity().translate(position.x, position.y, 0).scale(scale.x, scale.y, 1); // Middle/Fill

            shader.setUniform("projection", camera.getProjection().mul(transform));
            switch (selectedState) {
                case STATE_SELECTED :
                    sheet.bindTile(shader, 4, 1);
                    break;
                case STATE_CLICKED :
                    sheet.bindTile(shader, 7, 1);
                    break;
                default :
                    sheet.bindTile(shader, 1, 1);
                    break;
            }
            Assets.getModel().render();

            renderSides(position, scale, camera, sheet, shader);
            renderCorners(position, scale, camera, sheet, shader);
        }

    }
    private void renderSides(Vector2f position, Vector2f scale, Camera camera, TileSheet sheet, Shader shader) {
        transform.identity().translate(position.x, position.y + scale.y - 16, 0).scale(scale.x, 16, 1); // Top
        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED :
                sheet.bindTile(shader, 4, 0);
                break;
            case STATE_CLICKED :
                sheet.bindTile(shader, 7, 0);
                break;
            default :
                sheet.bindTile(shader, 1, 0);
                break;
        }
        Assets.getModel().render();

        transform.identity().translate(position.x, position.y - scale.y + 16, 0).scale(scale.x, 16, 1); // Bottom
        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED :
                sheet.bindTile(shader, 4, 2);
                break;
            case STATE_CLICKED :
                sheet.bindTile(shader, 7, 2);
                break;
            default :
                sheet.bindTile(shader, 1, 2);
                break;
        }
        Assets.getModel().render();

        transform.identity().translate(position.x - scale.x + 16, position.y, 0).scale(16, scale.y, 1); // Left

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED :
                sheet.bindTile(shader, 3, 1);
                break;
            case STATE_CLICKED :
                sheet.bindTile(shader, 6, 1);
                break;
            default :
                sheet.bindTile(shader, 0, 1);
                break;
        }
        Assets.getModel().render();

        transform.identity().translate(position.x + scale.x - 16, position.y, 0).scale(16, scale.y, 1); // Right

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED :
                sheet.bindTile(shader, 5, 1);
                break;
            case STATE_CLICKED :
                sheet.bindTile(shader, 8, 1);
                break;
            default :
                sheet.bindTile(shader, 2, 1);
                break;
        }
        Assets.getModel().render();
    }

    private void renderCorners(Vector2f position, Vector2f scale, Camera camera, TileSheet sheet, Shader shader) {
        transform.identity().translate(position.x - scale.x + 16, position.y + scale.y - 16, 0).scale(16, 16, 1); // Top
        // Left


        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED :
                sheet.bindTile(shader, 3, 0);
                break;
            case STATE_CLICKED :
                sheet.bindTile(shader, 6, 0);
                break;
            default :
                sheet.bindTile(shader, 0, 0);
                break;
        }
        Assets.getModel().render();


        transform.identity().translate(position.x + scale.x - 16, position.y + scale.y - 16, 0).scale(16, 16, 1); // Top
        // Right


        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED :
                sheet.bindTile(shader, 5, 0);
                break;
            case STATE_CLICKED :
                sheet.bindTile(shader, 8, 0);
                break;
            default :
                sheet.bindTile(shader, 2, 0);
                break;
        }
        Assets.getModel().render();

        transform.identity().translate(position.x - scale.x + 16, position.y - scale.y + 16, 0).scale(16, 16, 1); // Bottom
        // Left


        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED :
                sheet.bindTile(shader, 3, 2);
                break;
            case STATE_CLICKED :
                sheet.bindTile(shader, 6, 2);
                break;
            default :
                sheet.bindTile(shader, 0, 2);
                break;
        }
        Assets.getModel().render();

        transform.identity().translate(position.x + scale.x - 16, position.y - scale.y + 16, 0).scale(16, 16, 1); // Bottom
        // Right


        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED :
                sheet.bindTile(shader, 5, 2);
                break;
            case STATE_CLICKED :
                sheet.bindTile(shader, 8, 2);
                break;
            default :
                sheet.bindTile(shader, 2, 2);
                break;
        }
        Assets.getModel().render();
    }
    /**
     * get the visible condition of button
     *
     * @return Boolean, true : visible, false : invisible
     * @author Weizhi
     * @version 1.0
     */
    public boolean isVisible() {
        return isVisible;
    }
    /**
     * Set the visible condition of button
     *
     * @param visible true : visible, false : invisible
     * @author Weizhi
     * @version 1.0
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}

