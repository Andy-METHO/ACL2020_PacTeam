package model;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * controleur de type KeyListener
 * 
 */
public class PacmanController implements GameController {


	private int [] params;

	
	/**
	 * construction du controleur par defaut le controleur n'a pas de commande
	 */
	public PacmanController() {
		params = new int[3];
		params[0] = 0;
		params[1] = 0;
		params[2] = 0;
	}


	public int [] getParams() {
		return params;
	}

	@Override
	/**
	 * met a jour les commandes en fonctions des touches appuyees
	 */
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyChar()) {

			// Q = gauche
			case 'q':
			case 'Q':
				params[0] = -1;
				params[1] = 0;
				break;

			// D = droite
			case 'd':
			case 'D':
				params[0] = 1;
				params[1] = 0;
				break;

			// Z = haut
			case 'z':
			case 'Z':
				params[1] = -1;
				params[0] = 0;
				break;

			// S = bas
			case 's':
			case 'S':
				params[1] = 1;
				params[0] = 0;
				break;

			// E = attaquer
			case 'e':
			case 'E':
				params[2] = 1;
				break;
		}

	}

	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyChar()) {

			case 'q':
			case 'Q':
			case 'd':
			case 'D':
				params[0] = 0;
				break;

			case 'z':
			case 'Z':
			case 's':
			case 'S':
				params[1] = 0;
				break;

			case 'e':
			case 'E':
				params[2] = 0;
				break;
		}
	}

	@Override
	/**
	 * ne fait rien
	 */
	public void keyTyped(KeyEvent e) {

	}

}
