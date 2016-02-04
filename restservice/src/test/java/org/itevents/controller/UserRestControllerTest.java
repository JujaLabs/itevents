package org.itevents.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.*;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vaa25 on 16.10.2015.
 */
public class UserRestControllerTest extends AbstractControllerTest {

    @Mock
    private RoleService roleService;
    @Mock
    private UserService userService;
    @Mock
    private FilterService filterService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EventService eventService;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserRestController userRestController;

    @Before
    public void init() {
        super.initMock(this);
        super.initMvc(userRestController);
    }

    @Test
    public void shouldRegisterNewSubscriber() throws Exception {
        String newSubscriberName = "newSubscriberName";
        String newSubscriberPassword = "newSubscriberPassword";

        doNothing().when(userService).addSubscriber(newSubscriberName, newSubscriberPassword);

        mockMvc.perform(post("/users/register")
                .param("username", newSubscriberName)
                .param("password", newSubscriberPassword))
                .andExpect(status().isCreated());

        verify(userService).addSubscriber(newSubscriberName, newSubscriberPassword);
    }

    @Test
    @WithMockUser(username = "testSubscriber", password = "testSubscriberPassword", authorities = "subscriber")
    public void shouldAddFilterForSubscription() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getAuthorizedUser()).thenReturn(user);
        doNothing().when(filterService).addFilter(eq(user), any(Filter.class));
        doNothing().when(userService).activateUserSubscription(user);

        mockMvc.perform(get("/users/subscribe"))
                .andExpect(status().isOk());

        verify(userService).getAuthorizedUser();
        verify(userService).activateUserSubscription(user);
        verify(filterService).addFilter(eq(user), any(Filter.class));
    }

    @Test
    public void shouldDeactivateSubscription() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getAuthorizedUser()).thenReturn(user);
        doNothing().when(userService).deactivateUserSubscription(user);

        mockMvc.perform(get("/users/unsubscribe"))
                .andExpect(status().isOk());

        verify(userService).getAuthorizedUser();
        verify(userService).deactivateUserSubscription(user);
    }

    @Test
    public void shouldReturnEventsByUser() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventJs());
        String expectedEventsInJson = new ObjectMapper().writeValueAsString(expectedEvents);

        when(eventService.getEventsByUser(user)).thenReturn(expectedEvents);
        when(userService.getUser(user.getId())).thenReturn(user);

        mockMvc.perform(get("/users/" + user.getId() + "/events"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedEventsInJson));
    }

    @Test
    public void shouldGenerateToken() throws Exception {
        when(tokenService.createToken("someMail@mail.ua", "passwd")).thenReturn("someToken");
        mockMvc.perform(post("/users/login")
                .param("username", "someMail@mail.ua")
                .param("password", "passwd")
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        User user = BuilderUtil.buildUserVlasov();
        String expectedEventsInJson = new ObjectMapper().writeValueAsString(user);

        when(userService.getUser(user.getId())).thenReturn(user);

        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedEventsInJson));
    }
}