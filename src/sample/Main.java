package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static int width = 0;
    public static int height = 0;
    public static final int tileSize = 25;

    @Override
    public void start(Stage primaryStage) throws Exception {

        setWidth();
        setHeight();

        Parent builderRoot = FXMLLoader.load(getClass().getResource("MazeBuilder.fxml"));

        Scene mazeBuilder = new Scene(builderRoot, 800, 500);

        primaryStage.setScene(mazeBuilder);
        SceneLibrary.setPrimaryStage(primaryStage);
        SceneLibrary.setMazeBuilder(mazeBuilder);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setWidth() {
        width = 0;
        while (width <= 5) {
            try {
                width = Integer.parseInt(JOP.input("Please enter the width of your mazes (Must be greater than 5)"));
            } catch (Exception e) {
                width = 0;
            }
        }
    }

    public static void setHeight() {
        height = 0;
        while (height <= 5) {
            try {
                height = Integer.parseInt(JOP.input("Please enter the height of your mazes (Must be greater than 5)"));
            } catch (Exception e) {
                height = 0;
            }
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getTileSize() {
        return tileSize;
    }
}
