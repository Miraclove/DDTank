package Rendering.render;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
/**
 * Class {@code Model} the model for render and set for OpenGL and LWJGL
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Model {
    private int draw_count;
    private int v_id;
    private int t_id;

    private int i_id;
    /**
     * Constructor for Model
     *
     * <p>Init Data
     *
     * @param vertices the vertices of model
     * @param indices the indices of model
     * @param tex_coords the texture of the model
     * @author Weizhi
     * @version 1.0
     */
    public Model(float[] vertices,float[] tex_coords,int[] indices){
        draw_count = indices.length;


        v_id = glGenBuffers();//shape
        glBindBuffer(GL_ARRAY_BUFFER,v_id);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(vertices),GL_STATIC_DRAW);//static: passing the data once, dynamic: going to be changing

        t_id = glGenBuffers();//texture
        glBindBuffer(GL_ARRAY_BUFFER,t_id);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(tex_coords),GL_STATIC_DRAW);

        i_id = glGenBuffers();//indices
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,i_id);


        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();

        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER,0); // unbind glarraybuffer
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
    }
    /**
     * render the model
     *
     * @author Weizhi
     * @version 1.0
     */
    public void render(){
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER,v_id);
        glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);

        glBindBuffer(GL_ARRAY_BUFFER,t_id);
        glVertexAttribPointer(1,2,GL_FLOAT,false,0,0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,i_id);
        glDrawElements(GL_TRIANGLES,draw_count,GL_UNSIGNED_INT,0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);



    }

    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
