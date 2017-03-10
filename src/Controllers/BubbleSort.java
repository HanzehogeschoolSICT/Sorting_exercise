package Controllers;

import Controllers.Sort;

import java.util.Arrays;

/**
 * Created by jouke on 6-3-2017.
 * Bubblesort implementation using generic methods
 * Able to index trough so it can be used in the GUI
 */
public class BubbleSort implements Sort {
    private int[] list;
    private boolean keepSearching;
    private boolean allowedToStep;
    private boolean isSorted = false;
    private int index;

    /*
     * Init
     */
    public BubbleSort(int[] list){
        this.list = list;
        this.keepSearching = false;
        this.allowedToStep = false;
    }

    /*
     * @return The current index value
     */
    public int getCurrentIndex(){
        return index;
    }

    /*
     * Go one index further in the BubbleSort algorithm
     */
    public void nextStep(){
        if(index < list.length-1) {
            if (list[index] > list[index + 1]) {
                swapWithNextValue(index);
                keepSearching = true;
            }
            index++;
        }else{
            if(keepSearching == false){
                isSorted = true;
                System.out.println("List is sorted");
            }else{
                keepSearching = false;
                index = 0;
            }
        }
    }

    /*
     * Swap list with next value in the list.
     * Swap list[i] with list[i+1]
     */
    public void swapWithNextValue(int currentIndex){
        int currentValue = list[currentIndex];
        list[currentIndex] = list[currentIndex + 1];
        list[currentIndex + 1] = currentValue;
    }

    /*
     * Get the current list
     */
    public int[] getList(){
        return list;
    }

    /*
    * @return true If list is sorted
    */
    public boolean isSorted(){
        return isSorted;
    }
}
