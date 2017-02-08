package removers;

import ballattributes.Ball;
import collidables.Collidable;
import gameopperating.GameLevel;
import listeners.HitListener;

/**
 * a class that helps the game class by removing it's balls when needed.
 */
public class BallRemover implements HitListener {
    private GameLevel game;

    /**
     * constructor.
     *
     * @param g the game this class removes blocks for.
     */
    public BallRemover(GameLevel g) {
        this.game = g;
    }

    /**
     * Blocks that are hit and reach 0 hit-points will be removed.
     *
     * @param beingHit is the death block being hit.
     * @param hitter   is the hitting ball.
     */
    @Override
    public void hitEvent(Collidable beingHit, Ball hitter) {
        if (!beingHit.isAlien() || hitter.getVelocity().getDy() < 0) {
            hitter.removeFromGame(this.game);
        }
    }
}
