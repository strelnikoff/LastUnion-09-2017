package Tests.Test;

import com.github.javafaker.Faker;
import lastunion.application.Application;
import lastunion.application.Managers.UserManager;
import lastunion.application.Models.SignUpModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

import static org.junit.Assert.assertSame;

@SuppressWarnings("DefaultFileTemplate")
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)

public class UserManagerTest {
    @Autowired
    private UserManager userManager;

    private static Faker faker;
    private static String userMail;
    private static String userLogin;
    private static String pass;

    @BeforeClass
    public static void setUpFaker() {
        faker = new Faker(new Locale("en-US"));
    }

    public void registerUserOk() {
        final SignUpModel signUpModel = new SignUpModel(userMail, userLogin, pass);
        final UserManager.ResponseCode responseCode = userManager.signUpUser(signUpModel);
        assertSame(responseCode, UserManager.ResponseCode.OK);
    }

    @Before
    public void setUp() {
        userMail = faker.internet().emailAddress();
        userLogin = faker.name().username();
        pass = faker.internet().password();
        registerUserOk();
    }

    @Test
    public void registerUserConflict() {
        final SignUpModel signUpModel = new SignUpModel(userMail, userLogin, pass);
        final UserManager.ResponseCode responseCode = userManager.signUpUser(signUpModel);
        assertSame(responseCode, UserManager.ResponseCode.LOGIN_IS_BUSY);
    }
}



//    @Test
//    public void registerUserNullMail() {
//        final SignUpModel signUpModel = new SignUpModel(
//                null,
//                faker.name().username(),
//                faker.internet().password());
//        final UserManager.ResponseCode responseCode = userManager.signUpUser(signUpModel);
//        assertSame(responseCode, UserManager.ResponseCode.);
//    }
//
//    @Test
//    public void registerNullLogin() {
//        final UserData userData = new UserData(
//                faker.internet().emailAddress(),
//                null,
//                faker.internet().password());
//        final AccountService.ErrorCodes error = accountService.register(userData);
//        assertSame(error, AccountService.ErrorCodes.INVALID_REG_DATA);
//    }
//
//    @Test
//    public void registerUserNullPass() {
//        final UserData userData = new UserData(
//                faker.internet().emailAddress(),
//                faker.name().username(),
//                null);
//        final AccountService.ErrorCodes error = accountService.register(userData);
//        assertSame(error, AccountService.ErrorCodes.INVALID_REG_DATA);
//    }