package Views;

import Controllers.BubbleSort;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button button; //id name in fxml file
    public BarChart barChart;
    public XYChart.Series series;
    public BubbleSort bubbleSort;
    private Thread thread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Generate array that has to be sorted
        int[] intList = {2,1,3,7,1,8,2,10};
        //Create new series
        series = new XYChart.Series<String,Integer>();
        //Set animation off so when updating it's not animating everytime
        barChart.setAnimated(false);

        //Use the BubbleSort Algorithm
        bubbleSort = new BubbleSort(intList);


        //Set the series
        listToSeries(bubbleSort.getList());
        System.out.println(Arrays.toString(bubbleSort.getList()));
        //Add series to barchart
        addSerieToBarChart(barChart);

        System.out.println("View is now loaded!");
    }

    /*
     * Convert an int array list to serie data
     */
    public void listToSeries(int[] list){
        series.getData().clear();
        for(int i=0; i<list.length; i++){
            int x = i;
            int y = list[i];
            XYChart.Data<String, Integer> d = new XYChart.Data<String,Integer>(String.valueOf(x),y);
            if(i==bubbleSort.getCurrentIndex() || i == bubbleSort.getCurrentIndex()+1){
                //Color the node that were using in BubbleSort
                d.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                        newValue.setStyle("-fx-bar-fill: blue;");
                    }
                });
            }else{
                //Color the node that were using in BubbleSort
                d.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                        newValue.setStyle("-fx-bar-fill: red;");
                    }
                });
            }
            series.getData().add(d);
        }
    }

    /*
     * Add serie to barchart
     */
    public void addSerieToBarChart(BarChart b){
        //Delete old series from barchart
        barChart.getData().clear();
        //Add new series to bar chart
        barChart.getData().add(series);
        System.out.println("Inserted some data in the BarChart");
    }

    /*
     * Next step in the algorithm (update view)
     */
    public void nextStepButton(){
        bubbleSort.nextStep();
        System.out.println(bubbleSort.getCurrentIndex());
        System.out.print(Arrays.toString(bubbleSort.getList()));
        listToSeries(bubbleSort.getList());
        addSerieToBarChart(barChart);
    }
}