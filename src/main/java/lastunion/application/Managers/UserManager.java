package lastunion.application.Managers;

import lastunion.application.DAO.UserDAO;
import lastunion.application.Models.SignInModel;
import lastunion.application.Models.SignUpModel;
import lastunion.application.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserManager {
    @NotNull private UserDAO userDAO;

    public enum ResponseCode {
        OK,
        LOGIN_IS_BUSY,
        INCORRECT_LOGIN,
        INCORRECT_PASSWORD,
        INCORRECT_SESSION,
        DATABASE_ERROR
    };

    @Autowired
    public UserManager(final JdbcTemplate jdbcTemplate){
        userDAO = new UserDAO(jdbcTemplate);
    }

    // Work with password
    ////////////////////////////////////////////////////////////////////////
    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final String  makePasswordHash(@NotNull final String password) {
        return passwordEncoder().encode(password);
    }

    private final boolean checkPassword(@NotNull final String password, @NotNull final String passwordHash){
        return passwordEncoder().matches(password, passwordHash);
    }

    public boolean checkPasswordByUserName(@NotNull final String password, @NotNull final String userLogin)
    {
        try {
            UserModel savedUser = userDAO.getUserByName(userLogin);

            if (checkPassword(savedUser.getUserPasswordHash(), password))
                return true;
        }
        catch(DataAccessException ex){
            return false;
        }
        return false;
    }
    //////////////////////////////////////////////////////////////////////////

    public ResponseCode signInUser(@NotNull final SignInModel signInUserData) {

        // Check sigInModel for empty fields
        //if (signInUserData.isFilledData())
        //    return ResponseCode.INCORRECT_AUTH_DATA;

        // Check user storaged in database
        try {
            UserModel savedUser = userDAO.getUserByName(signInUserData.getUserName());

            // wrong password
            if (!checkPassword(savedUser.getUserPasswordHash(), signInUserData.getUserPassword()))
                return ResponseCode.INCORRECT_PASSWORD;
        }
        // no user, storaged in database
        catch(EmptyResultDataAccessException ex) {
            return ResponseCode.INCORRECT_LOGIN;
        }
//        // error in work with db
        catch(DataAccessException ex){
            return ResponseCode.DATABASE_ERROR;
        }
        return ResponseCode.OK;
    }

    public ResponseCode signUpUser(@NotNull final SignUpModel signUpUserData) {

        // Check signUpModel for empty fields
        //if (!signUpUserData.isFilledData())  {
        //    return ResponseCode.INCORRECT_REG_DATA;
        //}

        // Creating UserModel to stoarage
        UserModel newUser = new UserModel(signUpUserData);
        newUser.setUserPasswordHash(makePasswordHash(signUpUserData.getUserPassword()));

        // trying to save user
        try {
            userDAO.saveUser(newUser);
        }
        // user with this login exist
        catch(DuplicateKeyException dupEx) {
            return ResponseCode.LOGIN_IS_BUSY;
        }
//        // error in work with db
        catch(DataAccessException daEx) {
            return ResponseCode.DATABASE_ERROR;
        }

        return ResponseCode.OK;
    }

    public ResponseCode changeUserEmail(@NotNull final String newEmail, @NotNull final String userName){
        // trying to get storaged user and copy its data to new
        // user, than in new user modify email and save it
        try {
            final UserModel user = userDAO.getUserByName(userName);
            final UserModel modifiedUser = new UserModel(user);
            modifiedUser.setUserEmail(newEmail);
            userDAO.modifyUser(user, modifiedUser);
        }
        // No user found
        catch (EmptyResultDataAccessException ex) {
            return ResponseCode.INCORRECT_SESSION;
        }
        // error db
        catch (DataAccessException ex) {
            return ResponseCode.DATABASE_ERROR;
        }
        return ResponseCode.OK;
    }

    public ResponseCode changeUserPassword(@NotNull final String newPassword, @NotNull final String userName){
        // trying to get storaged user and copy its data to new
        // user, than in new user modify email and save it
        try {
            final UserModel user = userDAO.getUserByName(userName);
            final UserModel modifiedUser = new UserModel(user);
            modifiedUser.setUserPasswordHash(makePasswordHash(newPassword));
            userDAO.modifyUser(user, modifiedUser);
        }
        // No user found
        catch (EmptyResultDataAccessException ex) {
            return ResponseCode.INCORRECT_SESSION;
        }
        // error db
        catch (DataAccessException ex) {
            return ResponseCode.DATABASE_ERROR;
        }
        return ResponseCode.OK;
    }

    public ResponseCode getUserByName(@NotNull final String userName, UserModel user){
        // trying to get storaged user
        try {
            user = userDAO.getUserByName(userName);
        }
        // No user found
        catch (EmptyResultDataAccessException ex) {
            return ResponseCode.INCORRECT_SESSION;
        }
        // error db
        catch (DataAccessException ex) {
            return ResponseCode.DATABASE_ERROR;
        }
        return ResponseCode.OK;
    }

    public ResponseCode deleteUserByName(@NotNull final String userName){
        // trying to get storaged user
        try {
            userDAO.deleteUserByName(userName);
        }
        // No user found
        catch (EmptyResultDataAccessException ex) {
            return ResponseCode.INCORRECT_SESSION;
        }
        // error db
        catch (DataAccessException ex) {
            return ResponseCode.DATABASE_ERROR;
        }
        return ResponseCode.OK;
    }

    public ResponseCode getUserById(@NotNull final Integer userId, UserModel user){
        // trying to get storaged user
        try {
            user = userDAO.getUserById(userId);
        }
//        catch(Excep
//        // No user found
        catch (EmptyResultDataAccessException ex) {
            return ResponseCode.INCORRECT_SESSION;
        }
        // error db
        catch (DataAccessException ex) {
            return ResponseCode.DATABASE_ERROR;
        }
        return ResponseCode.OK;
    }
}
