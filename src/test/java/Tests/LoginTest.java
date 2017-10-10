package Tests;


import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@RunWith(SpringRunner.class)
public class LoginTest {
    @Autowired
    private static MockMvc mock;
    private static Faker faker;

    private static String userName;
    private static String userEMail;
    private static String userPassword;


    @BeforeClass
    static public void initFaker(){
        faker = new Faker();
    }

    private static String getJsonRequest(String uName, String uPassword, String uEmail, boolean emailNeeds ){
        StringBuilder sb = new StringBuilder("{\"userName\":\"");
        sb.append(uName);
        sb.append("\",");
        if (emailNeeds) {
            sb.append("\"userEmail\":\"");
            sb.append(uEmail);
            sb.append("\",");
        }
        sb.append("\"userPassword\":\"");
        sb.append(uPassword);
        sb.append("\"}");
        return sb.toString();
    }

    public void createUser() throws Exception {

    }

    @Before
    public void setUp(){
        userName = faker.name().username();
        userEMail = faker.internet().emailAddress();
        userPassword = faker.internet().password();

        try {
            createUser();
        }
        catch (Exception ex)
        {
        }

    }


    @Test
    public void loginNormal() throws Exception{
        this.mock.perform(
                post("/api/user/signin")
                        .contentType("application/json")
                        .content(getJsonRequest(userName, userPassword, userEMail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(true)))
                .andExpect(jsonPath("$.responseMessage", is("Ok! en")));
    }

    @Test
    public void loginNullUserName() throws Exception{
        this.mock.perform(
                post("/api/user/signin")
                        .contentType("application/json")
                        .content(getJsonRequest(null , userPassword, userEMail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }

    @Test
    public void loginNullUserPassword() throws Exception{
        this.mock.perform(
                post("/api/user/signin")
                        .contentType("application/json")
                        .content(getJsonRequest(userName , null, userEMail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }


    @Test
    public void loginIncorrectUserName() throws Exception{
        this.mock.perform(
                post("/api/user/signup")
                        .contentType("application/json")
                        .content(getJsonRequest("Petya" , userPassword, userEMail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }


    @Test
    public void loginIncorrectUserPassword() throws Exception{
        this.mock.perform(
                post("/api/user/signup")
                        .contentType("application/json")
                        .content(getJsonRequest(userName , "no", userEMail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Invalid authentication data! en")));
    }

    @Test
    public void loginIncorrectDocumepe() throws Exception{
        this.mock.perform(
                post("/api/user/signin")
                        .contentType("text/html"))
                        .andExpect(status().isUnsupported
                        .content(getJsonRequest(userName , userPassword, userEMail, false)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.responseMessage", is("Json contains null fields! en")));
    }


}
