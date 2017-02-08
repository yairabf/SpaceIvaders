import animations.AnimationRunner;
import animations.HighScoresTable;
import animations.Animation;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import gameopperating.GameFlow;
import levels.LevelInformation;
import levels.LevelInformationBySpecification;
import menus.MenuAnimation;
import menus.Task;
import menus.ShowHiScoresTask;
import menus.Menu;

import java.io.File;

/**
 * the class runs the whole game.
 */
public class Ass6Game {
    /**
     * the main method to run the game.
     *
     * @param args the list of levels to run.
     * @throws Exception if only illegal args are given.
     */
    public static void main(String[] args) throws Exception {
        //the file will be the default one
        //creating gui, animation runner
        GUI gui = new GUI("The Vader Invader", 800, 600);
        final KeyboardSensor ks = gui.getKeyboardSensor();
        final AnimationRunner animationRunner = new AnimationRunner(gui);
        //creating a high scores file
        while (true) {
            final File scoresFile = new File("HighScores1.bin");
            HighScoresTable scores = new HighScoresTable(10);
            if (scoresFile.exists()) {
                scores.load(scoresFile);
            } else {
                scores.save(scoresFile);
            }
            Ass6Game ass5Game = new Ass6Game();
            ass5Game.gameMenu(ks, animationRunner, scores, scoresFile);
        }
    }

    /**
     * this function runs the menu and the tasks accordingly.
     *
     * @param ks              the keyboard sensor.
     * @param animationRunner the animation runner.
     * @param scores          the high scores list.
     * @param scoresFile      the file that holds scores.
     */
    public void gameMenu(final KeyboardSensor ks, final AnimationRunner animationRunner,
                         HighScoresTable scores, final File scoresFile) {
        //creating the menu animation
        Menu<Task<Void>> menu = new MenuAnimation<>("The Vader Invader", ks);
        //adding the quit animation
        Task<Void> quit = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        };
        menu.addSelection("q", "quit game", quit);
        //adding the high score animation
        Animation scoreTable = new KeyPressStoppableAnimation(ks, "space",
                new HighScoresAnimation(scores));
        menu.addSelection("h", "high scores", new ShowHiScoresTask(animationRunner, scoreTable));
        Ass6Game ass5Game = new Ass6Game();
        //adding a sub menu as subMenuInfo
        Task<Void> startGame = new Task<Void>() {
            @Override
            public Void run() {
                GameFlow flow = new GameFlow(animationRunner, ks, scoresFile);
                LevelInformation levelInfo = new LevelInformationBySpecification();
                levelInfo.setMovementSpeed(3);
                    flow.runLevels();
                return null;
            }
        };
        //all start game does is summons the sub menu. but that happens at the menu animation class.
        menu.addSelection("s", "start game", startGame);
        animationRunner.run(menu);
        Task t = menu.getStatus();
        t.run();
    }
}
