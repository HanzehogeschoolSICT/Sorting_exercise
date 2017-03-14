package controllers;

import models.BubbleSort;
import models.InsertionSort;
import models.Sort;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jouke on 13-3-2017.
 */
public class ViewController implements Initializable {
    private boolean stopSorting = false;
    private int sortingSpeed = 1000; //default delay is 1000ms
    @FXML public TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loaded the main view");
    }

    /**
     * Shuffle a array list of integers
     * @param list
     */
    public void shuffleIntList(int[] list){
        int index;
        Random random = ThreadLocalRandom.current();
        for(int i = list.length -1; i>1; i--){
            //get random index
            index = random.nextInt(i + 1);

            //swap random index with current index
            int temp = list[index];
            list[index] = list[i];
            list[i] = temp;
        }
    }

    /**
     * Generate a list from 1 to N(size)
     */
    public int[] generateIntList(int size){
        int[] list = new int[size];
        for(int i=0; i<size; i++){
            list[i] = i+1;
        }
        return list;
    }

    /**
     * Go one step further in a sorting algorithm and update the series
     * @param sortAlgorithm
     */
    public void nextStep(XYChart.Series series, Sort sortAlgorithm){
        if(sortAlgorithm.isSorted()){
            showPopupListIsSorted();
        }else{
            sortAlgorithm.nextStep();
            updateSeries(series, sortAlgorithm);
        }
    }

    public void startSorting(XYChart.Series series, Sort sortAlgorithm){
        stopSorting = false;
        Thread thread = new Thread() {
            public void run() {
                while(!sortAlgorithm.isSorted()) {
                    //When the stop button is clicked break the while loop
                    if(stopSorting){
                        break;
                    }
                    try{
                        //Go one step further in the bubbleSort algorithm
                        sortAlgorithm.nextStep();
                        //Update the JavaFX view
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                updateSeries(series, sortAlgorithm);
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

    /**
     * Change sorting speed
     * @param cb ChoiceBox
     * @param t TextField
     */
    public void changeSpeed(ChoiceBox cb, TextField t)
    {
        int indicator = 1;
        switch(cb.getValue().toString()) {
            case "ms":
                indicator = 1;
                break;

            case "s":
                indicator = 1000;
                break;

            case "m":
                indicator = 60000;
                break;

            case "h":
                indicator = 3600000;
                break;
        }
        this.sortingSpeed = Integer.parseInt(t.getText()) * indicator;
    }
    /**
     * Sets the stopSorting boolean that is used as a condition in the startSorting() method
     */
    public void stopSorting(){
        stopSorting = true;
    }

    /*
     * Make button position relative to certain chart
     * @param Button The button that is positioned relative to a chart
     * @param BarChart A BarChart where the button is relative to
     * @param xOffset The amount of pixels the button is relative to the BarChart position
     */
    public void fixHboxRelativeToScreen(HBox box, BarChart barChart, int xOffset){
        //Fix position of button when resizing the width
        barChart.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
            box.setLayoutX(barChart.getLayoutX() + xOffset);
            box.setLayoutY(barChart.getHeight());
        });
        //Fix position of button when resizing the height
        barChart.heightProperty().addListener((ObservableValue, oldSceneWidth, newSceneWidth)->{
            box.setLayoutX(barChart.getLayoutX() + xOffset);
            box.setLayoutY(barChart.getHeight());
        });
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
    * Add serie to barchart
    * @NOTE: ONLY do this one time
    * BarChart updates automaticly when serie is updated
    */
    public void addSerieToBarChart(XYChart.Series series, BarChart b){
        //Delete old series from barchart
        b.getData().clear();
        //Add new series to bar chart
        b.getData().add(series);
        System.out.println("Inserted some data in the BarChart");
    }

    /*
     * Convert an int array list to serie data
     */
    public void updateSeries(XYChart.Series series, Sort sortAlgorithm){
        int[] list = sortAlgorithm.getList();
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

}
