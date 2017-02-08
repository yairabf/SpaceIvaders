package animations;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;

/**
 * Class representing animation of the high score table.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;

    /**
     * constructor.
     *
     * @param scores is the scores for the game.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //the background
        InputStream image =
                ClassLoader.getSystemClassLoader().
                        getResourceAsStream("background_images/score.jpg");
        Image img = null;
        try {
            img = ImageIO.read(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        d.drawImage(0, 0, img);
        d.setColor(Color.WHITE);
        d.drawText(100, 80, "Score Table", 40);
        d.setColor(Color.orange);
        d.drawText(100, 82, "Score Table", 40);
        int lineCount = 0;
        //i is for the rank
        int i = 1;
        for (ScoreInfo scoreInfo : this.scores.getHighScores()) {
            d.drawText(100, 125 + lineCount, Integer.toString(i) + ") " + scoreInfo.toString(), 30);
            lineCount += 40;
            i++;
        }
    }

    /**
     * a method deciding whether the loop should stop or not.
     *
     * @return true if should stop, otherwise false.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
