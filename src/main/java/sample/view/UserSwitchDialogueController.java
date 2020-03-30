package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.MainApp;
import sample.model.UserType;
import sample.utils.UIUtils;

public class UserSwitchDialogueController {
    private Stage dialogStage;
    private UserType userType;
    private boolean okClicked;
    private MainApp mainApp;

    @FXML
    private ImageView userTypeTip;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private CheckBox rememberUserType;


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize(){
        Tooltip.install(userTypeTip, UIUtils.withDelay(new Tooltip("test"),100));

    }

//    @FXML
//    private void rememberUserChoice(){
//        boolean isRemeberUserChoice = rememberUserType.isSelected();
//
//        if(isRemeberUserChoice){
//            mainApp.rememberUserChoice(userType);
//        }
//    }

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
