package org.panda.systems.kakeibon.app.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

import static org.junit.jupiter.api.Assertions.*;
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
    Users users1, users2, users3;
    UsersForm usersForm2, usersForm3;
    UsersExt usersExtEntryDateIsNull;
    UsersExt usersExtEntryDateIsNotNull;
    UsersExt usersExt2, usersExt3;
    UsersExtForm usersExtForm2, usersExtForm3;

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
                = "{bcrypt}$2a$10$mekheplkOdsT5v5VLdF.heNd60EEXT3JyVU9qpq.DscBxmMkdutOa";
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
                = "{bcrypt}$2a$10$yVqiUjrYj5jPMMZ0/M2xh.J6PZqiONu4QT3oB4ZNuF/z1RQX.qLE2";
        this.users2 = new Users();
        this.users2.setUserId(2L);
        this.users2.setUsername("juiceB");
        this.users2.setPassword(this.password2);
        this.users2.setRoleName(RoleName.USER);
        this.users2.setEnabled(true);
        this.users2.setAccountNonExpired(true);
        this.users2.setAccountNonLocked(true);
        this.users2.setCredentialsNonExpired(true);
        this.usersForm2 = new UsersForm(users2);

        this.password3
                = webSecurityConfig
                .getPasswordEncoder()
                .encode("test");
        this.users3 = new Users();
        this.users3.setUserId(3L);
        this.users3.setUsername("juiceC");
        this.users3.setPassword(this.password3);
        this.users3.setRoleName(RoleName.USER);
        this.users3.setEnabled(true);
        this.users3.setAccountNonExpired(true);
        this.users3.setAccountNonLocked(true);
        this.users3.setCredentialsNonExpired(true);
        this.usersForm3 = new UsersForm(users3);

        this.usersExtEntryDateIsNull = new UsersExt();
        this.usersExtEntryDateIsNull.setUserId(1L);
        this.usersExtEntryDateIsNull.setFirstName("Juice");
        this.usersExtEntryDateIsNull.setLastName("A");
        this.usersExtEntryDateIsNull.setEmail("juiceA@example.com");
        this.usersExtEntryDateIsNull.setBirthDay(LocalDate.parse("2021-06-30"));
        this.usersExtEntryDateIsNull.setPhoneNumber("0A0-1234-5678");
        this.usersExtEntryDateIsNull.setEntryDate(null);
        this.usersExtEntryDateIsNull.setUpdateDate(null);

        this.usersExtEntryDateIsNotNull = new UsersExt();
        this.usersExtEntryDateIsNotNull.setUserId(1L);
        this.usersExtEntryDateIsNotNull.setFirstName("Juice");
        this.usersExtEntryDateIsNotNull.setLastName("A");
        this.usersExtEntryDateIsNotNull.setEmail("juiceA@example.com");
        this.usersExtEntryDateIsNotNull.setBirthDay(LocalDate.parse("2021-06-30"));
        this.usersExtEntryDateIsNotNull.setPhoneNumber("0A0-1234-5678");
        this.usersExtEntryDateIsNotNull.setEntryDate(
                ZonedDateTime.parse("2021-06-30T11:22:33.123456+09:00")
        );
        this.usersExtEntryDateIsNotNull.setUpdateDate(null);

        this.usersExt2 = new UsersExt();
        this.usersExt2.setUserId(2L);
        this.usersExt2.setFirstName("Foo2");
        this.usersExt2.setLastName("Bar2");
        this.usersExt2.setEmail("juiceB@example.com");
        this.usersExt2.setBirthDay(LocalDate.parse("1970-01-01"));
        this.usersExt2.setPhoneNumber("00011112222");
        this.usersExtForm2 = new UsersExtForm(this.usersExt2);

        this.usersExt3 = new UsersExt();
        this.usersExt3.setUserId(3L);
        this.usersExt3.setFirstName("Juice");
        this.usersExt3.setLastName("C");
        this.usersExt3.setEmail("juiceC@example.com");
        this.usersExt3.setBirthDay(LocalDate.parse("2021-06-30"));
        this.usersExt3.setPhoneNumber("0A0-1234-5678");
        this.usersExt3.setEntryDate(
                ZonedDateTime.parse("2021-06-30T11:22:33.123456+09:00")
        );
        this.usersExt3.setUpdateDate(null);
        this.usersExtForm3 = new UsersExtForm(this.usersExt3);
    }

    @Nested
    @DisplayName("Create UsersController instance test")
    class CreateUsersControllerInstanceTest {
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
    }

    @Nested
    @DisplayName("Set datas test")
    class SetDatasTest {
        @Test
        void testSetUsers() {
            UsersForm src = new UsersForm(users3);
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
            UsersExtForm src
                    = (new UsersExtForm())
                    .setUserExtToForm(usersExtEntryDateIsNull);
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
            UsersExtForm src
                    = (new UsersExtForm())
                    .setUserExtToForm(usersExtEntryDateIsNotNull);
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
    }

    @Nested
    @DisplayName("List up all users feature test")
    class UsersManagementsTest {
        @Test
        void testList() throws Exception {
            // Do processing login
            MvcResult result
                    = ProcessingLogin(mockMvc, AccessMethod.GET,
                    "/users/list", "users/showList",
                    users1);

            CustomUserDetails userDetails
                    = autoCast(getObj(result, "userDetails"));

            assertNotNull(userDetails);
            assertEquals(users1, userDetails.getUsers());
        }

        @Test
        void testShow() {
            // Do processing login
            MvcResult result
                    = ProcessingLogin(
                    mockMvc, AccessMethod.GET,
                    "/users/1/show", "users/list",
                    users1);

            CustomUserDetails userDetails
                    = autoCast(getObj(result, "userDetails"));

            assertNotNull(userDetails);
            assertEquals(users1, userDetails.getUsers());
        }

        @Test
        void testCreateForm() {
            // Do processing login
            MvcResult result
                    = ProcessingLogin(
                    mockMvc, AccessMethod.GET,
                    "/users/create", "users/createDetail",
                    users1);

            CustomUserDetails userDetails
                    = autoCast(getObj(result, "userDetails"));

            assertNotNull(userDetails);
            assertEquals(users1, userDetails.getUsers());
        }

        @Test
        void testEditFormUserIdIs1() {
            Long userId = 1L;
            UsersForm usersForm = new UsersForm(users1);

            doReturn(users1).when(usersDetailRepository).findByUserId(userId);

            // Do processing login
            MvcResult result
                    = ProcessingLoginUsersEditForm(
                    mockMvc, AccessMethod.POST,
                    "/users/1/edit", "users/editDetail",
                    userId, users1, usersForm);

            CustomUserDetails userDetails
                    = autoCast(getObj(result, "userDetails"));

            assertNotNull(userDetails);
            assertEquals(users1, userDetails.getUsers());

            UsersForm usersFormResult
                    = autoCast(getObj(result, "usersForm"));
            assertNotNull(usersFormResult);
            assertEquals(users1, usersFormResult.getUsersFromForm());

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
            UsersForm usersForm = new UsersForm(users2);

            doReturn(users2).when(usersDetailRepository).findByUserId(userId);

            // Do processing login
            MvcResult result
                    = ProcessingLoginUsersEditForm(
                    mockMvc, AccessMethod.POST,
                    "/users/2/edit", "users/editDetail",
                    userId, users1, usersForm);

            CustomUserDetails userDetails
                    = autoCast(getObj(result, "userDetails"));

            assertNotNull(userDetails);
            assertEquals(users1, userDetails.getUsers());

            UsersForm usersFormResult
                    = autoCast(getObj(result, "usersForm"));
            assertNotNull(usersFormResult);
            assertEquals(users2, usersFormResult.getUsersFromForm());

            List<RoleName> roleNames
                    = autoCast(getObj(result, "roleNames"));
            assertNotNull(roleNames);
            assertEquals(2, roleNames.size());
            assertEquals(RoleName.ADMIN, roleNames.get(0));
            assertEquals(RoleName.USER, roleNames.get(1));
        }

        @Test
        void testCreateConfirmOk() {
            UsersForm usersForm = new UsersForm(users3);
            UsersExtForm usersExtForm = new UsersExtForm();

            Users users3OfInnerClass = new Users();
            usersController.setUsers(usersForm3, users3OfInnerClass);

            try {
                doReturn(users3OfInnerClass).when(usersDetailRepository).saveAndFlush(any());
            } catch (Exception e) {
                e.printStackTrace();
            }

            UsersExt usersExt = new UsersExt();
            usersController.setUsersExt(usersExtForm3, usersExt);
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
                    usersForm, usersExtForm, users1);
        }

        @Test
        @DisplayName("Unique constraint error of username test")
        void testCreateConfirmUniqueConstraintError() {
            password3
                    = webSecurityConfig
                    .getPasswordEncoder()
                    .encode("test");

            usersForm3.setUsername("juiceA");

            ProcessingLoginUsersCreateConfirmUniqueConstraintError(
                    mockMvc, AccessMethod.POST,
                    "/users/createConfirm", "users/createDetail",
                    usersForm3, usersExtForm3, users1);
        }

        @Test
        void testCreateConfirmEmptyOfRequireFieldsError() {
            UsersExtForm usersExtForm = new UsersExtForm();

            usersForm3.setUsername(null);
            usersForm3.setPassword(null);

            ProcessingLoginUsersCreateConfirmEmptyOfRequireFieldsError(
                    mockMvc, AccessMethod.POST,
                    "/users/createConfirm", "users/createDetail",
                    usersForm3, usersExtForm, users1);
        }

        @Test
        void testEditConfirmOk() {
            UsersForm usersForm = new UsersForm(users2);
            UsersExtForm usersExtForm = new UsersExtForm();

            usersForm.setUsername("juiceC");
            usersForm.setPassword("test");
            usersForm.setRoleName(RoleName.ADMIN);
            usersForm.setEnabled(false);
            usersForm.setAccountNonExpired(false);
            usersForm.setAccountNonLocked(false);
            usersForm.setCredentialsNonExpired(false);

            Users target = new Users();
            usersController.setUsers(usersForm, target);

            doReturn(target).when(usersDetailRepository).findByUserId(target.getUserId());

            try {
                doReturn(target).when(usersDetailRepository).saveAndFlush(target);
            } catch (Exception e) {
                e.printStackTrace();
            }

            usersExtForm.setUserId(usersForm.getUserId());
            usersExtForm.setFirstName("juice");
            usersExtForm.setLastName("C");
            usersExtForm.setEmail("juiceC@example.com");
            usersExtForm.setBirthDay(LocalDate.parse("1970-06-30"));
            usersExtForm.setPhoneNumber("000-1111-2222");

            UsersExt usersExt = new UsersExt();
            usersController.setUsersExt(usersExtForm, usersExt);

            doReturn(usersExt).when(usersExtRepository).findByUserExtId(target.getUserId());

            try {
                doReturn(usersExt).when(usersExtRepository).saveAndFlush(usersExt);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Do processing login
            MvcResult result
                    = ProcessingLoginUsersEditConfirmOk(
                    mockMvc, AccessMethod.POST,
                    "/users/2/editConfirm", "/users/list",
                    usersForm, usersExtForm, users1);
        }

        @Test
        void testEditConfirmUniqueConstraintError() {
            usersForm2.setUsername("juiceA");
            doReturn(users2).when(usersDetailRepository).findByUserId(usersForm2.getUserId());
            doReturn(true)
                    .when(usersDetailRepository)
                    .existsByUsername(usersForm2.getUsername());

            ProcessingLoginUsersEditConfirmUniqueConstraintError(
                    mockMvc, AccessMethod.POST,
                    "/users/2/editConfirm", "users/editDetail",
                    usersForm2, usersExtForm2, users1);
        }

        @Test
        void testEditConfirmEmptyOfRequireFieldsError() {
            UsersExtForm usersExtForm = new UsersExtForm();

            users2.setUsername(null);
            users2.setPassword(null);
            usersForm2.setUsername(null);
            doReturn(users2).when(usersDetailRepository).findByUserId(users2.getUserId());
            doReturn(usersExt2).when(usersExtRepository).findByUserExtId(usersExt2.getUserId());

            ProcessingLoginUsersEditConfirmEmptyOfRequireFieldsError(
                    mockMvc, AccessMethod.POST,
                    "/users/2/editConfirm", "users/editDetail",
                    usersForm2, usersExtForm, users1);
        }
    }
}