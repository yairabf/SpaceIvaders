package sprites;

import gameopperating.GameLevel;
import biuoop.DrawSurface;

/**
 * sprites can be drawn on the screen, and can be notified that time has passed.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the time the passed since the beginning of the operation
     */
    void timePassed(double dt);

    /**
     * add a sprite into game object depends on his feature.
     *
     * @param g the game to add the sprite.
     */
    void addToGame(GameLevel g);
}
