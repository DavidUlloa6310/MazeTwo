package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Maze extends GridPane {
    //ADD OPTION FOR TELEPORTER

    private boolean[][] maze;
    private PlayerModel player;

    private Image walkablePath;
    private Image blockedPath;
    private Image mobBlock;

    private Point playerSpawn;

    private int finalX;
    private int finalY;

    private ArrayList<Mob> mobs = new ArrayList<>();
    private ArrayList<Point> mobSpawns = new ArrayList();

    private ArrayList<Sword> swords = new ArrayList<>();
    private ArrayList<Point> swordSpawns = new ArrayList<>();
    private ArrayList<Teleporter> teleporters = new ArrayList<>();

    public Maze(boolean[][] maze, ResourcePack resourcePack, Point finalBlock, Point playerSpawn) {
        this(maze, resourcePack.getWalkablePath(), resourcePack.getBlockedPath(), resourcePack.getMobBlock(), finalBlock, playerSpawn);
    }

    public Maze(boolean[][] maze, ResourcePack resourcePack, ArrayList<Point> swords, ArrayList<Point> mobs, ArrayList<Teleporter> teleporters, Point finalBlock, Point playerSpawn) {
        this(maze, resourcePack, finalBlock, playerSpawn);

        this.swordSpawns = swords;
        generateSwords(swords);

        this.mobSpawns = mobs;
        generateMobs(mobs);
        this.teleporters = teleporters;

    }

    public Maze(boolean[][] maze, Image walkablePath, Image blockedPath, Image mobBlock, Point finalBlock, Point playerSpawn) {
        this.maze = maze;

        this.walkablePath = walkablePath;
        this.blockedPath = blockedPath;
        this.mobBlock = mobBlock;

        this.finalX = finalBlock.getX();
        this.finalY = finalBlock.getY();

        this.playerSpawn = playerSpawn;

        generateMaze();
        this.player = new PlayerModel(playerSpawn, this);
    }

    public void start() {
        startMobs();
    }

    public void end() {

        for (Mob mob : mobs) {
            mob.reset();
        }

        JOptionPane.showMessageDialog(null, "You've Lost!\nThrough the game, you walked " + player.getSteps() + " blocks");

        if (SceneLibrary.getPreviewStage() == null) {
            //SET PRIMARY STAGE BACK TO LIST OF MAZES.
        } else {
            SceneLibrary.getPreviewStage().close();
            SceneLibrary.setPreviewStage(null);
        }

        generateSwords(swordSpawns);
        player.reset();
    }

    public void generateMaze() {
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                add(new ImageView(walkablePath), c, r);
                if (maze[r][c]) add(new ImageView(blockedPath), c, r);
                if (r == finalX && c == finalY) add(new ImageView(ResourcePack.getEndBlock()), r, c);
            }
        }
    }

    public void addMob(Point...mobs) {
        for (Point mob : mobs) {
            this.mobSpawns.add(mob);
            this.mobs.add(new Mob(mob, mobBlock, this));
        }
    }

    public void addSword(Point...swords) {
        for (Point sword : swords) {
            this.swordSpawns.add(sword);
            this.swords.add(new Sword(sword, this));
        }
    }

    public void addTeleporters(Teleporter...teleporters) {
        this.teleporters.addAll(Arrays.asList(teleporters));
    }

    public void generateMobs(ArrayList<Point> mobSpawns) {
        for (Point mobSpawn : mobSpawns) {
            mobs.add(new Mob(mobSpawn, mobBlock, this));
        }
    }

    public void generateSwords(ArrayList<Point> swords) {
        for (Point sword : swords) {
            this.swords.add(new Sword(sword, this));
        }
    }

    public void startMobs() {
        for (Mob mob : mobs)
            mob.startAnimationTimer();
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void updatePlayerUI() {

    }

    public boolean[][] getMaze() {
        return maze;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public ArrayList<Sword> getSwords() {
        return swords;
    }

    public ArrayList<Teleporter> getTeleporters() {
        return teleporters;
    }

    public int getFinalX() {
        return finalX;
    }

    public int getFinalY() {
        return finalY;
    }

    public ArrayList<Point> getMobSpawns() { return mobSpawns; }

    public ArrayList<Point> getSwordSpawns() { return swordSpawns; }


}
