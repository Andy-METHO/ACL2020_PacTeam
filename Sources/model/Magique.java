package model;

import Audio.AudioPlayer;
import engine.TextureFactory;

import java.awt.image.BufferedImage;

public class Magique extends CaseSpeciale{
    public Magique(int x1, int y1, PacmanGame g) {
        super(x1, y1, g);
    }

    @Override
    public void declencherEffet() {

        Joueur joueur = getGame().getJoueur();
        joueur.setPdv(joueur.getPdv() + 1);
        AudioPlayer.getInstance().play(AudioPlayer.MAGIQUE);
        System.out.println("Magie ! +1 PV");
    }

    @Override
    public BufferedImage getTexture() {
        return TextureFactory.getInstance().magique();
    }
}
