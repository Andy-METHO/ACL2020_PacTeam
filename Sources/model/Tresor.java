package model;

import Audio.AudioPlayer;
import engine.TextureFactory;

import java.awt.image.BufferedImage;

public class Tresor extends CaseSpeciale {

    private PacmanGame game;
    public Tresor(int x1, int y1, PacmanGame g) {
        super(x1, y1, g);
        this.game = g;
    }

    @Override
    public void declencherEffet() {

        AudioPlayer.getInstance().play(AudioPlayer.TRESOR);
        System.out.println("Victoire!");
        game.nextCarte();
        //System.exit(0);
    }

    @Override
    public BufferedImage getTexture() {
        return TextureFactory.getInstance().tresor();
    }
}
