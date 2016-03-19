package org.itevents.integration;

import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ContextConfiguration({
		"classpath*:mvc-dispatcher-servlet.xml",
		"classpath*:spring-security.xml",
		"classpath*:applicationContext.xml",
		"classpath:applicationContextTestAddon.xml"
})
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		WithSecurityContextTestExecutionListener.class
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityTests {

	@Inject
	private WebApplicationContext context;
	private MockMvc mvc;
	private final String UNAUTHORISED_ERROR_AS_JSON = new BuilderUtil().buildUnauthorisedErrorAsJson();

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(username="kuchin@email.com", roles={"ADMIN"})
	public void shouldGrantAccessToAdminForAdmin() throws Exception {
		mvc.perform(get("/admin"))
				.andExpect(authenticated().withUsername("kuchin@email.com").withRoles("ADMIN"))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldGrantAccessToIndexForAnonymous() throws Exception {
		mvc.perform(get("/"))
				.andExpect(view().name("index"))
				.andExpect(unauthenticated())
				.andExpect(status().isOk());
	}

	@Test
	public void shouldDenyAccessToAdminForAnonymous() throws Exception {
		mvc.perform(get("/admin"))
				.andExpect(unauthenticated())
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(UNAUTHORISED_ERROR_AS_JSON));
	}

	@Test
	@WithMockUser(username="vlasov@email.com", roles={"subscriber"})
	public void shouldDenyAccessToAdminForSubscriber() throws Exception {
		mvc.perform(get("/admin"))
				.andExpect(authenticated().withUsername("vlasov@email.com").withRoles("subscriber"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username="vlasov@email.com", roles={"subscriber"})
	public void shouldDenyAccessToRegisterNewSubscriberForAuthorizedSubscriber() throws Exception {
		mvc.perform(post("/users/register"))
				.andExpect(authenticated().withUsername("vlasov@email.com"))
				.andExpect(status().isForbidden());
	}

	@Test
	public void shouldGrantAccessToRegisterNewSubscriberForAnonymous() throws Exception {
		mvc.perform(post("/users/register")
                .param("username", "vlasov@email.com")
                .param("password", "password"))
                .andExpect(status().isCreated());
	}

	@Test
	public void shouldReturnErrorWhenAssignAnonymousUserToEvent() throws Exception {
		mvc.perform(post("/events/-1/assign"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(UNAUTHORISED_ERROR_AS_JSON));
	}

	@Test
	public void shouldReturnErrorWhenUnassignAnonymousUserFromEvent() throws Exception {
		mvc.perform(post("/events/-1/unassign"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(UNAUTHORISED_ERROR_AS_JSON));
	}

	@Test
	public void shouldReturnErrorWhenSubscribeAnonymousUser() throws Exception {
		mvc.perform(post("/users/subscribe"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(UNAUTHORISED_ERROR_AS_JSON));
	}

	@Test
	public void shouldReturnErrorWhenUnsubscribeAnonymousUser() throws Exception {
		mvc.perform(post("/users/unsubscribe"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(UNAUTHORISED_ERROR_AS_JSON));
	}

	@Test
	public void shouldReturnErrorWhenAnonymousUserTryToGetEventsOfUser() throws Exception {
		mvc.perform(post("/users/-1/events"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(UNAUTHORISED_ERROR_AS_JSON));
	}

	@Test
	public void shouldReturnErrorWhenRegisterAnonymousUserToEvent() throws Exception {
		mvc.perform(post("/users/-1/events"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(UNAUTHORISED_ERROR_AS_JSON));
	}
}