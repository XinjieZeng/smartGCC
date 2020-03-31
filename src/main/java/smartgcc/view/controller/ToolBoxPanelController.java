package smartgcc.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import smartgcc.model.CommandType;

public class ToolBoxPanelController {
    private Stage dialogueStage;
    private EditorPanelController controller;

    @FXML
    CheckBox compile_executable;
    @FXML
    CheckBox compile_object;
    @FXML
    CheckBox link_shared;
    @FXML
    CheckBox link_dynamic;
    @FXML
    CheckBox debug_g;
    @FXML
    CheckBox debug_gdbb;
    @FXML
    CheckBox gen_ftrapv;
    @FXML
    CheckBox gen_fwrapv;
    @FXML
    CheckBox optimize_1;
    @FXML
    CheckBox optimize_2;
    @FXML
    CheckBox optimize_3;
    @FXML
    CheckBox developer_fsave_optimization_record;
    @FXML
    CheckBox developer_save_temps;


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
        if(compile_executable.isSelected()){
            controller.compileToExecutableFile();
        }
        if(compile_object.isSelected()){
            controller.compileToObjectFile();
        }
    }

    private void executeLinkCommand(){
        if(link_shared.isSelected()){
            controller.linkCFile();
        }

        if(link_dynamic.isSelected()){
            controller.dynamicLink();
        }
    }

    private void executeDebugCommand(){
        if(debug_g.isSelected()){
            controller.debugInNativeInfo();
        }

        if(debug_gdbb.isSelected()){
            controller.debugInDwarfInfo();
        }
    }

    private void executeGenCodeCommand(){
        if(gen_ftrapv.isSelected()){
            controller.genCodeFtrapv();
        }

        if(gen_fwrapv.isSelected()){
            controller.genCodeFwrapv();
        }
    }

    private void executeOptimizeCodeCommand(){

        if(optimize_1.isSelected()){
            controller.optimizeLevelOne();
        }

        if(optimize_2.isSelected()){
            controller.optimizeLevelTWo();
        }

        if(optimize_3.isSelected()){
            controller.optimizeLevelThree();
        }
    }

    private void executeDeveloperCommand(){
        if(developer_fsave_optimization_record.isSelected()){
            controller.generateSrcfice();
        }

        if(developer_save_temps.isSelected()){
            controller.saveTemporaryFile();
        }
    }

    public void setController(EditorPanelController editorPanelController){
        this.controller = editorPanelController;
    }

    public void setDialogueStage(Stage dialogueStage){
        this.dialogueStage = dialogueStage;
    }
}
