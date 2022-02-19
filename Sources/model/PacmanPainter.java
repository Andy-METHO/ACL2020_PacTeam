package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import engine.GamePainter;
import engine.TextureFactory;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	private PacmanGame game;
	private float scaleRatio;


	/**
	 * appelle constructeur parent
	 * 
	 * @param g
	 *            le jeutest a afficher
	 */
	public PacmanPainter(PacmanGame g) {
		game = g;
		scaleRatio = 1;
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {

		Graphics2D crayon = (Graphics2D) im.getGraphics();

		afficherSol(crayon);
		afficherEntites(crayon);
		afficherHUD(crayon);

		crayon.dispose();
	}



	private void afficherSol(Graphics2D crayon) {

		BufferedImage img = TextureFactory.getInstance().sol();

		int taille = (int) (scaleRatio * PacmanGame.TAILLE_CASE);

		for(int i = 0; i < game.getLargeur(); i++){
			for(int j = 0; j < game.getHauteur(); j++){
				crayon.drawImage(img, i * taille, j * taille, null);
			}
		}
	}

	private void afficherEntites(Graphics2D crayon) {

		List<Entite> entites = new ArrayList<>();
		entites.addAll(game.getMurs());
		entites.addAll(game.getCaseSpeciales());
		entites.addAll(game.getMonstres());
		entites.add(game.getJoueur());

		BufferedImage img;


		for(Entite e : entites){
			img = e.getTexture();
			crayon.drawImage(img, (int)(e.getX() * scaleRatio), (int) (e.getY() * scaleRatio), null);
		}

	}

	private void afficherHUD(Graphics2D crayon){

		BufferedImage img = TextureFactory.getInstance().vie();

		int pasX = (int) (scaleRatio * 10 ), x = 0, y = (int) (PacmanGame.TAILLE_CASE * scaleRatio);

		for(int i=0; i< game.getJoueur().getPdv(); i++){
			crayon.drawImage(img, x + pasX, y, null);
			x += pasX;
		}

		crayon.drawString("Level " + game.getNumCarte(), 300 * scaleRatio, 10 * scaleRatio);
		//affichage du timer pour le joueur
		long elapsedSeconds = game.getTimeRemaining() / 1000;
		long secondsDisplay = elapsedSeconds % 60;
		long elapsedMinutes = elapsedSeconds / 60;
		if (secondsDisplay <10) { //pour éviter l'affichage 1:1 quand les secondes sont inférieurs à 10
			crayon.drawString("Timer : " + elapsedMinutes + ":0" + secondsDisplay,300 * scaleRatio,20 * scaleRatio);
		}
		else {
			crayon.drawString("Timer : " + elapsedMinutes + ":" + secondsDisplay,300 * scaleRatio,20 * scaleRatio);
		}
	}


	public void setScaleRatio(float r) {
		scaleRatio = r;
	}

	@Override
	public int getWidth() {
		return (int) (game.getLargeur() * PacmanGame.TAILLE_CASE);
	}

	@Override
	public int getHeight() {
		return (int) (game.getHauteur() * PacmanGame.TAILLE_CASE);
	}

}
