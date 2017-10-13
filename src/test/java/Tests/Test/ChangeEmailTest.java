package Tests.Test;

import com.github.javafaker.Faker;
import lastunion.application.Application;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Category(Tests.IntegrationTest.class)
public class ChangeEmailTest {
    @Autowired
    private MockMvc mock;
    private static Faker faker;
    private static String pathUrl;
    private static String userName;
    private static String userEmail;
    private static String userPassword;



    @BeforeClass
    static public void initFaker(){ faker = new Faker(); }

    private static String getJsonRequest(String uName, String uPassword, String uEmail){
        JSONObject jso = new JSONObject();
        jso.put("userName", uName);
        jso.put("userPassword", uPassword);
        jso.put("userEmail", uEmail);
        return jso.toString();
    }

    private static String getJsonRequestNewEmail(String newEmail){
        JSONObject jso = new JSONObject();
        jso.put("newEmail", newEmail);
        return jso.toString();
    }


    public void createUser() throws Exception {
        this.mock.perform(
                post("/api/user/signup")
                        .contentType("application/json")//MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonRequest(userName, userPassword, userEmail)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(true)))
                .andExpect(jsonPath("$.responseMessage", is("User created successfully! en")));
    }

    @Before
    public void setUp(){
        userName = faker.name().username();
        userEmail = faker.internet().emailAddress();
        userPassword = faker.internet().password();
        pathUrl = "/api/user/change_email";

        try {
            createUser();
        }
        catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    @Test
    public void changeEmailNormal() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequestNewEmail(faker.internet().emailAddress()))
                        .sessionAttr("userName", userName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(true)))
                .andExpect(jsonPath("$.responseMessage", is("Ok! en")));
    }

    @Test
    public void changeEmailIncorrectEmail() throws Exception {
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequestNewEmail("aaa"))
                        .sessionAttr("userName", userName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Email not valid! en")));
    }


    @Test
    public void changeEmailNullUserNewEmail() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequestNewEmail(null))
                        .sessionAttr("userName", userName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Email not valid! en")));
    }

    @Test
    public void changeEmailInvalidSession() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequest(null , userPassword, userEmail))
                        .sessionAttr("userName", null))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }

    @Test
    public void signUpNullUserPassword() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequest(userName , null, userEmail)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }


    @Test
    public void signUpNullUserEmail() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequest(userName , userPassword, null)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }


    @Test
    public void signUnIncorrectDocumentType() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("text/html"))
                .andExpect(status().isUnsupportedMediaType());
    }
}
