package de.bigzee.siegertipp.player;

import de.bigzee.siegertipp.BaseControllerTest;
import de.bigzee.siegertipp.account.Account;
import de.bigzee.siegertipp.account.UserService;
import de.bigzee.siegertipp.model.Gender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lzimmerm on 20.06.2014.
 */
public class PlayerControllerTest extends BaseControllerTest {

    @Autowired
    @InjectMocks
    private PlayerController playerController;

    @Mock
    private UserService userService;

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void displaysPlayerList() throws Exception {
        loginUser();

        mockMvc.perform(get("/player/list"))
                .andExpect(model().attributeExists("players"))
                .andExpect(view().name("player/list"))
                .andExpect(content().string(containsString("<title>Startseite</title>"))
                );
    }

    @Test
    public void failCreatePlayer() throws Exception {
        loginUser();
        when(userService.findByUsername((currentUser().getName()))).thenReturn(new Account(currentUser().getName(), "xxx", "ROLE_USER"));

        mockMvc.perform(post("/player/create").param("name", "").param("gender", Gender.MALE.toString()))
                .andExpect(view().name("player/create"))
                .andExpect(model().attributeExists("player"))
                .andExpect(content().string(containsString("may not be empty")));
    }
}