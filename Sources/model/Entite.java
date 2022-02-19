package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entite {

    private int x;
    private int y;
    private PacmanGame game;
    private Rectangle body;

    public Entite(int x1, int y1, PacmanGame g) {
        this.x = x1;
        this.y = y1;
        game = g;
        body = new Rectangle();
        getBody().setBounds(0, 0, PacmanGame.TAILLE_CASE, PacmanGame.TAILLE_CASE);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PacmanGame getGame() {
        return game;
    }

    public Rectangle getBody() {
        return body;
    }

    public Rectangle getBodyAbsolutePosition() {
        Rectangle rect = new Rectangle(body);
        rect.setLocation(x + rect.x, y + rect.y);
        return rect;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + x + ", " + y + ")";
    }

    public String getType(){
        return getClass().getSimpleName();
    }

    public abstract BufferedImage getTexture();

    public boolean estCaseSpeciale(){
        return false;
    }


}
