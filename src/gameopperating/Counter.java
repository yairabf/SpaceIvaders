package gameopperating;

/**
 * a class that is a counter. we use it for block hits.
 */
public class Counter {
    private int value;

    /***
     * constructor.
     *
     * @param startingVal is the value the counter will start at.
     */
    public Counter(int startingVal) {
        this.value = startingVal;
    }

    /**
     * add number to current count.
     *
     * @param number is the amount to increase the counter.
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number is the amount to be decreased.
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * get current count.
     *
     * @return the current count.
     */
    public int getValue() {
        return this.value;
    }
}
