package model;

import Audio.AudioPlayer;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Iterator;

public class Joueur extends EntiteMobile {

    private int pdv;
    private boolean attaque;
    private boolean surPassage;
    private final int range = PacmanGame.TAILLE_CASE/2;
    private final int PDV_MAX = 10;

    // x, y coordonnées du spawn
    public Joueur(int x, int y, PacmanGame game) {
        super(x, y, game);
        pdv = PDV_MAX;
        attaque = false;
        surPassage = false;
        initAnimation(this);
        getBody().setBounds(0, 0, PacmanGame.TAILLE_CASE, PacmanGame.TAILLE_CASE);
    }

    @Override
    public void deplacer() {

        int velX = getVelX();
        int velY = getVelY();

        if(velX != 0 || velY != 0){
            if(!collision()){
                setX(getX() + velX);
                setY(getY() + velY);
            }
        }
    }


    public boolean collision() {

        boolean ret = collisionMur();
        boolean passage = false;

        Rectangle body = getNextPosition();

        Iterator<CaseSpeciale> it = getGame().getCaseSpeciales().listIterator();
        while(it.hasNext()){
            CaseSpeciale c = it.next();
            if(body.intersects(c.getBodyAbsolutePosition())){

                if(c.getType().equals("Passage")){
                    if(!estSurPassage()){
                        c.declencherEffet();
                        ret = true;
                    }
                    passage = true;

                } else {
                    c.declencherEffet();
                    it.remove();
                }
            }
        }

        setSurPassage(passage);
        return ret;
    }

    public boolean collisionMur(){

        PacmanGame game = getGame();
        Rectangle body = getNextPosition();

        for(Mur m : game.getMurs()){

            Rectangle bodyM = m.getBodyAbsolutePosition();

            if(body.intersects(bodyM)){

                Rectangle intersection = body.intersection(bodyM);

                if(getVelY() != 0) {
                    if (intersection.width <= PacmanGame.TAILLE_CASE * 0.25f) {
                        if (bodyM.x < body.x) {
                            setX(bodyM.x + bodyM.width);
                            setY(body.y);
                        } else if (bodyM.x > body.x) {
                            setX(bodyM.x - bodyM.width);
                            setY(body.y);
                        }
                    }
                } else if(getVelX() != 0) {
                    if (intersection.height <= PacmanGame.TAILLE_CASE * 0.25f) {
                        if (bodyM.y < body.y) {
                            setX(body.x);
                            setY(bodyM.y + bodyM.height);
                        } else if (bodyM.y > body.y) {
                            setX(body.x);
                            setY(bodyM.y - bodyM.height);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    //Attaque tous les monstres à portée
    public void attaquerMonstres(){
        PacmanGame g = getGame();

        if(attaque) {
            g.setAttaque_cd(System.currentTimeMillis());
            //Centre du joueur
            int x = getX() + PacmanGame.TAILLE_CASE / 2;
            int y = getY() + PacmanGame.TAILLE_CASE / 2;

            //Portée d'attaque horizontale et verticale
            Line2D.Double lX = new Line2D.Double(x - range, y, x + range, y);
            Line2D.Double lY = new Line2D.Double(x, y - range, x, y + range);

            Iterator<Monstre> it = getGame().getMonstres().listIterator();
            while (it.hasNext()) {
                Monstre m = it.next();
                Rectangle bodyM = m.getBodyAbsolutePosition();
                if (lX.intersects(bodyM) || lY.intersects(bodyM)) {
                    m.setPdv(m.getPdv() - 1);
                    System.out.println("JOUEUR ATTAQUE (PV MONSTRE : " + m.getPdv() + ")");
                    if (m.getPdv() <= 0) {
                        it.remove();
                        g.addTime(g.getTimeToPlay()+10000);
                        System.out.println("MONSTRE EST MORT");
                        AudioPlayer.getInstance().play(AudioPlayer.MORT_MONSTRE);
                    }
                }
            }

            attaque = false;
        }


    }


    public void setPdv(int pdv) {
        this.pdv = pdv;

    }

    public int getPdv() {
        return pdv;
    }

    public void setAttaque(boolean b) {
        attaque = b;
    }

    public boolean estSurPassage() {
        return surPassage;
    }

    public void setSurPassage(boolean surPassage) {
        this.surPassage = surPassage;
    }


}
