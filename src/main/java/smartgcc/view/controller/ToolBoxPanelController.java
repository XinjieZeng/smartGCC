package smartgcc.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import smartgcc.utils.UIUtils;

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
    private void initialize(){ 
    	Tooltip optimize_1_tip = new Tooltip("Moderate optimization; optimizes reasonably well but does not degrade compilation time significantly.");
    	Tooltip optimize_2_tip = new Tooltip("Full optimization; generates highly optimized code and has the slowest compilation time.");
    	Tooltip optimize_3_tip = new Tooltip("Full optimization as in -O2, and also attempts automatic inlining of small subprograms within a unit.");
    	
    	Tooltip developer_fsave_optimization_record_tip = new Tooltip("Write a SRCFILE.opt-record.json.gz file detailing what optimizations were performed, for those optimizations that support -fopt-info.");
    	Tooltip developer_save_temps_tip = new Tooltip("Store the usual “temporary” intermediate files permanently; place them in the current directory and name them based on the source file.");
    	
    	Tooltip compile_executable_tip = new Tooltip("Compile to an executable file.");
    	Tooltip compile_object_tip = new Tooltip("Compile to an object file.");
    	
    	Tooltip link_shared_tip = new Tooltip("Produce a shared object which can then be linked with other objects to form an executable.");
    	Tooltip link_dynamic_tip = new Tooltip("Pass the flag -export-dynamic to the ELF linker, on targets that support it. \n This instructs the linker to add all symbols, not only used ones, to the dynamic symbol table. \n This option is needed for some uses of dlopen or to allow obtaining backtraces from within a program.");
    	
    	Tooltip debug_g_tip = new Tooltip("Produce debugging information in the operating system’s native format (stabs, COFF, XCOFF, or DWARF). \n GDB can work with this debugging information.");
    	Tooltip debug_gdbb_tip = new Tooltip("Produce debugging information for use by GDB. \n This means to use the most expressive format available (DWARF, stabs, or the native format if neither of those are supported), \n including GDB extensions if at all possible.");
    	
    	Tooltip gen_ftrapv_tip = new Tooltip("This option generates traps for signed overflow on addition, subtraction, multiplication operations.");
    	Tooltip gen_fwrapv_tip = new Tooltip("This option instructs the compiler to assume that signed arithmetic overflow of \n addition, subtraction and multiplication wraps around using twos-complement representation.");
    	
    	compile_executable.setTooltip(compile_executable_tip);
    	compile_object.setTooltip(compile_object_tip);
    	
    	link_shared.setTooltip(link_shared_tip);
    	link_dynamic.setTooltip(link_dynamic_tip);
    	
    	debug_g.setTooltip(debug_g_tip);
    	debug_gdbb.setTooltip(debug_gdbb_tip);
    	
    	gen_ftrapv.setTooltip(gen_ftrapv_tip);
    	gen_fwrapv.setTooltip(gen_fwrapv_tip);
    	
    	optimize_1.setTooltip(optimize_1_tip);
    	optimize_2.setTooltip(optimize_2_tip);
        optimize_3.setTooltip(optimize_3_tip);
        
        developer_fsave_optimization_record.setTooltip(developer_fsave_optimization_record_tip);
        developer_save_temps.setTooltip(developer_save_temps_tip);
        
    }

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
