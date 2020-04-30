package UI;


import Rendering.test.LocalGame;
import Rendering.test.Main;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class Test{

    public static void generateImage(OutputStream os, double arc){

        //
        try{

            BufferedImage bg = ImageIO.read(new File("./image/bg1.png"));//仪表盘背景
            BufferedImage pointer = ImageIO.read(new File("./image/pointer.png"));//仪表盘指针
            BufferedImage canvas = new BufferedImage(bg.getWidth(), bg.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = (Graphics2D)canvas.createGraphics();//Graphics2D用于画图

            double x = Math.cos(arc)*pointer.getWidth();
            double y = Math.sin(arc)*pointer.getWidth();

            int pw = pointer.getWidth();
            int ph = pointer.getHeight();

            g.drawImage(bg, 0, 0, bg.getWidth(), bg.getHeight(), null);

            g.rotate(arc, bg.getWidth()/2, bg.getHeight()/2);//旋转
            g.drawImage(pointer, bg.getWidth()/2-100, bg.getHeight()/2, 20, bg.getHeight()/2, null);

//            g.rotate(-arc, radius, radius);
//            String ps = String.valueOf(percent);
//            if(ps.length() > 5) ps = ps.substring(0, 5);//截断字符串
//            FontRenderContext context = g.getFontRenderContext();
//            Font font = new Font("Serif", Font.BOLD, 20);
//            Rectangle2D bounds = font.getStringBounds(ps, context);//获取字符串边界信息，如长、宽等
//            g.setColor(Color.WHITE);
//            g.fillRect((int)(radius - bounds.getWidth() / 2) - 1, (int)(radius - bounds.getHeight() / 2) - 1, (int)bounds.getWidth() + 1, (int)bounds.getHeight() + 1);
//            g.setColor(Color.BLACK);
//            g.setFont(font);//字体设置
//            g.drawString(ps, (int)(radius - bounds.getWidth() / 2), (int)(radius - bounds.getHeight() / 2 - bounds.getY()));//显示比率

            ImageIO.write(canvas, "png", os);//生成图片到outputstream
        }
        catch(Exception ex){

            System.out.println("generate image error!");
            System.out.println(ex);
        }
    }

    public static void main(String[] args) throws Exception{
//        System.out.println(Math.cos(Math.toRadians(60)));
//        FileOutputStream fos = new FileOutputStream(new File("./image/" + "0" + ".png"));
//        generateImage(fos,Math.toRadians(30));
//        fos.close();
        new LocalGame(1);
    }
}