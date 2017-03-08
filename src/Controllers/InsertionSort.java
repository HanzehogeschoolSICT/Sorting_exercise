package Controllers;

import java.util.Arrays;

/**
 * Created by jouke on 7-3-2017.
 */
public class InsertionSort implements Sort {
    private int[] list;

    public InsertionSort(int[] list){
        this.list = list;
    }

    public void sortInt(){
        for (int i = 1; i < list.length; i++){
            for (int j = i; j > 0 && list[j-1] > list[j]; j--){
                swapWithPreviousValue(j);
                System.out.println(Arrays.toString(list));
            }
        }
        System.out.println(Arrays.toString(list));
    }

    /*
     * Swap list with previous value in the list.
     * Swap list[i] with list[i-1]
     */
    public void swapWithPreviousValue(int index){
        int currentValue = list[index];
        list[index] = list[index-1];
        list[index-1] = currentValue;
    }
    /*
     * Go one step forward
     */
    @Override
    public void stepForward() {

    }

    /*
     * Wait untill the step button is clicked
     */
    @Override
    public void waitForStep() {

    }
}
