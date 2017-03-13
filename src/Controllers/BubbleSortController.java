package Controllers;

import Models.BubbleSort;
import Models.InsertionSort;
import Models.Sort;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jouke on 13-3-2017.
 */
public class BubbleSortController extends ViewController implements Initializable {
    private BubbleSort bubbleSort;
    @FXML private BarChart bubbleSortBarChart;
    @FXML private HBox controlBoxB;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Generate array that has to be sorted
        int[] intList = {2,1,3,7,1,8,2,10};

        //Use BubbleSort algorithm
        bubbleSort = new BubbleSort(intList);

        //Turn animation off
        bubbleSortBarChart.setAnimated(false);

        //Add series one time to this barchart
        addSerieToBarChart(bubbleSortBarChart);

        //Fix button positions when resizing the window
        fixHboxRelativeToScreen(controlBoxB, bubbleSortBarChart,16);

        System.out.println("Loaded BubbleSort view");
    }

    /*
     * Goes one step further in the chosen algorithm
     * Updates a BarChart after the step is done
     */
    public void nextStep(){
        super.nextStep(bubbleSort);
    }

    public void startSorting(){
        super.startSorting(bubbleSort);
    }

    public void stopSorting(){
        super.stopSorting();
    }
}
