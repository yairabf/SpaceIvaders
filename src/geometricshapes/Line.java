package geometricshapes;

import gameopperating.GameLevel;
import sprites.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;

/**
 * class that represent the object Line.
 */
public class Line implements Sprite {
    private Point start;
    private Point end;
    private Color color;


    /**
     * constructors.
     *
     * @param start is the point that the line start.
     * @param end   is the point which the line ends.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * constructors.
     *
     * @param x1 is the x value of the start point
     * @param y1 is the y value of the start point
     * @param x2 is the x value of the end point
     * @param y2 is the y value of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Return the length of the line.
     *
     * @return length of the line
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        return new Point((end.getX() + start.getX()) / 2,
                (end.getY() + start.getY()) / 2);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     *
     * @param other is the other line that we check if our line intersect
     *              with
     * @return if the lines intersect or not.
     */
    public boolean isIntersecting(Line other) {
        return (intersectionWith(other) != null);
    }

    /**
     * Returns the intersection point if the lines intersect,
     * and null otherwise.
     *
     * @param other is the other line that we check if our line intersect
     *              with
     * @return the intersection point of two lines in case they are intersect,
     * and null in case they are not.
     */
    public Point intersectionWith(Line other) {
            /*in case both of the line have the same slope*/
        if (lineIsVertical(this) && lineIsVertical(other)) {
            return null;
        } else if (!lineIsVertical(this) && !lineIsVertical(other) && (slope(this) == slope(other))) {
            return null;
        } else {
            Point p;
            /*in case the first line is vertical*/
            if (lineIsVertical(this)) {
                p = verticalLineIntersection(this, other);
                /*in case the second line is vertical*/
            } else if (lineIsVertical(other)) {
                p = verticalLineIntersection(other, this);
                /*check the regular case by calculating the lines equations*/
            } else {
                //first line slope
                double slopeFirst = slope(this);
                //second line slope
                double slopeSecond = slope(other);
                //the b value of the first line equation
                double bFirst = this.start.getY() - (slopeFirst * this.start.getX());
                //the b value of the second line equation
                double bSecond = other.start().getY() - (slopeSecond * other.start().getX());
                double x = ((bSecond - bFirst) / (slopeFirst - slopeSecond));
                double y = ((slopeFirst * x) + bFirst);
                p = new Point(x, y);
                /*check if the lines intersect*/
            }
            if (this.pointOnTheLines(p, other)) {
                return p;
            } else {
                return null;
            }
        }
    }

    /**
     * the method check if a point can be found on the two lines.
     *
     * @param p      the point that we want to check if it can be found on
     *               a specific line.
     * @param second the second specific line which on him we
     *               want to check if the point can be found
     * @return if the point is on both line or not.
     */
    public boolean pointOnTheLines(Point p, Line second) {
        /*check if the x value of the intersection point is between the first
        * line x'es values*/
        /*the epsilon variable makes sure if the lines intersect near enough
        * it will still count as an intersection*/
        double epsilon = 0.0001;
        return (Math.min(this.start().getX(), this.end().getX()) <= p.getX() + epsilon
                && p.getX() <= Math.max(this.start().getX(), this.end().getX()) + epsilon
                && (Math.min(this.start().getY(), this.end().getY()) <= p.getY() + epsilon
                && p.getY() <= Math.max(this.start().getY(), this.end().getY()) + epsilon)
                && Math.min(second.start().getX(), second.end().getX()) <= p.getX() + epsilon
                && p.getX() <= Math.max(second.start().getX(), second.end().getX()) + epsilon
                && Math.min(second.start().getY(), second.end().getY()) <= p.getY() + epsilon
                && p.getY() <= Math.max(second.start().getY(), second.end().getY()) + epsilon);
    }

    /**
     * equals -- return true is the lines are equal, false otherwise.
     * the method check both start points and end points of the two lines.
     *
     * @param other the other line that we check with if the line are equal.
     * @return if the lines are equal or not.
     */
    public boolean equals(Line other) {
        return ((this.start.equals(other.start())
                && this.end.equals(other.end())))
                || (this.start.equals(other.end()))
                && this.end.equals(other.start());
    }

    /**
     * the method calculate the slope of the line equation.
     *
     * @param l is the line that we want to check it's slope.
     * @return a string that contains the slope value.
     */
    public double slope(Line l) {
            /*use the equations of slope equation m = (y2-y1)/(x2-x1)*/
        return (l.end().getY() - l.start().getY()) / (l.end().getX() - l.start().getX());
    }

    /**
     * calculate the intersection of two lines in the case on of them is vertical.
     * the method uses the line equation of the non vertical line to find the intersection point.
     *
     * @param vertical the vertical line of the two lines.
     * @param second   the non vertical line of the two lines.
     * @return the intersection point.
     */
    public Point verticalLineIntersection(Line vertical, Line second) {
        //convert the slop of the line into a double variable
        double slop = slope(second);
        //calculate the b value of the line equation(y=mx+b)
        double b = second.start().getY() - (slop * second.start().getX());
                /*check if the intersection point is on both lines*/
        double y = (slop * vertical.start().getX()) + b;
        return new Point(vertical.start.getX(), y);
    }

    /**
     * return if the line is vertical or not.
     *
     * @param line the line we check.
     * @return if the line id vertical or not.
     */
    public boolean lineIsVertical(Line line) {
        return line.start().getX() == line.end().getX();
    }

    /**
     * method that find the closest intersection point to the start of the line.
     *
     * @param rect the rectangle the line intersect with.
     * @return the closest intersection point.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List points = rect.intersectionPoints(this);
        Point p = this.end;
        if (points.size() == 0) {
            return null;
        }
        int i = 0;
        while (i < points.size()) {
            if (this.start.distance((Point) points.get(i)) <= this.start.distance(p)) {
                p = (Point) points.get(i);
            }
            i++;
        }
        return p;
    }

    /**
     * checks if points intersect.
     *
     * @param intersection the other point to be checked with.
     * @return true if intersect otherwise false.
     */
    public boolean intersectOnTheLine(Point intersection) {
        if (intersection.getX() <= this.start().getX() && intersection.getX() >= this.end().getX()
                && intersection.getY() <= this.start().getY() && intersection.getY() >= this.end().getY()) {
            return true;
        }
        return false;
    }

    /**
     * setts the color of the line.
     *
     * @param c is the color.
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawLine((int) this.start.getX(), (int) this.start.getY(), (int) this.end.getX(), (int) this.end.getY());
    }

    /**
     * notify the sprite that time has passed.
     * @param dt the time the passed since the beginning of the operation
     */
    public void timePassed(double dt) {
    }

    /**
     * add a sprite into game object depends on his feature.
     *
     * @param g the game to add the sprite.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
