package levels;

import sprites.FormationCharge;
import collidables.Block;
import sprites.Shield;
import sprites.Sprite;

import java.util.List;

/**
 * an interface giving information for a level of the game.
 */
public interface LevelInformation {


    /**
     * set the mame of the level according to the parameter that been received.
     *
     * @param i number of the level.
     */
    void setLevelName(int i);

    /**
     * the name of the level.
     *
     * @return the name of the level.
     */
    String levelName();

    /**
     * sets the speed for the movement of the alien formation.
     *
     * @param movementSpeed the speed.
     */
    void setMovementSpeed(int movementSpeed);

    /**
     * Returns a sprite with the background of the level.
     *
     * @return a sprite representing the background.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return a list of Blocks.
     */
    List<List<Block>> aliensFormation();

    /**
     * the number of the blocks to be removed in order to finish the level.
     *
     * @return a number of blocks.
     */
    int numberOfBlocksToRemove();

    /**
     * getter for the level's speed.
     *
     * @return the level's speed.
     */
    double getSpeed();

    /**
     * getter of the aliens formation.
     *
     * @return the alien's formation of the level.
     */
    FormationCharge getFormationCharge();

    /**
     * the method tells us if there any aliens left to destroy.
     *
     * @return true or false.
     */
    boolean isEmpty();

    /**
     * initializes the is empty to false.
     */
    void initEmptinnes();

    /**
     * the method set if there any aliens left to destroy.
     *
     * @param t boolean parameter.
     */
    void setEmpty(boolean t);


    /**
     * sets the speed for the formation.
     *
     * @param speed the speed we want to set.
     */
    void setFormationSpeed(double speed);

    /**
     * return the shield of the current level.
     *
     * @return the shield.
     */
    Shield getShield();
}
