package animations;

import java.io.Serializable;

/**
 * keeps the scire info: the name of the player and his score.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * constructor that receives name of the user and it score.
     *
     * @param name  the player's name.
     * @param score the player's score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * getter for the player's name.
     *
     * @return player's name;
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for the player's score.
     *
     * @return player's score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * converts the score in to a string to display.
     *
     * @return the string.
     */
    public String toString() {
        return this.name + ": " + this.score;
    }
}