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
public class SignInTest {
    @Autowired
    private MockMvc mock;
    private static Faker faker;
    private static String pathUrl;
    private static String userName;
    private static String userEmail;
    private static String userPassword;


    @BeforeClass
    static public void initFaker(){ faker = new Faker(); }

    private static String getJsonRequest(String uName, String uPassword, String uEmail, boolean emailNeeds ){
        JSONObject jso = new JSONObject();
        jso.put("userName", uName);
        jso.put("userPassword", uPassword);
        if (emailNeeds)
            jso.put("userEmail", uEmail);
        return jso.toString();
    }


    public void createUser() throws Exception {
        this.mock.perform(
                post("/api/user/signup")
                        .contentType("application/json")//MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonRequest(userName, userPassword, userEmail, true)))
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
        pathUrl = "/api/user/signin";

        try {
            createUser();
        }
        catch (Exception ex) {
            throw new RuntimeException();
        }
    }


    @Test
    public void signInNormal() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequest(userName, userPassword, userEmail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(true)))
                .andExpect(jsonPath("$.responseMessage", is("Ok! en")));
    }

    @Test
    public void signInNullUserName() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequest(null , userPassword, userEmail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }

    @Test
    public void signInNullUserPassword() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequest(userName , null, userEmail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }


    @Test
    public void signInIncorrectUserName() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequest("Petya" , userPassword, userEmail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Invalid authentication data! en")));
    }


    @Test
    public void signInIncorrectUserPassword() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("application/json")
                        .content(getJsonRequest(userName , "no", userEmail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Invalid authentication data! en")));
    }


    @Test
    public void signInIncorrectDocumentType() throws Exception{
        this.mock.perform(
                post(pathUrl)
                        .contentType("text/html"))
                       .andExpect(status().isUnsupportedMediaType());
    }
}
