package sample.model;

public enum CommandType {
    CPLUS_COMPILE_COMMAND("Compile C+ file"),
    C_COMPILE_COMMAND("Compile C file"),
    CPLUS_LINKING_COMMAND("Linking C+ files"),
    C_DEBUG_COMMAND("Debug C files"),
    CPLUSCOMPILE("g++ -Wall -Werror -pedantic -std=c++1y "),
    CCOMPILE("gcc "),
    CDEBUG("gcc -g ");

    private String commandType;

    CommandType(String commandType){
        this.commandType =commandType;
    }

    public String toString(){
        return commandType;
    }
}
