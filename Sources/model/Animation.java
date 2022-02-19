package model;

import engine.TextureFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Animation {

    private int numFrame;
    private int delai;
    private int time;
    private boolean occupe;
    private Animations currentAnim;

    public Animation() {
        numFrame = 0;
        occupe = false;
        delai = 100;
        time = 0;
    }

    public void nextFrame() {
        numFrame++;
        if(numFrame >= sprite().size()){
            numFrame = 0;
        }
    }

    public abstract void animer();

    public int getNumFrame() {
        return numFrame;
    }

    public boolean estOccupe() {
        return occupe;
    }

    public void setNumFrame(int numFrame) {
        this.numFrame = numFrame;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    public boolean setAnim(Animations anim) {

        if(currentAnim != anim) {
            setNumFrame(0);
            currentAnim = anim;
            return true;
        }
        return false;
    }

    public  BufferedImage getFrame(){
        return sprite().get(numFrame);
    }

    private ArrayList<BufferedImage> sprite(){
        return TextureFactory.getInstance().getSprite(currentAnim);
    }


    public int getDelai() {
        return delai;
    }

    public void setDelai(int delai) {
        this.delai = delai;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setCurrentAnim(Animations currentAnim) {
        this.currentAnim = currentAnim;
    }
}
