package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static sample.Main.*;

public class Character extends ImageView {
    private int x;
    private int y;

    private Maze maze;

    private int steps;
    public int getSteps() { return steps; }

    public Character(Point spawn, Image image, Maze maze) {
        setImage(image);
        x = spawn.getX();
        y = spawn.getY();
        this.maze = maze;
        this.maze.add(this, x, y);
    }

    public void moveRight() {
        if (this.x != width - 1 && !maze.getMaze()[this.y][this.x + 1]) {
            this.x++;
            this.maze.getChildren().remove(this);
            this.maze.add(this, x, y);
            steps++;
        }
    }

    public void moveDown() {
        if (this.y != height - 1 && !maze.getMaze()[this.y + 1][this.x]) {
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
}
