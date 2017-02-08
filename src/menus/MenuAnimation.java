package menus;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * a class for the starting menu.
 *
 * @param <T> a general type.
 */
public class MenuAnimation<T> implements Menu<T> {
    private T status;
    private String name;
    private KeyboardSensor keyboardSensor;
    private boolean stop;
    private List<TaskInfo<T>> tasks = new ArrayList<>();
    /**
     * constructor.
     *
     * @param n  the name.
     * @param ks the keyboard sensor.
     */
    public MenuAnimation(String n, KeyboardSensor ks) {
        this.name = n;
        this.keyboardSensor = ks;
    }

    /**
     * adds an option to the menu.
     *
     * @param key  the key that stops.
     * @param n    the message to be displayed.
     * @param task returns a value of type Task to perform.
     */
    @Override
    public void addSelection(String key, String n, T task) {
        TaskInfo<T> selection = new TaskInfo<>(key, n, task);
        this.tasks.add(selection);
    }
    /**
     * runs a frame of the animation.
     *
     * @param d  a given surface to run on.
     * @param dt the time that takes to do the operation.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.stop = false;
        InputStream image =
                ClassLoader.getSystemClassLoader().
                        getResourceAsStream("background_images/Game.png");
        Image img = null;
        try {
            img = ImageIO.read(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        d.drawImage(0, 0, img);
        //the name of the menu
        d.drawText(440, 200, this.name, 40);
        d.setColor(new Color(0x9C3A35));
        d.drawText(442, 202, this.name, 40);
        int lineCount = 0;
        //writes down all options
        for (TaskInfo taskInfo : this.tasks) {
            d.drawText(440, 250 + lineCount, taskInfo.toString(), 30);
            lineCount += 50;
        }
        //finds out what key has been pressed
        for (TaskInfo<T> taskInfo : this.tasks) {
            if (keyboardSensor.isPressed(taskInfo.getKey())) {
                //updates the status accordingly
                this.status = taskInfo.getTask();
                this.stop = true;
                break;
            }
        }
    }

    /**
     * getter.
     *
     * @return the status.
     */
    @Override
    public T getStatus() {
        return this.status;
    }

    /**
     * a method deciding whether the loop should stop or not.
     *
     * @return true if should stop, otherwise false.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
