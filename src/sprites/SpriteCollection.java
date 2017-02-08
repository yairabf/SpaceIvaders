package sprites;


import biuoop.DrawSurface;

import java.util.ArrayList;

/**
 * calls which represent sprites collection.
 */
public class SpriteCollection {
    private ArrayList<Sprite> spritesList;

    /**
     * constructor which initiate new sprites list.
     */
    public SpriteCollection() {
        this.spritesList = new ArrayList();
    }

    /**
     * method that adds sprite object to the sprites collection.
     *
     * @param s the sprite object want to add.
     */
    public void addSprite(Sprite s) {
        this.spritesList.add(s);
    }

    /**
     * removes a sprite from the collection.
     *
     * @param s the sprite to be removed.
     */
    public void removeSprite(Sprite s) {
        this.spritesList.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     *
     * @param dt the game time counter.
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < this.spritesList.size(); i++) {
            this.spritesList.get(i).timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the draw surface that we draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.spritesList) {
            sprite.drawOn(d);
        }
    }
}
