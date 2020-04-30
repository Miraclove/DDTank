package Rendering.assets;

import Rendering.render.Model;
/**
 * Class {@code Assets} Assets for Rendering
 *
 * <p> Render
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Assets {
    private static Model model;

    /**
     * Get the Model of th Assets
     *
     *
     * @return {@link Model} Model of th Assets
     * @author Weizhi
     * @version 1.0
     */
    public static Model getModel() { return model; }
    /**
     * Init the Asset by VBO
     *
     * @author Weizhi
     * @version 1.0
     */
    public static void initAsset() {
        float[] vertices = new float[] {
                -1f, 1f, 0, //TOP LEFT     0
                1f, 1f, 0,  //TOP RIGHT    1
                1f, -1f, 0, //BOTTOM RIGHT 2
                -1f, -1f, 0,//BOTTOM LEFT  3
        };

        float[] texture = new float[] {
                0,0,
                1,0,
                1,1,
                0,1,
        };

        int[] indices = new int[] {
                0,1,2,
                2,3,0
        };

        model = new Model(vertices, texture, indices);
    }
    /**
     * Delete the model of the Assets
     *
     * @author Weizhi
     * @version 1.0
     */
    public static void deleteAsset() {
        model = null;
    }
}

