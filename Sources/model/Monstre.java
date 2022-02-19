package model;

import Audio.AudioPlayer;
import pathfinder.Node;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static model.GameMenu.gameOn;

public class Monstre extends EntiteMobile{


    private int pdv;
    private boolean seDeplace;
    private int xDest;
    private int yDest;

    // x, y coordonnées du spawn
    public Monstre(int x, int y, PacmanGame game) {
        super(x, y, game);
        Random random = new Random();
        pdv = random.nextInt(3)+1;
        seDeplace = false;
        initAnimation(this);
    }

    public Monstre(int x, int y, PacmanGame game,int difficulty) {
        super(x, y, game);
        Random random = new Random();
        pdv = random.nextInt(3*difficulty+1)+1;
        seDeplace = false;
        initAnimation(this);
    }


    @Override
    public void deplacer() {

        int velX = getVelX();
        int velY = getVelY();

        Joueur joueur = getGame().getJoueur();

        int tailleCase = PacmanGame.TAILLE_CASE;
        int jX = joueur.getX()/tailleCase;
        int jY = joueur.getY()/tailleCase;
        int x = getX()/tailleCase;
        int y = getY()/tailleCase;

        boolean deplacementTermine = (velX < 0 && x <= xDest - 1) || (velX > 0 && x >= xDest) || (velY < 0 && y <= yDest - 1)  || (velY > 0 && y >= yDest);
        boolean immobile = (velX == 0 && velY == 0 && x != jX && y != jY);

        if(deplacementTermine || immobile){

            if(deplacementTermine) {
                setX(xDest * tailleCase);
                setY(yDest * tailleCase);
                x = xDest;
                y = yDest;
            }

            List<Node> chemin = calculerChemin(x, y, jX, jY);

            if (chemin != null) {
                if (chemin.size() > 1) {
                    Node destination = chemin.get(1);
                    setVelX(destination.getX() - x);
                    setVelY(destination.getY() - y);
                    xDest = destination.getX();
                    yDest = destination.getY();
                }
            }

        }

        setX(getX() + getVelX());
        setY(getY() + getVelY());

    }

    public List<Node> calculerChemin(int x1, int y1, int x2, int y2) {
        return getGame().getPathfinder().cheminMonstre(x1, y1, x2, y2);
    }


    public void attaquerJoueur(){

        PacmanGame game = getGame();
        Joueur joueur = game.getJoueur();

        Rectangle bodyM = getBodyAbsolutePosition();
        Rectangle bodyJ = joueur.getBodyAbsolutePosition();

        if(bodyM.intersects(bodyJ)){

            joueur.setPdv(joueur.getPdv() - 1);
            System.out.println("MONSTRE ATTAQUE ! (PV restants: " + joueur.getPdv()+")");

            if (joueur.getPdv() <= 0){
                System.out.println("Défaite!");
                game.setFinished(true);
                game.setGameOver(true);
                gameOn = false;
                AudioPlayer.getInstance().play(AudioPlayer.MORT_JOUEUR);
            } else {
                AudioPlayer.getInstance().play(AudioPlayer.DEGATS_JOUEUR);
            }
        }

    }


    public void setPdv(int pdv) {
        this.pdv = pdv;
    }

    public int getPdv() {
        return pdv;
    }

}
