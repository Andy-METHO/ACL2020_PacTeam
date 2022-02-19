package model;

import Audio.AudioPlayer;
import engine.TextureFactory;

import java.awt.image.BufferedImage;

public class Chrono extends CaseSpeciale{
    public Chrono(int x1, int y1, PacmanGame g) {
        super(x1, y1, g);
    }

    @Override
    public void declencherEffet() {

        PacmanGame g = getGame();

        g.addTime(g.getTimeToPlay()+2000);
        System.out.println("+2 secondes");
        AudioPlayer.getInstance().play(AudioPlayer.CHRONO);
    }

    @Override
    public BufferedImage getTexture() {
        return TextureFactory.getInstance().chrono();
    }
}
