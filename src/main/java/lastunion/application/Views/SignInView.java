package lastunion.application.Views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class SignInView implements AbstractView {
    private final String userName;
    private final String userPassword;

    @JsonCreator
    SignInView(@JsonProperty("userName") String userName,
               @JsonProperty("userPassword") String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName(){
        return userName;
    }
    public String getUserPassword(){ return userPassword;}
    public final boolean isFilled(){ return userName != null && userPassword != null;}
    public final boolean isValid(){
        if (!isFilled()) return false;

        return true;
    }
}
