package model;

import engine.GameEngineGraphical;
import engine.TextureFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class GameMenu implements ActionListener{
    private JButton play;
    private JButton quit;

    private JButton facile;
    private JButton normale;
    private JButton difficile;


    private JFrame frame;
    public static boolean gameOn = false;
    private JPanel menu;
    private JPanel menu2;

    // creation du jeu particulier et de son afficheur
    private PacmanGame game;
    private PacmanPainter painter;
    private PacmanController controller;
    private GameEngineGraphical engine;


    public GameMenu(){

        Image playButton = TextureFactory.getInstance().playButton();
        Image quitButton = TextureFactory.getInstance().quitButton();

        Image facileButton = TextureFactory.getInstance().facileButton();
        Image normaleButton = TextureFactory.getInstance().normaleButton();
        Image difficileButton = TextureFactory.getInstance().difficileButton();

        Image playButtonHighlight = TextureFactory.getInstance().playButtonHighlighted();
        Image quitButtonHighlight = TextureFactory.getInstance().quitButtonHighlighted();

        Image facileButtonHighlight = TextureFactory.getInstance().facileButtonHighlighted();
        Image normaleButtonHighlight = TextureFactory.getInstance().normaleButtonHighlighted();
        Image difficileButtonHighlight = TextureFactory.getInstance().difficileButtonHighlighted();

        Image logoImg = TextureFactory.getInstance().logo();

        JLabel logo = new JLabel(new ImageIcon(logoImg));

        play = new JButton(new ImageIcon(playButton));
        play.setRolloverIcon(new ImageIcon(playButtonHighlight));
        play.setBorder(BorderFactory.createEmptyBorder());
        play.setContentAreaFilled(false);

        quit = new JButton(new ImageIcon(quitButton));
        quit.setRolloverIcon(new ImageIcon(quitButtonHighlight));
        quit.setBorder(BorderFactory.createEmptyBorder());
        quit.setContentAreaFilled(false);

        facile = new JButton(new ImageIcon(facileButton));
        facile.setRolloverIcon(new ImageIcon(facileButtonHighlight));
        facile.setBorder(BorderFactory.createEmptyBorder());
        facile.setContentAreaFilled(false);

        normale = new JButton(new ImageIcon(normaleButton));
        normale.setRolloverIcon(new ImageIcon(normaleButtonHighlight));
        normale.setBorder(BorderFactory.createEmptyBorder());
        normale.setContentAreaFilled(false);

        difficile = new JButton(new ImageIcon(difficileButton));
        difficile.setRolloverIcon(new ImageIcon(difficileButtonHighlight));
        difficile.setBorder(BorderFactory.createEmptyBorder());
        difficile.setContentAreaFilled(false);

        menu = new JPanel(new BorderLayout());
        menu.setBackground(Color.DARK_GRAY);

        menu2 = new JPanel(new BorderLayout());
        menu2.setBackground(Color.DARK_GRAY);

        play.addActionListener(this);
        quit.addActionListener(this);
        facile.addActionListener(this);
        normale.addActionListener(this);
        difficile.addActionListener(this);

        menu.add(logo, BorderLayout.PAGE_START);

        menu.add(play, BorderLayout.CENTER);
        menu.add(quit, BorderLayout.PAGE_END);

        menu2.add(facile,BorderLayout.LINE_START);
        menu2.add(normale,BorderLayout.CENTER);
        menu2.add(difficile,BorderLayout.LINE_END);

        game = new PacmanGame("/maps/map1.txt");
        painter = new PacmanPainter(game);
        controller = new PacmanController();

        frame = new JFrame("PACTEAM");
        frame.setContentPane(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(2 * PacmanGame.TAILLE_CASE * game.getLargeur(), 2 * PacmanGame.TAILLE_CASE * game.getHauteur()));
        frame.pack();
        frame.setVisible(true);
        frame.getContentPane().setFocusable(true);
        frame.setResizable(true);
        frame.getContentPane().requestFocus();

        TextureFactory.getInstance().resizeImages(2);
        painter.setScaleRatio(2);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                float scaleRatio1 = frame.getWidth()/(float)frame.getPreferredSize().width;
                float scaleRatio2 = frame.getHeight()/(float)frame.getPreferredSize().height;

                float scaleRatio = Float.max(scaleRatio1, scaleRatio2);

                int maxWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
                int maxHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

                int newWidth = (int)(frame.getPreferredSize().width * scaleRatio);
                int newHeight = (int)(frame.getPreferredSize().height * scaleRatio);

                if(newWidth > maxWidth || newHeight > maxHeight) {
                    frame.setSize(maxHeight, maxHeight);
                    scaleRatio = frame.getWidth() / (float) frame.getPreferredSize().width;
                } else {
                    frame.setSize(newWidth, newHeight);
                }


                TextureFactory.getInstance().resizeImages(scaleRatio);
                painter.setScaleRatio(scaleRatio);
            }
        });

        // classe qui lance le moteur de jeu generique
        engine = new GameEngineGraphical(game, painter, controller, frame);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == play){
            menu.remove(play);
            menu.add(menu2);
            frame.revalidate();
            frame.repaint();
        }

        else if(e.getSource() == quit){
            System.exit(0);
        }

        else if(e.getSource() == facile){
            game.setGameDifficulty(1);
            startgame();
        }

        else if(e.getSource() == normale){
            game.setGameDifficulty(2);
            startgame();
        }

        else if(e.getSource() == difficile){
            game.setGameDifficulty(3);
            startgame();
        }

    }

    public GameEngineGraphical getEngine() {
        return engine;
    }
    public PacmanGame getGame() { return game; }

    public void startgame(){
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        gameOn = true;
        game.setStartTime(System.currentTimeMillis()); //le timer commence ici
    }

    public void endGame(){
        frame.getContentPane().removeAll();
        if(game.isGameOver()){
            Image gameOverImg = TextureFactory.getInstance().gameOver();
            JLabel gameOver = new JLabel(new ImageIcon(gameOverImg));
            menu.add(gameOver, BorderLayout.PAGE_START);
            menu.add(quit, BorderLayout.PAGE_END);
            frame.setContentPane(menu);
        }
        else if(game.isWon()){
            Image victoryImg = TextureFactory.getInstance().victory();
            JLabel victory = new JLabel(new ImageIcon(victoryImg));
            menu.add(victory, BorderLayout.PAGE_START);
            menu.add(play, BorderLayout.CENTER);
            menu.add(quit, BorderLayout.PAGE_END);
            frame.setContentPane(menu);
        }
        frame.revalidate();
        frame.repaint();
    }
}
