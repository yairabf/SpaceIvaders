package animations;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;


/**
 * a class that creates the end screen.
 */
public class EndScreen implements Animation {
    private String message;
    private String message2;

    /**
     * constructor.
     *
     * @param s1 the first message.
     * @param s2 the second message.
     */
    public EndScreen(String s1, String s2) {
        this.message = s1;
        this.message2 = s2;
    }

    /**
     * draws a pause message while paused.
     *
     * @param dt is the amount to move.
     * @param d  the draw surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //the background
        InputStream image =
                ClassLoader.getSystemClassLoader().
                        getResourceAsStream("background_images/endGame.jpg");
        Image img = null;
        try {
            img = ImageIO.read(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        d.drawImage(0, 0, img);
        //the shadow for the text
        d.setColor(new Color(0xAE952F));
        d.drawText(8, d.getHeight() / 2, this.message, 50);
        d.drawText(8, d.getHeight() / 2 + 150, this.message2, 50);
        //the text
        d.setColor(new Color(0xA5AE28));
        d.drawText(10, d.getHeight() / 2, this.message, 50);
        d.drawText(10, d.getHeight() / 2 + 150, this.message2, 50);
    }

    /**
     * determines whether the animation of pause should stop or not.
     *
     * @return true if it should stop, otherwise false.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
