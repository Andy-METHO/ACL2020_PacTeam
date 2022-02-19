package pathfinder;

import model.Mur;
import model.PacmanGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pathfinder {

    private int[][] map;


    public Pathfinder(int h, int w, List<Mur> murs) {

        map = new int[w][h];

        for(Mur m : murs){
            int x = m.getX()/PacmanGame.TAILLE_CASE;
            int y = m.getY()/PacmanGame.TAILLE_CASE;
            if(m.isBorder()){
                map[x][y] = 2;
            } else {
                map[x][y] = 1;
            }
        }
    }


    public List<Node> cheminMonstre(int x1, int y1, int x2, int y2){
        return chemin(x1, y1, x2, y2, 1);
    }

    public List<Node> cheminMonstreFantome(int x1, int y1, int x2, int y2){
        return chemin(x1, y1, x2, y2, 2);
    }


    public List<Node> chemin(int x1, int y1, int x2, int y2, int typeEntite){

        Node current;
        Node debut = new Node(null, x1, y1);
        Node fin = new Node(null, x2, y2);

        List<Node> chemin = new ArrayList<>();

        List<Node> closed = new ArrayList<Node>();
        List<Node> open = new ArrayList<Node>();

        open.add(debut);

        Point[] pts = new Point[4];
        pts[0] = new Point(-1, 0);
        pts[1] = new Point(1, 0);
        pts[2] = new Point(0, -1);
        pts[3] = new Point(0, 1);


        while(!open.isEmpty()){

            current = open.get(0);

            for(Node n : open){
                if(n.getF() < current.getF()){
                    current = n;
                }
            }

            open.remove(current);
            closed.add(current);

            if(current.egal(fin)){
                while(current != null){
                    chemin.add(current);
                    current = current.getParent();
                }
                Collections.reverse(chemin);
                return chemin;
            }

            List<Node> adjacents = new ArrayList<>();

            for(Point point : pts){

                int x = point.x + current.getX();
                int y = point.y + current.getY();

                if( x >= 0 && x < map.length && y >= 0 && y < map[0].length){

                    switch(typeEntite){

                        //Monstre
                        //Peut aller sur case vide
                        case 1 :
                            if(map[x][y] == 0){
                                Node node = new Node(current, x, y);
                                adjacents.add(node);
                            }
                            break;

                        //MonstreFantome
                        //Peut aller sur case vide ou mur
                        case 2 :
                            if(map[x][y] == 0 || map[x][y] == 1){
                                Node node = new Node(current, x, y);
                                adjacents.add(node);
                            }
                            break;
                    }

                }
            }


            for(Node node : adjacents){

                boolean continuer = true;

                for(Node n : closed){
                    if(node.egal(n)){
                        continuer = false;
                    }
                }

                if(continuer){
                    node.setG(current.getG() + 1);
                    node.setH( Math.pow(node.getX() - fin.getX(), 2) + Math.pow(node.getY() - fin.getY(), 2));
                    node.setF(node.getG() + node.getH());

                    for(Node n : open){
                        if(node.egal(n) && node.getG() > n.getG()){
                            continuer = false;
                        }
                    }

                    if(continuer){
                        open.add(node);
                    }
                }

            }

        }
        return null;
    }


}
