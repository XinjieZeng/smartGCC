package sample.model;

public enum UserType {

    NOVICE("novice"),
    TYPICAL("typical"),
    EXPERT("expert");

    private String userType;

    UserType(String userType){
        this.userType =userType;
    }

    public String toString(){
        return userType;
    }


}
