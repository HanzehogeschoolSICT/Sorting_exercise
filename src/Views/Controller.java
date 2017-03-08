package Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button button; //id name in fxml file
    public BarChart barChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    public void buttonSetArray(){

        XYChart.Series<String,Integer> series1 = new XYChart.Series<String,Integer>();
        series1.getData().add(new XYChart.Data<String,Integer>("WAAROM KUT",10));



        //add series to bar chart
        barChart.getData().add(series1);
        System.out.println("Inserted some data");
    }


}