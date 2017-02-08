package animations;

import biuoop.DrawSurface;

/**
 * an animation interface.
 */
public interface Animation {

    /**
     * runs a frame of the animation.
     *
     * @param d  a given surface to run on.
     * @param dt the time that takes to do the operation.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * a method deciding whether the loop should stop or not.
     *
     * @return true if should stop, otherwise false.
     */
    boolean shouldStop();
}
