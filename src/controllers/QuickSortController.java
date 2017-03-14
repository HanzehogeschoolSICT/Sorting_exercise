package controllers;

import models.QuickSort;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jouke on 13-3-2017.
 */
public class QuickSortController extends ViewController implements Initializable {
    private XYChart.Series quickSortSeries = new XYChart.Series<String,Integer>();
    private QuickSort quickSort;
    @FXML private BarChart quickSortBarChart;
    @FXML private HBox controlBoxQ;

    //Controls speed slider
    @FXML public Slider speedSliderQ;
    @FXML public TextField speedLabelQ;
    @FXML public ChoiceBox speedUnitQ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Generate array that has to be sorted
        int[] intList = generateIntList(10);
        shuffleIntList(intList);

        //Use QuickSort algorithm
        quickSort = new QuickSort(intList);

        //Turn animation off
        quickSortBarChart.setAnimated(false);

        //Update the current series with the list from quickSort algorithm
        //Add series one time to this barchart
        updateSeries(quickSortSeries, quickSort);
        addSerieToBarChart(quickSortSeries, quickSortBarChart);

        //Fix button positions when resizing the window
        fixHboxRelativeToScreen(controlBoxQ, quickSortBarChart,16);

        //Add listeners to speed selection
        speedSliderQ.setMax(1000);
        speedSliderQ.valueProperty().addListener((observable, oldValue, newValue) -> {
            speedLabelQ.setText(String.valueOf(newValue.intValue()));
        });

        speedLabelQ.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                speedSliderQ.setValue(Double.parseDouble(newValue));
            }
            catch(NumberFormatException e) {
                speedLabelQ.setText("0");
                speedSliderQ.setValue(0.0);
            }

            super.changeSpeed(speedUnitQ, speedLabelQ);
        });

        speedUnitQ.getSelectionModel().selectedIndexProperty().addListener(e -> {
            super.changeSpeed(speedUnitQ, speedLabelQ);
        });

        System.out.println("Loaded QuickSort view");
    }

    /*
     * Goes one step further in the chosen algorithm
     * Updates a BarChart after the step is done
     */
    public void nextStep(){
        super.nextStep(quickSortSeries, quickSort);
    }

    public void startSorting(){
        super.startSorting(quickSortSeries, quickSort);
    }

    public void stopSorting(){
        super.stopSorting();
    }
}
