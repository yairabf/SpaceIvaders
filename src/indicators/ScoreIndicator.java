package indicators;

import gameopperating.Counter;
import gameopperating.GameLevel;
import sprites.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * a class that will show the player's score at the top of the screen.
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreCounter;

    /**
     * constructor.
     *
     * @param c the counter to hold a reference to.
     */
    public ScoreIndicator(Counter c) {
        this.scoreCounter = c;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the drawsurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.fillRectangle(0, 0, 800, 20);
        d.setColor(Color.black);
        d.drawText(380, 15, "score :" + String.valueOf(this.scoreCounter.getValue()), 15);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the game time counter.
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * add a sprite into game object depends on his feature.
     *
     * @param g the game to be added to.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
