package Views;

import Controllers.Sort;
import Controllers.BubbleSort;
import Controllers.InsertionSort;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;

import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class Controller implements Initializable {
    public Button insertionSortButtonNextStep;//id name in fxml file
    public Button bubbleSortButtonStart;
    public Button bubbleSortButtonStop;
    public Button insertionSortButtonStop;
    public Button insertionSortButtonStart;
    public Button bubbleSortButtonNextStep;
    public BarChart bubbleSortBarChart;
    public BarChart insertionSortBarChart;
    public TabPane tabPane;
    public XYChart.Series series;
    public Sort bubbleSort;
    public Sort insertionSort;
    public boolean stopSorting = false;
    public int sortingSpeed = 1000; //This variable is used to set the waiting time in the start sorting method

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Generate array that has to be sorted
        int[] intList = {2,1,3,7,1,8,2,10};
        //Create new series
        series = new XYChart.Series<String,Integer>();
        //Set animation off so when updating it's not animating everytime
        bubbleSortBarChart.setAnimated(false);
        insertionSortBarChart.setAnimated(false);

        //Use the BubbleSort Algorithm
        bubbleSort = new BubbleSort(intList);

        //Use the InsertionSort algorithm
        insertionSort = new InsertionSort(intList);

        //Fix button positions when resizing the window
        fixButtonPositionOnResize(bubbleSortButtonNextStep, bubbleSortBarChart,16);
        fixButtonPositionOnResize(insertionSortButtonNextStep, insertionSortBarChart,16);
        fixButtonPositionOnResize(bubbleSortButtonStart, bubbleSortBarChart, 90);
        fixButtonPositionOnResize(insertionSortButtonStart, insertionSortBarChart, 90);
        fixButtonPositionOnResize(bubbleSortButtonStop, bubbleSortBarChart, 138);
        fixButtonPositionOnResize(insertionSortButtonStop, insertionSortBarChart, 138);


        //Check if the tab is switched
        tabPane.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            changedTab();
        }));

        //Set the series of the first tab
        listToSeries(bubbleSort.getList(), bubbleSort);
        addSerieToBarChart(bubbleSortBarChart);

        System.out.println("View is now loaded!");
    }


    /*
     * When a tab is changed
     */
    public void changedTab(){
        String currentTab = tabPane.getSelectionModel().getSelectedItem().getText();
        if(currentTab.equals("InsertionSort")){
            updateBarChart(insertionSort, insertionSortBarChart);
        }else{
            updateBarChart(bubbleSort, bubbleSortBarChart);
        }
    }

    /*
     * Make button position relative to certain chart
     * @param Button The button that is positioned relative to a chart
     * @param BarChart A BarChart where the button is relative to
     * @param xOffset The amount of pixels the button is relative to the BarChart position
     */
    public void fixButtonPositionOnResize(Button button, BarChart barChart, int xOffset){
        //Fix position of button when resizing the width
        barChart.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
            button.setLayoutX(barChart.getLayoutX() + xOffset);
            button.setLayoutY(barChart.getHeight());
        });
        //Fix position of button when resizing the height
        barChart.heightProperty().addListener((ObservableValue, oldSceneWidth, newSceneWidth)->{
            button.setLayoutX(barChart.getLayoutX() + xOffset);
            button.setLayoutY(barChart.getHeight());
        });
    }

    /*
     * Convert an int array list to serie data
     */
    public void listToSeries(int[] list, Sort sortAlgorithm){
        series.getData().clear();
        for(int i=0; i<list.length; i++){
            int x = i;
            int y = list[i];
            int pointer1=-1;
            int pointer2=-1;
            XYChart.Data<String, Integer> d = new XYChart.Data<String,Integer>(String.valueOf(x),y);
            //Color settings is based on the algorithm
            if(sortAlgorithm instanceof BubbleSort){
                pointer1 = sortAlgorithm.getCurrentIndex();
                pointer2 = sortAlgorithm.getCurrentIndex()+1;
            }
            if(sortAlgorithm instanceof InsertionSort){
                pointer1 = sortAlgorithm.getCurrentIndex();
                pointer2 = sortAlgorithm.getCurrentIndex()-1;
            }
            //Color nodes
            if(i==pointer1 || i==pointer2){
                d.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                        newValue.setStyle("-fx-bar-fill: blue;");
                    }
                });
            }else{
                d.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                        newValue.setStyle("-fx-bar-fill: red;");
                    }
                });
            }
            //Add data to series
            series.getData().add(d);
        }
    }

    /*
     * Add serie to barchart
     */
    public void addSerieToBarChart(BarChart b){
        //Delete old series from barchart
        b.getData().clear();
        //Add new series to bar chart
        b.getData().add(series);
        System.out.println("Inserted some data in the BarChart");
    }

    /*
     * Next step in the algorithm (update view)
     */
    public void bubbleSortNextStepButton(){
        if(bubbleSort.isSorted()){
            showPopupListIsSorted();
        }else{
            bubbleSort.nextStep();
            updateBarChart(bubbleSort, bubbleSortBarChart);
        }
    }

    /*
     * Popup message when the list is already sorted
     */
    public void showPopupListIsSorted(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The list is sorted");
        alert.setHeaderText("The list is sorted");
        alert.showAndWait();
    }

    /*
     * Next step in the algorithm (update view)
     */
    public void insertionSortNextStepButton(){
        if(insertionSort.isSorted()){
            showPopupListIsSorted();
        }else{
            insertionSort.nextStep();
            updateBarChart(insertionSort, insertionSortBarChart);
        }
    }

    /*
     * Update a barchart with new values
     */
    public void updateBarChart(Sort sortAlorightm, BarChart barChart){
       listToSeries(sortAlorightm.getList(), sortAlorightm);
       addSerieToBarChart(barChart);
    }

    /*
     * Automaticly performs a nextstep in the BubbelSort algorithm untill the list is sorted
     */
    public void bubbleSortStartSorting(){
        Thread thread = new Thread() {
            public void run() {
                while(!bubbleSort.isSorted()) {
                    //When the stop button is clicked break the while loop
                    if(stopSorting){
                        break;
                    }
                    try{
                        //Go one step further in the bubbleSort algorithm
                        bubbleSort.nextStep();
                        //Update the JavaFX view
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                updateBarChart(bubbleSort, bubbleSortBarChart);
                            }
                        });
                        Thread.sleep(sortingSpeed);
                    }catch (InterruptedException e){
                        System.out.println(e.getStackTrace());
                    }
                }
            }
        };
        thread.start();
    }

    /*
     * Automaticly performs a nextstep in the InsertionSort algorithm untill the list is sorted
     */
    public void insertionSortStartSorting(){
        Thread thread = new Thread() {
            public void run() {
                while(!bubbleSort.isSorted()) {
                    //When the stop button is clicked break the while loop
                    if(stopSorting){
                        break;
                    }
                    try{
                        //Go one step further in the bubbleSort algorithm
                        insertionSort.nextStep();
                        //Update the JavaFX view
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                updateBarChart(insertionSort, insertionSortBarChart);
                            }
                        });
                        Thread.sleep(sortingSpeed);
                    }catch (InterruptedException e){
                        System.out.println(e.getStackTrace());
                    }
                }
            }
        };
        thread.start();
    }

    /*
     * Stop the bubbleSortStartSorting() method at the current time/step
     * Sets the stopSorting boolean that is used as a condition in the bubbleSortStartSorting() method
     */
    public void insertionSortStopSorting(){
        stopSorting = true;
    }


    /*
     * Stop the bubbleSortStartSorting() method at the current time/step
     * Sets the stopSorting boolean that is used as a condition in the bubbleSortStartSorting() method
     */
    public void bubbleSortStopSorting(){
        stopSorting = true;
    }
}