package Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

    public static int TRESOR = 0;
    public static int DEGATS_JOUEUR = 1;
    public static int MORT_JOUEUR = 2;
    public static int MORT_MONSTRE = 3;
    public static int PASSAGE = 4;
    public static int PIEGE = 5;
    public static int MAGIQUE = 6;
    public static int CHRONO = 7;

    private Clip[] clips;

    private static AudioPlayer instance;

    private boolean muted = false;

    public AudioPlayer() {

        clips = new Clip[8];

        clips[DEGATS_JOUEUR] = charger("/sons/hitmarker.wav");
        clips[MORT_JOUEUR] =  charger("/sons/oof.wav");
        clips[MORT_MONSTRE] =  charger("/sons/oof.wav");
        clips[PASSAGE] =  charger("/sons/tp.wav");
        clips[PIEGE] =  charger("/sons/piege.wav");
        clips[MAGIQUE] =  charger("/sons/heal.wav");
        clips[TRESOR] =  charger("/sons/victoire.wav");
        clips[CHRONO] =  charger("/sons/chrono.wav");

    }


    public static AudioPlayer getInstance(){
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    private Clip charger(String s){
        Clip clip = null;
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(s));
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return clip;
    }


    public void play(int i) {

        if(!muted) {
            Clip clip = clips[i];

            if (clip == null) {
                return;
            }

            stop(i);
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stop(int i) {

        Clip clip = clips[i];

        if(clip == null){
            return;
        }

        if(clip.isRunning()){
            clip.stop();
        }
    }

    public void mute(){
        muted = true;
        for(int i = 0; i < clips.length; i++){
            stop(i);
        }
    }


}
