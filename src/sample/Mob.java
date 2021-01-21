package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static sample.Main.*;
import javax.swing.*;

public class Mob extends Character {
    private boolean isDead = false;
    private int speed = 2;

    private AnimationTimer animationTimer;

    public boolean isDead() { return isDead; }
    public void setDead(boolean isDead) { this.isDead = isDead; }

    public Mob(Point point, Image image, Maze maze) {
        super(point, image, maze);
        animationTimer = new AnimationTimer() {
            long lastTick = 0;

            @Override
            public void handle(long now) {
                if (lastTick == 0) {
                    if (!isDead()) {
                        lastTick = now;
                        move(maze.getPlayer());
                        checkDamage(maze.getPlayer());
                        maze.updatePlayerUI();
                    }
                }

                if (now - lastTick > 500000000 / speed) {
                    lastTick = now;

                    move(maze.getPlayer());
                    checkDamage(maze.getPlayer());
                    maze.updatePlayerUI();

                }

            }
        };
    }

    public void move(Character character) {
        if (character.getCoordX() > this.getCoordX()) {
            this.moveRight();
        } else if (character.getCoordX() < this.getCoordX()) {
            this.moveLeft();
        }

        if (character.getCoordY() > this.getCoordY()) {
            this.moveDown();
        } else if (character.getCoordY() < this.getCoordY()) {
            this.moveUp();
        }

    }

    public void die() {
        this.setDead(true);
        toBack();
        stopAnimationTimer();
    }

    public void alive() {
        this.setDead(false);
        startAnimationTimer();
    }

    public void reset() {
        stopAnimationTimer();
        respawn(getSpawn().getX(), getSpawn().getY());
        this.speed = 2;
        toFront();
    }

    public boolean checkDamage(PlayerModel player) {
        if (this.getCoordX() == player.getCoordX() && this.getCoordY() == player.getCoordY()) {
            if (player.hasSword()) {
                player.removeSword();
                this.die();
                return false;
            } else {
                if (player.getHealth() > 1) {
                    player.changeHealth(-1);
                    return true;
                } else {
                    JOP.msg("You've Lost!\nThrough the game, you walked " + player.getSteps() + " blocks");
                    getMaze().end();
                }
            }
        }
        return false;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void startAnimationTimer() {
        if (!isDead)
        animationTimer.start();
    }
    public void stopAnimationTimer() {
        animationTimer.stop();
    }

}
