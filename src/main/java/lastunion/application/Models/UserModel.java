package lastunion.application.Models;

public final class UserModel {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPasswordHash;
    private Integer userHighScore;

    public UserModel(){}

    public UserModel(Integer userId, String userName, String userEmail,
                     String userPasswordHash, Integer userHighScore){
        setUserId(userId);
        setUserName(userName);
        setUserEmail(userEmail);
        setUserPasswordHash(userPasswordHash);
        setUserHighScore(userHighScore);
    }

    public UserModel(UserModel other){
        setUserId(other.userId);
        setUserName(other.userName);
        setUserEmail(other.userEmail);
        setUserPasswordHash(other.userPasswordHash);
        setUserHighScore(other.userHighScore);
    }


    public UserModel(Integer userId, String userName, String userEmail,
                     String userPasswordHash){
        setUserId(userId);
        setUserName(userName);
        setUserEmail(userEmail);
        setUserPasswordHash(userPasswordHash);
    }

    public UserModel(String userName, String userEmail,
                     String userPassword){
        setUserName(userName);
        setUserEmail(userEmail);
        setUserPasswordHash(userPassword);
    }

    public UserModel(SignUpModel signUpModel) {
        setUserName(signUpModel.getUserName());
        setUserEmail(signUpModel.getUserEmail());
        setUserPasswordHash(signUpModel.getUserPassword());
    }

    /*
    Getters and Setters
     */

    public Integer getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public String getUserPasswordHash(){
        return userPasswordHash;
    }
    public Integer getUserHighScore(){
        return userHighScore;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public void setUserPasswordHash(String userPasswordHash){
        this.userPasswordHash = userPasswordHash;
    }
    public void setUserHighScore(Integer userHighScore){
        this.userHighScore = userHighScore;
    }
}
