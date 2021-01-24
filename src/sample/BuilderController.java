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

    private final ObservableList<String> resourcePacks = FXCollections.observableArrayList("Grass", "Cave", "Nether", "End", "Snow", "Mushroom");
    private Maze[] customMazes = new Maze[3];

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
        aboutUI();
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
        int xBox = (int) pixelX / Main.getTileSize();
        int yBox = (int) pixelY / Main.getTileSize();

        if (xBox > Main.getWidth() - 1 || xBox < 0 || yBox < 0 || yBox > Main.getHeight() - 1)
            return;

        switch(activeClick) {
            case DEFAULT:
                maze[yBox][xBox] = true;
                gc.drawImage(resourcePack.getBlockedPath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                break;
            case SPAWN:
                if (playerSpawn == null) {
                    playerSpawn = new Point(xBox, yBox);
                    gc.drawImage(ResourcePack.getStartBlock(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else if (playerSpawn.getX() == xBox && playerSpawn.getY() == yBox) {
                    playerSpawn = null;
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else {
                    JOP.msg("There can be only one player spawn. \n Please unselect the existing spawn");
                }
                break;
            case ENDBLOCK:
                if (exitBlock == null) {
                    exitBlock = new Point(xBox, yBox);
                    maze[yBox][xBox] = false;
                    gc.drawImage(ResourcePack.getEndBlock(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else if (exitBlock.getX() == xBox && exitBlock.getY() == yBox) {
                    exitBlock = null;
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else {
                    JOP.msg("There can be only one exit block. \n Please unselect the existing exit block");
                }
                break;
            case MOBS:
                if (removeFromList(xBox, yBox, mobSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else {
                    gc.drawImage(resourcePack.getMobBlock(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                    mobSpawns.add(new Point(xBox, yBox));
                }
                break;
            case SWORDS:
                if (removeFromList(xBox, yBox, swordSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else {
                    gc.drawImage(ResourcePack.getSword(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                    swordSpawns.add(new Point(xBox, yBox));
                }
                break;
            case BOOTS:
                if (removeFromList(xBox, yBox, bootSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else {
                    gc.drawImage(ResourcePack.getBoots(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                    bootSpawns.add(new Point(xBox, yBox));
                }
                break;
            case ARMOR:
                if (removeFromList(xBox, yBox, armorSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else {
                    gc.drawImage(ResourcePack.getArmor(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                    armorSpawns.add(new Point(xBox, yBox));
                }
                break;
            case INVISPOTION:
                if (removeFromList(xBox, yBox, invisPotionSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else {
                    gc.drawImage(ResourcePack.getInvisPotion(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                    invisPotionSpawns.add(new Point(xBox, yBox));
                }
                break;
            case HEALTHPOTION:
                if (removeFromList(xBox, yBox, healthPotionSpawns)) {
                    gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                } else {
                    gc.drawImage(ResourcePack.getHealthPotion(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
                    healthPotionSpawns.add(new Point(xBox, yBox));
                }
                break;
        }
    }

    public void onMouseRightClick(double pixelX, double pixelY) {

        int xBox = (int) pixelX / Main.getTileSize();
        int yBox = (int) pixelY / Main.getTileSize();

        if (activeClick == ActiveClick.DEFAULT &&  xBox < Main.getWidth() && yBox < Main.getHeight() && xBox > -1 && yBox > -1) {
            maze[yBox][xBox] = false;
            gc.drawImage(resourcePack.getWalkablePath(), xBox * Main.getTileSize(), yBox * Main.getTileSize());
        }

    }

    public void resetCanvas() {

        canvas.setWidth(Main.getWidth() * Main.getTileSize());
        canvas.setHeight(Main.getHeight() * Main.getTileSize());

        for (int r = 0; r < Main.getHeight(); r++) {
            for (int c = 0; c < Main.getWidth(); c++) {
                gc.drawImage(resourcePack.getWalkablePath(), c * Main.getTileSize(), r * Main.getTileSize());
            }
        }
    }

    public void repaintCanvas() {

        for (int r = 0; r < Main.getHeight(); r++) {
            for (int c = 0; c < Main.getWidth(); c++) {
                if (maze[r][c]) gc.drawImage(resourcePack.getBlockedPath(), c * Main.getTileSize(), r * Main.getTileSize()); else gc.drawImage(resourcePack.getWalkablePath(), c * Main.getTileSize(), r * Main.getTileSize());
            }
        }

        if (playerSpawn != null) gc.drawImage(ResourcePack.getStartBlock(), playerSpawn.getX() * Main.getTileSize(), playerSpawn.getY() * Main.getTileSize());
        if (exitBlock != null) gc.drawImage(ResourcePack.getEndBlock(), exitBlock.getX() * Main.getTileSize(), exitBlock.getY() * Main.getTileSize());

        for (Point sword : swordSpawns) {
            gc.drawImage(ResourcePack.getSword(), sword.getX() * Main.getTileSize(), sword.getY() * Main.getTileSize());
        }

        for (Point mob : mobSpawns) {
            gc.drawImage(resourcePack.getMobBlock(), mob.getX() * Main.getTileSize(), mob.getY() * Main.getTileSize());
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
        } else if (resourceChoiceBox.getValue().equals("Snow")) {
            resourcePack = ResourcePack.SNOW;
        } else if (resourceChoiceBox.getValue().equals("Mushroom")) {
            resourcePack = ResourcePack.MUSHROOM;
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

    public void changeDimensions() {
        String input = "LMAOO";
        while (input.toLowerCase().charAt(0) != 'y') {
            input = JOP.input("Changing the dimensions will delete current maze.\nPlease type 'y' if you accept, 'n' if you don't");
            if (input.toLowerCase().charAt(0) == 'n')
                return;
        }

        Main.setWidth();
        Main.setHeight();

        clearMaze();

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
        boolean[][] array = new boolean[Main.getHeight()][Main.getWidth()];
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[0].length; c++) {
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

    // USE CURRENT MAZE
    public Maze getCurrentMaze() {
        Maze currentMaze = new Maze(maze, resourcePack, mobSpawns, teleporters, exitBlock, playerSpawn);

        currentMaze.addSwords(swordSpawns);
        currentMaze.addBoots(bootSpawns);
        currentMaze.addArmor(armorSpawns);
        currentMaze.addHealthPotion(healthPotionSpawns);
        currentMaze.addInvisPotion(invisPotionSpawns);

        return currentMaze;
    }
    public void previewGame() {
        if (playerSpawn == null || exitBlock == null) {
            JOP.msg("Every maze needs to have at least one player spawn and a exit block. ");
            return;
        }
        SceneLibrary.previewGame(getCurrentMaze());
    }
    public void save() {
        int save = 4;
        while (save < 1 || save > 3) {
            try {
                save = Integer.parseInt(JOP.input("Please enter under which save you want to have the maze (1, 2, or 3)."));
            } catch(Exception NumberFormatException) {
                save = 4;
            }
        }

        int index = save - 1;
        if (playerSpawn == null || exitBlock == null) {
            JOP.msg("Every maze needs to have at least one player spawn and a exit block. ");
            return;
        }
        customMazes[index] = getCurrentMaze();

    }

    // PLAY SAVED MAZES
    public void playCustomMazeOne() {
        if (customMazes[0] != null) {
            SceneLibrary.previewGame(customMazes[0]);
        }
    }
    public void playCustomMazeTwo() {
        if (customMazes[1] != null) {
            SceneLibrary.previewGame(customMazes[1]);
        }
    }
    public void playCustomMazeThree() {
        if (customMazes[2] != null) {
            SceneLibrary.previewGame(customMazes[2]);
        }
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

    // ABOUT GUIDES
    public void aboutUI() {
        JOP.msg("You can build the maze by left clicking to put blocked area, right click to remove.\nEvery maze has to have a spawn and exit, so click buttons on the bottom to select each.\nSame applies to mobs and items. The image on the bottom left shows selected item." +
                "\nYou can remove items and mobs by left clicking already placed mobs.\nTo play the maze, go to top left and click File, Play.\nTo clear maze, click Edit, Clear.\nTo change the dimensions, go to Edit, Change Dimensions.\nTo save maze, click File, Save, and enter what save you want to have.\nTo play saved mazes, click Saves, and select which maze. There are also premade mazes that can be played." +
                "\nTo select the resource pack, select from drop down menu and click the update button.");
    }

    public void aboutItems() {
        JOP.msg("Sword: If you run into the mob, and have a sword, the mob dies.\nArmor: Regens health and increases max health to 4 hearts.\nBoots: Slows down mob speed.\nHealth Potion: Heals player.\nInvis Potion: Makes mobs stop chasing player.");
    }

}