package model;

import engine.TextureFactory;

import java.awt.image.BufferedImage;

public class Mur extends Entite {

    private boolean estBordure;
    public Mur(int x1, int y1, PacmanGame game, boolean estBordure) {
        super(x1, y1, game);
        this.estBordure = estBordure;
    }

    public boolean isBorder(){
        return estBordure;
    }

    @Override
    public BufferedImage getTexture() {
        return TextureFactory.getInstance().mur();
    }


}
