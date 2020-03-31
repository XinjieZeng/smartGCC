package smartgcc.view.controller;

import javafx.stage.Stage;
import smartgcc.MainApp;

public class HelpPanelController {

    private MainApp mainApp;
    private Stage dialogueStage;
    private EditorPanelController controller;

    public void setDialogueStage(Stage dialogueStage){
        this.dialogueStage = dialogueStage;
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    public void setController(EditorPanelController controller){
        this.controller = controller;
    }
}
