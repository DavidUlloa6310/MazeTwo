package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static sample.Main.*;

public class Character extends ImageView {
    //Any character; can be minotaur or player.
    private int x;
    private int y;
    private Point spawn;

    private Maze maze;

    private int steps;
    public int getSteps() { return steps; }
    public Point getSpawn() { return spawn; }

    public Character(Point spawn, Image image, Maze maze) {
        setImage(image);
        this.spawn = spawn;
        x = spawn.getX();
        y = spawn.getY();
        this.maze = maze;
        this.maze.add(this, x, y);
    }

    public void moveRight() {
        if (this.x != maze.getMaze()[0].length - 1 && !maze.getMaze()[this.y][this.x + 1]) {
            this.x++;
            this.maze.getChildren().remove(this);
            this.maze.add(this, x, y);
            steps++;
        }
    }

    public void moveDown() {
        if (this.y != maze.getMaze().length - 1 && !maze.getMaze()[this.y + 1][this.x]) {
            this.y++;
            this.maze.getChildren().remove(this);
            this.maze.add(this, x, y);
            steps++;
        }
    }

    public void moveLeft() {
        if (this.x != 0 && !maze.getMaze()[this.y][this.x - 1]) {
            this.x--;
            this.maze.getChildren().remove(this);
            this.maze.add(this, x, y);
            steps++;
        }
    }

    public void moveUp() {
        if (this.y != 0 && !maze.getMaze()[this.y - 1][this.x]) {
            this.y--;
            this.maze.getChildren().remove(this);
            this.maze.add(this, x, y);
            steps++;
        }
    }

    public void respawn(int respawnX, int respawnY) {
        this.maze.getChildren().remove(this);
        this.maze.add(this, x, y);
        toFront();
        this.x = respawnX;
        this.y = respawnY;
    }

    public int getCoordX() {
        return x;
    }

    public int getCoordY() {
        return y;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }
}
