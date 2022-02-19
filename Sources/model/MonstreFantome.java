package model;

import pathfinder.Node;

import java.util.List;

public class MonstreFantome extends Monstre{
    public MonstreFantome(int x, int y, PacmanGame game) {
        super(x, y, game);
        initAnimation(this);
    }
    public MonstreFantome(int x, int y, PacmanGame game,int difficulty) {
        super(x, y, game,difficulty);
        initAnimation(this);
    }

    @Override
    public List<Node> calculerChemin(int x1, int y1, int x2, int y2) {
        return getGame().getPathfinder().cheminMonstreFantome(x1, y1, x2, y2);
    }

    @Override
    public String getType(){
        return "Monstre";
    }

}
