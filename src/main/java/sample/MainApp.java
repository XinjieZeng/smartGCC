package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.model.UserType;
import sample.view.EditorPanelController;
import sample.view.IdleMonitor;
import sample.view.ToolBoxPanelController;
import sample.view.UserSwitchDialogueController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private EditorPanelController editorPanelController;

    public MainApp(){}

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SmartGCC");
        switchUserDialogue();
    }

    public void initRootLayout(UserType userType){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("EditorPanel.fxml"));
//            loader.setLocation(getClass(),"/sample.view/EditorPanel.fxml");
//                    .getResource("/sample.view/EditorPanel.fxml"));
            rootLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);





            // Give the controller access to the main app.
            editorPanelController = loader.getController();
            editorPanelController.setMainApp(this);

            if(userType == UserType.NOVICE){
                editorPanelController.disableFunctionalitiesFromNovice();
            }
            else if(userType == UserType.TYPICAL){
                editorPanelController.disableFunctionalitiesFromTypical();
            }
            else{
                editorPanelController.disableFunctionalitiesFromExpert();
            }

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EditorPanelController getEditorPanelController(){
        return editorPanelController;
    }
    public void switchUserDialogue(){
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("UserSwitchDialogue.fxml"));
//            loader.load(getClass().getClassLoader().getResource("EditorPanel.fxml"));
//             loader.load(MainApp.class.getResource("EditorPanel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("User Type");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UserSwitchDialogueController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Set the dialog icon.

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    public void switchToolboxDialogue() {

        try{
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("ToolBoxPanel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("ToolBox");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ToolBoxPanelController controller = loader.getController();
            controller.setDialogueStage(dialogStage);
            controller.setMainApp(this);

            // Set the dialog icon.

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public Stage getPrimaryStage(){
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
