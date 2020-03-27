package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import sample.MainApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EditorPanelController {
    private MainApp mainApp;
    private Stage dialogueStage;

    @FXML
    private MenuItem codeGeneration;
    @FXML
    private MenuItem codeOptimization;
    @FXML
    private MenuItem codeDevelopment;
    @FXML
    private MenuItem toolBox;
    @FXML
    private ListView fileListView;
    @FXML
    private AnchorPane outputWindow;
    @FXML
    private Text output;


    public void setDialogueStage(Stage dialogueStage){
        this.dialogueStage = dialogueStage;
    }

    public EditorPanelController(){
    }

    @FXML
    private void initialize(){
        fileListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        List<String> extentions = new ArrayList();
        extentions.add("*.cpp");
        extentions.add("*.c");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                " C files(*.c)", extentions);
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        String path = file.getAbsolutePath();

        if (file != null) {
            fileListView.getItems().add(file.getName() + " " + path);
        }
    }


    @FXML
    private void handleOpenDirectory(){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("/Users/xinjiezeng/Desktop"));

        File selectedDirectory = directoryChooser.showDialog(mainApp.getPrimaryStage());

        File[] files = selectedDirectory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".c")
                        || name.toLowerCase().endsWith(".cpp");
            }
        });

        if(files != null){
            for(File file: files){
                fileListView.getItems().add(file.getName() + " " + file.getAbsolutePath());
            }
        }


    }

    @FXML
    private void compileCPlusFile(){
        List<String> selectedItems = fileListView.getSelectionModel().getSelectedItems();
        String command = "g++ -Wall -Werror -pedantic -std=c++1y -o ";

        String result = "";
        int value = 0;

            for(String item: selectedItems){
                String file = item.split(" ")[0];
                String path = item.split(" ")[1];


                String targetFileName = file.substring(0, file.length() - 4);
                command = command + targetFileName + " " + file  + " "  + path;


                CommandLine commandLine = CommandLine.parse(command);
                DefaultExecutor executor = new DefaultExecutor();
                executor.setExitValue(1);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream();

                executor.setStreamHandler(new PumpStreamHandler(byteArrayOutputStream, errorOutputStream));

                try{
                    value = executor.execute(commandLine);
                    System.out.println(value);
                }catch (Exception e){
                    e.printStackTrace();
                }

                System.out.println(byteArrayOutputStream.toString());
                result += byteArrayOutputStream.toString() + "\n";
                byteArrayOutputStream.reset();


                if(value == 1){
                    result += "sucessfully compiled " + file + " to " + targetFileName + ".o";
                }

            }

            output.setText(result);
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    public void selectUserType(){
        mainApp.switchUserDialogue();
    }

    public void disableFunctionalitiesFromNovice(){
        codeGeneration.setDisable(true);
        codeDevelopment.setDisable(true);
        codeOptimization.setDisable(true);
    }

    public void disableFunctionalitiesFromTypical(){
        codeDevelopment.setDisable(true);
    }

    public void disableFunctionalitiesFromExpert(){
        toolBox.setDisable(true);
    }
    public void showToolBoxDialogue(){
        mainApp.switchToolboxDialogue();
    }
}
