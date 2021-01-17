package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ListController {

    @FXML
    private ListView mazeList;

    @FXML
    private void initialize() {
        mazeList.setItems(SceneLibrary.getMazes());
    }

}
