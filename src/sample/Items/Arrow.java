package sample.Items;


import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import sample.*;

public class Arrow extends ImageView {

    private AnimationTimer animationTimer;
    private int coordX;
    private int coordY;
    private final ImageView arrow = this;
    private final Maze maze;
    private final Direction direction;

    public Arrow(Point point, Maze maze) {

        coordX = point.getX();
        coordY = point.getY();
        direction = maze.getPlayer().getDirection();

        this.maze = maze;

        maze.add(new ImageView(ResourcePack.getArrow()), 0, 0);

        animationTimer = new AnimationTimer() {
            long lastTick = 0;

            @Override
            public void handle(long now) {
                if (lastTick == 0) {

                    if (coordX >= maze.getMaze()[0].length || coordX < 0 || coordY >= maze.getMaze().length || coordY < 0) {
                        this.stop();
                    }

                    switch (direction) {
                        case UP:
                            maze.getChildren().remove(arrow);
                            arrow.toFront();
                            maze.add(arrow, coordX, --coordY);
                            break;
                        case DOWN:
                            maze.getChildren().remove(arrow);
                            arrow.toFront();
                            maze.add(arrow, coordX, ++coordY);
                            break;
                        case LEFT:
                            maze.getChildren().remove(arrow);
                            arrow.toFront();
                            maze.add(arrow, --coordX, coordY);
                            break;
                        case RIGHT:
                            maze.getChildren().remove(arrow);
                            arrow.toFront();
                            maze.add(arrow, ++coordX, coordY);
                    }

                    checkCollision();

                }

                if (now - lastTick > 900000000) {

                    if (coordX >= maze.getMaze()[0].length || coordX < 0 || coordY >= maze.getMaze().length || coordY < 0) {
                        this.stop();
                    }

                    switch (direction) {
                        case UP:
                            maze.getChildren().remove(arrow);
                            arrow.toFront();
                            maze.add(arrow, coordX, --coordY);
                            break;
                        case DOWN:
                            maze.getChildren().remove(arrow);
                            arrow.toFront();
                            maze.add(arrow, coordX, ++coordY);
                            break;
                        case LEFT:
                            maze.getChildren().remove(arrow);
                            arrow.toFront();
                            maze.add(arrow, --coordX, coordY);
                            break;
                        case RIGHT:
                            maze.getChildren().remove(arrow);
                            arrow.toFront();
                            maze.add(arrow, ++coordX, coordY);
                    }

                    checkCollision();
                }

            }
        };
        switch (maze.getPlayer().getDirection()) {
            case LEFT:
                setRotate(270);
            case RIGHT:
                setRotate(90);
                break;
            case DOWN:
                setRotate(180);
                break;
        }
        maze.add(this, point.getX(), point.getY());
    }

    public void checkCollision() {
        for (Mob mob : maze.getMobs()) {
            if (mob.getCoordX() == getCoordX() && mob.getCoordY() == getCoordY()) {
                mob.die();
            }
        }
    }

    public void startAnimationTimer() {
        animationTimer.start();
    }

    public void stopAnitmationTimer() {
        animationTimer.stop();
    }

    //GETTERS

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
}
