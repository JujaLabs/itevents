package org.itevents;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml",
		"classpath*:mvc-dispatcher-servlet.xml", "classpath*:spring-security.xml"})
@WebAppConfiguration
public class SequrityTests {

	@Inject
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void testLoginFail() throws Exception {
		mvc.perform(formLogin().user("vlasov@email.com").password("wrongPassword"))
				.andExpect(unauthenticated())
				.andExpect(status().isUnauthorized());

	}

	@Test
	public void testLoginSuccess() throws Exception {
		mvc.perform(formLogin().user("vlasov@email.com").password("alex"))
				.andExpect(authenticated())
				.andExpect(status().isOk());

	}
}
