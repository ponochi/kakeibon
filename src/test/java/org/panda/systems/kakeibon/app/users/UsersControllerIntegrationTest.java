package org.panda.systems.kakeibon.app.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.panda.systems.kakeibon.WebSecurityConfig;
import org.panda.systems.kakeibon.domain.model.users.RoleName;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.repository.users.UsersDetailRepository;
import org.panda.systems.kakeibon.domain.repository.users.UsersExtRepository;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetails;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.panda.systems.kakeibon.domain.service.users.UserExtsService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AutoConfigureMockMvc
@EnableWebSecurity
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UsersControllerIntegrationTest extends LoginProcess implements Converter, Utils {

    private MockMvc mockMvc;

    @Autowired
    UsersDetailRepository usersDetailRepository;
    @Autowired
    UsersExtRepository usersExtRepository;

    CustomUserDetailsService customUserDetailsService;
    UserExtsService userExtsService;

    WebSecurityConfig webSecurityConfig;

    UsersController usersController;

    // Login user data.
    String password;
    Users users;

    @BeforeEach
    void setUp() {
        customUserDetailsService = new CustomUserDetailsService(usersDetailRepository);
        userExtsService = new UserExtsService(usersExtRepository);
        webSecurityConfig = new WebSecurityConfig();
        usersController = new UsersController(
                customUserDetailsService,
                userExtsService,
                webSecurityConfig
        );

        mockMvc = MockMvcBuilders
                .standaloneSetup(usersController)
                .build();

        this.password
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");
        this.users = new Users();
        this.users.setUserId(1L);
        this.users.setUsername("juiceA");
        this.users.setPassword(this.password);
        this.users.setRoleName(RoleName.ADMIN);
        this.users.setEnabled(true);
        this.users.setAccountNonExpired(true);
        this.users.setAccountNonLocked(true);
        this.users.setCredentialsNonExpired(true);
    }

    @Test
    void testList() throws Exception {
        // Do processing login
        MvcResult result
                = ProcessingLogin(mockMvc, AccessMethod.GET,
                "/users/list", "users/showList",
                this.users);

        CustomUserDetails userDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(userDetails);
        assertEquals(this.users.getUserId(), userDetails.getUsers().getUserId());
        assertEquals(this.users.getUsername(), userDetails.getUsers().getUsername());
        assertEquals(this.users.getPassword(), userDetails.getUsers().getPassword());
        assertEquals(this.users.getRoleName(), userDetails.getUsers().getRoleName());
        assertTrue(userDetails.getUsers().getEnabled());
        assertTrue(userDetails.getUsers().getAccountNonExpired());
        assertTrue(userDetails.getUsers().getAccountNonLocked());
        assertTrue(userDetails.getUsers().getCredentialsNonExpired());

        List<CustomUserDetails> customUserDetails
                = autoCast(getObj(result, "customUserDetails"));

        // Check the display of the user list
        assertNotNull(customUserDetails);

        assertEquals(2, customUserDetails.size());

        assertEquals(2L, customUserDetails.get(0).getUsers().getUserId());
        assertEquals("juiceB", customUserDetails.get(0).getUsers().getUsername());
        assertEquals(
                "{bcrypt}$2a$10$yVqiUjrYj5jPMMZ0/M2xh.J6PZqiONu4QT3oB4ZNuF/z1RQX.qLE2",
                customUserDetails.get(0).getUsers().getPassword());
        assertEquals(
                RoleName.USER,
                customUserDetails.get(0).getUsers().getRoleName());
        assertTrue(customUserDetails.get(0).getUsers().getEnabled());
        assertTrue(customUserDetails.get(0).getUsers().getAccountNonExpired());
        assertTrue(customUserDetails.get(0).getUsers().getAccountNonLocked());
        assertTrue(customUserDetails.get(0).getUsers().getCredentialsNonExpired());

        assertEquals("juiceA", customUserDetails.get(1).getUsername());
        assertEquals(
                "{bcrypt}$2a$10$mekheplkOdsT5v5VLdF.heNd60EEXT3JyVU9qpq.DscBxmMkdutOa",
                customUserDetails.get(1).getPassword());
        assertEquals(
                RoleName.ADMIN,
                customUserDetails.get(1).getUsers().getRoleName());
        assertTrue(customUserDetails.get(1).getUsers().getEnabled());
        assertTrue(customUserDetails.get(1).getUsers().getAccountNonExpired());
        assertTrue(customUserDetails.get(1).getUsers().getAccountNonLocked());
        assertTrue(customUserDetails.get(1).getUsers().getCredentialsNonExpired());
    }

    @Test
    void testShow() {
        // Do processing login
        MvcResult result
                = ProcessingLogin(
                mockMvc, AccessMethod.GET,
                 "/users/1/show", "users/showDetail",
                this.users);

        CustomUserDetails userDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(userDetails);
        assertEquals(this.users.getUserId(), userDetails.getUsers().getUserId());
        assertEquals(this.users.getUsername(), userDetails.getUsers().getUsername());
        assertEquals(this.users.getPassword(), userDetails.getUsers().getPassword());
        assertEquals(this.users.getRoleName(), userDetails.getUsers().getRoleName());
        assertTrue(userDetails.getUsers().getEnabled());
        assertTrue(userDetails.getUsers().getAccountNonExpired());
        assertTrue(userDetails.getUsers().getAccountNonLocked());
        assertTrue(userDetails.getUsers().getCredentialsNonExpired());

        UsersForm form
                = autoCast(getObj(result, "usersForm"));

        assertNotNull(form);
        assertEquals(this.users.getUserId(), form.getUserId());
        assertEquals(this.users.getUsername(), form.getUsername());
        assertEquals(
                "{bcrypt}$2a$10$mekheplkOdsT5v5VLdF.heNd60EEXT3JyVU9qpq.DscBxmMkdutOa",
                form.getPassword());
        assertEquals(this.users.getRoleName(), form.getRoleName());
        assertTrue(this.users.getEnabled());
        assertTrue(this.users.getAccountNonExpired());
        assertTrue(this.users.getAccountNonLocked());
        assertTrue(this.users.getCredentialsNonExpired());

        assertEquals(1L, form.getUsersExtForm().getUserId());
        assertEquals("Foo1", form.getUsersExtForm().getLastName());
        assertEquals("Bar1", form.getUsersExtForm().getFirstName());
        assertEquals("juiceA@example.com", form.getUsersExtForm().getEmail());
        assertEquals("1971-12-31",
                form.getUsersExtForm()
                        .getBirthDay()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals("00011112222", form.getUsersExtForm().getPhoneNumber());
        assertEquals(ZonedDateTime.parse("2024-07-19T11:22:33.123456+09:00")
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                ZonedDateTime.parse(form.getUsersExtForm()
                        .getEntryDate().toString(),
                        DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .toString());
        assertNull(form.getUsersExtForm().getUpdateDate());
    }

    @Test
    void testCreateForm() {
        // Do processing login
        MvcResult result
                = ProcessingLogin(
                mockMvc, AccessMethod.GET,
                "/users/create", "users/createDetail",
                this.users);

        CustomUserDetails userDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(userDetails);
        assertEquals(this.users.getUserId(), userDetails.getUsers().getUserId());
        assertEquals(this.users.getUsername(), userDetails.getUsers().getUsername());
        assertEquals(this.users.getPassword(), userDetails.getUsers().getPassword());
        assertEquals(this.users.getRoleName(), userDetails.getUsers().getRoleName());
        assertTrue(userDetails.getUsers().getEnabled());
        assertTrue(userDetails.getUsers().getAccountNonExpired());
        assertTrue(userDetails.getUsers().getAccountNonLocked());
        assertTrue(userDetails.getUsers().getCredentialsNonExpired());

        UsersForm form
                = autoCast(getObj(result, "usersForm"));

        assertNotNull(form);
        assertNull(form.getUserId());
        assertNull(form.getUsername());
        assertNull(form.getPassword());
        // Default values
        assertEquals(RoleName.USER, form.getRoleName());
        assertTrue(this.users.getEnabled());
        assertTrue(this.users.getAccountNonExpired());
        assertTrue(this.users.getAccountNonLocked());
        assertTrue(this.users.getCredentialsNonExpired());

        assertNull(form.getUsersExtForm().getUserId());
        assertNull(form.getUsersExtForm().getLastName());
        assertNull(form.getUsersExtForm().getFirstName());
        assertNull(form.getUsersExtForm().getEmail());
        assertNull(form.getUsersExtForm().getBirthDay());
        assertNull(form.getUsersExtForm().getPhoneNumber());
        assertNull(form.getUsersExtForm().getEntryDate());
        assertNull(form.getUsersExtForm().getUpdateDate());

        List<RoleName> roleNames
                = autoCast(getObj(result, "roleNames"));

        assertNotNull(roleNames);
        assertEquals(2, roleNames.size());
        assertEquals(RoleName.ADMIN, roleNames.get(0));
        assertEquals(RoleName.USER, roleNames.get(1));
    }

    @Test
    void testEditForm() {
    }

    @Test
    void testCreateConfirm() {
    }

    @Test
    void testConfirm() {
    }
}