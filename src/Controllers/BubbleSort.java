package Controllers;

import Controllers.Sort;

import java.util.Arrays;

/**
 * Created by jouke on 6-3-2017.
 * Bubblesort implementation using generic methods
 * Able to step trough so it can be used in the GUI
 */
public class BubbleSort implements Sort {
    private int[] list;
    private boolean keepSearching;
    private boolean allowedToStep;

    public BubbleSort(int[] list){
        this.list = list;
        this.keepSearching = true;
        this.allowedToStep = false;
    }

    public void sortInt(){
        for (int i = 1; i < list.length && keepSearching; i++) {
            keepSearching=false;
            allowedToStep=false;
            //next j
            for (int j = 0; j < list.length-i; j++){
                //Compare j with j + 1
                //Check if there has to be swapped
                //@todo compare 2 values->update in view
                if (list[j] > list[j + 1]) {
//                   waitForStep();
                   swapWithNextValue(j);
                    //still swaps left in this sequence
                    // so keep searching
                     keepSearching = true;
                } else {
                    //NO SWAP
                    System.out.println(list[j] + "is kleiner dan" + list[j + 1] + " lijst blijft hetzelfde=" + Arrays.toString(list));
                }
            }
        }
        System.out.println("The list is now sorted");
    }



    /*
     * Swap list with next value in the list.
     * Swap list[i] with list[i+1]
     */
    public void swapWithNextValue(int currentIndex){
        //SWAP & debugging
        System.out.println(list[currentIndex] + "is groter dan" + list[currentIndex + 1]);
        System.out.println("swap" + list[currentIndex] + "," + list[currentIndex + 1]);
        int currentValue = list[currentIndex];
        list[currentIndex] = list[currentIndex + 1];
        list[currentIndex + 1] = currentValue;
        System.out.println(Arrays.toString(list));
        //@todo UPDATE VIEW method here
    }

    /*
     * Get the current list
     */
    public int[] getList(){
        return list;
    }

    /*
     * Go one step forward
     */
    @Override
    public void stepForward() {
        allowedToStep = true;
    }

    /*
     * Wait untill the step button is clicked
     */
    @Override
    public void waitForStep(){
        while(!allowedToStep){
            //wait currently hangs here.
            //Thread start
        }
    }
}
