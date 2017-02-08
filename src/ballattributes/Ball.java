package ballattributes;

import collidables.Collidable;
import collidables.CollisionInfo;
import gameopperating.GameEnvironment;
import gameopperating.GameLevel;
import geometricshapes.Line;
import geometricshapes.Point;
import sprites.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Ball class- an object of a ball.
 */
public class Ball implements Sprite {
    private Point point;
    private int size;
    private Color color;
    private Velocity velocity;
    private GameEnvironment game;

    /**
     * constructor.
     *
     * @param center is the point of the ball is at.
     * @param r      is the r of the ball.
     * @param color  the color of the ball.
     */
    public Ball(Point center, int r, Color color) {
        this.point = center;
        this.size = Math.abs(r);
        this.color = color;
    }

    /**
     * constructor.
     *
     * @param x     the x placement on the axis.
     * @param y     the y placement on the axis.
     * @param r     the radius of the ball.
     * @param color the color of the ball.
     */
    public Ball(int x, int y, int r, Color color) {
        this.point = new Point(x, y);
        this.size = Math.abs(r);
        this.color = color;
    }

    /**
     * sets the game environment.
     *
     * @param game1 the game environment.
     */
    public void setGame(GameEnvironment game1) {
        this.game = game1;
    }

    /**
     * returns the x value of the ball.
     *
     * @return returns the x value.
     */
    public int getX() {
        return (int) this.point.getX();
    }

    /**
     * returns the y vlue of the ball.
     *
     * @return returns the y value.
     */
    public int getY() {
        return (int) this.point.getY();
    }

    /**
     * returns the size of the ball.
     *
     * @return returns the size.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * returns the balls color.
     *
     * @return returns the color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * getter for the game environment.
     *
     * @return the game environment of the ball.
     */
    public GameEnvironment getGame() {
        return game;
    }

    /**
     * gets the center point value.
     *
     * @return returns the center point.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * the method draws the ball on a given surface.
     *
     * @param surface is the drawsurface given.
     */
    public void drawOn(DrawSurface surface) {
        if (this.velocity != null) {
            surface.setColor(Color.BLACK);
            surface.drawCircle(this.getX(), this.getY(), this.size);
        }
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.size);
    }

    @Override
    public void timePassed(double dt) {
        moveOneStep(dt);
    }

    /**
     * sets the velocity of the ball.
     *
     * @param vel the given velocity.
     */
    public void setVelocity(Velocity vel) {
        this.velocity = vel;
    }

    /**
     * return the velocity of the ball.
     *
     * @return returns the velocity.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * the method move the ball one step using his velocity.
     *
     * @param dt is the amount to move.
     */
    public void moveOneStep(double dt) {
        //get the course of the ball as aline
        Line trajectory = this.computeTrajectory(this.getPoint(), dt);
        //receives the info regarding the collision point and shape.
        CollisionInfo currentCollision = this.game.getClosestCollision(trajectory);
        if (currentCollision != null) {
            Collidable block = currentCollision.collisionObject();
            Point collisionPoint = currentCollision.collisionPoint();
            //moves the ball almost to the collision point
            this.moveCenterToAlmostCollision(collisionPoint);
            this.point = this.getVelocity().applyToPoint(this.point, dt);
            block.hit(this);
        } else {
            this.point = this.getVelocity().applyToPoint(this.point, dt);
        }
    }

    /**
     * once a collision is detected the method moves the ball to almost the collision point.
     *
     * @param collisionPoint the collision point.
     */
    public void moveCenterToAlmostCollision(Point collisionPoint) {
        Line newLine = new Line(this.point, collisionPoint);
        this.point = newLine.middle();
        Line newLine1 = new Line(this.point, collisionPoint);
        this.point = newLine1.middle();
        Line newLine2 = new Line(this.point, collisionPoint);
        this.point = newLine2.middle();
    }

    /**
     * the method compute the movement in case the ball didn't colluded with any object on its way.
     *
     * @param startLocation the ball on its start location.
     * @param dt            is the amount to move.
     * @return the "way" the ball does.
     */
    public Line computeTrajectory(Point startLocation, double dt) {
        Point endLocation = new Point(startLocation.getX() + this.getVelocity().getDx() * dt,
                startLocation.getY() + this.getVelocity().getDy() * dt);
        return new Line(startLocation, endLocation);
    }

    /**
     * adds the ball to the game.
     *
     * @param g is the game level given.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * removes the ball from a given game.
     *
     * @param g the game the ball should be removed from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}