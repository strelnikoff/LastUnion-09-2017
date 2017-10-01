package lastunion.application.Views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class EmailView implements AbstractView {
    private String newEmail;

    @JsonCreator
    public EmailView(@JsonProperty("userEmail") String newUserEmail){
        this.newEmail = newUserEmail;
    }
    public String getNewEmail(){
        return newEmail;
    }
    public void setNewEmail(String newUserEmail) {
        this.newEmail = newUserEmail;
    }

    @Override
    public final boolean isFilled() {  return newEmail != null;    }
    @Override
    public final boolean isValid(){
        if (!isFilled()) return false;

        return true;
    }
}
