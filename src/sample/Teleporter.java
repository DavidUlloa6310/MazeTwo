package sample;

import javafx.scene.image.ImageView;

public class Teleporter extends ImageView {
    private final Point startPoint;
    private final Point teleportPoint;

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getTeleportPoint() {
        return teleportPoint;
    }

    public Teleporter(Point startPoint, Point teleportPoint, Maze maze) {
        this.startPoint = startPoint;
        this.teleportPoint = teleportPoint;
        setImage(ResourcePack.getTeleporter());
        maze.add(this, getStartPoint().getX(), getStartPoint().getY());
    }

}
