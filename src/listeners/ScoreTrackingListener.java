package listeners;

import ballattributes.Ball;
import collidables.Collidable;
import gameopperating.Counter;

/**
 * a class that listens to the game and updates the score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * constructor.
     *
     * @param scoreCounter the score to start with.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * performs hit event and updates the score.
     *
     * @param beingHit is the block being hit.
     * @param hitter   parameter is the Ball that's doing the hitting.
     */
    public void hitEvent(Collidable beingHit, Ball hitter) {
            this.currentScore.increase(100);
    }
}
