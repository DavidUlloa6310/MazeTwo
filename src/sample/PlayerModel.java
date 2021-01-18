package sample;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerModel extends Character {

    private boolean hasSword = false;
    private boolean hasBoots = false;

    private int health;

    public int getHealth() { return health; }

    public PlayerModel(Point spawn, Maze maze) {
        super(spawn, new Image("images/characters/player.png"), maze);
        health = 3;
    }

    public void changeHealth(int amount) {
        health += amount;
        getMaze().updatePlayerUI();
    }

    public void setHealth(int health) {
        this.health = health;
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

    public boolean hasBoots() {
        return hasBoots;
    }

    public boolean hasSword() {
        return hasSword;
    }

    public void reset() {
        respawn(getSpawn().getX(),getSpawn().getY());
        health = 3;
        hasSword = false;
    }

}
