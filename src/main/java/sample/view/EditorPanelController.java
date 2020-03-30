package sample.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import sample.MainApp;
import sample.model.CommandType;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class EditorPanelController {
    private MainApp mainApp;
    private String path = "";
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
    private AnchorPane outputWindow;
    @FXML
    private Text output;
    @FXML
    private Menu commandHistory;
    @FXML
    private TextArea editorArea;
    @FXML
    private Label fileName;



    public EditorPanelController() {
    }

    @FXML
    private void initialize() {

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                ae -> autoSave()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void autoSave(){

        ObservableList<CharSequence> paragraph = editorArea.getParagraphs();
        Iterator<CharSequence> iter = paragraph.iterator();

        try {

            File file;
            if(path.isEmpty()){
                file = new File("main.c");
            }
            else{
                file = new File(path);
            }

            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            while(iter.hasNext()) {
                CharSequence seq = iter.next();
                bf.append(seq);
                bf.newLine();
            }
            bf.flush();
            bf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveFile(){
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "c files (*.c)", "*.c");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension

            Path newFilePath = Paths.get(file.getAbsolutePath());

            try{
                Files.createFile(newFilePath);
                FileUtils.writeStringToFile(file, editorArea.getText(), Charset.defaultCharset());
                result += "Successfully save " + file.getName() + " to " + newFilePath.toAbsolutePath().toString();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * Opens a FileChooser to let the user select a cFile to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        List<String> extensions = Collections.singletonList("*.c");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                " C files(*.c)", extensions);
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        path = file.getAbsolutePath();
        fileName.setText(file.getName());
        try{
            String s = FileUtils.readFileToString(file, Charset.defaultCharset());
            editorArea.appendText(s);
        }catch (Exception e){
            e.printStackTrace();
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

        addAction(commandType);
    }

    private void addAction(CommandType commandType){
        MenuItem menuItem = new MenuItem(commandType.toString());
        commandHistory.getItems().add(menuItem);

        switch (commandType){
            case COMPILE_EXECUTABLE_COMMAND:
                menuItem.setOnAction(e -> compileToExecutableFile());
                break;
            case COMPILE_OBJECT_COMMAND:
                menuItem.setOnAction(e -> compileToObjectFile());
                break;
            case LINKING_COMMAND:
                menuItem.setOnAction(e -> linkCFile());
                break;
            case DEBUG_G_COMMAND:
                menuItem.setOnAction(e -> debugInNativeInfo());
                break;
            case DEBUG_GDBB_COMMAND:
                menuItem.setOnAction(e -> debugInDwarfInfo());
                break;
            case CODE_GEN_FTRAPV:
                menuItem.setOnAction(e -> genCodeFtrapv());
                break;
            case CODE_GEN_FWRAPV_COMMAND:
                menuItem.setOnAction(e -> genCodeFwrapv());
                break;
            case OPTIMIZE_LEVEL1_COMMAND:
                menuItem.setOnAction(e -> optimizeLevelOne());
                break;
            case OPTIMIZE_LEVEL2_COMMAND:
                menuItem.setOnAction(e -> optimizeLevelTWo());
                break;
            case OPTIMIZE_LEVEL3_COMMAND:
                menuItem.setOnAction(e -> optimizeLevelThree());
                break;
            case DEVELOPER_OPTIMIZATION_COMMAND:
                menuItem.setOnAction(e -> generateSrcfice());
                break;
            case RUN_COMMAND:
                menuItem.setOnAction(e -> run());
                break;
            case STOP_COMMAND:
                menuItem.setOnAction(e -> stop());
                break;
        }
    }

    /**
     * gcc compile option1
     * gcc hello.c -o hello
     * one step compilation
     * compile c file to executable file
     */
    @FXML
     void compileToExecutableFile(){
        String command = CommandType.COMPILE_TO_EXECUTABLE.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.COMPILE_EXECUTABLE_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.COMPILE_EXECUTABLE_COMMAND);
    }

    /**
     * gcc compile option2
     * compile c file to object file
     * gcc -E hello.c -o hello.o
     */
    @FXML
    void compileToObjectFile(){

        String command = CommandType.COMPILE_TO_OBJECT_FILE.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.COMPILE_OBJECT_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.COMPILE_OBJECT_COMMAND);
    }

    /**
     * execute the command from smartGCC interface on gcc compiler and return the output back to smartGCC
     * @param command
     * @param file
     */
    private void executeCommand(String command, String file, CommandType commandType) {

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
                result += file + ": successfully implements " + commandType.toString() + "\n";
            }

            output.setText(result);
        } catch (Exception e) {
            result += "Error: \n" + errorOutputStream.toString();
            output.setText(result);
            System.out.println(errorOutputStream.toString());
        }
    }

    private void executeFile(String path, String command, CommandType commandType){

        if(commandType == CommandType.COMPILE_OBJECT_COMMAND){
            String fileName = Paths.get(path).getFileName().toString();
            command = constructCommand(command, fileName);
            executeCommand(command, fileName, commandType);
            addCommandHistory(CommandType.COMPILE_OBJECT_COMMAND);
            return;
        }

        String fileName = Paths.get(path).getFileName().toString();
        command = constructCommand(command, fileName);
        executeCommand(command, fileName, commandType);
        addCommandHistory(commandType);

    }

    private String constructCommand(String command, String fileName){

        Path sourceFilePath;
        Path destFilePath;

        if(path.isEmpty()){
            sourceFilePath = Paths.get(fileName);
            destFilePath = Paths.get(FilenameUtils.removeExtension(fileName));
        }else{
            sourceFilePath = Paths.get(path);
            destFilePath = Paths.get(sourceFilePath.getParent().toString(), FilenameUtils.removeExtension(fileName));

        }

        command += sourceFilePath + " -o " + destFilePath;

        return command;
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
    void linkCFile(){
        String command = CommandType.LINK_C.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.LINKING_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.LINKING_COMMAND);
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
    void debugInNativeInfo(){

        String command = CommandType.DEBUG_G.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.DEBUG_G_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.DEBUG_G_COMMAND);
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
    void debugInDwarfInfo(){
        String command = CommandType.DEBUG_GGDB.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.DEBUG_GDBB_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.DEBUG_GDBB_COMMAND);
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
    void optimizeLevelTWo(){
        String command = CommandType.OPTIMIZE_C_LEVEL2.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.OPTIMIZE_LEVEL2_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.OPTIMIZE_LEVEL2_COMMAND);
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
    void optimizeLevelOne(){
        String command = CommandType.OPTIMIZE_C_LEVEL1.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.OPTIMIZE_LEVEL1_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.OPTIMIZE_LEVEL1_COMMAND);
    }


    /**
     * gcc optimization option 3
     * gcc optimization command (level 3)
     * gcc -Wall -O3 -c hello.c
     * Optimize yet more. -O3 turns on all optimizations specified by -O2
     * and generate the object file
     */
    @FXML
    void optimizeLevelThree(){
        String command = CommandType.OPTIMIZE_C_LEVEL3.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.OPTIMIZE_LEVEL3_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.OPTIMIZE_LEVEL3_COMMAND);
    }

    /**
     * code Generation option 1
     * This option generates traps for signed overflow on addition, subtraction, multiplication operations.
     * The options -ftrapv and -fwrapv override each other, so using -ftrapv -fwrapv on the command-line results in -fwrapv being effective.
     * Note that only active options override, so using -ftrapv -fwrapv -fno-wrapv on the command-line results in -ftrapv being effective.
     */
    @FXML
    void genCodeFtrapv(){

        String command = CommandType.CODE_GEN_FTRAPV.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.CODE_GEN_FTRAPV_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.CODE_GEN_FTRAPV_COMMAND);
    }

    /**
     * code generation option 2
     * This option generates traps for signed overflow on addition, subtraction, multiplication operations.
     * The options -ftrapv and -fwrapv override each other, so using -ftrapv -fwrapv on the command-line results in -fwrapv being effective.
     * Note that only active options override, so using -ftrapv -fwrapv -fno-wrapv on the command-line results in -ftrapv being effective.
     */
    @FXML
    void genCodeFwrapv(){
        String command = CommandType.CODE_GEN_FWRAPV.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.CODE_GEN_FTRAPV_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.CODE_GEN_FTRAPV_COMMAND);
    }


    /**
     * gcc developer option1
     * Write a SRCFILE.opt-record.json.gz file detailing what optimizations were performed,
     * for those optimizations that support -fopt-info.
     *
     * gcc  -fsave-optimization-record hello.c
     */
    @FXML
    void generateSrcfice(){
        String command = CommandType.DEVELOPER_OPTIMIZATION.toString();

        if(path.isEmpty()){
            executeFile("main.c", command, CommandType.DEVELOPER_OPTIMIZATION_COMMAND);
            return;
        }

        executeFile(path, command,CommandType.DEVELOPER_OPTIMIZATION_COMMAND);
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
        compileToExecutableFile();

        if(path.isEmpty()){
            String fileName = "main";
            String command = CommandType.RUN + fileName;
            executeCommand(command, fileName, CommandType.RUN_COMMAND);
            return;
        }

        String fileName = Paths.get(path).getFileName().toString();
        Path filePath = Paths.get(path);
        String command = Paths.get(filePath.getParent().toString(), FilenameUtils.removeExtension(fileName)).toString();
        executeCommand(command, fileName, CommandType.RUN_COMMAND);
    }

    @FXML
    private void stop(){}

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

    public void showHelpDialogue(){
        mainApp.switchHelpBoxDialogue();
    }

}
