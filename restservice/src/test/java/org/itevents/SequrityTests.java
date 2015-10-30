package org.itevents;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ContextConfiguration({"classpath*:mvc-dispatcher-servlet.xml", "classpath*:spring-security.xml"})
@TestExecutionListeners(mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
		value = WithSecurityContextTestExecutionListener.class)
@WebAppConfiguration
@DatabaseSetup(value = "file:src/test/resources/dbunit/UserMapperTest/UserMapperTest_initial.xml",
		type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/UserMapperTest/UserMapperTest_initial.xml",
		type = DatabaseOperation.DELETE_ALL)
public class SequrityTests extends AbstractDbTest {

	@Inject
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void shouldNotLoginWithWrongPassword() throws Exception {
		mvc.perform(post("/users/login")
				.param("username", "vlasov@email.com")
				.param("password", "wrongPassword"))
				.andExpect(unauthenticated())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void shouldLoginWithCorrectPassword() throws Exception {
		mvc.perform(post("/users/login")
				.param("username", "vlasov@email.com")
				.param("password", "alex"))
				.andExpect(authenticated().withUsername("vlasov@email.com"))
				.andExpect(status().isOk());

		mvc.perform(logout());
	}

	@Test
	@WithUserDetails("vlasov@email.com")
	public void shouldLogout() throws Exception {
		mvc.perform(post("/users/logout"))
				.andExpect(unauthenticated())
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
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUserDetails("vlasov@email.com")
	public void shouldDenyAccessToAdminForSubscriber() throws Exception {
		mvc.perform(get("/admin"))
				.andExpect(authenticated().withUsername("vlasov@email.com").withRoles("subscriber"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithUserDetails("kuchin@email.com")
	public void shouldGrantAccessToAdminForAdmin() throws Exception {
		mvc.perform(get("/admin"))
				.andExpect(authenticated().withUsername("kuchin@email.com").withRoles("admin"))
				.andExpect(status().isOk());
	}

	@Test
	@WithUserDetails("vlasov@email.com")
	public void shouldDenyAccessToRegisterNewSubscriberForAuthorizedSubscriber() throws Exception {
		mvc.perform(post("/users/register"))
				.andExpect(authenticated().withUsername("vlasov@email.com"))
				.andExpect(status().isForbidden());
	}

	@Test
	public void shouldGrantAccessToRegisterNewSubscriberForAnonymous() throws Exception {
		User testSubscriber = BuilderUtil.buildSubscriberTest();

		mvc.perform(post("/users/register")
				.param("username", testSubscriber.getLogin())
				.param("password", testSubscriber.getPassword()))
				.andExpect(authenticated().withUsername("guest"))
				.andExpect(status().isOk());
	}

}
