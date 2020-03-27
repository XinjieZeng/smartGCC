package sample.model;

import javafx.fxml.FXML;
import sample.MainApp;
import sample.model.UserType;

public class RootLayoutController {
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }


    @FXML
    public void switchUserType(){

        mainApp.switchUserDialogue();

    }




}
