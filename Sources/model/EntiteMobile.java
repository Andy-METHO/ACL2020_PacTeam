package model;


import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class EntiteMobile extends Entite {

    private int velX;
    private int velY;

    private AnimationEntiteMobile animation;

    public EntiteMobile(int x, int y, PacmanGame game) {
        super(x, y, game);
        velX = 0;
        velY = 0;
    }

    public abstract void deplacer();

    //Position du body après application du déplacement
    public Rectangle getNextPosition(){
        Rectangle rect = getBodyAbsolutePosition();
        rect.setLocation(rect.x + getVelX(), rect.y + getVelY());
        return rect;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelX(int velX) {
        this.velX = velX * PacmanGame.VITESSE;
    }

    public void setVelY(int velY) {
        this.velY = velY * PacmanGame.VITESSE;
    }


    public void animer(){
        animation.animer();
    }

    public void initAnimation(EntiteMobile e){
        animation = new AnimationEntiteMobile(e);
    }

    @Override
    public BufferedImage getTexture() {
        return animation.getFrame();
    }
}
