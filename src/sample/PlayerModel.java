package sample;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerModel extends Character {

    private boolean hasSword = false;
    private int health;

    public int getHealth() { return health; }

    public PlayerModel(Point spawn, Maze maze) {
        super(spawn, new Image("images/characters/player.png"), maze);
        health = 3;
    }

    public void changeHealth(int amount) {
        health += amount;
    }

    public void addSword() {
        hasSword = true;
    }

    public void removeSword() {
        hasSword = false;
    }

    public boolean hasSword() {
        return hasSword;
    }

}
