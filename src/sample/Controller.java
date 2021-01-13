package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private ImageView selectorImage;

    @FXML
    private ChoiceBox resourceChoiceBox;

    private GraphicsContext gc;

    private final ObservableList<String> resourcePacks = FXCollections.observableArrayList("Grass", "Cave", "Nether", "End");

    private boolean[][] maze = generateArray();
    private ResourcePack resourcePack = ResourcePack.GRASS;
    private Point playerSpawn;
    private Point exitBlock;

    private ArrayList<Point> swordSpawns = new ArrayList<>();
    private ArrayList<Point> mobSpawns = new ArrayList<>();
    private final ArrayList<Teleporter> teleporters = new ArrayList<>();

    private ActiveClick activeClick = ActiveClick.DEFAULT;

    @FXML
    private void initialize() {

        resourceChoiceBox.setItems(resourcePacks);

        gc = canvas.getGraphicsContext2D();
        resetCanvas();
        selectorImage.setImage(resourcePack.getBlockedPath());
    }

    public void clickCanvas(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) onMouseLeftClick(e.getX(), e.getY()); else onMouseRightClick(e.getX(), e.getY());
    }

    public void onMouseDragged(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY && activeClick == ActiveClick.DEFAULT) {
            onMouseLeftClick(e.getX(), e.getY());
        } else if (activeClick == ActiveClick.DEFAULT) {
            onMouseRightClick(e.getX(), e.getY());
        }
    }

    public void onMouseLeftClick(double pixelX, double pixelY) {
        int xBox = (int) pixelX / 25;
        int yBox = (int) pixelY / 25;

        if (xBox > 23 || xBox < 0 || yBox < 0 || yBox > 15)
            return;

        switch(activeClick) {
            case DEFAULT:
                maze[yBox][xBox] = true;
                gc.drawImage(resourcePack.getBlockedPath(), xBox * 25, yBox * 25);
                break;
            case SPAWN:
                if (playerSpawn == null) {
                    playerSpawn = new Point(xBox, yBox);
                    gc.drawImage(ResourcePack.getStartBlock(), xBox * 25, yBox * 25);
                } else if (playerSpawn.getX() == xBox && playerSpawn.getY() == yBox) {
                    playerSpawn = null;
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
                } else {
                    JOP.msg("There can be only one player spawn. \n Please unselect the existing spawn");
                }
                break;
            case ENDBLOCK:
                if (exitBlock == null) {
                    exitBlock = new Point(xBox, yBox);
                    gc.drawImage(ResourcePack.getEndBlock(), xBox * 25, yBox * 25);
                } else if (exitBlock.getX() == xBox && exitBlock.getY() == yBox) {
                    exitBlock = null;
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
                } else {
                    JOP.msg("There can be only one exit block. \n Please unselect the existing exit block");
                }
                break;
            case MOBS:
                if (removeFromList(xBox, yBox, mobSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
                } else {
                    gc.drawImage(resourcePack.getMobBlock(), xBox * 25, yBox * 25);
                    mobSpawns.add(new Point(xBox, yBox));
                }
                break;
            case SWORDS:
                if (removeFromList(xBox, yBox, swordSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
                } else {
                    gc.drawImage(ResourcePack.getSword(), xBox * 25, yBox * 25);
                    swordSpawns.add(new Point(xBox, yBox));
                }
                break;
        }
    }

    public void onMouseRightClick(double pixelX, double pixelY) {

        int xBox = (int) pixelX / 25;
        int yBox = (int) pixelY / 25;

        if (activeClick == ActiveClick.DEFAULT &&  xBox < 24 && yBox < 16 && xBox > -1 && yBox > -1) {
            maze[yBox][xBox] = false;
            gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
        }

    }

    public void resetCanvas() {
        for (int r = 0; r < 16; r++) {
            for (int c = 0; c < 24; c++) {
                gc.drawImage(resourcePack.getWalkablePath(), c * 25, r * 25);
            }
        }
    }

    public void repaintCanvas() {

        for (int r = 0; r < 16; r++) {
            for (int c = 0; c < 24; c++) {
                if (maze[r][c]) gc.drawImage(resourcePack.getBlockedPath(), c * 25, r * 25); else gc.drawImage(resourcePack.getWalkablePath(), c * 25, r * 25);
            }
        }

        if (playerSpawn != null) gc.drawImage(ResourcePack.getStartBlock(), playerSpawn.getX() * 25, playerSpawn.getY() * 25);
        if (exitBlock != null) gc.drawImage(ResourcePack.getEndBlock(), exitBlock.getX() * 25, exitBlock.getY() * 25);

        for (Point sword : swordSpawns) {
            gc.drawImage(ResourcePack.getSword(), sword.getX() * 25, sword.getY() * 25);
        }

        for (Point mob : mobSpawns) {
            gc.drawImage(resourcePack.getMobBlock(), mob.getX() * 25, mob.getY() * 25);
        }
    }

    public void updateCanvas() {
        if (resourceChoiceBox.getValue().equals("Grass")) {
            resourcePack = ResourcePack.GRASS;
        } else if (resourceChoiceBox.getValue().equals("Cave")) {
            resourcePack = ResourcePack.CAVE;
        } else if (resourceChoiceBox.getValue().equals("Nether")) {
            resourcePack = ResourcePack.NETHER;
        } else if (resourceChoiceBox.getValue().equals("End")) {
            resourcePack = ResourcePack.END;
        }

        selectorImage.setImage(resourcePack.getBlockedPath());
        repaintCanvas();
    }

    public void clearMaze() {
        resetCanvas();
        this.maze = generateArray();
        playerSpawn = null;
        exitBlock = null;
        mobSpawns = new ArrayList<Point>();
        swordSpawns = new ArrayList<Point>();
    }

    public void placeSpawn() {
        if (activeClick == ActiveClick.SPAWN) {
            activeClick = ActiveClick.DEFAULT;
            selectorImage.setImage(resourcePack.getBlockedPath());
        } else {
            activeClick = ActiveClick.SPAWN;
            selectorImage.setImage(ResourcePack.getStartBlock());
        }
    }

    public void placeExit() {
        if (activeClick == ActiveClick.ENDBLOCK) {
            activeClick = ActiveClick.DEFAULT;
            selectorImage.setImage(resourcePack.getBlockedPath());
        } else {
            activeClick = ActiveClick.ENDBLOCK;
            selectorImage.setImage(ResourcePack.getEndBlock());
        }
    }

    public void placeMobs() {
        if (activeClick == ActiveClick.MOBS) {
            activeClick = ActiveClick.DEFAULT;
            selectorImage.setImage(resourcePack.getBlockedPath());
        } else {
            activeClick = ActiveClick.MOBS;
            selectorImage.setImage(resourcePack.getMobBlock());
        }
    }

    public void placeSwords() {
        if (activeClick == ActiveClick.SWORDS) {
            activeClick = ActiveClick.DEFAULT;
            selectorImage.setImage(resourcePack.getBlockedPath());
        } else {
            activeClick = ActiveClick.SWORDS;
            selectorImage.setImage(ResourcePack.getSword());
        }
    }

    public boolean[][] generateArray() {
        boolean[][] array = new boolean[16][24];
        for (int r = 0; r < 16; r++) {
            for (int c = 0; c < 24; c++) {
                array[r][c] = false;
            }
        }

        return array;
    }

    public boolean removeFromList(int x, int y, ArrayList<Point> list) {
        for (Point point : list) {
            if (point.getX() == x && point.getY() == y) {
                list.remove(point);
                return true;
            }
        }

        return false;
    }

    public void saveGame() {
        if (playerSpawn == null || exitBlock == null) {
            JOP.msg("Every maze needs to have at least one player spawn and a exit block. ");
            return;
        }

        SceneLibrary.addScene(new Maze(maze, resourcePack, swordSpawns, mobSpawns, new ArrayList<>(), exitBlock, playerSpawn));

    }

    public void playGame() {
        if (playerSpawn == null || exitBlock == null) {
            JOP.msg("Every maze needs to have at least one player spawn and a exit block. ");
            return;
        }

        Maze currentMaze = new Maze(maze, resourcePack, swordSpawns, mobSpawns, teleporters, exitBlock, playerSpawn);
        SceneLibrary.playScene(currentMaze);
    }

}