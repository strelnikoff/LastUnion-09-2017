package lastunion.application.Views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpView implements AbstractView {
    private final String userName;
    private final String userPassword;
    private final String userEmail;

    @JsonCreator
    SignUpView(@JsonProperty("userName") String userName, @JsonProperty("userPassword") String userPassword,
               @JsonProperty("userEmail") String userEmail){
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public final String getUserName(){
        return userName;
    }
    public final String getUserPassword(){ return userPassword; }
    public final String getUserEmail() {return userEmail; }
    public final  boolean isFilled(){
        return userEmail != null && userPassword != null && userName !=null;
    }

    public final boolean isValid() {
        if (!isFilled()) return false;

        return true;
        //TODO check data for valid
    }
}
