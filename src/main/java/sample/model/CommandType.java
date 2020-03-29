package sample.model;

public enum CommandType {
    CPLUS_COMPILE_COMMAND("Compile C+ file"),
    C_COMPILE_COMMAND("Compile C file"),
    C_LINKING_COMMAND("Linking C+ files"),
    C_DEBUG_COMMAND("Debug C files"),
    COMPILE_C_PLUS("g++ -Wall -Werror -pedantic -std=c++1y "),
    COMPILE_TO_EXECUTABLE("gcc "),
    COMPILE_TO_OBJECT_FILE("gcc "),
    LINK_C("gcc -shared "),
    OPTIMIZE_C_LEVEL1("gcc -Wall -O1 -c "),
    OPTIMIZE_C_LEVEL2("gcc -Wall -O2 -c "),
    OPTIMIZE_C_LEVEL3("gcc -Wall -O3 -c "),
    DEVELOPER_OPTIMIZATION("gcc  -fsave-optimization-record "),
    DEBUG_G("gcc -g "),
    DEBUG_GGDB("gcc -ggdb");

    private String commandType;

    CommandType(String commandType){
        this.commandType =commandType;
    }

    public String toString(){
        return commandType;
    }
}
