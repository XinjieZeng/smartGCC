package sample.model;

public enum CommandType {
    CPLUS_COMPILE_COMMAND("Compile C+ file"),
    COMPILE_EXECUTABLE_COMMAND("Compile to Executable"),
    COMPILE_OBJECT_COMMAND("Compile to Object"),
    LINKING_COMMAND("Linking -shared"),
    DEBUG_G_COMMAND("Debug -g"),
    DEBUG_GDBB_COMMAND("Debug -gdbb"),
    OPTIMIZE_LEVEL1_COMMAND("Level 1 Optimization"),
    OPTIMIZE_LEVEL2_COMMAND("Level 2 Optimization"),
    OPTIMIZE_LEVEL3_COMMAND("level 3 Optimization"),
    DEVELOPER_OPTIMIZATION_COMMAND("developer optimization"),
    COMPILE_C_PLUS("g++ -Wall -Werror -pedantic -std=c++1y "),
    COMPILE_TO_EXECUTABLE("gcc "),
    COMPILE_TO_OBJECT_FILE("gcc "),
    LINK_C("gcc -shared "),
    OPTIMIZE_C_LEVEL1("gcc -Wall -O1 -c "),
    OPTIMIZE_C_LEVEL2("gcc -Wall -O2 -c "),
    OPTIMIZE_C_LEVEL3("gcc -Wall -O3 -c "),
    DEVELOPER_OPTIMIZATION("gcc -fsave-optimization-record "),
    DEBUG_G("gcc -g "),
    DEBUG_GGDB("gcc -ggdb ");

    private String commandType;

    CommandType(String commandType){
        this.commandType =commandType;
    }

    public String toString(){
        return commandType;
    }
}
