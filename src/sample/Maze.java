package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Items.*;

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

    private ArrayList<Item> items = new ArrayList<>();

    private ArrayList<Teleporter> teleporters = new ArrayList<>();

    public Maze(boolean[][] maze, ResourcePack resourcePack, Point finalBlock, Point playerSpawn) {
        this(maze, resourcePack.getWalkablePath(), resourcePack.getBlockedPath(), resourcePack.getMobBlock(), finalBlock, playerSpawn);
    }

    public Maze(boolean[][] maze, ResourcePack resourcePack, ArrayList<Point> mobs, ArrayList<Teleporter> teleporters, Point finalBlock, Point playerSpawn) {
        this(maze, resourcePack, finalBlock, playerSpawn);
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
        updatePlayerUI();
    }

    public void start() {
        startMobs();
    }

    public void end() {

        resetMobs();
        resetItems();

        SceneLibrary.getPreviewStage().close();
        SceneLibrary.setPreviewStage(null);

        player.reset();
    }

    public void generateMaze() {
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                add(new ImageView(walkablePath), c, r);
                if (maze[r][c]) add(new ImageView(blockedPath), c, r);
            }
        }

        add(new ImageView(ResourcePack.getEndBlock()), finalX, finalY);

    }

    public void addMob(Point...mobs) {
        for (Point mob : mobs) {
            this.mobSpawns.add(mob);
            this.mobs.add(new Mob(mob, mobBlock, this));
        }
    }

    public void startMobs() {
        for (Mob mob : mobs)
            mob.startAnimationTimer();
    }

    public void stopMobs() {
        for (Mob mob : mobs)
            mob.stopAnimationTimer();
    }

    public void resetMobs() {
        for (Mob mob : mobs) {
            mob.reset();
        }
    }

    public void changeMobSpeed(int speed) {
        for (Mob mob : mobs) {
            mob.setSpeed(speed);
        }
    }

    public void resetItems() {

        for (Item item : items) {
            getChildren().remove(item);
            item.reset();
        }
    }

    public void addSword(Point...swords) {
        for (Point sword : swords) {
            this.items.add(new Sword(sword, this));
        }
    }

    public void addSwords(ArrayList<Point> points) {
        for (Point point : points) {
            this.items.add(new Sword(point, this));
        }
    }

    public void addBoots(ArrayList<Point> points) {
        for (Point point : points) {
            this.items.add(new Boot(point, this));
        }
    }

    public void addArmor(ArrayList<Point> points) {
        for (Point point : points) {
            this.items.add(new Armor(point, this));
        }
    }

    public void addHealthPotion(ArrayList<Point> points) {
        for (Point point : points) {
            this.items.add(new HealthPotion(point, this));
        }
    }

    public void addInvisPotion(ArrayList<Point> points) {
        for (Point point : points) {
            this.items.add(new InvisPotion(point, this));
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

    public PlayerModel getPlayer() {
        return player;
    }

    public void updatePlayerUI() {
        for (int i = 0; i < 10; i++)
            add(new Rectangle(25, 25, Color.WHITE), 24, i);

        for (int i = 0; i < player.getHealth(); i++) {
            add(new ImageView(ResourcePack.getHeart()), 24, i);
        }

        if (player.hasSword()) add(new ImageView(ResourcePack.getSword()), 24, player.getHealth() + 1);
        if (player.hasBoots()) add(new ImageView(ResourcePack.getBoots()), 24, player.getHealth() + 2);

    }

    public Maze cloneMaze() {
        Maze maze = new Maze(this.maze, getWalkablePath(), getBlockedPath(), getMobBlock(), new Point(getFinalX(), getFinalY()), getPlayerSpawn());
        maze.setTeleporters(this.getTeleporters());
        for (Teleporter teleporter : teleporters) {
            teleporter.setMaze(maze);
        }

        maze.setItems(getItems());

        for (Item item : getItems()) {
            item.setMaze(maze);
        }

        maze.resetItems();

        maze.generateMobs(getMobSpawns());
        return maze;
    }

    public boolean[][] getMaze() {
        return maze;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
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

    public ArrayList<Item> getItems() {
        return items;
    }

    public Image getWalkablePath() {
        return walkablePath;
    }

    public Image getBlockedPath() {
        return blockedPath;
    }

    public Image getMobBlock() {
        return mobBlock;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public void setTeleporters(ArrayList<Teleporter> teleporters) {
        this.teleporters = teleporters;
    }

    public void setMaze(boolean[][] maze) {
        this.maze = maze;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public void setWalkablePath(Image walkablePath) {
        this.walkablePath = walkablePath;
    }

    public void setBlockedPath(Image blockedPath) {
        this.blockedPath = blockedPath;
    }

    public void setMobBlock(Image mobBlock) {
        this.mobBlock = mobBlock;
    }

    public void setPlayerSpawn(Point playerSpawn) {
        this.playerSpawn = playerSpawn;
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }

    public void setMobs(ArrayList<Mob> mobs) {
        this.mobs = mobs;
    }

    public void setMobSpawns(ArrayList<Point> mobSpawns) {
        this.mobSpawns = mobSpawns;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
