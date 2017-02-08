package animations;

import sprites.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;


/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private int countFrom;
    private boolean stop;
    private long timeForSleep;
    private SpriteCollection gameScreen;

    /**
     * constructor.
     *
     * @param numOfSeconds amount od time for displaying screen.
     * @param countFrom    number to start countdown from.
     * @param gameScreen   the screen to be displayed.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.timeForSleep = (long) (numOfSeconds / countFrom * 1000);
    }

    /**
     * shows the countdown.
     *
     * @param d  a given surface to run on.
     * @param dt is the amount to move.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        Sleeper sleeper = new Sleeper();
        d.setColor(Color.white);
        if (this.countFrom == 0) {
            d.drawText(380, 300, "GO", 50);
        } else if (this.countFrom == -1) {
            this.stop = true;
        } else {
            d.drawText(400, 300, String.valueOf(countFrom), 50);
        }
        this.countFrom--;
        sleeper.sleepFor(this.timeForSleep);
    }

    /**
     * a method to determine whether the animation should stop.
     *
     * @return true if should stop, otherwise false.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
