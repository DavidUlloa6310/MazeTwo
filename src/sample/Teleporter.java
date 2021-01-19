package sample;

import javafx.scene.image.ImageView;

public class Teleporter extends ImageView {
    private final Point startPoint;
    private final Point teleportPoint;
    private Maze maze;

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getTeleportPoint() {
        return teleportPoint;
    }

    public Teleporter(Point startPoint, Point teleportPoint, Maze maze) {
        this.startPoint = startPoint;
        this.teleportPoint = teleportPoint;
        this.maze = maze;
        setImage(ResourcePack.getTeleporter());
        maze.add(this, getStartPoint().getX(), getStartPoint().getY());
    }

    public void setMaze(Maze maze) {
        maze.getChildren().remove(this);
        this.maze = maze;
        maze.add(this, getStartPoint().getX(), getStartPoint().getY());
    }

}
