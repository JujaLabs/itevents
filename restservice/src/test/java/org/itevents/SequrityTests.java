package org.itevents;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


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
				.andExpect(authenticated().withUsername("vlasov@email.com"))
				.andExpect(status().isOk());
	}

	@Test
	public void testLoginSuccessPost() throws Exception {
		mvc.perform(post("/login")
						.param("username", "vlasov@email.com")
						.param("password", "alex")
				.with(csrf()))
				.andExpect(authenticated().withUsername("vlasov@email.com"))
				.andExpect(status().isOk());
		mvc.perform(logout());
	}

	@Test
	@WithUserDetails("vlasov@email.com")
	public void testLogout() throws Exception {
		mvc.perform(logout())
				.andExpect(unauthenticated())
				.andExpect(status().isOk());
	}

	@Test
	public void testIndexAnonymous() throws Exception {
		mvc.perform(get("/"))
				.andExpect(view().name("index"))
				.andExpect(authenticated().withUsername("guest"))
				.andExpect(status().isOk());
	}

	@Test
	@WithUserDetails("vlasov@email.com")
	public void testIndexAuthenticated() throws Exception {
		mvc.perform(get("/"))
				.andExpect(view().name("index"))
				.andExpect(authenticated().withUsername("vlasov@email.com"))
				.andExpect(status().isOk());
	}

	@Test
	public void testAdminAnonymous() throws Exception {
		mvc.perform(get("/admin"))
				.andExpect(unauthenticated())
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUserDetails("vlasov@email.com")
	public void testAdminDenied() throws Exception {
		mvc.perform(get("/admin"))
				.andExpect(authenticated().withUsername("vlasov@email.com"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithUserDetails("kuchin@email.com")
	public void testAdminGranted() throws Exception {
		mvc.perform(get("/admin"))
				.andExpect(authenticated().withUsername("kuchin@email.com").withRoles("admin"))
				.andExpect(view().name("index"))
				.andExpect(status().isOk());
	}

}
