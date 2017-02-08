package ballattributes;

import geometricshapes.Point;

/**
 * A class of an object velocity.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor.
     *
     * @param dx the change on x axis.
     * @param dy the change on the y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * returns the dx variable.
     *
     * @return returns dx.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * returns the dy variable.
     *
     * @return returns dy.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * converts angle and speed to x and y changes on axis.
     *
     * @param angle the angle.
     * @param speed the speed movement.
     * @return a velocity with dx dy.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.round(Math.cos(Math.toRadians(angle - 90)) * speed);
        double dy = Math.round(Math.sin(Math.toRadians(angle - 90)) * speed);
        return new Velocity(dx, dy);
    }

    /**
     * receives a point and returns a new point after velocity changes.
     *
     * @param p the previous point.
     * @param dt the game time counter.
     * @return returns the new point.
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + (dx * dt), p.getY() + (dy * dt));
    }
}