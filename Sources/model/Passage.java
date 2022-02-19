package model;

import Audio.AudioPlayer;
import engine.TextureFactory;

import java.awt.image.BufferedImage;

public class Passage extends CaseSpeciale{
    public Passage(int x1, int y1, PacmanGame g) {
        super(x1, y1, g);
    }

    @Override
    public void declencherEffet() {

        Joueur joueur = getGame().getJoueur();

        for (CaseSpeciale c : getGame().getCaseSpeciales()) {
            if (c.getType().equals("Passage") && (c.getX() != getX() || c.getY() != getY())) {
                joueur.setX(c.getX());
                joueur.setY(c.getY());
                AudioPlayer.getInstance().play(AudioPlayer.PASSAGE);
                break;
            }
        }

    }

    @Override
    public BufferedImage getTexture() {
        return TextureFactory.getInstance().passage();
    }
}
