package engine;

import javax.swing.*;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * interface graphique avec son controller et son afficheur
 *
 */
public class GraphicalInterface  {

	/**
	 * le Panel pour l'afficheur
	 */
	private DrawingPanel panel;
	
	/**
	 * la construction de l'interface graphique: JFrame avec panel pour le game
	 * 
	 * @param gamePainter l'afficheur a utiliser dans le moteur
	 * @param gameController l'afficheur a utiliser dans le moteur
	 * 
	 */
	public GraphicalInterface(GamePainter gamePainter, GameController gameController, JFrame frame){
		// attacher le panel contenant l'afficheur du game
		this.panel = new DrawingPanel(gamePainter);
		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();

		// attacher controller au panel du game
		this.panel.addKeyListener(gameController);

		frame.getContentPane().setFocusable(true);
		frame.getContentPane().requestFocus();

	}

	/**
	 * mise a jour du dessin
	 */
	public void paint() {
		this.panel.drawGame();	
	}

}
