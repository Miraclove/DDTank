package Logic;

import Config.InstantInfo;
import org.lwjgl.system.jni.JNINativeMethod;
import sun.awt.image.BufferedImageGraphicsConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
/**
 * Class {@code ImageProcess} Works as a Tool class to process graphics
 *
 * <p> contains tools methods as static methods
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class ImageProcess {
    /**
     * Resize the {@code img} to new width {@code newW} and new height {@code newH}
     *
     * @param img the input image to resize
     * @param newH new height of the image
     * @param newW new width of the image
     * @return {@link BufferedImage}  the resized image
     * @author Weizhi
     * @version 1.0
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
//        BufferedImageGraphicsConfig config = BufferedImageGraphicsConfig.getConfig(dimg);
//        dimg =config.createCompatibleImage(newW, newH, Transparency.TRANSLUCENT);
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
    /**
     * Out put the image as test.png
     *
     * @param image the input image to store as file
     * @author Weizhi
     * @version 1.0
     */
    public static void outputImage(BufferedImage image){
        try{
            OutputStream os = new FileOutputStream("test.png");
            ImageIO.write(image, "png", os);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Merge two images into a single image
     *
     * <p>The width of the image should be the same while {@code isHorizontal} is true
     * <p> The height of the image should be the same while {@code isHorizontal} is false
     *
     *
     * @param img1 the first input image
     * @param img2 the second input image
     * @param isHorizontal set the method to merge, true is up and down, false is left and right
     * @return {@link BufferedImage}  the merged image
     * @author Weizhi
     * @version 1.0
     */
    public static BufferedImage mergeImage(BufferedImage img1, BufferedImage img2, boolean isHorizontal){
        try{
            int w1 = img1.getWidth();
            int h1 = img1.getHeight();
            int w2 = img2.getWidth();
            int h2 = img2.getHeight();

            // 从图片中读取RGB
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
            int[] ImageArrayTwo = new int[w2 * h2];
            ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);

            // 生成新图片
            BufferedImage DestImage = null;

            if (isHorizontal) { // 水平方向合并
                DestImage = new BufferedImage(w1+w2, h1, img1.getType());
                DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
                DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
            } else { // 垂直方向合并
                DestImage = new BufferedImage(w1, h1 + h2, img1.getType());
                DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
                DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // 设置下半部分的RGB
            }

            return DestImage;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Fold two images into a single image
     *
     * @param buffImg the input image
     * @param iconPath the file path of the image which is going to place at the input image
     * @param x the position x of the icon on input image
     * @param y the position y of the icon on input image
     * @return {@link BufferedImage}  the folded image
     * @author Weizhi
     * @version 1.0
     */
    public static BufferedImage addImageByIcon(BufferedImage buffImg,String iconPath, int x, int y){
        try {
//            Image srcImg = ImageIO.read(new File(srcImgPath));
//            srcImg = srcImg.getScaledInstance(320,320,0);
//            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null),
//                    BufferedImage.TYPE_INT_RGB);
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(buffImg.getScaledInstance(buffImg.getWidth(null), buffImg.getHeight(null), Image.SCALE_SMOOTH), 0,
                    0, null);
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
            // 6、水印图片的位置
            g.drawImage(img, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();
            // 8、生成图片
            return buffImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * get the {@link BufferedImage} from the file
     *
     * @param fileUrl the file path of the image
     * @return {@link BufferedImage}  the image from file
     * @author Weizhi
     * @version 1.0
     */
    public static BufferedImage getBufferedImage(String fileUrl) {
        try{
            File f = new File(fileUrl);
            return ImageIO.read(f);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    /**
     * get the {@link BufferedImage} from the file
     *
     * @param srcImgPath the file path of the image
     * @return {@link BufferedImage}  the image from file
     * @author Weizhi
     * @version 1.0
     */
    public static BufferedImage getBufferedImageFromFile(String srcImgPath){

        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            srcImg = srcImg.getScaledInstance(320,320,0);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            return buffImg;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    /**
     * get the {@link BufferedImage} from the text and font
     *
     * @param font the font image setting
     * @param str the text going to render
     * @param height the height of the image
     * @param width the width of the image
     * @return {@link BufferedImage}  the image from the text and font
     * @author Weizhi
     * @version 1.0
     */
    public static BufferedImage createFontImage(String str, Font font, Integer width, Integer height){
        try{
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            // Create a graphics context and turn antialiasing on
            Graphics2D graphics = image.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Comic Sans FTW
            graphics.setFont(font);
            graphics.setColor(Color.RED);
            graphics.drawString(str, 1, font.getSize()/2+height/2);

            // Dispose of the context
            graphics.dispose();
            return image;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    /**
     * get the {@link BufferedImage} mini map
     *
     * @param iconPath file path of the entity image
     * @param srcImgPath file path of the playground map image
     * @param instantInfo the entities rendering info
     * @return {@link BufferedImage}  the rendering mini map image
     * @author Weizhi
     * @version 1.0
     */
    public static BufferedImage markImageByIcon(String iconPath, String srcImgPath, InstantInfo instantInfo) {
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            srcImg = srcImg.getScaledInstance(320,320,0);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0,
                    0, null);
            // 3、设置水印旋转
//            if (null != degree) {
//                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
//            }
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
            g.drawImage(img, (int)10, (int)40, null);
            g.drawImage(img, (int)100, (int)40, null);
            g.drawImage(img, (int)200, (int)40, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();
            // 8、生成图片
            return buffImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
