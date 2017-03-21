package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import models.InsertionSort;
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
public class InsertionSortController extends ViewController implements Initializable {
    private XYChart.Series insertionSortSeries = new XYChart.Series<String,Integer>();
    private InsertionSort insertionSort;
    @FXML private BarChart insertionSortBarChart;
    @FXML public HBox controlBoxI;


    //Controls speed slider
    @FXML public Slider speedSliderI;
    @FXML public TextField speedLabelI;
    @FXML public ChoiceBox speedUnitI;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Generate array that has to be sorted
        int[] intList = generateIntList(10);
        shuffleIntList(intList);

        //Use InsertionSort algorithm
        insertionSort = new InsertionSort(intList);

        //Turn animation off
        insertionSortBarChart.setAnimated(false);

        //Update the current series with the list from insertionSort algorithm
        //Add series one time to this barchart
        updateSeries(insertionSortSeries, insertionSort);
        addSerieToBarChart(insertionSortSeries, insertionSortBarChart);

        //Fix button positions when resizing the window
        fixHboxRelativeToScreen(controlBoxI, insertionSortBarChart,16);

        //Add listeners to speed selection
        speedSliderI.setMax(1000);
        speedSliderI.valueProperty().addListener((observable, oldValue, newValue) -> {
            speedLabelI.setText(String.valueOf(newValue.intValue()));
        });

        speedLabelI.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                speedSliderI.setValue(Double.parseDouble(newValue));
            }
            catch(NumberFormatException e) {
                speedLabelI.setText("0");
                speedSliderI.setValue(0.0);
            }

            super.changeSpeed(speedUnitI.getValue().toString(), speedLabelI);
        });

        speedUnitI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changeSpeed(newValue, speedLabelI);
            }
        });

        System.out.println("Loaded InsertionSort view");
    }

    /*
     * Goes one step further in the chosen algorithm
     * Updates a BarChart after the step is done
     */
    public void nextStep(){
        super.nextStep(insertionSortSeries, insertionSort);
    }

    public void startSorting(){
        super.startSorting(insertionSortSeries, insertionSort);
    }

    public void stopSorting(){
        super.stopSorting();
    }
}
