package smartgcc.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import smartgcc.MainApp;
import smartgcc.model.Layout;
import smartgcc.model.UserType;
import smartgcc.utils.UIUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class UserSwitchDialogueController {
    private Stage dialogStage;
    private UserType userType;

    private EditorPanelController editorPanelController;

    @FXML
    private ImageView userTypeTip;
    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private void initialize(){
        String tips = "Novice: new to the GCC compiler \n" +
                "Typical: limited knowledge of gcc compiler\n" +
                "Expert: advanced knowledge of gcc compiler";

        Tooltip.install(userTypeTip, UIUtils.withDelay(new Tooltip(tips),100));

    }

    private void rememberUserChoice(){
        saveUserTypeToFile();
    }

    private void saveUserTypeToFile(){
        File file = new File("userChoice.txt");

        try {
            FileUtils.write(file,userType.toString(), StandardCharsets.UTF_8);
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

        EditorPanelController controller = Optional.ofNullable(editorPanelController).orElseGet(() -> {
            FXMLLoader loader = UIUtils.initLoader(Layout.EDITOR_PANEL);
            try {
                AnchorPane panel = loader.load();
                Scene scene = new Scene(panel);
                dialogStage.setScene(scene);

                return loader.getController();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        });

        if(toggleGroupValue.equalsIgnoreCase(UserType.NOVICE.toString())){
            userType = UserType.NOVICE;
            controller.changeFunctionality(UserType.NOVICE);
        }
        else if(toggleGroupValue.equalsIgnoreCase(UserType.TYPICAL.toString())){
            userType = UserType.TYPICAL;
            controller.changeFunctionality(UserType.TYPICAL);
        }
        else{
            userType = UserType.EXPERT;
            controller.changeFunctionality(UserType.EXPERT);
        }
        rememberUserChoice();
    }

    public void setEditorPanelController(EditorPanelController editorPanelController) {
        this.editorPanelController = editorPanelController;
    }
}
