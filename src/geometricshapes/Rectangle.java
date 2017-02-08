package geometricshapes;

import java.util.ArrayList;

/**
 * class that represents rectangle.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * constructor using a start point and width and height.
     *
     * @param upperLeft is the point that in the upper left of the rectangle.
     * @param width     is the width of the rectangle.
     * @param height    is the height oif the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * constructor using the x and y values of the upper left point and width and height.
     *
     * @param xLeftUp the x of the top left point.
     * @param yLeftUp the y of th top left point.
     * @param width   the width of the rectangle.
     * @param height  the height of the rectangle.
     */
    public Rectangle(double xLeftUp, double yLeftUp, double width, double height) {
        this.upperLeft = new Point(xLeftUp, yLeftUp);
        this.width = width;
        this.height = height;
    }

    /**
     * method that create list of all the intersection points that the rectangle has with a line.
     *
     * @param line the line that intersect with the rectangle.
     * @return a list of all the intersection points.
     */
    public java.util.List intersectionPoints(Line line) {
        //create a list which hold the intersection points
        java.util.List<Point> pointsList = new ArrayList<>();
        //initiate an array full with the rectangle line.
        Line[] lines = this.getArrayLines();
        /*checks for each line if it intersects with the current line.*/
        for (Line item : lines) {
            if (line.isIntersecting(item)) {
                //save the intersection point.
                Point intersection = item.intersectionWith(line);
                pointsList.add(intersection);
            }
        }
        return pointsList;
    }

    /**
     * return the upper left point of the rectangle.
     *
     * @return the upper left point.
     */

    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * return the height of the rectangle.
     *
     * @return the height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * return the width of the rectangle.
     *
     * @return the width.
     */
    public double getWidth() {

        return width;
    }

    /**
     * return the upper line of the rectangle's scope.
     *
     * @return the upper line.
     */
    public Line getUpperLine() {
        Point end = new Point(this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY());
        return new Line(this.getUpperLeft(), end);
    }

    /**
     * return the downer line of the rectangle's scope.
     *
     * @return the downer line.
     */
    public Line getDownerLine() {
        Point start = new Point(this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.getHeight());
        Point end = new Point(this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY()
                + this.getHeight());
        return new Line(start, end);
    }

    /**
     * return the left line of the rectangle's scope.
     *
     * @return the left line.
     */
    public Line getLeftLine() {
        Point end = new Point(this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.getHeight());
        return new Line(this.getUpperLeft(), end);
    }

    /**
     * return the right line of the rectangle's scope.
     *
     * @return the right line.
     */
    public Line getRightLine() {
        Point start = new Point(this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY());
        Point end = new Point(this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY()
                + this.getHeight());
        return new Line(start, end);

    }

    /**
     * the method return an array full with the rectangle's lines.
     *
     * @return the array full with lines.
     */
    public Line[] getArrayLines() {
        Line[] arrayLines = new Line[4];
        arrayLines[0] = this.getLeftLine();
        arrayLines[1] = this.getRightLine();
        arrayLines[2] = this.getUpperLine();
        arrayLines[3] = this.getDownerLine();
        return arrayLines;
    }

    /**
     * getter.
     *
     * @return the center of the point.
     */
    public Point getCenterPoint() {
        return new Point(this.getUpperLine().middle().getX(), this.getLeftLine().middle().getY());
    }

    /**
     * sets the top left point of the rectangle.
     *
     * @param p the new point.
     */
    public void setTopLeft(Point p) {
        this.upperLeft = p;
    }

    /**
     * setting the x for the top left point.
     *
     * @param x the x for the point.
     */
    public void setTopLeftX(double x) {
        this.upperLeft.setX(x);
    }

    /**
     * setting the y for the top left point.
     *
     * @param y is the y axis to set to.
     */
    public void setTopLeftY(double y) {
        this.upperLeft.setY(y);
    }

    /**
     * returns the line with the closest intersection.
     *
     * @param l         the line.
     * @param collision the collission point.
     * @return the line with the closest intersection.
     */
    public Line lineWithClosestcollision(Line l, Point collision) {
        Line[] lines = this.getArrayLines();
        for (Line item : lines) {
            if (item.isIntersecting(l) && item.intersectionWith(l).equals(collision)) {
                return item;
            }
        }
        return null;
    }
}
