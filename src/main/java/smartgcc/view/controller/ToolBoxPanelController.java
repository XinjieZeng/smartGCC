package smartgcc.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import smartgcc.model.CommandType;

public class ToolBoxPanelController {
    private Stage dialogueStage;
    private EditorPanelController controller;

    @FXML
    ToggleGroup compileToggleGroup;
    @FXML
    ToggleGroup linkToggleGroup;
    @FXML
    ToggleGroup debugToggleGroup;
    @FXML
    ToggleGroup genCodeToggleGroup;
    @FXML
    ToggleGroup optimizeToggleGroup;
    @FXML
    ToggleGroup developerToggleGroup;

    public ToolBoxPanelController(){}

    @FXML
    private void initialize(){ }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOK(){
        dialogueStage.close();
        executeCompileCommand();
        executeLinkCommand();
        executeDebugCommand();
        executeGenCodeCommand();
        executeOptimizeCodeCommand();
        executeDeveloperCommand();
    }

    private void executeCompileCommand(){
        RadioButton selectedRadioButton = (RadioButton) compileToggleGroup.getSelectedToggle();

        if(selectedRadioButton == null){
            return;
        }

        String toggleGroupValue = selectedRadioButton.getText();

        if(toggleGroupValue.equalsIgnoreCase(CommandType.COMPILE_EXECUTABLE_COMMAND.toString())){
            controller.compileToExecutableFile();
        }
        else if(toggleGroupValue.equalsIgnoreCase(CommandType.COMPILE_OBJECT_COMMAND.toString())){
            controller.compileToObjectFile();
        }
    }

    private void executeLinkCommand(){
        RadioButton selectedRadioButton = (RadioButton) linkToggleGroup.getSelectedToggle();

        if(selectedRadioButton == null){
            return;
        }
        String toggleGroupValue = selectedRadioButton.getText();

        if(toggleGroupValue.equalsIgnoreCase(CommandType.LINKING_COMMAND.toString())){
            controller.linkCFile();
        }
    }

    private void executeDebugCommand(){
        RadioButton selectedRadioButton = (RadioButton) debugToggleGroup.getSelectedToggle();

        if(selectedRadioButton == null){
            return;
        }
        String toggleGroupValue = selectedRadioButton.getText();

        if(toggleGroupValue.equalsIgnoreCase(CommandType.DEBUG_G_COMMAND.toString())){
            controller.debugInNativeInfo();
        }
        else if(toggleGroupValue.equalsIgnoreCase(CommandType.DEBUG_GDBB_COMMAND.toString())){
            controller.debugInDwarfInfo();
        }
    }

    private void executeGenCodeCommand(){
        RadioButton selectedRadioButton = (RadioButton) genCodeToggleGroup.getSelectedToggle();

        if(selectedRadioButton == null){
            return;
        }

        String toggleGroupValue = selectedRadioButton.getText();

        if(toggleGroupValue.equalsIgnoreCase(CommandType.CODE_GEN_FWRAPV_COMMAND.toString())){
            controller.genCodeFtrapv();
        }
        else if(toggleGroupValue.equalsIgnoreCase(CommandType.CODE_GEN_FTRAPV_COMMAND.toString())){
            controller.genCodeFwrapv();
        }
    }

    private void executeOptimizeCodeCommand(){
        RadioButton selectedRadioButton = (RadioButton) optimizeToggleGroup.getSelectedToggle();
        if(selectedRadioButton == null){
            return;
        }
        String toggleGroupValue = selectedRadioButton.getText();

        if(toggleGroupValue.equalsIgnoreCase(CommandType.OPTIMIZE_LEVEL1_COMMAND.toString())){
            controller.optimizeLevelOne();
        }
        else if(toggleGroupValue.equalsIgnoreCase(CommandType.OPTIMIZE_LEVEL2_COMMAND.toString())){
            controller.optimizeLevelTWo();
        }
        else if(toggleGroupValue.equalsIgnoreCase(CommandType.OPTIMIZE_LEVEL3_COMMAND.toString())){
            controller.optimizeLevelThree();
        }
    }

    private void executeDeveloperCommand(){
        RadioButton selectedRadioButton = (RadioButton) developerToggleGroup.getSelectedToggle();

        if(selectedRadioButton == null){
            return;
        }
        String toggleGroupValue = selectedRadioButton.getText();

        if(toggleGroupValue.equalsIgnoreCase(CommandType.DEVELOPER_OPTIMIZATION_COMMAND.toString())){
            controller.generateSrcfice();
        }
    }

    public void setController(EditorPanelController editorPanelController){
        this.controller = editorPanelController;
    }

    public void setDialogueStage(Stage dialogueStage){
        this.dialogueStage = dialogueStage;
    }
}
