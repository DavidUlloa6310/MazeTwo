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

//        addEventFilter(KeyEvent.KEY_PRESSED, key -> {
//
//            if (key.getCode() == KeyCode.W) {
//                moveUp();
//            } else if (key.getCode() == KeyCode.A) {
//                moveLeft();
//            } else if (key.getCode() == KeyCode.S) {
//                moveDown();
//            } else if (key.getCode() == KeyCode.D) {
//                moveRight();
//            }
//
//            if ((getCoordX() == maze.getFinalX() && getCoordY() == maze.getFinalY()) || key.getCode() == KeyCode.L) {
//                JOP.msg("You've Won!\nThrough the game, you walked " + getSteps() + " blocks");
//                //END GAME OR SWITCH SCENE.
//            }
//
//            for (Mob mob : maze.getMobs()) {
//
//                if (!mob.isDead() && mob.checkDamage(this)) {
////                    Media media = new Media(new File(deathSoundPath).toURI().toString());
////                    MediaPlayer deathPlayer = new MediaPlayer(media);
////                    deathPlayer.setAutoPlay(true);
//                }
//
//                maze.updatePlayerUI();
//            }
//
//            for (Sword sword : maze.getSwords()) {
//
//                if (getCoordX() == sword.getCoordX() && getCoordY() == sword.getCoordY() && !sword.isGone()) {
//                    addSword();
//                    maze.getChildren().remove(sword);
//                    maze.updatePlayerUI();
//                }
//            }
//
//            for (Teleporter teleporter : maze.getTeleporters()) {
//                if (getCoordX() == teleporter.getStartPoint().getX() && getCoordY() == teleporter.getStartPoint().getY()) {
//                    respawn(teleporter.getTeleportPoint().getX(), teleporter.getTeleportPoint().getY());
//                    break;
//                }
//            }
//
//        });

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
