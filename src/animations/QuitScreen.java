package animations;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;

public class QuitScreen implements Animation {
    /**
     * draws a pause message while paused.
     *
     * @param dt the game time counter.
     * @param d  the draw surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //the background
        InputStream image =
                ClassLoader.getSystemClassLoader().
                        getResourceAsStream("background_images/stop.png");
        Image img = null;
        try {
            img = ImageIO.read(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        d.drawImage(0, 0, img);
        //the shade for the text
        d.setColor(new Color(0xA4332F));
        d.drawText(318, d.getHeight() / 2 + 100, "Are you sure you want to quit?", 50);
        d.drawText(168, d.getHeight() / 2 + 150, "press space to continue or y to quit", 50);
        //the text
        d.setColor(new Color(0xAE952F));
        d.drawText(320, d.getHeight() / 2 + 100, "Are you sure you want to quit?", 50);
        d.drawText(170, d.getHeight() / 2 + 150, "press space to continue or y to quit", 50);

    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
