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

    public Teleporter(Point startPoint, Point teleportPoint) {
        this.startPoint = startPoint;
        this.teleportPoint = teleportPoint;
        setImage(ResourcePack.getTeleporter());
    }

}
