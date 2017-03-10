package Controllers;

import java.util.Arrays;

/**
 * Created by jouke on 7-3-2017.
 */
public class InsertionSort implements Sort {
    private int[] list;
    private int currentIndex=1;
    private int endIndex=1;
    private boolean isSorted = false;

    public InsertionSort(int[] list){
        this.list = list;
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
     * @return The end index value
     */
    public int getEndIndex(){
        return endIndex;
    }

    /*
     * @return The current index value
     */
    public int getCurrentIndex(){
        return currentIndex;
    }

    /*
     * Get the current list
     */
    public int[] getList(){
        return list;
    }

    /*
     * Go one step further in the InserionSort algorithm
     */
    public void nextStep(){
        if(endIndex < list.length){
            //Check if the left side of the list is sorted
            if(currentIndex > 0){
                if(list[currentIndex-1] > list[currentIndex]){
                    swapWithPreviousValue(currentIndex);
                }
                currentIndex--;
            }else{
                //End of the left side of list.
                //Take new number so original step + 1;
                endIndex += 1;
                currentIndex = endIndex;
            }
        }else{
            System.out.println("List is sorted");
            isSorted = true;
        }
    }

    /*
     * @return true If list is sorted
     */
    public boolean isSorted(){
        return isSorted;
    }
}
