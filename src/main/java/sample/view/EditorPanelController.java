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
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FilenameUtils;
import sample.MainApp;
import sample.model.CommandType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class EditorPanelController {
    private MainApp mainApp;
    private Stage dialogueStage;

    private String result = "";

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
    @FXML
    private Menu commandHistory;


    public void setDialogueStage(Stage dialogueStage) {
        this.dialogueStage = dialogueStage;
    }

    public EditorPanelController() {
    }

    @FXML
    private void initialize() {
        fileListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        List<String> extensions = Arrays.asList("*.cpp", "*.c", "*.h");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                " C files(*.c)", extensions);
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        String path = file.getAbsolutePath();


        fileListView.getItems().add(file.getName() + " " + path);

    }


    @FXML
    private void handleOpenDirectory() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("/Users/xinjiezeng/Desktop"));

        File selectedDirectory = directoryChooser.showDialog(mainApp.getPrimaryStage());

        File[] files = selectedDirectory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".c")
                        || name.toLowerCase().endsWith(".cpp");
            }
        });

        if (files != null) {
            for (File file : files) {
                fileListView.getItems().add(file.getName() + " " + file.getAbsolutePath());
            }
        }


    }

    private void executeCommand(String command, String file) {

        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream();

        executor.setStreamHandler(new PumpStreamHandler(byteArrayOutputStream, errorOutputStream));

        try {
            int value = executor.execute(commandLine);
            System.out.println(byteArrayOutputStream.toString());
            result += byteArrayOutputStream.toString() + "\n";
            if (value == 0) {
                result += "sucess! \n";
            }

            output.setText(result);
        } catch (Exception e) {
            result += "Error: \n" + errorOutputStream.toString();
            output.setText(result);
            System.out.println(errorOutputStream.toString());
        }
    }


    private void addCommandHistory(CommandType commandType){
        for(MenuItem menuItem: commandHistory.getItems()){
            if(menuItem.getText().equalsIgnoreCase("empty")){
                menuItem.setVisible(false);
            }
            if(menuItem.getText().equalsIgnoreCase(commandType.toString())){
                return;
            }
        }

        MenuItem menuItem = new MenuItem(commandType.toString());
        //menuItem.setOnAction();

        commandHistory.getItems().add(menuItem);


        menuItem.setOnAction(e -> compileCPlusFile());

        if(commandHistory.getItems().size() >= 5){
            commandHistory.getItems().get(0).setVisible(false);
        }
    }

    @FXML
    private void compileCPlusFile(){

        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();

        if(isFileNotSelect(selectedFiles)){
            return;
        }

        String command = CommandType.CPLUSCOMPILE.toString();
        executeFiles(selectedFiles, command, CommandType.CPLUS_COMPILE_COMMAND);
    }

    private boolean isFileNotSelect(List<String> selectedFiles){
        if(selectedFiles.isEmpty()){
            result += "please open or select files before choosing commands";
            output.setText(result);
            return true;
        }
        return false;
    }


    @FXML
    private void compileCFile(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();

        if(isFileNotSelect(selectedFiles)){
            return;
        }

        String command = CommandType.CCOMPILE.toString();
        executeFiles(selectedFiles, command, CommandType.C_COMPILE_COMMAND);
    }

    private void executeFiles(List<String> selectedFiles, String command, CommandType commandType){
        for(String file: selectedFiles) {
            String fileName = file.split(" ")[0];
            command = constructCommand(command, file, fileName);
            executeCommand(command, fileName);
            addCommandHistory(commandType);
        }
    }

    private String constructCommand(String command, String item, String fileName) {
        Path sourceFilePath = Paths.get(item.split(" ")[1]);
        Path destFilePath = Paths.get(sourceFilePath.getParent().toString() , FilenameUtils.removeExtension(fileName));

        command += sourceFilePath + " -o " + destFilePath;
        return command;
    }

    @FXML
    private void cPlusDebug(){

    }

    @FXML
    private void cDebug(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.CDEBUG.toString();
        executeFiles(selectedFiles, command, CommandType.C_DEBUG_COMMAND);
    }



    @FXML
    private void handleExit() {
        System.exit(0);
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
        result += "the user type has been changed to novice users\n";
        output.setText(result);
    }

    public void disableFunctionalitiesFromTypical(){
        codeDevelopment.setDisable(true);
        result += "the user type has been changed to typical users\n";
        output.setText(result);
    }

    public void disableFunctionalitiesFromExpert(){
        toolBox.setDisable(true);
        result += "the user type has been changed to expert users\n";
        output.setText(result);
    }
    public void showToolBoxDialogue(){
        mainApp.switchToolboxDialogue();
    }

}
