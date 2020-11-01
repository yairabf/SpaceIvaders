package gameopperating;

import animations.*;

import collidables.Collidable;
import collidables.Block;
import collidables.Paddle;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import levels.LevelInformation;
import levels.NameOfLevel;
import listeners.HitListener;
import listeners.ScoreTrackingListener;
import removers.BallRemover;
import removers.BlockRemover;
import removers.ShieldRemover;
import sprites.Sprite;
import sprites.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;


/**
 * class that represents the game itself.
 */
public class GameLevel implements Animation {
    private AnimationRunner runner;
    private boolean running;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter blockCounter;
    private Counter scoreCounter;
    private Counter livesCounter;
    private KeyboardSensor keyboard;
    private LevelInformation levelInformation;
    private Paddle paddleGame;

    /**
     * constructor.
     *
     * @param l      is the level info.
     * @param ky     is the keyboard.
     * @param runner is the animation runner.
     * @param lives  the amount of lives for the game.
     * @param score  the starting score of the game.
     */
    public GameLevel(LevelInformation l,
                     KeyboardSensor ky, AnimationRunner runner, Counter lives, Counter score) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blockCounter = new Counter(l.numberOfBlocksToRemove());
        this.scoreCounter = score;
        this.livesCounter = lives;
        this.keyboard = ky;
        this.runner = runner;
        this.levelInformation = l;
    }


    /**
     * the method add collide object to the game environment.
     *
     * @param c the collidable we want to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * the method add sprite object to the sprite collection.
     *
     * @param s the sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /*public BlockRemover getBlockRemover{
        return this.blockRemover;
    }*/

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)and add them to the game.
     */
    public void initialize() {
        //adding the background to the game
        this.addSprite(this.levelInformation.getBackground());
        //creating the blockRemover;
        BlockRemover blockRemover = new BlockRemover(this, this.blockCounter);
        //this.blockRemover = new BlockRemover(this, this.blockCounter);
        //creating ballRemover
        BallRemover ballRemover = new BallRemover(this);
        ShieldRemover shieldRemover = new ShieldRemover(this);
        //creating a score indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreCounter);
        scoreIndicator.addToGame(this);
        //creating a lives indicator
        LivesIndicator livesIndicator = new LivesIndicator(livesCounter);
        livesIndicator.addToGame(this);
        //creating a score tracking listener
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.scoreCounter);
        //creating a sprite that will write the name of the level.
        NameOfLevel nameOfLevel = new NameOfLevel(this.levelInformation.levelName());
        nameOfLevel.addToGame(this);
        this.gameAliensCreator(blockRemover, scoreTrackingListener, ballRemover);

        this.shieldCreator(ballRemover, shieldRemover);
        //creating the shield blocks for the game


    }

    /**
     * a method deciding wether the loop should stop or not.
     *
     * @return true if should stop, otherwise false.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * summons the runner and runs one go for the game(till a life ends).
     */
    public void playOneTurn() {
        this.resetGameEnvironment();
        //creating the game paddle
        this.paddleGame = new Paddle(this.keyboard, this.environment, this);
        this.paddleGame.setImage();
        this.paddleGame.addToGame(this);
        this.levelInformation.getFormationCharge().setStartFormation();
        this.levelInformation.getFormationCharge().setSpeed(this.levelInformation.getSpeed());
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
        paddleGame.removeFromGame(this);
    }

    /**
     * does one frame for the game.
     *
     * @param d  a given surface to run on.
     * @param dt is the amount to move.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //if all blocks have been removed.
        if (levelInformation.isEmpty()) {
            this.running = false;
        }
        if (this.levelInformation.getFormationCharge().getMostDown() >= 520 || this.paddleGame.isBeenHit()) {
            //when the paddle gets hit or we have been disqualified we remove it so that it wont
            // keep drawing itself on the draw surface
            this.paddleGame.removeFromGame(this);
            this.livesCounter.decrease(1);
            this.running = false;
        }

        //if the player has paused
        if (this.keyboard.isPressed("p")) {
            KeyPressStoppableAnimation k = new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen());
            this.runner.run(k);
        }
//        //if the player has paused
//        if (this.keyboard.isPressed("q")) {
//            KeyPressStoppableAnimation k = new KeyPressStoppableAnimation(this.keyboard, "y", new QuitScreen());
//            this.runner.run(k);
//        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
    }

    /**
     * removes a given collidable from the sprite list.
     *
     * @param c the collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * removes a given sprite from the sprite list.
     *
     * @param s the sprite to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }


    /**
     * creates shield for the paddle.
     *
     * @param ballRemover   the death block needs to know the ball remover. will remove balls when hits death block.
     * @param shieldRemover listener that tells the shield block to remove themselves in case of hit happens.
     */
    private void shieldCreator(BallRemover ballRemover, ShieldRemover shieldRemover) {
        this.levelInformation.getShield().addToGame(this);
        this.levelInformation.getShield().addHitListener(ballRemover);
        this.levelInformation.getShield().addHitListener(shieldRemover);
        //creating the death block
        Block deathBlock = new Block(0, 600, 800, 20, Color.black, -1);
        deathBlock.addToGame(this);
        deathBlock.addHitListener(ballRemover);
    }

    /**
     * adding the blocks as part of the game.
     *
     * @param blockRemover          removes blocks that have been hit.
     * @param scoreTrackingListener keeps score of the player.
     * @param ballRemover           remove block if  the ball hits alien.
     */
    private void gameAliensCreator(HitListener blockRemover, HitListener scoreTrackingListener,
                                   HitListener ballRemover) {
        this.levelInformation.setEmpty(false);
        this.levelInformation.getFormationCharge().setGameEnvironment(this.environment);
        this.levelInformation.getFormationCharge().setGameLevel(this);
        this.levelInformation.getFormationCharge().addToGame(this);
        this.levelInformation.getFormationCharge().addHitListener(blockRemover);
        this.levelInformation.getFormationCharge().addHitListener(scoreTrackingListener);
        this.levelInformation.getFormationCharge().addHitListener(ballRemover);
    }

    /**
     * getter.
     *
     * @return level info
     */
    public LevelInformation getLevelInformation() {
        return this.levelInformation;
    }

    /**
     *
     */
    private void resetGameEnvironment() {
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.addSprite(this.levelInformation.getBackground());
        //creating a score indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreCounter);
        scoreIndicator.addToGame(this);
        //creating a lives indicator
        LivesIndicator livesIndicator = new LivesIndicator(livesCounter);
        livesIndicator.addToGame(this);
        //creating a sprite that will write the name of the level.
        NameOfLevel nameOfLevel = new NameOfLevel(this.levelInformation.levelName());
        nameOfLevel.addToGame(this);
        this.levelInformation.getFormationCharge().setGameEnvironment(this.environment);
        this.levelInformation.getFormationCharge().addToGame(this);
        this.levelInformation.getShield().addToGame(this);
    }


}
