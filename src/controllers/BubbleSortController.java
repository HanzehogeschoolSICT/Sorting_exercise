package controllers;

import models.BubbleSort;
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
public class BubbleSortController extends ViewController implements Initializable {
    private XYChart.Series bubbleSortSeries = new XYChart.Series<String,Integer>();
    private BubbleSort bubbleSort;
    @FXML private BarChart bubbleSortBarChart;
    @FXML public HBox controlBoxB;


    //Controls speed slider
    @FXML public Slider speedSliderB;
    @FXML public TextField speedLabelB;
    @FXML public ChoiceBox speedUnitB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Generate array that has to be sorted
        int[] intList = {2,1,3,7,1,8,2,10};

        //Use BubbleSort algorithm
        bubbleSort = new BubbleSort(intList);

        //Turn animation off
        bubbleSortBarChart.setAnimated(false);

        //Update the current series with the list from bubbleSort algorithm
        //Add series one time to this barchart
        updateSeries(bubbleSortSeries, bubbleSort);
        addSerieToBarChart(bubbleSortSeries, bubbleSortBarChart);

        //Fix button positions when resizing the window
        fixHboxRelativeToScreen(controlBoxB, bubbleSortBarChart,16);

        //Add listeners to speed selection
        speedSliderB.setMax(1000);
        speedSliderB.valueProperty().addListener((observable, oldValue, newValue) -> {
            speedLabelB.setText(String.valueOf(newValue.intValue()));
        });

        speedLabelB.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                speedSliderB.setValue(Double.parseDouble(newValue));
            }
            catch(NumberFormatException e) {
                speedLabelB.setText("0");
                speedSliderB.setValue(0.0);
            }

            super.changeSpeed(speedUnitB, speedLabelB);
        });

        speedUnitB.getSelectionModel().selectedIndexProperty().addListener(e -> {
            this.changeSpeed(speedUnitB, speedLabelB);
        });


        System.out.println("Loaded BubbleSort view");
    }

    /*
     * Goes one step further in the chosen algorithm
     * Updates a BarChart after the step is done
     */
    public void nextStep(){
        super.nextStep(bubbleSortSeries, bubbleSort);
    }

    public void startSorting(){
        super.startSorting(bubbleSortSeries, bubbleSort);
    }

    public void stopSorting(){
        super.stopSorting();
    }
}
