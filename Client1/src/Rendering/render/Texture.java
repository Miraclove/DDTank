package Rendering.render;

import Logic.ImageProcess;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
/**
 * Class {@code Text} render texture
 *
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Texture {
    private int id;
    private int width;
    private int height;
    /**
     * Constructor for Texture
     *
     * <p>Init Data
     *
     * @param filename the file of the image
     * @author Weizhi
     * @version 1.0
     */
    public Texture(String filename){
        BufferedImage bi;
        try{
            bi = ImageIO.read(new File(filename));
            width = bi.getWidth();
            height = bi.getHeight();

            int[] pixels_raw = new int[width*height*4];
            pixels_raw = bi.getRGB(0,0,width,height,null,0,width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
            for (int i = 0; i<height;i++){
                for (int j = 0; j < width;j++){
                    int pixel = pixels_raw[i*width+j];
                    pixels.put((byte)((pixel >> 16) & 0xFF));//RED
                    pixels.put((byte)((pixel >> 8) & 0xFF));//GREEN
                    pixels.put((byte)((pixel ) & 0xFF));//BLUE
                    pixels.put((byte)((pixel >> 24) & 0xFF));//ALPHA
                }
            }
            pixels.flip();

            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D,id);

            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);//sharp texture
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);//sharp texture

            glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels);//GL_UNSIGNED_BYTE: RGB GL_BYTE: RGBA

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Constructor for Texture
     *
     * <p>Init Data
     *
     * @param str the text to render
     * @param font the font of text
     * @param height the height of texture
     * @param width the width of texture
     * @author Weizhi
     * @version 1.0
     */
    public Texture(String str, Font font,Integer width, Integer height){
        BufferedImage bi;
        try{
            bi = ImageProcess.createFontImage(str,font,width,height);
            width = bi.getWidth();
            height = bi.getHeight();

            int[] pixels_raw = new int[width*height*4];
            pixels_raw = bi.getRGB(0,0,width,height,null,0,width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
            for (int i = 0; i<height;i++){
                for (int j = 0; j < width;j++){
                    int pixel = pixels_raw[i*width+j];
                    pixels.put((byte)((pixel >> 16) & 0xFF));//RED
                    pixels.put((byte)((pixel >> 8) & 0xFF));//GREEN
                    pixels.put((byte)((pixel ) & 0xFF));//BLUE
                    pixels.put((byte)((pixel >> 24) & 0xFF));//ALPHA
                }
            }
            pixels.flip();
            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D,id);
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);//sharp texture
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);//sharp texture
            glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels);//GL_UNSIGNED_BYTE: RGB GL_BYTE: RGBA
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Constructor for Texture
     *
     * <p>Init Data
     *
     * @param bi the buffer of the image
     * @author Weizhi
     * @version 1.0
     */
    public Texture(BufferedImage bi){
        try{
            width = bi.getWidth();
            height = bi.getHeight();
            int[] pixels_raw = new int[width*height*4];
            pixels_raw = bi.getRGB(0,0,width,height,null,0,width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
            for (int i = 0; i<height;i++){
                for (int j = 0; j < width;j++){
                    int pixel = pixels_raw[i*width+j];
                    pixels.put((byte)((pixel >> 16) & 0xFF));//RED
                    pixels.put((byte)((pixel >> 8) & 0xFF));//GREEN
                    pixels.put((byte)((pixel ) & 0xFF));//BLUE
                    pixels.put((byte)((pixel >> 24) & 0xFF));//ALPHA
                }
            }
            pixels.flip();
            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D,id);
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);//sharp texture
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);//sharp texture
            glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels);//GL_UNSIGNED_BYTE: RGB GL_BYTE: RGBA
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    /**
     * bind with sampler
     *
     * @param int the sampler id
     * @author Weizhi
     * @version 1.0
     */
    public void bind(int sampler){
        if (sampler >= 0 && sampler <=31){
            glActiveTexture(GL_TEXTURE0 + sampler);

            glBindTexture(GL_TEXTURE_2D,id);
        }

    }
}
