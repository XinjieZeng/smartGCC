package sample.model;

public enum CommandType {
    COMPILE_EXECUTABLE_COMMAND("compile to executable file"),
    COMPILE_OBJECT_COMMAND("compile to object file"),
    LINKING_COMMAND("link -shared "),
    DEBUG_G_COMMAND("debug -g "),
    DEBUG_GDBB_COMMAND("debug -gdbb "),
    OPTIMIZE_LEVEL1_COMMAND("optimization level 1"),
    OPTIMIZE_LEVEL2_COMMAND("optimization level 2"),
    OPTIMIZE_LEVEL3_COMMAND("optimization level 3"),
    CODE_GEN_FTRAPV_COMMAND("code gen -ftrapv"),
    CODE_GEN_FWRAPV_COMMAND("code gen -fwrapv"),
    DEVELOPER_OPTIMIZATION_COMMAND("developer -fsave-optimization-record "),
    RUN_COMMAND("RUN"),
    STOP_COMMAND("Stop"),
    COMPILE_TO_EXECUTABLE("gcc "),
    COMPILE_TO_OBJECT_FILE("gcc "),
    LINK_C("gcc -shared "),
    OPTIMIZE_C_LEVEL1("gcc -Wall -O1 -c "),
    OPTIMIZE_C_LEVEL2("gcc -Wall -O2 -c "),
    OPTIMIZE_C_LEVEL3("gcc -Wall -O3 -c "),
    DEVELOPER_OPTIMIZATION("gcc -fsave-optimization-record "),
    DEBUG_G("gcc -g "),
    CODE_GEN_FTRAPV("gcc -ftrapv "),
    CODE_GEN_FWRAPV("gcc -fwrapv "),
    RUN("./"),
    DEBUG_GGDB("gcc -ggdb ");

    private String commandType;

    CommandType(String commandType){
        this.commandType =commandType;
    }

    public String toString(){
        return commandType;
    }
}
