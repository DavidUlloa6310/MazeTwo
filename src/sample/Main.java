package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static final int width = 24;
    public static final int height = 16;
    public static final int tileSize = 25;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

//        ArrayList<Point> minotaurs = new ArrayList<Point>();
//        minotaurs.add(new Point(10, 10));
//        minotaurs.add(new Point(15, 0));
//
//        ArrayList<Point> swords = new ArrayList<Point>();
//        swords.add(new Point(10, 10));
//
//        Maze firstMaze = new Maze(MazeArray.getFirstMaze(), ResourcePack.CAVE, swords, minotaurs, new ArrayList<>(), new Point(10,10), new Point(0,0));
//
//        primaryStage.setScene(setUpScene(firstMaze));
//        primaryStage.show();
    }

    public Scene setUpScene(Maze maze) {
        Scene scene = new Scene(maze, 600, 400);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, key -> {

            if (key.getCode() == KeyCode.W) {
                maze.getPlayer().moveUp();
            } else if (key.getCode() == KeyCode.A) {
                maze.getPlayer().moveLeft();
            } else if (key.getCode() == KeyCode.S) {
                maze.getPlayer().moveDown();
            } else if (key.getCode() == KeyCode.D) {
                maze.getPlayer().moveRight();
            }

            if ((maze.getPlayer().getCoordX() == maze.getFinalX() && maze.getPlayer().getCoordY() == maze.getFinalY()) || key.getCode() == KeyCode.L) {
                JOP.msg("You've Won!\nThrough the game, you walked " + maze.getPlayer().getSteps() + " blocks");
                //END GAME OR SWITCH SCENE.
            }

            for (Mob mob : maze.getMobs()) {

                if (!mob.isDead() && mob.checkDamage(maze.getPlayer())) {
//                    Media media = new Media(new File(deathSoundPath).toURI().toString());
//                    MediaPlayer deathPlayer = new MediaPlayer(media);
//                    deathPlayer.setAutoPlay(true);
                }

                maze.updatePlayerUI();
            }

            for (Sword sword : maze.getSwords()) {

                if (maze.getPlayer().getCoordX() == sword.getCoordX() && maze.getPlayer().getCoordY() == sword.getCoordY() && !sword.isGone()) {
                    maze.getPlayer().addSword();
                    maze.getChildren().remove(sword);
                    maze.updatePlayerUI();
                }
            }

            for (Teleporter teleporter : maze.getTeleporters()) {
                if (maze.getPlayer().getCoordX() == teleporter.getStartPoint().getX() && maze.getPlayer().getCoordY() == teleporter.getStartPoint().getY()) {
                    maze.getPlayer().respawn(teleporter.getTeleportPoint().getX(), teleporter.getTeleportPoint().getY());
                    break;
                }
            }

        });
        return scene;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
