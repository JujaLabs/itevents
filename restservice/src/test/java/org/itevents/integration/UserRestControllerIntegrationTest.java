package org.itevents.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:mvc-dispatcher-servlet.xml",
                        "classpath:spring-securityTestAddon.xml",
                        "classpath:applicationContextIntegrationTestAddon.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "file:src/test/resources/dbunit/UserMapperTest/UserMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/UserMapperTest/UserMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
@WebAppConfiguration
public class UserRestControllerIntegrationTest {

    @Inject
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = webAppContextSetup(context).build();
    }

    @Test
    public void shouldExpect422IfLoginAlreadyExists() throws Exception {
        String existingLogin = "anakin@email.com";
        String existingLoginInUpperCase = existingLogin.toUpperCase();
        String anyPassword = "randomString";

        mvc.perform(post("/users/register")
                .param("username", existingLogin)
                .param("password", anyPassword))
                .andExpect(status().isUnprocessableEntity());

        mvc.perform(post("/users/register")
                .param("username", existingLoginInUpperCase)
                .param("password", anyPassword))
                .andExpect(status().isUnprocessableEntity());
    }
}
