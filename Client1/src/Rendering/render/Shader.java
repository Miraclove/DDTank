package Rendering.render;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;
/**
 * Class {@code Shader} the shader to render graphics
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Shader {
    private int program;
    private int vs;
    private int fs;
    /**
     * Constructor for Shader
     *
     * <p>Init Data
     *
     * @param filename the file path of config file of shader
     * @author Weizhi
     * @version 1.0
     */
    public Shader(String filename){
        program = glCreateProgram();

        vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs,readFile(filename+".vs"));
        glCompileShader(vs);
        if(glGetShaderi(vs,GL_COMPILE_STATUS)!=1){
            System.err.println(glGetShaderInfoLog(vs));
            System.exit(1);
        }

        fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs,readFile(filename+".fs"));
        glCompileShader(fs);
        if(glGetShaderi(fs,GL_COMPILE_STATUS)!=1){
            System.err.println(glGetShaderInfoLog(fs));
            System.exit(1);
        }

        glAttachShader(program,vs);
        glAttachShader(program,fs);

        glBindAttribLocation(program,0,"vertices");
        glBindAttribLocation(program,1,"textures");

        glLinkProgram(program);
        if (glGetProgrami(program,GL_LINK_STATUS)!=1){
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);
        if (glGetProgrami(program,GL_VALIDATE_STATUS)!=1){
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }




    }
    /**
     * Set the uniform of shader
     *
     * @param name the name of uniform
     * @param value the value of shader
     * @author Weizhi
     * @version 1.0
     */
    public void setUniform(String name, int value){
        int location = glGetUniformLocation(program,name);
        if (location!=-1){
            glUniform1i(location,value);
        }

    }
    /**
     * Set the uniform of shader
     *
     * @param uniformName the name of uniform
     * @param value the value of shader
     * @author Weizhi
     * @version 1.0
     */
    public void setUniform(String uniformName, Vector4f value){
        int location = glGetUniformLocation(program,uniformName);
        if (location!=-1){
            glUniform4f(location,value.x,value.y,value.z,value.w);
        }
    }
    /**
     * Set the uniform of shader
     *
     * @param name the name of uniform
     * @param value the value of shader
     * @author Weizhi
     * @version 1.0
     */
    public void setUniform(String name, Matrix4f value){
        int location = glGetUniformLocation(program,name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);

        if (location!=-1){
            glUniformMatrix4fv(location,false,buffer);
        }

    }
    /**
     * bind the tile for render
     *
     * @author Weizhi
     * @version 1.0
     */
    public void bind(){
        glUseProgram(program);
    }

    private String readFile(String filename){
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(new File("./res/shaders/"+filename)));
            String line;
            while((line = br.readLine())!=null){
                string.append(line);
                string.append("\n");
            }
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return string.toString();
    }
}
