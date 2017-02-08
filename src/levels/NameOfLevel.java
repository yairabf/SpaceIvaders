package levels;

import gameopperating.GameLevel;
import sprites.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * a sprite that write the name of the level on the screen.
 */
public class NameOfLevel implements Sprite {
    private String nameOfLevel;

    /**
     * constructor.
     *
     * @param name is the name to be written on the screen.
     */
    public NameOfLevel(String name) {
        this.nameOfLevel = name;
    }

    /**
     * draws the name on the top right side of the screen.
     *
     * @param d the draw surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(630, 15, this.nameOfLevel, 15);
    }

    @Override
    public void timePassed(double dt) {

    }

    /**
     * adds to a given game.
     *
     * @param g the game to add the sprite.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
