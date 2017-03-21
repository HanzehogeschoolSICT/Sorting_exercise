package models;

/**
 * Created by jouke on 7-3-2017.
 */
public interface Sort {
    /*
     * One step further in the sorting algorithm
     */
    void nextStep();

    /*
     * Get the current list
     */
    int[] getList();

    /*
     * Get the current index
     */
    int getCurrentIndex();

    /*
     * @return true If list is sorted
     */
    boolean isSorted();
}
