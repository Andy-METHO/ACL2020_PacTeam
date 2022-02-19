package engine;

import model.PacmanGame;

import javax.swing.*;
import java.sql.*;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * moteur de game generique.
 * On lui passe un game et un afficheur et il permet d'executer un game.
 */
public class GameEngineGraphical {

	/**
	 * le game a executer
	 */
	private Game game;

	/**
	 * l'afficheur a utiliser pour le rendu
	 */
	private GamePainter gamePainter;

	/**
	 * le controlleur a utiliser pour recuperer les commandes
	 */
	private GameController gameController;

	/**
	 * l'interface graphique
	 */
	private GraphicalInterface gui;

	private JFrame f;

	/**
	 * construit un moteur
	 * 
	 * @param game
	 *            game a lancer
	 * @param gamePainter
	 *            afficheur a utiliser
	 * @param gameController
	 *            controlleur a utiliser
	 * @param f
	 * 		fenêtre à utiliser
	 */


	private long tDebut;
	private long tFin;

	public GameEngineGraphical(Game game, GamePainter gamePainter, GameController gameController, JFrame f) {
		// creation du game
		this.game = game;
		this.gamePainter = gamePainter;
		this.gameController = gameController;
		this.f = f;
		this.tDebut = System.currentTimeMillis();
		this.tFin = System.currentTimeMillis();
	}

	/**
	 * permet de lancer le game
	 */
	public void run() throws InterruptedException {

		//60 FPS
		long delta = 1000L/60L;

		long timerMonstres = 0;

		// creation de l'interface graphique
		this.gui = new GraphicalInterface(this.gamePainter,this.gameController, f);

		// boucle de game
		while (!this.game.isFinished()) {

			tDebut = System.currentTimeMillis();
			// fait evoluer le game
			this.game.evolve(gameController.getParams());

			if(tDebut - timerMonstres >= 500) {
				((PacmanGame)game).attaquerJoueur();
				timerMonstres = tDebut;
			}

			// affiche le game
			this.gui.paint();

			tFin = System.currentTimeMillis();

			if(delta > (tFin - tDebut)) {
				Thread.sleep(delta - (tFin - tDebut) );
			}
		}
	}

}
