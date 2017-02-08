package gameopperating;

import animations.AnimationRunner;
import animations.HighScoresTable;
import animations.Animation;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import animations.ScoreInfo;
import animations.EndScreen;

import biuoop.DialogManager;
import levels.LevelInformation;
import biuoop.KeyboardSensor;
import levels.LevelInformationBySpecification;

import java.io.File;


/**
 * a class that creates a flow to the game.
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private HighScoresTable highScoresTable;
    private File file;

    /**
     * constructor.
     *
     * @param ar   the animation runner.
     * @param ks   the keyboard sensor.
     * @param file is the high scores file.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, File file) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.file = file;
    }

    /**
     * creates a game and runs all levels of a given list.
     */
    public void runLevels()  {
        Counter scoreCounter = new Counter(0);
        Counter livesCounter = new Counter(5);
        double speed = 1;
        int name = 1;
       while (true) {
           LevelInformation levelInformation = new LevelInformationBySpecification();
           GameLevel level = new GameLevel(levelInformation, this.keyboardSensor,
                   this.animationRunner, livesCounter, scoreCounter);
           levelInformation.setLevelName(name);
           levelInformation.setFormationSpeed(speed);
           level.initialize();
           levelInformation.setEmpty(false);
           while (!levelInformation.isEmpty()) {
               //while there are lives and blocks left play a turn.
               level.playOneTurn();
               if (livesCounter.getValue() == 0) {
                   break;
               }
           }
           speed *= 1.1;
           name++;
           if (livesCounter.getValue() == 0) {
               break;
           }
       }
        String message;
        String message2;
        if (livesCounter.getValue() == 0) {
            message = "Game Over. Your score is " + scoreCounter.getValue();
            message2 = " ";
        } else {
            message = "You Win! Your score is " + scoreCounter.getValue();
            message2 = "press space to exit";
        }
        Animation endScreen = new KeyPressStoppableAnimation(keyboardSensor, "space",
                new EndScreen(message, message2));
        this.animationRunner.run(endScreen);
        this.addScore(scoreCounter);
        Animation scoreTable = new KeyPressStoppableAnimation(keyboardSensor, "space",
                new HighScoresAnimation(this.highScoresTable));
        this.animationRunner.run(scoreTable);
    }


    /**
     * adds the score to the file.
     *
     * @param score the amount to add.
     */
    private void addScore(Counter score) {
        if (!this.file.exists()) {
            this.highScoresTable = new HighScoresTable(10);
        } else {
            this.highScoresTable = HighScoresTable.loadFromFile(this.file);
        }
        DialogManager dialog = animationRunner.getGui().getDialogManager();
        String name = dialog.showQuestionDialog("Name", "What is your name?", "");
        ScoreInfo scoreInfo = new ScoreInfo(name, score.getValue());
        this.highScoresTable.add(scoreInfo);
        this.highScoresTable.save(this.file);
    }

    /**
     * getter.
     *
     * @return the highscore table.
     */
    public HighScoresTable getHighScoresTable() {
        return this.highScoresTable;
    }
}
