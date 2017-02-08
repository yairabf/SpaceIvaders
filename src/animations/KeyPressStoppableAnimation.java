package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Class that represent a object that charge of the continuance of animations that their ending depends on some key
 * pressing.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * constructor.
     *
     * @param s the keyboard.
     * @param k the kay that resume the animation.
     * @param a the animation.
     */
    public KeyPressStoppableAnimation(KeyboardSensor s, String k, Animation a) {
        this.sensor = s;
        this.key = k;
        this.animation = a;
        this.isAlreadyPressed = true;
    }

    /**
     * does a frame for the animation.
     *
     * @param d  a given surface to run on.
     * @param dt the time that takes to do the operation.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (!this.sensor.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }
        if (!this.isAlreadyPressed && this.sensor.isPressed(this.key)) {
            this.stop = true;
        }
    }


    /**
     * the method tells us if the animation should stop.
     *
     * @return true false.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
