package lastunion.application.Views;

import lastunion.application.Models.UserModel;

import javax.validation.constraints.NotNull;

public class UserView implements AbstractView {
    private Integer userId;
    private String userLogin;
    private String userEmail;
    private Integer userHighScore;

    public UserView(){}

    public UserView(@NotNull UserModel userModel){
        setUserId(userModel.getUserId());
        setUserLogin(userModel.getUserName());
        setUserEmail(userModel.getUserEmail());
        setUserHighScore(userModel.getUserHighScore());
    }



    public Integer getUserId(){
        return userId;
    }
    public String getUserLogin(){
        return userLogin;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public Integer getUserHighScore(){
        return userHighScore;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setUserLogin(String userLogin){
        this.userLogin = userLogin;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public void setUserHighScore(Integer userHighScore){
        this.userHighScore = userHighScore;
    }

    public final boolean isFilled(){
        return userId != null && userLogin != null && userEmail != null && userHighScore != null;
    }

    public final boolean isValid(){
        return isFilled();
    }
}
