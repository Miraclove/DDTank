package Rendering.render;

import Config.EntityInfo;
import Logic.GameController;
import Logic.ImageProcess;
import Rendering.engine.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * Class {@code Animation} the animation of the entity
 *
 * @author Weizhi
 * @since 05/02/2020
 */
public class Animation {
    private Texture[] frames;
    private int pointer;

    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double fps;
    /**
     * Constructor for Animation
     *
     * <p>Init Data
     *
     * @param amount the amount of frames of the animation
     * @param entityInfo the entityinfo
     * @param filename the file path of animation frame
     * @param fps the fps of the animation
     * @author Weizhi
     * @version 1.0
     */
    public Animation(int amount, int fps, String filename,EntityInfo entityInfo,Boolean isWeapon){
        this.pointer = 0;
        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = Timer.getTime();
        this.fps = 1.0/(double) fps;
        this.frames = new Texture[amount];
        for (int i = 0; i<amount;i++){
            if (isWeapon){
                BufferedImage title = ImageProcess.createFontImage(entityInfo.getHealth()+"",new Font("Comic Sans MS", Font.BOLD, 20),50,50);
                BufferedImage player = ImageProcess.getBufferedImage("./res/animation/"+filename+"/"+i+".png");
                this.frames[i] = new Texture(ImageProcess.mergeImage(
                        ImageProcess.resize(title,50,50),
                        ImageProcess.resize(player,50,50),false));
            }else {
                BufferedImage title = ImageProcess.createFontImage(entityInfo.getUserName(),new Font("Comic Sans MS", Font.BOLD, 15),50,25);
                BufferedImage health = ImageProcess.createFontImage(entityInfo.getHealth()+"",new Font("Comic Sans MS", Font.BOLD, 15),50,25);
                BufferedImage player = ImageProcess.getBufferedImage("./res/animation/"+filename+"/"+i+".png");
                title = ImageProcess.mergeImage(
                        ImageProcess.resize(title,50,25),
                        ImageProcess.resize(health,50,25),false);
                title = ImageProcess.mergeImage(
                        ImageProcess.resize(title,50,50),
                        ImageProcess.resize(player,50,50),false);
                this.frames[i] = new Texture(title);
            }

//            this.frames[i] = new Texture("./res/animation/"+filename+"/"+i+".png");


        }
    }
    /**
     * bind the animation to shader
     *
     * @author Weizhi
     * @version 1.0
     */
    public void bind(){
        bind(0);
    }
    /**
     * bind the animation to shader
     *
     * @param sampler sampler of the shader
     * @author Weizhi
     * @version 1.0
     */
    public void bind(int sampler){
        this.currentTime = Timer.getTime();
        this.elapsedTime += currentTime - lastTime;

        if (elapsedTime >= fps){
            elapsedTime -= fps;
            pointer++;
        }
        if (pointer >= frames.length){
            pointer = 0;
        }
        this.lastTime = currentTime;
        frames[pointer].bind(sampler);
    }
}
