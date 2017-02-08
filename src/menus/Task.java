package menus;

/**
 * an interface for tasks.
 *
 * @param <T> a general type.
 */
public interface Task<T> {
    /**
     * runs a given animation.
     *
     * @return a type of object
     */
    T run();
}
