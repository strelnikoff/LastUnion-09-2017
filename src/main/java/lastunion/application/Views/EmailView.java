package lastunion.application.Views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


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
        if (!isFilled()) return false;
        try {
            InternetAddress emailAddr = new InternetAddress(newEmail);
            emailAddr.validate();
        } catch (AddressException ex) {
            return false;
        }
        return true;
    }
}
