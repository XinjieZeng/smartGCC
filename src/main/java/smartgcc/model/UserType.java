package smartgcc.model;

public enum UserType {

    NOVICE("novice"),
    TYPICAL("typical"),
    EXPERT("expert"),
    UNKNOWN("unknown");

    private String userType;

    UserType(String userType){
        this.userType =userType;
    }

    public String toString(){
        return userType;
    }


}
