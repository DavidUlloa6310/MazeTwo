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
        Parent builderRoot = FXMLLoader.load(getClass().getResource("MazeBuilder.fxml"));
        Parent listRoot = FXMLLoader.load(getClass().getResource("MazeList.fxml"));

        Scene mazeList = new Scene(listRoot, 800, 500);
        Scene mazeBuilder = new Scene(builderRoot, 800, 500);

        primaryStage.setScene(mazeBuilder);
        SceneLibrary.setPrimaryStage(primaryStage);
        SceneLibrary.setMazeList(mazeList);
        SceneLibrary.setMazeBuilder(mazeBuilder);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
