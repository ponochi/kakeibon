package org.panda.systems.kakeibon.app.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.panda.systems.kakeibon.WebSecurityConfig;
import org.panda.systems.kakeibon.domain.model.users.RoleName;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetails;
import org.panda.systems.kakeibon.utils.common.AccessMethod;
import org.panda.systems.kakeibon.utils.common.LoginProcess;
import org.panda.systems.kakeibon.utils.common.Converter;
import org.panda.systems.kakeibon.utils.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@EnableWebSecurity
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MainControllerTest extends LoginProcess implements Converter, Utils {

    private MockMvc mockMvc;

    @Autowired
    WebSecurityConfig webSecurityConfig;
    @Autowired
    MainController mainController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(mainController)
                .build();
    }

    @Test
    void mainMenu() {
        String password
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");
        Users users = new Users();
        users.setUserId(1L);
        users.setUsername("juiceA");
        users.setPassword(password);
        users.setRoleName(RoleName.ADMIN);
        users.setEnabled(true);
        users.setAccountNonExpired(true);
        users.setAccountNonLocked(true);
        users.setCredentialsNonExpired(true);

        MvcResult result
                = ProcessingLogin(mockMvc, AccessMethod.GET,
                "/menu/mainMenu", "topMenu", users);

        CustomUserDetails customUserDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(customUserDetails);
        assertEquals(users.getUsername(), customUserDetails.getUsername());
        assertEquals(users.getPassword(), customUserDetails.getPassword());
        assertEquals(users.getRoleName(), customUserDetails.getUsers().getRoleName());
        assertTrue(customUserDetails.getUsers().getEnabled());
        assertTrue(customUserDetails.getUsers().getAccountNonExpired());
        assertTrue(customUserDetails.getUsers().getAccountNonLocked());
        assertTrue(customUserDetails.getUsers().getCredentialsNonExpired());
    }
}