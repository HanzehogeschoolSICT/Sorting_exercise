package models;

/**
 * Created by jouke on 13-3-2017.
 */
public class QuickSort implements Sort{
        private int[] list;
        private int currentPartitionStart; //The start index of the current partition
        private int currentPartitionEnd; //The end index of the current partition
        private int currentLowIndex;
        private int currentHighIndex;
        private int currentPivotIndex;
        private boolean isSorted = false;
        private boolean stopSorting = false;
        private int left;
        private int right;
        private int stack[];
        private int stackTop;

        /*
         * Init
         */
        public QuickSort(int[] list){
            this.list = list;
            this.stack =  new int[list.length];
            this.stackTop =-1;
            left = 0;
            right= list.length-1;
            // push initial values in the stack
            stack[++stackTop] = left;
            stack[++stackTop] = right;
        }

        /*
         * @return Current partition start
         */
        public int getCurrentPartitionStart(){
            return currentPartitionStart;
        }

        /*
         * @return Current partition end
         */
        public int getCurrentPartitionEnd(){
            return currentPartitionEnd;
        }

        /*
         * @return The current index value
         */
        public int getCurrentIndex(){
            return currentPivotIndex;
        }

        /*
         * @return Low index of current partition
         */
        public int getLowIndex(){
            return currentLowIndex;
        }

        /*
         * @return High index of current partition
         */
        public int getHighIndex(){
            return currentHighIndex;
        }

        /*
         * @return Pivot index of current partition
         */
        public int getPivot(){
            return currentPivotIndex;
        }



    /**
     * Source: http://www.geeksforgeeks.org/iterative-quick-sort/
     * One step further in the QuickSort algorithm
     */
        public void nextStep(){

            // keep popping elements until stack is not empty
            if (stackTop >= 0)
            {
                // pop h and l
                right = stack[stackTop--];
                left = stack[stackTop--];

                // set pivot element at it's proper position
                int p = partition(list, left, right);

                // If there are elements on left side of pivot,
                // then push left side to stack
                if ( p-1 > left )
                {
                    stack[ ++stackTop ] = left;
                    stack[ ++stackTop ] = p - 1;
                }

                // If there are elements on right side of pivot,
                // then push right side to stack
                if ( p+1 < right )
                {
                    stack[ ++stackTop ] = p + 1;
                    stack[ ++stackTop ] = right;
                }
            }
        }

    /**
     * Source: http://www.geeksforgeeks.org/iterative-quick-sort/
     * @param list
     * @param left
     * @param right
     * @return
     */
        public int partition(int[] list, int left, int right) {
            currentPivotIndex = (left-1);
            int x = list[right];
            int i = (left - 1);

            for (int j = left; j <= right- 1; j++)
            {
                if (list[j] <= x)
                {
                    i++;
                    // swap arr[i] and arr[j]
                    swapValueInList(list, i, j);
                }
            }
            // swap arr[i+1] and arr[h]
            swapValueInList(list,i+1,right);
            return (i + 1);
        }



        /*
         * Swap two values
         */
        public void swapValueInList(int[] list, int index1, int index2){
            int temp = list[index1];
            list[index1] = list[index2];
            list[index2] = temp;
        }
        /*
         * Get the current list
         * @return list
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
