package pathfinder;

public class Node {

    private Node parent;
    private int x;
    private int y;
    private double g;
    private double h;
    private double f;

    public Node(Node p, int xpos, int ypos) {
        parent = p;
        x = xpos;
        y = ypos;
        f = 0;
        g = 0;
        h = 0;
    }


    public boolean egal(Node node) {
        return (x == node.getX()) && (y == node.getY());
    }


    public Node getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }
}
