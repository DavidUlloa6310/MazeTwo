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

    private static ObservableList<Maze> mazes = generateScenes();

    public static ObservableList<Maze> generateScenes() {

        ObservableList<Maze> mazes = FXCollections.observableArrayList();

        ArrayList<Point> mazeOneSwordSpawns = new ArrayList<Point>();
        mazeOneSwordSpawns.add(new Point(10,10));

        ArrayList<Point> mazeOneMobSpawns = new ArrayList<>();
        mazeOneMobSpawns.add(new Point(10,10));

        Maze firstMaze = new Maze(MazeArray.getFirstMaze(), ResourcePack.GRASS, mazeOneSwordSpawns, mazeOneMobSpawns, new ArrayList<Teleporter>(), new Point(23, 0), new Point(0,0));
        mazes.add(firstMaze);

        ArrayList<Point> mazeTwoMobSpawns = new ArrayList<Point>();
        mazeTwoMobSpawns.add(new Point(10, 10));
        mazeTwoMobSpawns.add(new Point(5,10));
        mazeTwoMobSpawns.add(new Point(8, 13));

        ArrayList<Point> mazeTwoSwordSpawns = new ArrayList<Point>();
        mazeTwoSwordSpawns.add(new Point(17, 10));
        mazeTwoSwordSpawns.add(new Point(5,10));
        mazeTwoSwordSpawns.add(new Point(8, 13));

        Maze secondMaze = new Maze(MazeArray.getSecondMaze(), ResourcePack.CAVE, mazeTwoSwordSpawns, mazeTwoMobSpawns, new ArrayList<Teleporter>(), new Point(21, 13), new Point(0,0));
        mazes.add(secondMaze);

        ArrayList<Point> mazeThreeMobSpawns = new ArrayList<Point>();
        mazeThreeMobSpawns.add(new Point(21,3));
        mazeThreeMobSpawns.add(new Point(17,11));
        mazeThreeMobSpawns.add(new Point(5,13));

        ArrayList<Point> mazeThreeSwordSpawns = new ArrayList<Point>();
        mazeThreeMobSpawns.add(new Point(7,7));
        mazeThreeMobSpawns.add(new Point(19,7));
        mazeThreeMobSpawns.add(new Point(20,11));
        mazeThreeMobSpawns.add(new Point(5,14));

        Maze thirdMaze = new Maze(MazeArray.getThirdMaze(), ResourcePack.NETHER, mazeThreeSwordSpawns, mazeThreeMobSpawns, new ArrayList<Teleporter>(), new Point(22,0), new Point(11,7));
        mazes.add(thirdMaze);

        ArrayList<Point> mazeFourMobSpawns = new ArrayList<Point>();
        mazeFourMobSpawns.add(new Point(6,3));
        mazeFourMobSpawns.add(new Point(13,2));
        mazeFourMobSpawns.add(new Point(17,14));

        ArrayList<Point> mazeFourSwordSpawns = new ArrayList<Point>();
        mazeFourSwordSpawns.add(new Point(0,7));
        mazeFourSwordSpawns.add(new Point(1,10));
        mazeFourSwordSpawns.add(new Point(16,1));

        ArrayList<Teleporter> teleporters = new ArrayList<Teleporter>();
        teleporters.add(new Teleporter(new Point(2,12), new Point(15,0)));
        teleporters.add(new Teleporter(new Point(15,0), new Point(19,15)));
        teleporters.add(new Teleporter(new Point(19,15), new Point(19, 5)));
        teleporters.add(new Teleporter(new Point(19,5), new Point(23,10)));
        teleporters.add(new Teleporter(new Point (11, 7), new Point(19,15)));

        Maze fourthMaze = new Maze(MazeArray.getFourthMaze(), ResourcePack.END, mazeFourSwordSpawns, mazeFourMobSpawns, teleporters, new Point(21,3), new Point(0,0));
        mazes.add(fourthMaze);

        return mazes;
    }

    public static ObservableList<Maze> getMazes() {
        return mazes;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        SceneLibrary.primaryStage = primaryStage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void playScene(int index) {
        if (index < mazes.size()) {
            Scene activeScene = setUpScene(mazes.get(index));
            mazes.get(index).startMobs();
            primaryStage.setScene(activeScene);
        }
    }

    public static void playScene(Maze maze) {
        Scene scene = setUpScene(maze);
        maze.startMobs();
        primaryStage.setScene(scene);
    }

    public static void addScene(Maze maze) {
        mazes.add(maze);
    }

    public static Scene setUpScene(Maze maze) {
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
}
