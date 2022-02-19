package model;

import java.awt.*;
import java.io.*;

import java.util.*;
import java.util.List;

import engine.Game;
import pathfinder.Pathfinder;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class PacmanGame implements Game {

	// Nombre de cases
	private int largeur;
	private int hauteur;

	public static final int TAILLE_CASE = 32;
	public static final int VITESSE = 2;

	private Joueur joueur;
	private List<Mur> murs;
	private List<CaseSpeciale> caseSpeciales;
	private List<Monstre> monstres;
	private Pathfinder pathfinder;
	private int numCarte;
	private long startTime; //le temps en milliseconde où le jeu démarre
	private long attaque_cd; //Temps pour le Cooldown de l'attaque
	private long timeToPlay = 120 * 1000; //120 secondes pour compléter le jeu
	private long timeRemaining; //temps restant pour compléter le jeu
	private boolean isGameOver;
	private boolean isWon;
	private boolean isFinished;
	private int gameDifficulty;

	/**
	 * constructeur avec fichier source pour le help
	 * 
	 */
	public PacmanGame(String source) {
		isGameOver = false;
		isWon = false;
		isFinished = false;
		hauteur = 0;
		largeur = 0;
		murs = new ArrayList<Mur>();
		caseSpeciales = new ArrayList<CaseSpeciale>();
		monstres = new ArrayList<Monstre>();
		chargerCarte(source);
		numCarte = 1;
		attaque_cd = System.currentTimeMillis();
	}

	private void chargerCarte(String source) {

		BufferedReader helpReader;

		try {
			helpReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(source)));
			String ligne;

			int emplacement_vide = 0;
			int nbMonstre = 0;

			Random rand = new Random();
			int nombreAleatoire;

			List<Point> casesVides = new ArrayList<>();

			int lig = 0;
			while ((ligne = helpReader.readLine()) != null) {

				ligne = ligne.replaceAll("\\s+","");

				for(int col = 0; col < ligne.length(); col ++) {
					if(ligne.length() > 1) {
						if(largeur == 0){
							largeur = ligne.length();
						}
						switch (ligne.charAt(col)) {
							case '/':
								murs.add(new Mur(col * TAILLE_CASE, lig * TAILLE_CASE, this, false));
								break;
							case '#':
								murs.add(new Mur(col * TAILLE_CASE, lig * TAILLE_CASE, this, true));
								break;
							case '_':
								casesVides.add(new Point(col * TAILLE_CASE, lig * TAILLE_CASE));
								break;
							case 'J':
								joueur = new Joueur(col * TAILLE_CASE, lig * TAILLE_CASE, this);
								break;
							case 'T':
								caseSpeciales.add(new Tresor(col * TAILLE_CASE, lig * TAILLE_CASE, this));
								break;
							case 'P':
								caseSpeciales.add(new Piege(col * TAILLE_CASE, lig * TAILLE_CASE, this));
								break;
							case 'V':
								caseSpeciales.add(new Magique(col * TAILLE_CASE, lig * TAILLE_CASE, this));
								break;
							case 'O':
								caseSpeciales.add(new Passage(col * TAILLE_CASE, lig * TAILLE_CASE, this));
								break;
							case '*':
								caseSpeciales.add(new Chrono(col * TAILLE_CASE,lig * TAILLE_CASE, this));
								break;
						}
					} else {
						nbMonstre = Character.getNumericValue(ligne.charAt(col));
					}
				}
				lig++;
			}


			for(int i = 0; i < nbMonstre; i++){
				Point point = casesVides.get(rand.nextInt(casesVides.size()));
				nombreAleatoire = rand.nextInt(2);

				switch (nombreAleatoire) {
					case 0:
						monstres.add(new Monstre(point.x, point.y, this, gameDifficulty));
						break;
					case 1:
						monstres.add(new MonstreFantome(point.x, point.y, this, gameDifficulty));
						break;
				}
				casesVides.remove(point);
			}

			hauteur = lig;
			helpReader.close();
			pathfinder = new Pathfinder(hauteur, largeur, murs);

		} catch (IOException e) {
			System.out.println("Erreur chargement carte");
		}

	}

	/**
	 * faire evoluer le jeu
	 */
	@Override
	public void evolve(int [] params) {


		//on calcule le temps restant et on l'affiche fans la console
		long elapsedTime = System.currentTimeMillis() - startTime;
		timeRemaining = timeToPlay - elapsedTime;
		long elapsedSeconds = timeRemaining / 1000;
		long secondsDisplay = elapsedSeconds % 60;
		long elapsedMinutes = elapsedSeconds / 60;

		long test_attaque = System.currentTimeMillis() - attaque_cd;

		joueur.setVelX(params[0]);
		joueur.setVelY(params[1]);
		joueur.setAttaque(params[2] == 1);
		joueur.deplacer();

		if(test_attaque > 1000 ) {
			joueur.attaquerMonstres();
		}

		deplacerMonstres();

		if (secondsDisplay < 10) {
			//System.out.println("Time : " + elapsedMinutes + ":0" + secondsDisplay);
		}
		else{
			//System.out.println("Time : " + elapsedMinutes + ":" + secondsDisplay);
		}

		joueur.animer();
		animerMonstres();

	}

	public void deplacerMonstres(){
		for (Monstre m : monstres) {
			m.deplacer();
		}
	}

	public void animerMonstres() {
		for (Monstre m : monstres) {
			m.animer();
		}
	}

	public void attaquerJoueur(){
		for(Monstre m : getMonstres()){
			m.attaquerJoueur();
		}
	}


	public Joueur getJoueur() {
		return joueur;
	}

	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public List<Mur> getMurs() {
		return murs;
	}

	public List<CaseSpeciale> getCaseSpeciales() {
		return caseSpeciales;
	}

	public List<Monstre> getMonstres() {
		return monstres;
	}

	public Pathfinder getPathfinder() {
		return pathfinder;
	}

	public int getNumCarte() { return numCarte;	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getTimeRemaining() {
		return timeRemaining;
	}

	public void addTime(long time) {
		this.timeToPlay = time;
	}

	public long getTimeToPlay() {
		return timeToPlay;
	}

	public void nextCarte(){
		if(numCarte<4) {
			numCarte++;
			hauteur = 0;
			largeur = 0;
			murs = new ArrayList<Mur>();
			caseSpeciales = new ArrayList<CaseSpeciale>();
			monstres = new ArrayList<Monstre>();
			chargerCarte("/maps/map" + numCarte + ".txt");
		}
		else{
			System.out.println("Victoire! EndScreen.");
			setWon(true);
			setFinished(true);
		}
	}


	public int getGameDifficulty() {
		return gameDifficulty;
	}

	public void setGameDifficulty(int gameDifficulty) {
		this.gameDifficulty = gameDifficulty;
	}


	public void setAttaque_cd(long attaque_cd) {
		this.attaque_cd = attaque_cd;
	}

	public void setGameOver(boolean b) {
		isGameOver = b;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public boolean isWon() {
		return isWon;
	}

	public void setWon(boolean won) {
		isWon = won;
	}

	public void setFinished(boolean finished) {
		isFinished = finished;
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		return  isFinished;
	}
}
