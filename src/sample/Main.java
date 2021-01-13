package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int width = 24;
    public static final int height = 16;
    public static final int tileSize = 25;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MazeBuilder.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 800, 500));
        SceneLibrary.setPrimaryStage(primaryStage);
//        SceneLibrary.playScene(0);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
