package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.MainApp;
import sample.model.UserType;

public class UserSwitchDialogueController {
    private Stage dialogStage;
    private UserType userType;
    private boolean okClicked;
    private MainApp mainApp;

    @FXML
    ToggleGroup toggleGroup;



    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize(){}

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleCancel(){

        dialogStage.close();
    }

    @FXML
    public void handleOK(){
        dialogStage.close();

        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();

        if(toggleGroupValue.equalsIgnoreCase(UserType.NOVICE.toString())){
            mainApp.initRootLayout(UserType.NOVICE);
        }
        else if(toggleGroupValue.equalsIgnoreCase(UserType.TYPICAL.toString())){
            mainApp.initRootLayout(UserType.TYPICAL);
        }
        else{
            mainApp.initRootLayout(UserType.EXPERT);
        }

    }



    public boolean isOKClicked(){
        return okClicked;
    }


}
