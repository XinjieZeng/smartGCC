package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import sample.MainApp;
import sample.model.UserType;
import sample.utils.UIUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
    @FXML
    private Text rememberUserText;


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize(){
        String tips = "Novice: new to the GCC compiler \n" +
                "Typical: limited knowledge of gcc compiler\n" +
                "Expert: advanced knowledge of gcc compiler";

        Tooltip.install(userTypeTip, UIUtils.withDelay(new Tooltip(tips),100));

    }

    private void rememberUserChoice(){
        boolean isRememberUserClicked = rememberUserType.isSelected();

        if(isRememberUserClicked){
            rememberUserType.setVisible(false);
            rememberUserText.setVisible(false);
        }

        saveUserTypeToFile(isRememberUserClicked);
    }

    private void saveUserTypeToFile(boolean isRememberUserClicked){
        File file = new File("userChoice.txt");

        String res = Boolean.toString(isRememberUserClicked) + " " + userType.toString();

        try {
            FileUtils.write(file,res, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


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
            userType = UserType.NOVICE;
            mainApp.initRootLayout(UserType.NOVICE);
        }
        else if(toggleGroupValue.equalsIgnoreCase(UserType.TYPICAL.toString())){
            mainApp.initRootLayout(UserType.TYPICAL);
            userType = UserType.TYPICAL;
        }
        else{
            mainApp.initRootLayout(UserType.EXPERT);
            userType = UserType.EXPERT;
        }

        rememberUserChoice();
    }

    public void disableRememberSetting(){
        rememberUserText.setVisible(false);
        rememberUserType.setVisible(false);

    }



    public boolean isOKClicked(){
        return okClicked;
    }


}
