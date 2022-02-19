package engine;


import model.Animation;
import model.Animations;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class TextureFactory {

    private HashMap<String, BufferedImage> images;
    private HashMap<String, BufferedImage> scaledImages;

    private EnumMap<Animations, ArrayList<BufferedImage>> sprites;
    private EnumMap<Animations, ArrayList<BufferedImage>> scaledSprites;

    private static TextureFactory instance;

    private TextureFactory() throws IOException {

        images = new HashMap<String, BufferedImage>();
        scaledImages = new HashMap<String, BufferedImage>();

        sprites = new EnumMap<Animations, ArrayList<BufferedImage>>(Animations.class);
        scaledSprites = new EnumMap<Animations, ArrayList<BufferedImage>>(Animations.class);

        images.put("mur", ImageIO.read(getClass().getResource("/images/mur.png")));
        images.put("sol", ImageIO.read(getClass().getResource("/images/sol.jpg")));
        images.put("magique",ImageIO.read(getClass().getResource("/images/magique.png")));
        images.put("piege", ImageIO.read(getClass().getResource("/images/piege.png")));
        images.put("passage", ImageIO.read(getClass().getResource("/images/passage.png")));
        images.put("tresor", ImageIO.read(getClass().getResource("/images/tresor.png")));
        images.put("vie", ImageIO.read(getClass().getResource("/images/heart.png")));
        images.put("playButton", ImageIO.read(getClass().getResource("/images/PlayButton.png")));
        images.put("playButtonHighlighted", ImageIO.read(getClass().getResource("/images/PlayButtonHighlight.png")));
        images.put("quitButton", ImageIO.read(getClass().getResource("/images/QuitButton.png")));
        images.put("quitButtonHighlighted", ImageIO.read(getClass().getResource("/images/QuitButtonHighlight.png")));
        images.put("facileButton", ImageIO.read(getClass().getResource("/images/facile.png")));
        images.put("facileButtonHighlighted", ImageIO.read(getClass().getResource("/images/facile2.png")));
        images.put("normaleButton", ImageIO.read(getClass().getResource("/images/normale.png")));
        images.put("normaleButtonHighlighted", ImageIO.read(getClass().getResource("/images/normale2.png")));
        images.put("difficileButton", ImageIO.read(getClass().getResource("/images/difficile.png")));
        images.put("difficileButtonHighlighted", ImageIO.read(getClass().getResource("/images/difficile2.png")));
        images.put("logo", ImageIO.read(getClass().getResource("/images/logo.png")));
        images.put("chrono", ImageIO.read(getClass().getResource("/images/chrono.png")));
        images.put("gameOver", ImageIO.read(getClass().getResource("/images/gameOver.png")));
        images.put("victory", ImageIO.read(getClass().getResource("/images/victory.png")));

        BufferedImage joueur = ImageIO.read(getClass().getResource("/images/soldier.png"));
        BufferedImage monstre = ImageIO.read(getClass().getResource("/images/spooky.png"));
        BufferedImage fantome = ImageIO.read(getClass().getResource("/images/ghost.png"));

        sprites.put(Animations.JOUEUR_WALK_RIGHT, splitRow(32,2, 3, joueur));
        sprites.put(Animations.JOUEUR_WALK_LEFT, splitRow(32,1, 3, joueur));
        sprites.put(Animations.JOUEUR_WALK_UP, splitRow(32,3, 3, joueur));
        sprites.put(Animations.JOUEUR_WALK_DOWN, splitRow(32,0, 3, joueur));

        sprites.put(Animations.MONSTRE_WALK_RIGHT, splitRow(32,2, 3, monstre));
        sprites.put(Animations.MONSTRE_WALK_LEFT, splitRow(32,1, 3, monstre));
        sprites.put(Animations.MONSTRE_WALK_UP, splitRow(32,3, 3, monstre));
        sprites.put(Animations.MONSTRE_WALK_DOWN, splitRow(32,0, 3, monstre));

        sprites.put(Animations.FANTOME_WALK_RIGHT, splitRow(32,2, 3, fantome));
        sprites.put(Animations.FANTOME_WALK_LEFT, splitRow(32,1, 3, fantome));
        sprites.put(Animations.FANTOME_WALK_UP, splitRow(32,3, 3, fantome));
        sprites.put(Animations.FANTOME_WALK_DOWN, splitRow(32,0, 3, fantome));

        resizeImages(1.5f);

    }

    private ArrayList<BufferedImage> splitRow(int tailleCase, int ligne, int nbCol, BufferedImage img) {

        ArrayList<BufferedImage> images = new ArrayList<>();
        for(int i = 0; i < nbCol; i++){
            images.add(img.getSubimage(tailleCase * i, ligne * tailleCase, tailleCase, tailleCase));
        }
        return images;
    }


    public static TextureFactory getInstance(){
        if (instance == null) {
            try {
                instance = new TextureFactory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    public void resizeImages(float scaleRatio){

        scaledImages.clear();
        scaledSprites.clear();

        for (Map.Entry<String, BufferedImage> entry : images.entrySet()) {
            BufferedImage image = entry.getValue();
            scaledImages.put(entry.getKey(), resize(image, scaleRatio));
        }

        for(Map.Entry<Animations, ArrayList<BufferedImage>> entries : sprites.entrySet()){
            ArrayList<BufferedImage> ligne = new ArrayList<>();
            ArrayList<BufferedImage> values = entries.getValue();
            for(BufferedImage image : values){
                ligne.add(resize(image, scaleRatio));
            }
            scaledSprites.put(entries.getKey(), ligne);
        }

    }

    public BufferedImage resize(BufferedImage image, float scaleRatio){
        int width = (int)(scaleRatio * image.getWidth());
        int height = (int)(scaleRatio * image.getHeight());

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        return  resized;
    }

    public BufferedImage joueur() {
        return scaledImages.get("joueur");
    }

    public BufferedImage monstre() {
        return scaledImages.get("monstre");
    }
    public BufferedImage fantome() {
        return scaledImages.get("fantome");
    }

    public BufferedImage mur() {
        return scaledImages.get("mur");
    }

    public BufferedImage sol() {
        return scaledImages.get("sol");
    }

    public BufferedImage magique() {
        return scaledImages.get("magique");
    }

    public BufferedImage piege() {
        return scaledImages.get("piege");
    }

    public BufferedImage passage() {
        return scaledImages.get("passage");
    }

    public BufferedImage tresor() {
        return scaledImages.get("tresor");
    }

    public BufferedImage vie() {
        return scaledImages.get("vie");
    }

    public BufferedImage playButton() { return scaledImages.get("playButton"); }
    public BufferedImage playButtonHighlighted() { return scaledImages.get("playButtonHighlighted"); }

    public BufferedImage quitButton() { return scaledImages.get("quitButton"); }
    public BufferedImage quitButtonHighlighted() { return scaledImages.get("quitButtonHighlighted"); }

    public BufferedImage logo() { return scaledImages.get("logo");}

    public BufferedImage chrono() { return scaledImages.get("chrono"); }

    public BufferedImage facileButton() {
        return scaledImages.get("facileButton");
    }

    public BufferedImage facileButtonHighlighted() {
        return scaledImages.get("facileButtonHighlighted");
    }

    public BufferedImage normaleButton() {
        return scaledImages.get("normaleButton");
    }
    public BufferedImage normaleButtonHighlighted() {
        return scaledImages.get("normaleButtonHighlighted");
    }

    public BufferedImage difficileButton() {
        return scaledImages.get("difficileButton");
    }

    public BufferedImage difficileButtonHighlighted() {
        return scaledImages.get("difficileButtonHighlighted");
    }


    public BufferedImage gameOver() { return scaledImages.get("gameOver"); }

    public BufferedImage victory() { return scaledImages.get("victory"); }

    public ArrayList<BufferedImage> getSprite(Animations anim){
        return scaledSprites.get(anim);
    }
}
