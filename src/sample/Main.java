package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

        try
        {
            Parent root = loader.load();

            Controller controller = loader.getController();

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);

            controller.setStageAndScene(primaryStage, scene);

            primaryStage.show();
        }
        catch (Exception e)
        {
            System.out.println("Error initializing gui");
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
