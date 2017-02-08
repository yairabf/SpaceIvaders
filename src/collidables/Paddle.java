package collidables;

import ballattributes.Ball;
import ballattributes.Velocity;
import gameopperating.GameEnvironment;
import gameopperating.GameLevel;
import geometricshapes.Point;
import geometricshapes.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;
import sprites.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * this class creates a paddle for the game.
 */
public class Paddle implements Sprite, Collidable, HitNotifier {
    private KeyboardSensor keyboard;
    private Rectangle rect;
    private Image image;
    private boolean beenHit;
    private double speed;
    private List<HitListener> hitListeners = new ArrayList<>();
    private GameEnvironment gameEnvironment;
    private double currentTime = 0;
    private GameLevel gameLevel;

    /**
     * * constructor.
     *
     * @param keyboard        the keyboard to control the paddle.
     * @param gameEnvironment the game environment of the paddle.
     * @param level           the game level the we use the paddle.
     */
    public Paddle(KeyboardSensor keyboard, GameEnvironment gameEnvironment, GameLevel level) {
        this.rect = new Rectangle(360, 550, 79, 55);
        this.keyboard = keyboard;
        this.speed = 400;
        this.gameEnvironment = gameEnvironment;
        this.beenHit = false;
        this.gameLevel = level;
    }


    /**
     * moves the paddle left.
     *
     * @param dt the game time counter.
     */
    private void moveLeft(double dt) {
        double newX = this.rect.getUpperLeft().getX() - (this.speed * dt);
        if (inBoundaries(newX)) {
            this.rect.setTopLeftX(newX);
        } else {
            this.rect.setTopLeft(new Point(0, this.rect.getUpperLeft().getY()));
        }
    }

    /**
     *
     */
    public void setImage() {
        InputStream image1 =
                ClassLoader.getSystemClassLoader().
                        getResourceAsStream("block_images/rabelShip.png");
        Image img = null;
        try {
            img = ImageIO.read(image1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.image = img;
    }

    /**
     * moves the paddle right.
     *
     * @param dt the game time counter.
     */
    private void moveRight(double dt) {
        double newX = this.rect.getUpperLeft().getX() + (this.speed * dt);
        if (inBoundaries(newX)) {
            this.rect.setTopLeftX(newX);
        } else {
            this.rect.setTopLeft(new Point(800 - this.rect.getWidth(), this.rect.getUpperLeft().getY()));
        }
    }

    /**
     * checks if the paddle is in the boundries.
     *
     * @param x the x variable to be checked.
     * @return true if in boundaries else false.
     */
    private boolean inBoundaries(double x) {
        return (x + this.rect.getWidth() <= 800 && x >= 0);
    }

    /**
     * activates time passed for paddle.moves the paddle accordingly.
     *
     * @param dt the game time counter.
     */
    @Override
    public void timePassed(double dt) {
        if (keyboard.isPressed(keyboard.LEFT_KEY)) {
            this.moveLeft(dt);
        } else if (keyboard.isPressed(keyboard.RIGHT_KEY)) {
            this.moveRight(dt);
        }
        this.currentTime += dt;
        if (this.currentTime >= 0.35) {
            if (keyboard.isPressed(keyboard.SPACE_KEY)) {
                this.shootBalls();
                this.currentTime = 0;
            }
        }
    }

    /**
     * draws the paddle on a gives surface.
     *
     * @param d the drawsurface.
     */
    public void drawOn(DrawSurface d) {
        //d.setColor(this.color);
        //d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
        //        (int) this.rect.getWidth(), (int) this.rect.getHeight());
        //d.setColor(Color.black);
        // d.drawRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
        //         (int) rect.getWidth(), (int) rect.getHeight());
        d.drawImage((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(), this.image);
    }

    /**
     * returns the shape.
     *
     * @return the shape rectangle of object.
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * returns the new velocity of the object that hit the paddle.
     *
     * @param hitter the hitting ball.
     */
    public void hit(Ball hitter) {
        this.notifyHit(hitter);
        this.beenHit = true;
        hitter.removeFromGame(this.gameLevel);
    }


    /**
     * Add this paddle to the game.
     *
     * @param g the game to be added to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * the method tells if the paddle been hit or not.
     *
     * @return if the paddle been hit or not.
     */
    public boolean isBeenHit() {
        return beenHit;
    }

    /**
     * removes the paddle from the game.
     *
     * @param g the game the paddle should be removed from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

    /**
     *
     */
    public void shootBalls() {
        double xLocation = this.rect.getUpperLeft().getX() + (this.rect.getWidth() / 2);
        double yLocation = this.rect.getUpperLeft().getY() - 5;
        Point location = new Point(xLocation, yLocation);
        Ball shootingBall = new Ball(location, 3, Color.RED);
        Velocity velocity = Velocity.fromAngleAndSpeed(0, 350);
        shootingBall.setVelocity(velocity);
        shootingBall.setGame(this.gameEnvironment);
        shootingBall.addToGame(this.gameLevel);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    @Override
    public boolean isAlien() {
        return false;
    }

    /**
     * the method tells all the paddle's listeners that the paddle been hit.
     *
     * @param hitter the ball that hit the paddle
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
