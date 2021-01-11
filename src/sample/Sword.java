package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sword extends ImageView {
    private int x;
    private int y;
    private boolean isGone;

    public int getCoordX() {
        return x;
    }

    public int getCoordY() {
        return y;
    }

    public boolean isGone() { return isGone; }
    public void setGone(boolean gone) { isGone = gone; }

    public Sword(Point point, Maze maze) {
        this.x = point.getX();
        this.y = point.getY();
        setImage(ResourcePack.getSword());
        maze.add(this, x, y);
    }
}
