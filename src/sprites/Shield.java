package sprites;

import biuoop.DrawSurface;
import collidables.Block;
import gameopperating.GameLevel;
import listeners.HitListener;
import listeners.HitNotifier;

import java.util.List;

/**
 * Class that represent the ship shield.
 */
public class Shield implements Sprite, HitNotifier {
    private List<Block> shield;

    /**
     * Constructor.
     *
     * @param shield the list of blocks.
     */
    public Shield(List<Block> shield) {
        this.shield = shield;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        for (Block shieldBlock : this.shield) {
            shieldBlock.drawOn(d);
        }
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the time the passed since the beginning of the operation
     */
    @Override
    public void timePassed(double dt) {
        this.removeDeadBlocks();
    }

    /**
     * add a sprite into game object depends on his feature.
     *
     * @param g the game to add the sprite.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        for (Block shieldBlock : this.shield) {
            g.addCollidable(shieldBlock);
        }
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl is the listener to be added.
     */
    @Override
    public void addHitListener(HitListener hl) {
        for (Block shieldBlock : this.shield) {
            shieldBlock.addHitListener(hl);
        }
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl is the listener to be removed.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        for (Block shieldBlock : this.shield) {
            shieldBlock.removeHitListener(hl);
        }
    }

    /**
     * removes blocks that die.
     */
    private void removeDeadBlocks() {
        for (Block b : this.shield) {
            if (b.hasBeenHit()) {
                this.shield.remove(b);
                break;
            }
        }
    }
}
