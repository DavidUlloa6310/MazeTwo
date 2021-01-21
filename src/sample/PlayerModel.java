package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Items.Arrow;

public class PlayerModel extends Character {

    private boolean hasSword = false;
    private boolean hasBoots = false;
    private boolean hasBow = true;

    private Direction direction = Direction.DOWN;
    private int health;

    public int getHealth() { return health; }

    public PlayerModel(Point spawn, Maze maze) {
        super(spawn, new Image("images/characters/player.png"), maze);
        health = 3;
    }

    @Override
    public void moveRight() {
        super.moveRight();
        setDirection(Direction.RIGHT);
    }

    @Override
    public void moveDown() {
        super.moveDown();
        setDirection(Direction.DOWN);
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        setDirection(Direction.LEFT);
    }

    @Override
    public void moveUp() {
        super.moveUp();
        setDirection(Direction.UP);
    }

    public void shoot() {
        if (!hasBow) return;
        Point point = new Point(getCoordX(), getCoordY());
        Arrow arrow = new Arrow(point, getMaze());
        arrow.startAnimationTimer();
    }

    public void reset() {
        respawn(getSpawn().getX(),getSpawn().getY());
        health = 3;
        hasSword = false;
    }

    //SETTERS AND GETTERS

    public void changeHealth(int amount) {
        health += amount;
        getMaze().updatePlayerUI();
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void addSword() {
        hasSword = true;
        getMaze().updatePlayerUI();
    }

    public void removeSword() {
        hasSword = false;
        getMaze().updatePlayerUI();
    }

    public void addBoots() {
        hasBoots = true;
        getMaze().updatePlayerUI();
    }

    public void removeBoots() {
        hasBoots = false;
        getMaze().updatePlayerUI();
    }

    public void addBow() {
        hasBow = true;
        getMaze().updatePlayerUI();
    }

    public void removeBow() {
        hasBow = false;
        getMaze().updatePlayerUI();
    }

    public boolean hasBoots() {
        return hasBoots;
    }

    public boolean hasSword() {
        return hasSword;
    }

    public boolean hasBow() {
        return hasBow;
    }

}
