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
     * Opens a FileChooser to let the user select a cFile to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        List<String> extensions = Arrays.asList("*.c", "*.h");
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

    /**
     * execute the command from smartGCC interface on gcc compiler and return the output back to smartGCC
     * @param command
     * @param file
     */
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


    /**
     * add command history
     * @param commandType
     */
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


        menuItem.setOnAction(e -> compileToExecutableFile());

        if(commandHistory.getItems().size() >= 5){
            commandHistory.getItems().get(0).setVisible(false);
        }
    }

    private boolean isFileNotSelect(List<String> selectedFiles){
        if(selectedFiles.isEmpty()){
            result += "please open or select files before choosing commands";
            output.setText(result);
            return true;
        }
        return false;
    }

    /**
     * gcc compile option1
     * gcc hello.c -o hello
     * one step compilation
     * compile c file to executable file
     */
    @FXML
    private void compileToExecutableFile(){

        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();

        if(isFileNotSelect(selectedFiles)){
            return;
        }

        String command = CommandType.COMPILE_TO_EXECUTABLE.toString();
        executeFiles(selectedFiles, command, CommandType.COMPILE_EXECUTABLE_COMMAND);
    }


    /**
     * gcc compile option2
     * compile c file to object file
     * gcc -E hello.c -o hello.o
     */
    @FXML
    private void compileToObjectFile(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();

        if(isFileNotSelect(selectedFiles)){
            return;
        }


        String command = CommandType.COMPILE_TO_OBJECT_FILE.toString();
        executeFiles(selectedFiles, command, CommandType.COMPILE_OBJECT_COMMAND);
    }

    /**
     * gcc link option
     * gcc -shared hello.o -o hello.so
     * Produce a shared object which can then be linked with other objects to form an executable.
     * Not all systems support this option. For predictable results,
     * you must also specify the same set of options used for compilation
     * (-fpic, -fPIC, or model suboptions) when you specify this linker option.1
     *
     */
    @FXML
    private void linkCFile(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.LINK_C.toString();
        executeFiles(selectedFiles, command, CommandType.LINK_C);
    }

    /**
     * gcc debugging option1
     * gcc debugging option gcc -g
     * -g
     * Produce debugging information in the operating system’s native format
     * (stabs, COFF, XCOFF, or DWARF). GDB can work with this debugging information.
     *
     * On most systems that use stabs format, -g enables use of extra debugging information
     * that only GDB can use; this extra information makes debugging work better in GDB
     * but probably makes other debuggers crash or refuse to read the program.
     *
     * gcc -g hello.c -o hello
     */
    @FXML
    private void produceNativeDubuggingInfo(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.DEBUG_G.toString();
        executeFiles(selectedFiles, command, CommandType.DEBUG_G_COMMAND);
    }

    /**
     * gcc debugging option 2
     * gcc debugging option gcc -ggdb
     * Produce debugging information for use by GDB. This means to use the most expressive format available
     * (DWARF, stabs, or the native format if neither of those are supported),
     * including GDB extensions if at all possible.
     *
     * gcc -ggdb hello.c -o hello
     */
    @FXML
    private void produceDwarfDebuffingInfo(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.DEBUG_GGDB.toString();
        executeFiles(selectedFiles, command, CommandType.DEBUG_GGDB);
    }

    /**
     * gcc optimization option 1
     * gcc optimization command (level 2)
     * gcc -Wall -O2 -c hello.c
     * Optimize even more. GCC performs nearly all supported optimizations that do not involve a space-speed tradeoff.
     * As compared to -O, this option increases both compilation time and the performance of the generated code
     * and generate object file.
     */
    @FXML
    private void optimizeLevelTWo(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.OPTIMIZE_C_LEVEL2.toString();

        executeFiles(selectedFiles, command, CommandType.OPTIMIZE_LEVEL2_COMMAND);
    }

    /**
     * gcc optimization option 2
     * gcc optimization command (level 1)
     * gcc -Wall -O1 -c hello.c
     *
     * With -O, the compiler tries to reduce code size and execution time,
     * without performing any optimizations that take a great deal of compilation time
     * and generate object file.
     */
    @FXML
    private void optimizeLevelOne(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.OPTIMIZE_C_LEVEL1.toString();

        executeFiles(selectedFiles, command, CommandType.OPTIMIZE_LEVEL1_COMMAND);
    }


    /**
     * gcc optimization option 3
     * gcc optimization command (level 3)
     * gcc -Wall -O3 -c hello.c
     * Optimize yet more. -O3 turns on all optimizations specified by -O2
     * and generate the object file
     */
    @FXML
    private void optimizeLevelThree(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.OPTIMIZE_C_LEVEL3.toString();

        executeFiles(selectedFiles, command, CommandType.OPTIMIZE_LEVEL3_COMMAND);
    }

    /**
     * code Generation option 1
     * This option generates traps for signed overflow on addition, subtraction, multiplication operations.
     * The options -ftrapv and -fwrapv override each other, so using -ftrapv -fwrapv on the command-line results in -fwrapv being effective.
     * Note that only active options override, so using -ftrapv -fwrapv -fno-wrapv on the command-line results in -ftrapv being effective.
     */
    @FXML
    private void codeGenerateOption1(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.CODE_GEN1_COMMAND.toString();

        executeFiles(selectedFiles, command, CommandType.CODE_GEN1_COMMAND);
    }

    /**
     * code generation option 2
     * This option generates traps for signed overflow on addition, subtraction, multiplication operations.
     * The options -ftrapv and -fwrapv override each other, so using -ftrapv -fwrapv on the command-line results in -fwrapv being effective.
     * Note that only active options override, so using -ftrapv -fwrapv -fno-wrapv on the command-line results in -ftrapv being effective.
     */
    @FXML
    private void codeGenerateOption2(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.CODE_GEN2_COMMAND.toString();

        executeFiles(selectedFiles, command, CommandType.CODE_GEN2_COMMAND);
    }


    /**
     * gcc developer option1
     * Write a SRCFILE.opt-record.json.gz file detailing what optimizations were performed,
     * for those optimizations that support -fopt-info.
     *
     * gcc  -fsave-optimization-record hello.c
     */
    @FXML
    private void generateSrcfice(){
        List<String> selectedFiles = fileListView.getSelectionModel().getSelectedItems();
        String command = CommandType.DEVELOPER_OPTIMIZATION.toString();
        executeFiles(selectedFiles, command, CommandType.DEVELOPER_OPTIMIZATION_COMMAND);
    }



    private void executeFiles(List<String> selectedFiles, String command, CommandType commandType){

        if(commandType == CommandType.COMPILE_OBJECT_COMMAND){
            for(String file: selectedFiles) {
                String fileName = file.split(" ")[0];
                command = constructCommand(command, file, fileName) + ".o";
                executeCommand(command, fileName);
                addCommandHistory(commandType);
            }
        }

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


    /**
     * exit the program
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }




    @FXML
    private void run(){

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
