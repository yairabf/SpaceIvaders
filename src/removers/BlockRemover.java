package removers;

import ballattributes.Ball;
import collidables.Collidable;
import gameopperating.Counter;
import gameopperating.GameLevel;
import listeners.HitListener;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constructor.
     *
     * @param game           the game this class removes blocks for.
     * @param blocksToRemove how many block remain.
     */
    public BlockRemover(GameLevel game, Counter blocksToRemove) {
        this.game = game;
        this.remainingBlocks = blocksToRemove;
    }

    /**
     * Blocks that are hit and reach 0 hit-points will be removed.
     *
     * @param beingHit is the block being hit.
     * @param hitter   is the hitting ball.
     */
    @Override
    public void hitEvent(Collidable beingHit, Ball hitter) {
        if (!beingHit.isAlien() || hitter.getVelocity().getDy() < 0) {
            //removes the block from the game
            beingHit.removeFromGame(this.game);
            //removes this listener from the being hit's listeners list.
            beingHit.removeHitListener(this);
            //decreasing the amount of blocks by 1.
            this.remainingBlocks.decrease(1);
        }
        if (this.remainingBlocks.getValue() == 0) {
            this.game.getLevelInformation().initEmptinnes();
        }
    }
}
