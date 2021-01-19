package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Item extends ImageView {
    private int x;
    private int y;
    private Maze maze;
    private boolean isGone;

    public int getCoordX() {
        return x;
    }

    public int getCoordY() {
        return y;
    }

    public Maze getMaze() {
        return maze;
    }

    public boolean isGone() { return isGone; }
    public void setGone(boolean gone) { isGone = gone; }

    public Item(Point point, Maze maze) {
        this.x = point.getX();
        this.y = point.getY();
        this.maze = maze;
        maze.add(this, x, y);
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public void reset() {
        maze.add(this, getCoordX(), getCoordY());
        setGone(false);
    }

    public abstract void collect(PlayerModel player);
}
