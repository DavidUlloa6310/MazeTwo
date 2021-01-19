package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class BuilderController {

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
    private ArrayList<Point> bootSpawns = new ArrayList<>();
    private ArrayList<Point> armorSpawns = new ArrayList<>();
    private ArrayList<Point> healthPotionSpawns = new ArrayList<>();
    private ArrayList<Point> invisPotionSpawns = new ArrayList<>();

    private ArrayList<Point> mobSpawns = new ArrayList<>();
    private final ArrayList<Teleporter> teleporters = new ArrayList<>();

    private ActiveClick activeClick = ActiveClick.DEFAULT;
    private Object ItemSelectionController;

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
            case BOOTS:
                if (removeFromList(xBox, yBox, bootSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
                } else {
                    gc.drawImage(ResourcePack.getBoots(), xBox * 25, yBox * 25);
                    bootSpawns.add(new Point(xBox, yBox));
                }
                break;
            case ARMOR:
                if (removeFromList(xBox, yBox, armorSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
                } else {
                    gc.drawImage(ResourcePack.getArmor(), xBox * 25, yBox * 25);
                    armorSpawns.add(new Point(xBox, yBox));
                }
                break;
            case INVISPOTION:
                if (removeFromList(xBox, yBox, invisPotionSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
                } else {
                    gc.drawImage(ResourcePack.getInvisPotion(), xBox * 25, yBox * 25);
                    invisPotionSpawns.add(new Point(xBox, yBox));
                }
                break;
            case HEALTHPOTION:
                if (removeFromList(xBox, yBox, healthPotionSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * 25, yBox * 25);
                } else {
                    gc.drawImage(ResourcePack.getHealthPotion(), xBox * 25, yBox * 25);
                    healthPotionSpawns.add(new Point(xBox, yBox));
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
        bootSpawns = new ArrayList<>();
        armorSpawns = new ArrayList<>();
        healthPotionSpawns = new ArrayList<>();
        invisPotionSpawns = new ArrayList<>();
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

    public void placeArmor() {
        if (activeClick == ActiveClick.ARMOR) {
            activeClick = ActiveClick.DEFAULT;
            selectorImage.setImage(resourcePack.getBlockedPath());
        } else {
            activeClick = ActiveClick.ARMOR;
            selectorImage.setImage(ResourcePack.getArmor());
        }
    }

    public void placeBoots() {
        if (activeClick == ActiveClick.BOOTS) {
            activeClick = ActiveClick.DEFAULT;
            selectorImage.setImage(resourcePack.getBlockedPath());
        } else {
            activeClick = ActiveClick.BOOTS;
            selectorImage.setImage(ResourcePack.getBoots());
        }
    }

    public void placeHealthPotion() {
        if (activeClick == ActiveClick.HEALTHPOTION) {
            activeClick = ActiveClick.DEFAULT;
            selectorImage.setImage(resourcePack.getBlockedPath());
        } else {
            activeClick = ActiveClick.HEALTHPOTION;
            selectorImage.setImage(ResourcePack.getHealthPotion());
        }
    }

    public void placeInvisPotion() {
        if (activeClick == ActiveClick.INVISPOTION) {
            activeClick = ActiveClick.DEFAULT;
            selectorImage.setImage(resourcePack.getBlockedPath());
        } else {
            activeClick = ActiveClick.INVISPOTION;
            selectorImage.setImage(ResourcePack.getInvisPotion());
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

    public void previewGame() {
        if (playerSpawn == null || exitBlock == null) {
            JOP.msg("Every maze needs to have at least one player spawn and a exit block. ");
            return;
        }

        Maze currentMaze = new Maze(maze, resourcePack, mobSpawns, teleporters, exitBlock, playerSpawn);

        currentMaze.addSwords(swordSpawns);
        currentMaze.addBoots(bootSpawns);
        currentMaze.addArmor(armorSpawns);
        currentMaze.addHealthPotion(healthPotionSpawns);
        currentMaze.addInvisPotion(invisPotionSpawns);

        SceneLibrary.previewGame(currentMaze);
    }

    public void playDefaultMazeOne() {
        SceneLibrary.previewGame(SceneLibrary.getMazes().get(0));
    }

    public void playDefaultMazeTwo() {
        SceneLibrary.previewGame(SceneLibrary.getMazes().get(1));
    }

    public void playDefaultMazeThree() {
        SceneLibrary.previewGame(SceneLibrary.getMazes().get(2));
    }

    public void playDefaultMazeFour() {
        SceneLibrary.previewGame(SceneLibrary.getMazes().get(3));
    }

    @FXML
    public void About() {
        JOP.msg("You can build the maze by left clicking to put blocked area, right click to remove.\nEvery maze has to have a spawn and exit, so click buttons on the bottom to select each.\nSame applies to mobs and swords. The image on the bottom left shows selected item." +
                "\nTo play the maze, go to top left and click File, Play.\nTo clear maze, click Edit, Clear.");
    }

}