package engine;

import java.awt.event.KeyListener;

/**
 * @author Horatiu Cirstea
 * 
 * controleur qui envoie des commandes au jeu 
 * 
 */
public interface GameController extends KeyListener {

	/**
	 * Le controleur retourne les commandes en cours
	 * 
	 * @return commandes faites par le joueur
	 */
	public int[] getParams();

}
