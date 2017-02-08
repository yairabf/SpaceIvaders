package sprites;

import ballattributes.Ball;
import ballattributes.Velocity;
import biuoop.DrawSurface;
import collidables.Block;
import gameopperating.GameEnvironment;
import gameopperating.GameLevel;
import geometricshapes.Point;
import listeners.HitListener;
import listeners.HitNotifier;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Class that represent the formation that the aliens move.
 */
public class FormationCharge implements Sprite, HitNotifier {
    private List<List<Block>> formation;
    private double speed;
    private GameEnvironment gameEnvironment;
    private GameLevel gameLevel;
    private double startX;
    private double startY;
    private double currentTime;


    /**
     * constructor.
     *
     * @param formation a list of lists that holds all the aliens.
     * @param speed     the formation's speed.
     * @param startX    the 'x' value of the top left position of the leftest alien
     * @param startY    the 'y' value of the top left position of the leftest alien
     */
    public FormationCharge(List<List<Block>> formation, double speed, double startX, double startY) {
        this.formation = formation;
        this.speed = speed;
        this.startX = startX;
        this.startY = startY;
        this.currentTime = 0;
    }

    /**
     * setter for the formation's speed.
     *
     * @param speed1 thee speed we want to set.
     */
    public void setSpeed(double speed1) {
        this.speed = speed1;

    }

    /**
     * the method moving all the aliens right.
     *
     * @param dt the number of second that passed since the last time the function been called.
     */
    private void moveRight(double dt) {
        double nextX = this.getMostRight() + this.speed * dt;
        if (nextX >= 800) {
            this.setYForAllBlocks();
            this.speed = this.speed * -1.1;
            this.setXForAllBlocks();
        } else {
            this.setXForAllBlocks();
        }
    }

    /**
     * the method moving all the aliens left.
     *
     * @param dt the number of second that passed since the last time the function been called.
     */
    private void moveLeft(double dt) {
        double nextX = this.getMostLeft() + (this.speed * dt);
        if (nextX <= 0) {
            this.setYForAllBlocks();
            this.speed *= -1.1;
            this.setXForAllBlocks();
        } else {
            this.setXForAllBlocks();
        }
    }

    /**
     * the method set all the block new location according to theirs 'x' value.
     */
    private void setXForAllBlocks() {
        for (List<Block> column : this.formation) {
            for (Block block : column) {
                double nextX = block.getRect().getUpperLeft().getX() + this.speed;
                block.getRect().setTopLeftX(nextX);
            }
        }
    }

    /**
     * the method set all the block new location according to theirs 'y' value.
     */
    private void setYForAllBlocks() {
        for (int i = 0; i < this.formation.size(); i++) {
            for (Block block : formation.get(i)) {
                double nextY = block.getRect().getUpperLeft().getY() + block.getRect().getHeight() / 2;
                block.getRect().setTopLeftY(nextY);
            }
        }
    }

    /**
     * the method create balls from the columns locations and shoots them.
     */
    private void shootingBalls() {
        Random random = new Random();
        int column = random.nextInt(this.formation.size());
        Block lowestBlockInColumn = this.formation.get(column).get(this.formation.get(column).size() - 1);
        double xLocation = lowestBlockInColumn.getRect().getUpperLeft().getX()
                + lowestBlockInColumn.getRect().getWidth() / 2;
        double yLocation = lowestBlockInColumn.getRect().getUpperLeft().getY()
                + lowestBlockInColumn.getRect().getHeight() + 10;
        Point location = new Point(xLocation, yLocation);
        Ball shootingBall = new Ball(location, 3, Color.RED);
        Velocity velocity = Velocity.fromAngleAndSpeed(180, 350);
        shootingBall.setVelocity(velocity);
        shootingBall.setGame(this.gameEnvironment);
        this.gameLevel.addSprite(shootingBall);
    }

    /**
     * the method returns the 'x' value of the most left block's location.
     *
     * @return the 'x' value of the most left block's location.
     */
    private double getMostRight() {
        return this.formation.get(this.formation.size() - 1).get(0).getRect().getUpperLeft().getX()
                + this.formation.get(this.formation.size() - 1).get(0).getRect().getWidth();
    }

    /**
     * the method return the 'x' value of the top left corner of the leftest aliens.
     *
     * @return the 'x' value of the top left corner of the leftest aliens
     */
    private double getMostLeft() {
        return this.formation.get(0).get(0).getRect().getUpperLeft().getX();
    }

    /**
     * the method returns the location that the lowest block reach.
     *
     * @return the location that the lowest block reach.
     */
    public double getMostDown() {
        double mostDown = 0;
        this.removeEmptyColumns();
        for (int i = 0; i < this.formation.size(); i++) {
            Block currentMostDownBlock = this.formation.get(i).get(this.formation.get(i).size() - 1);
            double currentMostDownLocation = currentMostDownBlock.getRect().getUpperLeft().getY()
                    + currentMostDownBlock.getRect().getHeight();
            if (currentMostDownLocation >= mostDown) {
                mostDown = currentMostDownLocation;
            }
        }
        return mostDown;
    }

    /**
     * will remove all the enpty columns of enemies.
     */
    public void removeEmptyColumns() {
        for (int i = 0; i < this.formation.size(); i++) {
            if (this.formation.get(i).isEmpty()) {
                this.formation.remove(i);
            }
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        for (List<Block> column : this.formation) {
            for (Block block : column) {
                block.drawOn(d);
            }
        }
    }

    @Override
    public void timePassed(double dt) {
        this.removeDeadBlocks();
        this.updateFormation();
        if (this.formation.size() > 0) {
            if (this.speed > 0) {
                this.moveRight(dt);
            } else {
                this.moveLeft(dt);
            }
            this.currentTime = this.currentTime + dt;
            if (this.currentTime >= 0.5) {
                this.shootingBalls();
                this.currentTime = 0;
            }
        }
    }

    /**
     * removes blocks that die.
     */
    private void removeDeadBlocks() {
        for (List<Block> blockColumn : this.formation) {
            for (Block b : blockColumn) {
                if (b.hasBeenHit()) {
                    blockColumn.remove(b);
                    break;
                }
            }
        }
    }


    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        for (int i = 0; i < this.formation.size(); i++) {
            for (Block block : this.formation.get(i)) {
                g.addCollidable(block);
            }
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        for (int i = 0; i < this.formation.size(); i++) {
            for (Block block : this.formation.get(i)) {
                block.addHitListener(hl);
            }
        }
    }

    @Override
    public void removeHitListener(HitListener hl) {
        for (int i = 0; i < this.formation.size(); i++) {
            for (Block block : this.formation.get(i)) {
                block.removeHitListener(hl);
            }
        }
    }

    /**
     * thew method set the game environment for the formation charge.
     *
     * @param game game environment.
     */
    public void setGameEnvironment(GameEnvironment game) {
        this.gameEnvironment = game;
    }

    /**
     * the method set the formation at the start location.
     */
    public void setStartFormation() {
        for (int i = 0; i < this.formation.size(); i++) {
            for (int j = 0; j < this.formation.get(i).size(); j++) {
                this.formation.get(i).get(j).getRect().setTopLeftX(this.startX + 50 * i);
                this.formation.get(i).get(j).getRect().setTopLeftY(this.startY + 40 * j);

            }
        }
    }

    /**
     * removes aliens columns.
     */
    private void updateFormation() {
        for (int i = 0; i < this.formation.size(); i++) {
            if (this.formation.get(i).size() == 0) {
                this.formation.remove(i);
            }
        }
    }

    /**
     * checks if the formation is empty.
     *
     * @return true or false.
     */
    public boolean isEmpty() {
        boolean isEmpty = true;
        for (int i = 0; i < this.formation.size(); i++) {
            if (!this.formation.get(i).isEmpty()) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    /**
     * setter for the game level.
     *
     * @param gameLevel1 the game level we want to set
     */
    public void setGameLevel(GameLevel gameLevel1) {
        this.gameLevel = gameLevel1;
    }


}