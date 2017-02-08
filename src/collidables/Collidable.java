package collidables;

import ballattributes.Ball;
import gameopperating.GameLevel;
import geometricshapes.Rectangle;
import listeners.HitListener;

/**
 * interface for objects that can be collide with.
 */
public interface Collidable {
    /**
     * return the "collision shape" of the object.
     *
     * @return the collision shape.
     */
    Rectangle getCollisionRectangle();

    /**
     * the method does thing according the type of the collidable.
     *
     * @param hitter the ball that hit the object.
     */
    void hit(Ball hitter);

    /**
     * the method remove the object from the a game level.
     *
     * @param g the game we want to remove our object.
     */
    void removeFromGame(GameLevel g);

    /**
     * the method remove a listener from the collidable.
     *
     * @param h the hit listener.
     */
    void removeHitListener(HitListener h);

    /**
     * The method return if the collidable is an alien or not.
     *
     * @return if the collidable is an alien or not.
     */
    boolean isAlien();


}
