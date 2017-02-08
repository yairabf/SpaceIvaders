package animations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that creates a highscore table, loads from and saves to files.
 */
public class HighScoresTable implements Serializable {
    private int size;
    private List<ScoreInfo> scores;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the size of the table.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.scores = new ArrayList<>(this.size);
    }

    /**
     * Add a high-score.
     *
     * @param score the score we want to add.
     */
    public void add(ScoreInfo score) {
        if (this.scores.size() < this.size /*|| this.getRank(score.getScore()) < this.size*/) {
            if (this.scores.size() == 0) {
                this.scores.add(score);
            } else {
                for (ScoreInfo scoreInfo : this.scores) {
                    if (this.getRank(score.getScore()) <= this.getRank(scoreInfo.getScore())) {
                        //this.scores.remove(scoreInfo);//**************************************************changed here
                        this.scores.add(this.getRank(score.getScore()) - 1, score);
                        break;
                    }
                }
            }
        } else if (this.getRank(score.getScore()) <= this.size) {
            this.scores.remove(this.size - 1);
            for (ScoreInfo scoreInfo : this.scores) {
                if (this.getRank(score.getScore()) <= this.getRank(scoreInfo.getScore())) {
                    //this.scores.remove(scoreInfo);//**************************************************changed here
                    this.scores.add(this.getRank(score.getScore()) - 1, score);
                    break;
                }
            }
        }
    }

    /**
     * Return table size.
     *
     * @return the size of the table.
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores. The list is sorted such that the highest scores come first.
     *
     * @return list of all the highest scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * return the rank of the current score.
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     *
     * @param score the score we want to check its rank.
     * @return the rank of the current score
     */
    public int getRank(int score) {
        if (this.scores.size() == 0) {
            return 1;
        } else {
            int rank = this.scores.size() + 1;
            for (ScoreInfo scoreInfo : this.scores) {
                if (score > scoreInfo.getScore()) {
                    rank--;
                }
            }
            return rank;
        }
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scores = new ArrayList<>(this.size);
    }

    /**
     * Load table data from file. Current table data is cleared.
     *
     * @param filename the file contains the scores info.
     * @throws IOException in case the file doesn't exist.
     */
    public void load(File filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream is = null;
        HighScoresTable highScoresTable = null;
        try {
            is = new ObjectInputStream(fis);
            highScoresTable = (HighScoresTable) is.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error in loading the table into a file");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("failed in closing the file.");
                }
            }
        }
        if (highScoresTable != null) {
            this.scores.addAll(highScoresTable.getHighScores());
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the name of the file that inside it we save the table.
     */
    public void save(File filename) {
        ObjectOutputStream os = null;
        try {
            FileOutputStream fs = new FileOutputStream(filename);
            os = new ObjectOutputStream(fs);
            os.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error in saving the table into a file");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("failed in closing the file.");
                }
            }
        }
    }

    /**
     * Read a table from file and return it. If the file does not exist, or there is a problem with reading it,
     * an empty table is returned.
     *
     * @param filename the file we try to load from.
     * @return a new table.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = new HighScoresTable(10);
        try {
            table.load(filename);
        } catch (IOException e) {
            return new HighScoresTable(0);
        }
        return table;
    }

}
