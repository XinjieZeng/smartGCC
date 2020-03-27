package sample.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.MainApp;

public class ToolBoxPanelController {
    private MainApp mainApp;
    private Stage dialogueStage;


    public void setDialogueStage(Stage dialogueStage){
        this.dialogueStage = dialogueStage;
    }

    public ToolBoxPanelController(){}

    @FXML
    private void initialize(){}

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
}
