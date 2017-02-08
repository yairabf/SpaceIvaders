package levels;

import sprites.FormationCharge;
import collidables.Block;
import sprites.Shield;
import sprites.Sprite;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Class that represent an object that hold the level information.
 */
public class LevelInformationBySpecification implements LevelInformation {
    private String levelName;
    private FormationCharge formationCharge;
    private Shield shield;
    private double speed;
    private boolean empty;
    private int startX = 10;
    private int startY = 30;
    private Sprite backGround;


    /**
     * constructor.
     */
    public LevelInformationBySpecification() {
        this.speed = 2;
        this.backGround = this.setBackground();
        this.formationCharge = new FormationCharge(this.aliensFormation(), this.speed,
                this.getStartX(), this.getStartY());
        this.shield = new Shield(this.shieldCreator());

        this.empty = formationCharge.isEmpty();
    }

    /**
     * The method set the speed of the level.
     *
     * @param movementSpeed the speed we want to set.
     */
    public void setMovementSpeed(int movementSpeed) {
        this.speed = movementSpeed;
    }

    /**
     * set the mame of the level according to the parameter that been received.
     *
     * @param i number of the level.
     */
    public void setLevelName(int i) {
        this.levelName = "Level " + i;
    }

    /**
     * getter for thew level's name.
     *
     * @return the level name.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * setter for the background.
     *
     * @return the background.
     */
    private Sprite setBackground() {
        Random random = new Random();
        int j = random.nextInt(4);
        Block block = new Block(0, 0, 800, 600, Color.black, -1);
        block.setImage(this.setBackgroundImage(j));
        return block;
    }

    /**
     * getter for the background.
     *
     * @return the background.
     */
    public Sprite getBackground() {
        return this.backGround;
    }


    /**
     * @return the list of blocks.
     */
    public List<List<Block>> aliensFormation() {
        Random random = new Random();
        int j = random.nextInt(4);
        List<List<Block>> formation = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            formation.add(i, this.aliensColumn(this.getStartX() + 50 * i, this.getStartY(), j));
        }
        return formation;
    }

    /**
     * @return the number of blocks to remove.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return 50;
    }

    /**
     * @return the starting x axis for te point.
     */
    private int getStartX() {
        return this.startX;
    }

    /**
     * @return the starting y axis for te point.
     */
    private int getStartY() {
        return this.startY;
    }

    /**
     * the method creates column of aliens stored in a list.
     *
     * @param x the 'x value of the highest alien.
     * @param y the 'y value of the highest alien.
     * @param j random number of the image.
     * @return list of blocks.
     */
    private List<Block> aliensColumn(int x, int y, int j) {
        Image img = this.setAliensImage(j);
        List<Block> column = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Block alien = new Block(x, y + (40 * i), 40, 30, Color.BLUE, 1);
            alien.setImage(img);
            alien.setAlien(true);
            column.add(alien);
        }
        return column;
    }

    /**
     * getter fo the level speed.
     * @return the level speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * getter for the level formation.
     * @return the formation charge.
     */
    public FormationCharge getFormationCharge() {
        return this.formationCharge;
    }

    /**
     * the method tells us if there any aliens left to destroy.
     *
     * @return true or false.
     */
    @Override
    public boolean isEmpty() {
        return this.empty;
    }

    /**
     * the method set if there any aliens left to destroy.
     *
     * @param t boolean parameter.
     */
    @Override
    public void setEmpty(boolean t) {
        this.empty = t;
    }
    /**
     * initializes the is empty to false.
     */
    @Override
    public void initEmptinnes() {
        this.empty = true;
    }

    /**
     * sets the speed for the formation.
     *
     * @param s the speed we want to set.
     */
    @Override
    public void setFormationSpeed(double s) {
        this.speed = s;
    }
    /**
     * return the shield of the current level.
     *
     * @return the shield.
     */
    public Shield getShield() {
        return shield;
    }

    /**
     * the method create the shield of the current level.
     * @return list of blocks.
     */
    private List<Block> shieldCreator() {
        List<Block> shieldBlocks = new ArrayList<>();
        int width = 5;
        int height = 5;
        double xStart1 = 80;
        double xStart2 = 320;
        double xStart3 = 560;
        double yStart = 520;
        for (int i = 0; i < 160; i += 5) {
            for (int j = 0; j < 20; j += 5) {
                Block shield1 = new Block(xStart1 + i, yStart + j, width, height, Color.lightGray, 1);
                shieldBlocks.add(shield1);
                Block shield2 = new Block(xStart2 + i, yStart + j, width, height, Color.lightGray, 1);
                shieldBlocks.add(shield2);
                Block shield3 = new Block(xStart3 + i, yStart + j, width, height, Color.lightGray, 1);
                shieldBlocks.add(shield3);
            }
        }
        return shieldBlocks;
    }

    /**
     * the method set the image for all the aliens.
     * @param i a random number.
     * @return image.
     */
    private Image setAliensImage(int i) {
        String[] images = {"block_images/vaderShip.png", "block_images/vaderShip1.png", "block_images/vaderShip2.png",
                "block_images/vaderShip3.png"};
        InputStream image =
                ClassLoader.getSystemClassLoader().
                        getResourceAsStream(images[i]);
        Image img = null;
        try {
            img = ImageIO.read(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * the method set the background image.
     * @param i a random number.
     * @return image.
     */
    private Image setBackgroundImage(int i) {
        String[] images = {"background_images/starWar.png", "background_images/starWar1.png",
                "background_images/starWar2.png", "background_images/starWar3.png"};
        InputStream image =
                ClassLoader.getSystemClassLoader().
                        getResourceAsStream(images[i]);
        Image img = null;
        try {
            img = ImageIO.read(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }
}
