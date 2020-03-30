package sample.view;

import javafx.stage.Stage;
import sample.MainApp;

public class HelpPanelController {

    private MainApp mainApp;
    private Stage dialogueStage;

    public void setDialogueStage(Stage dialogueStage){
        this.dialogueStage = dialogueStage;
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
}
