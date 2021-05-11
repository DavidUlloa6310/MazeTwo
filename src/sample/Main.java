package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //Much simpler Main class. Just has important launch variables, such as width, height, and tile size.

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
        while (width <= 5 || width >= 33) {
            try {
                width = Integer.parseInt(JOP.input("Please enter the width of your mazes (Min: 6, Max: 32)"));
            } catch (Exception e) {
                width = 0;
            }
        }
    }

    public static void setHeight() {
        height = 0;
        while (height <= 5 || height >= 17) {
            try {
                height = Integer.parseInt(JOP.input("Please enter the height of your mazes (Min: 6, Max: 16)"));
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
