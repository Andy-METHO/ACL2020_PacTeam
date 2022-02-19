package start;

import model.GameMenu;

import java.io.IOException;

import static model.GameMenu.gameOn;

/**
 * lancement du moteur avec le jeu
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {

		GameMenu menu = new GameMenu();
		while (!menu.getGame().isFinished()){
			System.out.println(gameOn);
			if(gameOn){
				menu.getEngine().run();
			}
		}
		menu.endGame();
	}
	//Test
//Conflit du présent
}
