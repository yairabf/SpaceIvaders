package collidables;

import ballattributes.Ball;
import gameopperating.Counter;
import gameopperating.GameLevel;
import geometricshapes.Point;
import geometricshapes.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;
import sprites.Sprite;
import biuoop.DrawSurface;


import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


/**
 * class that represent block object.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private List<Color> fillColor = new ArrayList<>();
    private Color strokeColor;
    private List<Image> image = new ArrayList<>();
    private Counter hitPoints;
    private List<HitListener> hitListeners = new ArrayList<>();
    private boolean hasBeenHit;
    private boolean isAlien;

    /**
     * The method return if the block is an alien block or not.
     *
     * @return if the block is an alien block or not.
     */
    public boolean isAlien() {
        return isAlien;
    }

    /**
     * The method set the boolean parameter if the block is an alien block or not.
     *
     * @param alien true or false.
     */
    public void setAlien(boolean alien) {
        isAlien = alien;
    }

    /**
     * constructor.
     *
     * @param rect  the rectangle that represent the block.
     * @param color the color of the block.
     * @param hits  the number of hits.
     */
    public Block(Rectangle rect, Color color, int hits) {
        this.rect = rect;
        this.setFillColor(color);
        this.hitPoints = new Counter(hits);
    }

    /**
     * constructor.
     *
     * @param xLeftUp the top left x axis.
     * @param yLeftUp the top left y axis.
     * @param width   the width of th block.
     * @param height  the height of the block.
     * @param color   the color of the block.
     * @param hits    the number of hits the block starts with.
     */
    public Block(double xLeftUp, double yLeftUp, double width, double height, Color color, int hits) {
        this.rect = new Rectangle(xLeftUp, yLeftUp, width, height);
        this.setFillColor(color);
        this.hitPoints = new Counter(hits);
    }

    /**
     * Third constructor.
     *
     * @param topLeft     the location of the top left corner of the block.
     * @param width       the width of the block.
     * @param height      the height of the block.
     * @param fillColors  list of all the block's colors.
     * @param strokeColor the color of the stroke of the block.
     * @param hitPoints   number of hits the block can be hit.
     */
    public Block(Point topLeft, int width, int height, List<Color> fillColors, Color strokeColor, int hitPoints) {
        this.rect = new Rectangle(topLeft, width, height);
        this.fillColor = fillColors;
        this.strokeColor = strokeColor;
        this.hitPoints = new Counter(hitPoints);
    }

    /**
     * getter for the list of block's colors.
     *
     * @return list of the block's color.
     */
    private List<Color> getFillColor() {
        return fillColor;
    }

    /**
     * setter for the block's color.
     *
     * @param fillColor1 the color we want to add.
     */
    private void setFillColor(Color fillColor1) {
        this.fillColor.add(fillColor1);
    }

    /**
     * setter for the block's image.
     *
     * @param image1 the image we want to set.
     */
    public void setImage(Image image1) {
        this.image.add(image1);
    }

    /**
     * getter for the block shape.
     *
     * @return the rectangle that represent the block.
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * return the "collision shape" of the object.
     *
     * @return the collision shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * returns the number of hits left on the block.
     *
     * @return the number of hits left.
     */
    public int getHitPoints() {
        return this.hitPoints.getValue();
    }

    /**
     * Notify the object we collided with it at collisionPoint with a given velocity.
     *
     * @param hitter is the ball hitting.
     */
    public void hit(Ball hitter) {
        if (!this.isAlien() || hitter.getVelocity().getDy() < 0) {
            this.hasBeenHit = true;
        }
        //notifies all listeners that block has been hit.
        this.notifyHit(hitter);
    }

    /**
     * @return true if the block has been hit otherwise false.
     */
    public boolean hasBeenHit() {
        return this.hasBeenHit;
    }

    /**
     * the method drew the block on the surface.
     *
     * @param d the draw surface we draw on.
     */
    public void drawOn(DrawSurface d) {
        /*checks if there an image to drew.*/
        if (this.image.size() > 0) {
            d.drawImage((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                    this.image.get(0));
        } else {
            /*if the color isn't depends on hitting(like backgrounds or frame limits).*/
            if (this.getHitPoints() == -1 || this.hitPoints == null) {
                d.setColor(this.fillColor.get(0));
            } else {
                d.setColor(this.fillColor.get(this.getFillColor().size() - this.hitPoints.getValue()));
            }
            d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                    (int) rect.getWidth(), (int) rect.getHeight());
        }
        /*checks if there a stroke to drew.*/
        if (this.strokeColor != null) {
            d.setColor(this.strokeColor);
            d.drawRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                    (int) rect.getWidth(), (int) rect.getHeight());
        }
    }

    /**
     * notifies the block that time has passed.
     *
     * @param dt the game time counter.
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * adds the block to the game.
     *
     * @param g the game to be added to.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * removes the block from the game, from the sprite and the collidable collections.
     *
     * @param game is the game the block should be removed from.
     */

    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl is the listener to be added.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }


    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl is the listener to be removed.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * call this method whenever a hit occurs.
     * notifies all listeners that this block has been hit.
     *
     * @param hitter the ball hitting this block.
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
