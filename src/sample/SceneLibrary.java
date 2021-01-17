package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public abstract class SceneLibrary {

    private static Stage primaryStage;
    private static Stage previewStage;

    private static Scene mazeList;
    private static Scene mazeBuilder;

    private static ObservableList<Maze> mazes = generateScenes();

    public static ObservableList<Maze> generateScenes() {

        ObservableList<Maze> mazes = FXCollections.observableArrayList();

        Maze firstMaze = new Maze(MazeArray.getFirstMaze(), ResourcePack.GRASS, new Point(23, 0), new Point(0,0));
        firstMaze.addMob(new Point(10,10));
        firstMaze.addSword(new Point(10,10));
        mazes.add(firstMaze);

        Maze secondMaze = new Maze(MazeArray.getSecondMaze(), ResourcePack.CAVE, new Point(21, 13), new Point(0,0));
        secondMaze.addMob(new Point(10,10), new Point(5,10), new Point(8,13));
        secondMaze.addSword(new Point(17,10), new Point(5,10), new Point(8,13));
        mazes.add(secondMaze);

        Maze thirdMaze = new Maze(MazeArray.getThirdMaze(), ResourcePack.NETHER, new Point(22,0), new Point(11,7));
        thirdMaze.addMob(new Point(21,3), new Point(17,11), new Point(5,13));
        thirdMaze.addSword(new Point(7,7), new Point(19,7), new Point(20, 11), new Point(5,14));
        mazes.add(thirdMaze);

        Maze fourthMaze = new Maze(MazeArray.getFourthMaze(), ResourcePack.END, new Point(21,3), new Point(0,0));
        fourthMaze.addMob(new Point(6,3), new Point(13,2), new Point(17,14));
        fourthMaze.addSword(new Point(0,7), new Point(1,10), new Point(16,1));
        fourthMaze.addTeleporters(
                new Teleporter(new Point(2,12), new Point(15,0), fourthMaze),
                new Teleporter(new Point(15,0), new Point(19,15), fourthMaze),
                new Teleporter(new Point(19,15), new Point(19, 5), fourthMaze),
                new Teleporter(new Point(19,5), new Point(23,10), fourthMaze),
                new Teleporter(new Point (11, 7), new Point(19,15), fourthMaze));
        mazes.add(fourthMaze);

        return mazes;
    }

    public static ObservableList<Maze> getMazes() {
        return mazes;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        SceneLibrary.primaryStage = primaryStage;
        primaryStage.setTitle("Maze Builder");
        primaryStage.setResizable(false);
    }
    public static void setPreviewStage(Stage previewStage) {
        SceneLibrary.previewStage = previewStage;
        previewStage.setTitle("Maze Preview");
        previewStage.setResizable(false);
    }

    public static void setMazeList(Scene mazeList) {
        SceneLibrary.mazeList = mazeList;
    }

    public static void setMazeBuilder(Scene mazeBuilder) {
        SceneLibrary.mazeBuilder = mazeBuilder;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static Stage getPreviewStage() { return previewStage; }

    public static void previewGame(int index) {
        if (index < mazes.size()) {
            Scene activeScene = setUpScene(mazes.get(index));
            mazes.get(index).startMobs();
            primaryStage.setScene(activeScene);
        }
    }

    public static void previewGame(Maze maze) {
        Scene scene = setUpScene(maze);
        SceneLibrary.setPreviewStage(new Stage());
        SceneLibrary.previewStage.setScene(scene);
        previewStage.show();
        maze.startMobs();
    }

    public static void addScene(Maze maze) {
        mazes.add(maze);
    }

    public static Scene setUpScene(Maze maze) {
        Scene scene = new Scene(maze, 625, 400);
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
                maze.end();
            }

            for (Mob mob : maze.getMobs()) {

                if (!mob.isDead() && mob.checkDamage(maze.getPlayer())) {

                    if (SceneLibrary.previewStage == null) {
                        //SEND PRIMARY STAGE BACK TO LIST
                    } else {
                        SceneLibrary.previewStage.close();
                        SceneLibrary.previewStage = null;
                    }
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
}
