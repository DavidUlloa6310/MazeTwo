package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.Observable;

public class Controller {

    ObservableList<String> resourcePacks = FXCollections.observableArrayList("");

    @FXML
    private Canvas canvas;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button spawnButton;

    @FXML
    private Button mobsButton;

    @FXML
    private Button swordButton;

    @FXML
    private Button exitButton;

    @FXML
    private ImageView selectorImage;

    @FXML
    private ChoiceBox resourceChoiceBox;

    private GraphicsContext gc;

    @FXML
    private void initialize() {

        gc = canvas.getGraphicsContext2D();
        paintCanvas();
        selectorImage.setImage(ResourcePack.CAVE.getWalkablePath());
    }

    public void clickCanvas(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) onMouseLeftClick(e.getX(), e.getY()); else onMouseRightClick(e.getX(), e.getY());
    }

    public void onMouseDragged(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) onMouseLeftClick(e.getX(), e.getY()); else onMouseRightClick(e.getX(), e.getY());
    }

    public void onMouseLeftClick(double pixelX, double pixelY) {
        int xBox = (int) pixelX / 25;
        int yBox = (int) pixelY / 25;
        gc.drawImage(ResourcePack.CAVE.getBlockedPath(), xBox * 25, yBox * 25);
    }

    public void onMouseRightClick(double pixelX, double pixelY) {
        int xBox = (int) pixelX / 25;
        int yBox = (int) pixelY / 25;
        gc.drawImage(ResourcePack.CAVE.getWalkablePath(), xBox * 25, yBox * 25);
    }

    public void paintCanvas() {
        for (int r = 0; r < 24; r++) {
            for (int c = 0; c < 16; c++) {
                gc.drawImage(ResourcePack.CAVE.getWalkablePath(), r * 25, c * 25);
            }
        }
    }

    public void placeSpawn() {
        System.out.println("TEST");
    }

    public void placeExit() {

    }

    public void placeMobs() {

    }

    public void placeSwords() {

    }
}