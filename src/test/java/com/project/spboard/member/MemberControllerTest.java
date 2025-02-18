package com.project.spboard.member;

import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    private final String testEmail = "join@email.com";
    private final String testPassword = "password";
    private final String testName = "name";
    @Autowired
    private MockMvc mockMvc;


    @Test
    void testRegisterAndLogin() throws Exception {
        // TestJoin
        String joinRequest = """
            {
                "email" : "%s",
                "password" : "%s",
                "name" : "%s"
            }
            """.formatted(testEmail, testPassword, testName);

        mockMvc.perform(post("/join")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(joinRequest))
            .andExpect(status().isCreated());

        //TestLogin
        MvcResult result = mockMvc.perform(post("/login")
                                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                               .param("username", testEmail)
                                               .param("password", testPassword))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.payload").exists())
            .andExpect(cookie().exists("Authorization"))
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        String accessToken = JsonPath.read(responseContent, "$.payload");
        Cookie refreshToken = Arrays.stream(result.getResponse().getCookies())
            .filter(cookie -> "Authorization".equals(cookie.getName())).findFirst().orElseThrow();

        //TestAccessToken
        mockMvc.perform(get("/checkLogin")
                            .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk());
        //TestRefreshToken
        mockMvc.perform(post("/refresh")
                            .header("Authorization", "Bearer " + accessToken)
                            .cookie(refreshToken))
            .andExpect(status().isOk());

    }

}
