package menus;

import animations.Animation;
import animations.AnimationRunner;

/**
 * a task that shows high scores.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * constructor.
     *
     * @param runner              the animation runner..
     * @param highScoresAnimation is the animation to run.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * runs the animation.
     *
     * @return null.
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}
