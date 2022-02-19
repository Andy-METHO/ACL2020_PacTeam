package model;

public abstract class CaseSpeciale extends Entite {

    public CaseSpeciale(int x1, int y1, PacmanGame g) {
        super(x1, y1, g);
        int pos = (int) (0.25 * PacmanGame.TAILLE_CASE);
        int taille = (int)(0.5 * PacmanGame.TAILLE_CASE);
        getBody().setBounds(pos, pos, taille, taille);

    }

    public abstract void declencherEffet();

    @Override
    public boolean estCaseSpeciale(){
        return true;
    }
}
