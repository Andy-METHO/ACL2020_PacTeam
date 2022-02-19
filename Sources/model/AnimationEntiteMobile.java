package model;


public class AnimationEntiteMobile extends Animation {

    private EntiteMobile entite;
    private Animations[] anims;

    public AnimationEntiteMobile(EntiteMobile e) {
        super();
        anims = new Animations[4];
        switch (e.getClass().getSimpleName()){
            case "Joueur":
                setAnim(Animations.JOUEUR_WALK_DOWN);
                anims[0] = Animations.JOUEUR_WALK_UP;
                anims[1] = Animations.JOUEUR_WALK_RIGHT;
                anims[2] = Animations.JOUEUR_WALK_DOWN;
                anims[3] = Animations.JOUEUR_WALK_LEFT;
                break;
            case "Monstre":
                setAnim(Animations.MONSTRE_WALK_DOWN);
                anims[0] = Animations.MONSTRE_WALK_UP;
                anims[1] = Animations.MONSTRE_WALK_RIGHT;
                anims[2] = Animations.MONSTRE_WALK_DOWN;
                anims[3] = Animations.MONSTRE_WALK_LEFT;
                break;
            case "MonstreFantome":
                setAnim(Animations.FANTOME_WALK_DOWN);
                anims[0] = Animations.FANTOME_WALK_UP;
                anims[1] = Animations.FANTOME_WALK_RIGHT;
                anims[2] = Animations.FANTOME_WALK_DOWN;
                anims[3] = Animations.FANTOME_WALK_LEFT;
                break;

        }
        entite = e;
    }


    @Override
    public void animer() {

        boolean nextFrame = true;

        if (getDelai() < getTime()) {

            setTime(0);

            if (entite.getVelX() < 0) {
                setAnim(anims[3]);
            } else if (entite.getVelX() > 0) {
                setAnim(anims[1]);
            } else if(entite.getVelY() > 0){
                setAnim(anims[2]);
            } else if(entite.getVelY() < 0){
                setAnim(anims[0]);
            } else if (entite.getVelX() == 0 && entite.getVelY() == 0) {
               nextFrame = false;
            }

            if(nextFrame){
                nextFrame();
            }

        } else {
            setTime(getTime() + 16);
        }
    }


}
