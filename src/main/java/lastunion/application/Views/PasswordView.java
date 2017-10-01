package lastunion.application.Views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordView implements AbstractView {
    private final String oldPassword;
    private final String newPassword;

    @JsonCreator
    public PasswordView(@JsonProperty("oldPassword") String oldPassword,
                        @JsonProperty("newPassword") String newPassword){
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public boolean isFilled(){
        return newPassword != null && oldPassword != null;
    }
    public boolean isValid() {
        if (!isFilled()) return false;
        return true;
        //TODO check data for valid
    }
}
