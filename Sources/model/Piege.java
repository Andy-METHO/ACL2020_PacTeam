package model;

import Audio.AudioPlayer;
import engine.TextureFactory;

import java.awt.image.BufferedImage;

import static model.GameMenu.gameOn;

public class Piege extends CaseSpeciale{
    public Piege(int x1, int y1, PacmanGame g) {
        super(x1, y1, g);
    }

    @Override
    public void declencherEffet() {

        Joueur joueur = getGame().getJoueur();
        PacmanGame game = getGame();

        joueur.setPdv(joueur.getPdv() - 1);
        System.out.println("Piège ! -1 PV");
        AudioPlayer.getInstance().play(AudioPlayer.PIEGE);
        if(joueur.getPdv() <= 0){
            System.out.println("Défaite!");
            game.setFinished(true);
            game.setGameOver(true);
            gameOn = false;
            //System.exit(-1);
        }
    }

    @Override
    public BufferedImage getTexture() {
        return TextureFactory.getInstance().piege();
    }
}
