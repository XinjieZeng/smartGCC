package smartgcc.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import smartgcc.MainApp;
import smartgcc.model.Layout;
import smartgcc.view.controller.EditorPanelController;
import smartgcc.view.controller.HelpPanelController;
import smartgcc.view.controller.ToolBoxPanelController;
import smartgcc.view.controller.UserSwitchDialogueController;

import java.io.IOException;
import java.lang.reflect.Field;

public class UIUtils {
    public static Tooltip withDelay(Tooltip tooltip, int delayInMills) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(delayInMills)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tooltip;
    }

    public static void switchUserDialog(Stage owner, Layout layout, EditorPanelController editorPanelController) {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = initLoader(layout);
        AnchorPane page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("User Type");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        UserSwitchDialogueController controller = loader.getController();
        controller.setEditorPanelController(editorPanelController);
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();

    }

    public static void switchToolBoxDialog(Stage owner, Layout layout, EditorPanelController editorPanelController){
        FXMLLoader loader = initLoader(layout);
        AnchorPane page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle("ToolBox");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        ToolBoxPanelController controller = loader.getController();
        controller.setController(editorPanelController);
        controller.setDialogueStage(dialogStage);
        dialogStage.showAndWait();
    }

    public static void switchHelpDialog(Stage owner, Layout layout, EditorPanelController editorPanelController){
        FXMLLoader loader = initLoader(layout);
        AnchorPane page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Help");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        HelpPanelController controller = loader.getController();
        controller.setController(editorPanelController);
        controller.setDialogueStage(dialogStage);
        dialogStage.showAndWait();
    }

    public static FXMLLoader initLoader(Layout layout) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getClassLoader().getResource(layout.getPath()));
        return loader;
    }

}
