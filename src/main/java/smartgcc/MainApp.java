package smartgcc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import smartgcc.model.Layout;
import smartgcc.model.UserType;
import smartgcc.utils.UIUtils;
import smartgcc.view.controller.EditorPanelController;
import smartgcc.view.controller.UserSwitchDialogueController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static smartgcc.model.UserType.*;

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private EditorPanelController editorPanelController;


    public MainApp() {
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SmartGCC");

        UserType userType = readUserSelection();
        if (userType != UNKNOWN) {
            initRootLayout(userType);
            return;
        }
        switchUserDialogue();
    }

    private UserType readUserSelection() {
        File file = new File("userChoice.txt");

        try {
            String res = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            if (res.isEmpty()) {
                return UNKNOWN;
            }
            if (res.equalsIgnoreCase(NOVICE.toString())) {
                return NOVICE;
            } else if (res.equalsIgnoreCase(TYPICAL.toString())) {
                return TYPICAL;
            } else if (res.equalsIgnoreCase(EXPERT.toString())) {
                return EXPERT;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return UNKNOWN;
    }

    public void initRootLayout(UserType userType) {
        try {

            FXMLLoader loader = UIUtils.initLoader(Layout.EDITOR_PANEL);
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            editorPanelController = loader.getController();
            editorPanelController.setStage(primaryStage);
            editorPanelController.changeFunctionality(userType);
            editorPanelController.setMainApp(this);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void switchUserDialogue() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("layout/UserSwitchDialogue.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("User Type");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            UserSwitchDialogueController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
