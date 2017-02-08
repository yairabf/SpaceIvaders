package geometricshapes;

/**
 * the class is an object represents a point.
 */
public class Point {
    private double x;
    private double y;

    /**
     * constructor.
     *
     * @param x the x value of the point.
     * @param y the y value of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * distance -- return the distance of this point to the other point.
     *
     * @param other the point that we want the check her length from our
     *              point
     * @return the distance between the two points
     */
    public double distance(Point other) {
        return Math.abs(Math.sqrt(Math.pow((this.x - other.getX()), 2) + Math.pow((this.y - other.getY()), 2)));
    }

    /**
     * equals -- return true is the points are equal, false otherwise.
     *
     * @param other the point that we compare with.
     * @return whether the points are equal or not
     */
    public boolean equals(Point other) {
        return (this.x == other.getX() && this.y == other.getY());
    }

    /**
     * Return the x and y values of this point.
     *
     * @return the x value of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y and y values of this point.
     *
     * @return the y value of the point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Set the x value of this point.
     *
     * @param xOther the x value of the point.
     */
    public void setX(double xOther) {
        this.x = xOther;
    }

    /**
     * set the y value of this point.
     *
     * @param yOther the y value of the point.
     */
    public void setY(double yOther) {
        this.y = yOther;
    }

    /**
     * the method checks if point is inside rectangle.
     *
     * @param rect the rectangle we check if the point can be found in it.
     * @return if the point inside or not.
     */
    public boolean pointInRectangle(Rectangle rect) {
        return ((rect.getUpperLeft().getX() <= this.getX()) && (this.getX() <= rect.getDownerLine().end().getX())
                && (rect.getUpperLeft().getY() <= this.getY()) && (this.getY() <= rect.getDownerLine().end().getY()));
    }
}