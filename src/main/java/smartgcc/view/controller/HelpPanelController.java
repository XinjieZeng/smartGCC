package smartgcc.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import smartgcc.MainApp;

public class HelpPanelController implements Initializable {

    private MainApp mainApp;
    private Stage dialogueStage;
    private EditorPanelController controller;

    @FXML
    private WebView viewweb;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	WebEngine webengine = viewweb.getEngine();
    	
    	webengine.load(getClass().getResource("/help/index.html").toString());
    }
    
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
