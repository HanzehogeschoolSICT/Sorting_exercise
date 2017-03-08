package Views;

import Controllers.BubbleSort;
import Controllers.InsertionSort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by jouke on 6-3-2017.
 */
public class main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        int[] intList = {2,1,3,7,1,8,2,10};
//        InsertionSort insertionSort = new InsertionSort(intList);
//        insertionSort.sortInt();
//

//        BubbleSort bubbleSort = new BubbleSort(intList);
        //Controllers.Sort doesnt start sorting until u stepforward
//        bubbleSort.sortInt();
//        System.out.println(bubbleSort.getList());
//        bubbleSort.stepForward();
//        bubbleSort.stepForward();
//        bubbleSort.stepForward();


        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}
