package org.panda.systems.kakeibon.app.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.panda.systems.kakeibon.WebSecurityConfig;
import org.panda.systems.kakeibon.domain.model.users.RoleName;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.model.users.UsersExt;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@EnableWebSecurity
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UsersControllerUnitTest extends LoginProcess implements Converter, Utils {

    private MockMvc mockMvc;

    UsersDetailRepository usersDetailRepository;
    CustomUserDetailsService customUserDetailsService;
    UsersExtRepository usersExtRepository;
    UserExtsService userExtsService;

    WebSecurityConfig webSecurityConfig;

    UsersController usersController;

    String password1, password2, password3;
    Users users1;
    Users users2;
    @Autowired
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        usersDetailRepository = mock(UsersDetailRepository.class);
        customUserDetailsService = new CustomUserDetailsService(usersDetailRepository);
        usersExtRepository = mock(UsersExtRepository.class);
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

        this.password1
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");
        this.users1 = new Users();
        this.users1.setUserId(1L);
        this.users1.setUsername("juiceA");
        this.users1.setPassword(this.password1);
        this.users1.setRoleName(RoleName.ADMIN);
        this.users1.setEnabled(true);
        this.users1.setAccountNonExpired(true);
        this.users1.setAccountNonLocked(true);
        this.users1.setCredentialsNonExpired(true);

        this.password2
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");
        this.users2 = new Users();
        this.users2.setUserId(2L);
        this.users2.setUsername("juiceB");
        this.users2.setPassword(this.password2);
        this.users2.setRoleName(RoleName.USER);
        this.users2.setEnabled(true);
        this.users2.setAccountNonExpired(true);
        this.users2.setAccountNonLocked(true);
        this.users2.setCredentialsNonExpired(true);
    }

    @Test
    void testUsersControllerWithoutParameters() {
        UsersController usersController = new UsersController();

        assertNotNull(usersController);
    }

    @Test
    void testUsersControllerWithParameters() {
        UsersController usersController = new UsersController(
                customUserDetailsService,
                userExtsService,
                webSecurityConfig
        );

        assertNotNull(usersController);
    }

    @Test
    void testSetUsers() {
        UsersForm src = new UsersForm();
        src.setUserId(1L);
        src.setUsername("juiceA");
        src.setPassword(
                Objects.requireNonNull(
                                this.webSecurityConfig)
                        .getPasswordEncoder()
                        .encode("test"));
        src.setRoleName(RoleName.ADMIN);
        src.setEnabled(true);
        src.setAccountNonExpired(true);
        src.setAccountNonLocked(true);
        src.setCredentialsNonExpired(true);

        Users dest = new Users();

        usersController.setUsers(src, dest);

        assertEquals(src.getUserId(), dest.getUserId());
        assertEquals(src.getUsername(), dest.getUsername());
        assertEquals(src.getPassword(), dest.getPassword());
        assertEquals(src.getRoleName(), dest.getRoleName());
        assertEquals(src.getEnabled(), dest.getEnabled());
        assertEquals(src.getAccountNonExpired(), dest.getAccountNonExpired());
        assertEquals(src.getAccountNonLocked(), dest.getAccountNonLocked());
        assertEquals(src.getCredentialsNonExpired(), dest.getCredentialsNonExpired());
    }

    @Test
    void testSetUsersExtEntryDateIsNull() {
        UsersExtForm src = new UsersExtForm();
        src.setUserId(1L);
        src.setFirstName("Juice");
        src.setLastName("A");
        src.setEmail("juiceA@example.com");
        src.setBirthDay(LocalDate.parse("2021-06-30"));
        src.setPhoneNumber("0A0-1234-5678");
        src.setEntryDate(null);
        src.setUpdateDate(null);

        UsersExt dest = new UsersExt();

        usersController.setUsersExt(src, dest);

        assertEquals(src.getUserId(), dest.getUserId());
        assertEquals(src.getFirstName(), dest.getFirstName());
        assertEquals(src.getLastName(), dest.getLastName());
        assertEquals(src.getEmail(), dest.getEmail());
        assertEquals(src.getBirthDay(), dest.getBirthDay());
        assertEquals(src.getPhoneNumber(), dest.getPhoneNumber());
        assertNotNull(dest.getEntryDate());
        assertNull(dest.getUpdateDate());
    }

    @Test
    void testSetUsersExtEntryDateIsNotNull() {
        UsersExtForm src = new UsersExtForm();
        src.setUserId(1L);
        src.setFirstName("Juice");
        src.setLastName("A");
        src.setEmail("juiceA@example.com");
        src.setBirthDay(LocalDate.parse("2021-06-30"));
        src.setPhoneNumber("0A0-1234-5678");
        src.setEntryDate(
                ZonedDateTime.parse("2021-06-30T11:22:33.123456+09:00")
        );
        src.setUpdateDate(null);

        UsersExt dest = new UsersExt();

        usersController.setUsersExt(src, dest);

        assertEquals(src.getUserId(), dest.getUserId());
        assertEquals(src.getFirstName(), dest.getFirstName());
        assertEquals(src.getLastName(), dest.getLastName());
        assertEquals(src.getEmail(), dest.getEmail());
        assertEquals(src.getBirthDay(), dest.getBirthDay());
        assertEquals(src.getPhoneNumber(), dest.getPhoneNumber());
        assertEquals(
                ZonedDateTime.parse(
                        "2021-06-30T11:22:33.123456+09:00"),
                dest.getEntryDate());
        assertNotNull(dest.getUpdateDate());
    }

    @Test
    void testList() throws Exception {
        // Do processing login
        MvcResult result
                = ProcessingLogin(mockMvc, AccessMethod.GET,
                "/users/list", "users/showList",
                this.users1);

        CustomUserDetails customUserDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(customUserDetails);
        assertEquals(this.users1.getUserId(), customUserDetails.getUsers().getUserId());
        assertEquals(this.users1.getUsername(), customUserDetails.getUsers().getUsername());
        assertEquals(this.users1.getPassword(), customUserDetails.getUsers().getPassword());
        assertEquals(this.users1.getRoleName(), customUserDetails.getUsers().getRoleName());
        assertTrue(customUserDetails.getUsers().getEnabled());
        assertTrue(customUserDetails.getUsers().getAccountNonExpired());
        assertTrue(customUserDetails.getUsers().getAccountNonLocked());
        assertTrue(customUserDetails.getUsers().getCredentialsNonExpired());
    }

    @Test
    void testShow() {
        // Do processing login
        MvcResult result
                = ProcessingLogin(
                mockMvc, AccessMethod.GET,
                "/users/1/show", "users/list",
                this.users1);

        CustomUserDetails customUserDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(customUserDetails);
        assertEquals(this.users1.getUserId(), customUserDetails.getUsers().getUserId());
        assertEquals(this.users1.getUsername(), customUserDetails.getUsers().getUsername());
        assertEquals(this.users1.getPassword(), customUserDetails.getUsers().getPassword());
        assertEquals(this.users1.getRoleName(), customUserDetails.getUsers().getRoleName());
        assertTrue(customUserDetails.getUsers().getEnabled());
        assertTrue(customUserDetails.getUsers().getAccountNonExpired());
        assertTrue(customUserDetails.getUsers().getAccountNonLocked());
        assertTrue(customUserDetails.getUsers().getCredentialsNonExpired());
    }

    @Test
    void testCreateForm() {
        // Do processing login
        MvcResult result
                = ProcessingLogin(
                mockMvc, AccessMethod.GET,
                "/users/create", "users/createDetail",
                this.users1);

        CustomUserDetails customUserDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(customUserDetails);
        assertEquals(this.users1.getUserId(), customUserDetails.getUsers().getUserId());
        assertEquals(this.users1.getUsername(), customUserDetails.getUsers().getUsername());
        assertEquals(this.users1.getPassword(), customUserDetails.getUsers().getPassword());
        assertEquals(this.users1.getRoleName(), customUserDetails.getUsers().getRoleName());
        assertTrue(customUserDetails.getUsers().getEnabled());
        assertTrue(customUserDetails.getUsers().getAccountNonExpired());
        assertTrue(customUserDetails.getUsers().getAccountNonLocked());
        assertTrue(customUserDetails.getUsers().getCredentialsNonExpired());
    }

    @Test
    void testEditFormUserIdIs1() {
        Long userId = 1L;
        UsersForm usersForm = new UsersForm();

        doReturn(this.users1).when(usersDetailRepository).findByUserId(userId);

        // Do processing login
        MvcResult result
                = ProcessingLoginUsersEditForm(
                mockMvc, AccessMethod.POST,
                "/users/1/edit", "users/editDetail",
                userId, this.users1, usersForm);

        CustomUserDetails userDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(userDetails);
        assertEquals(this.users1.getUserId(), userDetails.getUsers().getUserId());
        assertEquals(this.users1.getUsername(), userDetails.getUsers().getUsername());
        assertEquals(this.users1.getPassword(), userDetails.getUsers().getPassword());
        assertEquals(this.users1.getRoleName(), userDetails.getUsers().getRoleName());
        assertTrue(userDetails.getUsers().getEnabled());
        assertTrue(userDetails.getUsers().getAccountNonExpired());
        assertTrue(userDetails.getUsers().getAccountNonLocked());
        assertTrue(userDetails.getUsers().getCredentialsNonExpired());

        UsersForm usersFormResult
                = autoCast(getObj(result, "usersForm"));
        assertNotNull(usersFormResult);
        assertEquals(this.users1.getUserId(), usersFormResult.getUserId());
        assertEquals(this.users1.getUsername(), usersFormResult.getUsername());
        assertEquals(this.users1.getPassword(), usersFormResult.getPassword());
        assertEquals(this.users1.getRoleName(), usersFormResult.getRoleName());
        assertTrue(usersFormResult.getEnabled());
        assertTrue(usersFormResult.getAccountNonExpired());
        assertTrue(usersFormResult.getAccountNonLocked());
        assertTrue(usersFormResult.getCredentialsNonExpired());

        List<RoleName> roleNames
                = autoCast(getObj(result, "roleNames"));
        assertNotNull(roleNames);
        assertEquals(2, roleNames.size());
        assertEquals(RoleName.ADMIN, roleNames.get(0));
        assertEquals(RoleName.USER, roleNames.get(1));
    }

    @Test
    void testEditFormUserIdIs2() {
        Long userId = 2L;
        UsersForm usersForm = new UsersForm();

        doReturn(this.users2).when(usersDetailRepository).findByUserId(userId);

        // Do processing login
        MvcResult result
                = ProcessingLoginUsersEditForm(
                mockMvc, AccessMethod.POST,
                "/users/2/edit", "users/editDetail",
                userId, this.users1, usersForm);

        CustomUserDetails userDetails
                = autoCast(getObj(result, "userDetails"));

        assertNotNull(userDetails);
        assertEquals(this.users1.getUserId(), userDetails.getUsers().getUserId());
        assertEquals(this.users1.getUsername(), userDetails.getUsers().getUsername());
        assertEquals(this.users1.getPassword(), userDetails.getUsers().getPassword());
        assertEquals(this.users1.getRoleName(), userDetails.getUsers().getRoleName());
        assertTrue(userDetails.getUsers().getEnabled());
        assertTrue(userDetails.getUsers().getAccountNonExpired());
        assertTrue(userDetails.getUsers().getAccountNonLocked());
        assertTrue(userDetails.getUsers().getCredentialsNonExpired());

        UsersForm usersFormResult
                = autoCast(getObj(result, "usersForm"));
        assertNotNull(usersFormResult);
        assertEquals(this.users2.getUserId(), usersFormResult.getUserId());
        assertEquals(this.users2.getUsername(), usersFormResult.getUsername());
        assertEquals(this.users2.getPassword(), usersFormResult.getPassword());
        assertEquals(this.users2.getRoleName(), usersFormResult.getRoleName());
        assertTrue(usersFormResult.getEnabled());
        assertTrue(usersFormResult.getAccountNonExpired());
        assertTrue(usersFormResult.getAccountNonLocked());
        assertTrue(usersFormResult.getCredentialsNonExpired());

        List<RoleName> roleNames
                = autoCast(getObj(result, "roleNames"));
        assertNotNull(roleNames);
        assertEquals(2, roleNames.size());
        assertEquals(RoleName.ADMIN, roleNames.get(0));
        assertEquals(RoleName.USER, roleNames.get(1));
    }

    @Test
    void testCreateConfirmOk() {
        UsersForm usersForm = new UsersForm();
        UsersExtForm usersExtForm = new UsersExtForm();

        this.password3
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");

        usersForm.setUserId(3L);
        usersForm.setUsername("juiceC");
        usersForm.setPassword(password3);
        usersForm.setRoleName(RoleName.ADMIN);
        usersForm.setEnabled(true);
        usersForm.setAccountNonExpired(true);
        usersForm.setAccountNonLocked(true);
        usersForm.setCredentialsNonExpired(true);

        Users users3 = new Users();
        usersController.setUsers(usersForm, users3);

        try {
            doReturn(users3).when(usersDetailRepository).saveAndFlush(any());
        } catch (Exception e) {
            e.printStackTrace();
        }

        usersExtForm.setUserId(users3.getUserId());
        usersExtForm.setFirstName("Juice");
        usersExtForm.setLastName("C");
        usersExtForm.setEmail("juiceC@example.com");
        usersExtForm.setBirthDay(LocalDate.parse("2021-06-30"));
        usersExtForm.setPhoneNumber("0A0-1234-5678");

        UsersExt usersExt = new UsersExt();
        usersController.setUsersExt(usersExtForm, usersExt);
        try {
            doReturn(usersExt).when(usersExtRepository).saveAndFlush(any());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Do processing login
        MvcResult result
                = ProcessingLoginUsersCreateConfirmOk(
                mockMvc, AccessMethod.POST,
                "/users/createConfirm", "/users/list",
                usersForm, usersExtForm, this.users1);
    }

    @Test
    void testCreateConfirmUniqueConstraintError() {
        UsersForm usersForm = new UsersForm();
        UsersExtForm usersExtForm = new UsersExtForm();

        this.password3
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");

        usersForm.setUserId(3L);
        usersForm.setUsername("juiceA");
        usersForm.setPassword(password3);
        usersForm.setRoleName(RoleName.ADMIN);
        usersForm.setEnabled(true);
        usersForm.setAccountNonExpired(true);
        usersForm.setAccountNonLocked(true);
        usersForm.setCredentialsNonExpired(true);

        ProcessingLoginUsersCreateConfirmUniqueConstraintError(
                mockMvc, AccessMethod.POST,
                "/users/createConfirm", "users/createDetail",
                usersForm, usersExtForm, this.users1);
    }

    @Test
    void testCreateConfirmEmptyOfRequireFieldsError() {
        UsersForm usersForm = new UsersForm();
        UsersExtForm usersExtForm = new UsersExtForm();

        this.password3
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");

        usersForm.setUserId(3L);
        usersForm.setUsername(null);
        usersForm.setPassword(null);
        usersForm.setRoleName((RoleName) null);
        usersForm.setEnabled(true);
        usersForm.setAccountNonExpired(true);
        usersForm.setAccountNonLocked(true);
        usersForm.setCredentialsNonExpired(true);

        ProcessingLoginUsersCreateConfirmUniqueConstraintError(
                mockMvc, AccessMethod.POST,
                "/users/createConfirm", "users/createDetail",
                usersForm, usersExtForm, this.users1);
    }

    @Test
    void testEditConfirmOk() {
        UsersForm usersForm = new UsersForm();
        UsersExtForm usersExtForm = new UsersExtForm();

        this.password3
                = "{bcrypt}$2a$10$yVqiUjrYj5jPMMZ0/M2xh.J6PZqiONu4QT3oB4ZNuF/";

        usersForm.setUserId(2L);
        usersForm.setUsername("juiceB");
        usersForm.setPassword(password3);
        usersForm.setRoleName(RoleName.USER);
        usersForm.setEnabled(true);
        usersForm.setAccountNonExpired(true);
        usersForm.setAccountNonLocked(true);
        usersForm.setCredentialsNonExpired(true);

        Users users3 = new Users();
        usersController.setUsers(usersForm, users3);

        doReturn(users3).when(usersDetailRepository).findByUserId(any());

        try {
            doReturn(users3).when(usersDetailRepository).saveAndFlush(any());
        } catch (Exception e) {
            e.printStackTrace();
        }

        usersExtForm.setUserId(users3.getUserId());
        usersExtForm.setFirstName("Foo2");
        usersExtForm.setLastName("Bar2");
        usersExtForm.setEmail("juiceB@example.com");
        usersExtForm.setBirthDay(LocalDate.parse("1970-01-01"));
        usersExtForm.setPhoneNumber("00011112222");

        UsersExt usersExt = new UsersExt();
        usersController.setUsersExt(usersExtForm, usersExt);

        doReturn(usersExt).when(usersExtRepository).findByUserExtId(users3.getUserId());

        try {
            doReturn(usersExt).when(usersExtRepository).saveAndFlush(any());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Do processing login
        MvcResult result
                = ProcessingLoginUsersEditConfirmOk(
                mockMvc, AccessMethod.POST,
                "/users/2/editConfirm", "/users/list",
                usersForm, usersExtForm, this.users1);
    }

    @Test
    void testEditConfirmUniqueConstraintError() {
        UsersForm usersForm = new UsersForm();
        UsersExtForm usersExtForm = new UsersExtForm();

        this.password3
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");

        usersForm.setUserId(2L);
        usersForm.setUsername("juiceA");
        usersForm.setPassword(password3);
        usersForm.setRoleName(RoleName.ADMIN);
        usersForm.setEnabled(true);
        usersForm.setAccountNonExpired(true);
        usersForm.setAccountNonLocked(true);
        usersForm.setCredentialsNonExpired(true);

        ProcessingLoginUsersCreateConfirmUniqueConstraintError(
                mockMvc, AccessMethod.POST,
                "/users/2/editConfirm", "users/editDetail",
                usersForm, usersExtForm, this.users1);
    }

    @Test
    void testEditConfirmEmptyOfRequireFieldsError() {
        UsersForm usersForm = new UsersForm();
        UsersExtForm usersExtForm = new UsersExtForm();

        this.password3
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");

        usersForm.setUserId(2L);
        usersForm.setUsername(null);
        usersForm.setPassword(null);
        usersForm.setRoleName((RoleName) null);
        usersForm.setEnabled(true);
        usersForm.setAccountNonExpired(true);
        usersForm.setAccountNonLocked(true);
        usersForm.setCredentialsNonExpired(true);

        ProcessingLoginUsersCreateConfirmUniqueConstraintError(
                mockMvc, AccessMethod.POST,
                "/users/2/editConfirm", "users/editDetail",
                usersForm, usersExtForm, this.users1);
    }
}