package com.elstele.bill.test.controller;

import com.elstele.bill.controller.AuthController;
import com.elstele.bill.dao.interfaces.LocalUserDAO;
import com.elstele.bill.datasrv.interfaces.LocalUserDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Messagei18nHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Mock
    LocalUserDAO localUserDAO;
    @Mock
    LocalUserDataService localUserDataService;
    @InjectMocks
    AuthController controllerUnderTest;
    @Mock
    Messagei18nHelper messageHelper;

    private LocalUser lu;

    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        //initialise only one controller
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
        lu = new LocalUser();
        lu.setUsername("testUser");
        lu.setPassword("qwerty");
        lu.setId(1);
    }

    @Test
    public void loginPageResponseValidUser() throws Exception {
        when(localUserDataService.isCredentialValid("testUser", "qwerty")).thenReturn(true);
        when(localUserDataService.getUserByNameAndPass("testUser", "qwerty")).thenReturn(lu);

        this.mockMvc.perform(post("/login")
                .session(mockSession)
                .param("username", "testUser")
                .param("password", "qwerty")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(forwardedUrl("main"))
                .andExpect(request().sessionAttribute(Constants.LOCAL_USER, notNullValue()));
    }

    @Test
    public void loginPageResponseInvalidUser() throws Exception {

        when(localUserDataService.isCredentialValid("testUser", "qwerty")).thenReturn(true);
        when(messageHelper.getMessage("auth.error")).thenReturn("notnull");

        this.mockMvc.perform(post("/login")
                .param("userName", "foo")
                .param("userPass", "bar")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("login_page"))
                .andExpect(forwardedUrl("login_page"))
                .andExpect(model().attribute(Constants.ERROR_MESSAGE, notNullValue()));
    }

    @Test
    public void loginGet() throws Exception {

        mockSession.setAttribute(Constants.LOCAL_USER, lu);
        this.mockMvc.perform(get("/login")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attribute("username", lu.getUsername()));
    }

    @Test
    public void logoutCall() throws Exception {

        mockSession.setAttribute(Constants.LOCAL_USER, new LocalUser());
        this.mockMvc.perform(get("/logout")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(Constants.LOCAL_USER, nullValue()))
                .andExpect(view().name("login_page"));
    }


}
