package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

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
    private ArrayList<Sword> swords = new ArrayList<>();
    private ArrayList<Teleporter> teleporters = new ArrayList<>();

    public Maze(boolean[][] maze, ResourcePack resourcePack, ArrayList<Point> swordSpawns, ArrayList<Point> mobSpawns, ArrayList<Teleporter> teleporters, Point finalBlock, Point playerSpawn) {
        this(maze, resourcePack.getWalkablePath(), resourcePack.getBlockedPath(), resourcePack.getMobBlock(), swordSpawns, mobSpawns, teleporters, finalBlock, playerSpawn);
    }

    public Maze(boolean[][] maze, Image walkablePath, Image blockedPath, Image mobBlock, ArrayList<Point> swordSpawns, ArrayList<Point> mobSpawns, ArrayList<Teleporter> teleporters, Point finalBlock, Point playerSpawn) {
        this.maze = maze;

        this.walkablePath = walkablePath;
        this.blockedPath = blockedPath;
        this.mobBlock = mobBlock;

        this.finalX = finalBlock.getX();
        this.finalY = finalBlock.getY();

        this.playerSpawn = playerSpawn;

        this.teleporters = teleporters;

        generateMaze();
        this.player = new PlayerModel(playerSpawn, this);

        generateMobs(mobSpawns);
        generateSwords(swordSpawns);
        generateTeleporters(teleporters);

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

    public void generateMobs(ArrayList<Point> mobSpawns) {
        for (Point mobSpawn : mobSpawns) {
            mobs.add(new Mob(mobSpawn, mobBlock, this));
        }
    }

    public void startMobs() {
        for (Mob mob : mobs)
            mob.startAnimationTimer();
    }

    public void generateSwords(ArrayList<Point> swordSpawns) {
        for (Point swordSpawn : swordSpawns) {
            swords.add(new Sword(swordSpawn, this));
        }
    }

    public void generateTeleporters(ArrayList<Teleporter> teleporters) {
        for (Teleporter teleporter : teleporters) {
            add(teleporter, teleporter.getStartPoint().getX(), teleporter.getStartPoint().getY());
        }
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


}
