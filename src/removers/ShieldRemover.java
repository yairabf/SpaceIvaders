package removers;

import ballattributes.Ball;
import collidables.Collidable;
import gameopperating.GameLevel;
import listeners.HitListener;

/**
 * removes the shield blocks.
 */
public class ShieldRemover implements HitListener {
    private GameLevel game;

    /**
     * constructor.
     *
     * @param game          the game this class removes blocks for.
     */
    public ShieldRemover(GameLevel game) {
        this.game = game;
    }

    /**
     * Blocks that are hit and reach 0 hit-points will be removed.
     *
     * @param beingHit is the block being hit.
     * @param hitter   is the hitting ball.
     */
    @Override
    public void hitEvent(Collidable beingHit, Ball hitter) {
        //removes the block from the game
        beingHit.removeFromGame(this.game);
        //removes this listener from the being hit's listeners list.
        beingHit.removeHitListener(this);
    }
}
