package org.panda.systems.kakeibon.app.users;

import org.junit.jupiter.api.Test;
import org.panda.systems.kakeibon.domain.model.users.RoleName;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.panda.systems.kakeibon.domain.service.users.UserExtsService;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class UsersFormTest {

    @MockitoBean
    UserExtsService userExtsService;
    @MockitoBean
    CustomUserDetailsService customUserDetailsService;

    UsersForm usersForm;

    public UsersFormTest() {
//        usersForm = new UsersForm(customUserDetailsService);
        usersForm = new UsersForm();
    }

    @Test
    void testSetAndGetUsername() {
        usersForm.setUsername("username");
        assertEquals("username", usersForm.getUsername());
    }

    @Test
    void testSetAndGetUserId() {
        usersForm.setUserId(1L);
        assertEquals(1L, usersForm.getUserId());
    }

    @Test
    void testSetAndGetUsersExtForm() {
        UsersExtForm usersExtForm = new UsersExtForm(this.userExtsService);

        usersForm.setUsersExtForm(usersExtForm);
        assertNotNull(usersForm.getUsersExtForm());
        assertNotNull(usersForm.getUsersExtForm().getUserExtsService());
    }

    @Test
    void testSetAndGetPassword() {
        usersForm.setPassword("password");
        assertEquals("password", usersForm.getPassword());
    }

    @Test
    void testSetAndGetRoleName() {
        // 1st case
        usersForm.setRoleName(null);
        assertNull(usersForm.getRoleName());

        // 2nd case
        usersForm.setRoleName(RoleName.USER);
        assertEquals(RoleName.USER, usersForm.getRoleName());

        // 3rd case
        usersForm.setRoleName(RoleName.ADMIN);
        assertEquals(RoleName.ADMIN, usersForm.getRoleName());
    }

    @Test
    void testSetAndGetEnabled() {
        // 1st case
        usersForm.setEnabled(true);
        assertTrue(usersForm.getEnabled());

        // 2nd case
        usersForm.setEnabled(false);
        assertFalse(usersForm.getEnabled());
    }

    @Test
    void testSetAndGetAccountNonExpired() {
        // 1st case
        usersForm.setAccountNonExpired(true);
        assertTrue(usersForm.getAccountNonExpired());

        // 2nd case
        usersForm.setAccountNonExpired(false);
        assertFalse(usersForm.getAccountNonExpired());
    }

    @Test
    void testSetAndGetAccountNonLocked() {
        // 1st case
        usersForm.setAccountNonLocked(true);
        assertTrue(usersForm.getAccountNonLocked());

        // 2nd case
        usersForm.setAccountNonLocked(false);
        assertFalse(usersForm.getAccountNonLocked());
    }

    @Test
    void testSetAndGetCredentialsNonExpired() {
        // 1st case
        usersForm.setCredentialsNonExpired(true);
        assertTrue(usersForm.getCredentialsNonExpired());

        // 2nd case
        usersForm.setCredentialsNonExpired(false);
        assertFalse(usersForm.getCredentialsNonExpired());
    }

    @Test
    void setUserToForm() {
        // 1st case
        Users users = new Users();
        users.setUsername("username");
        users.setPassword("password");
        users.setRoleName(RoleName.USER);
        users.setEnabled(true);
        users.setAccountNonExpired(true);
        users.setAccountNonLocked(true);
        users.setCredentialsNonExpired(true);

//        UsersForm usersForm = new UsersForm(customUserDetailsService);
        UsersForm usersForm = new UsersForm();
        usersForm.setUserToForm(users);

        assertEquals("username", usersForm.getUsername());
        assertEquals("password", usersForm.getPassword());
        assertEquals(RoleName.USER, usersForm.getRoleName());
        assertTrue(usersForm.getEnabled());
        assertTrue(usersForm.getAccountNonExpired());
        assertTrue(usersForm.getAccountNonLocked());
        assertTrue(usersForm.getCredentialsNonExpired());

        // 2nd case
        users = new Users();
        users.setUsername("username");
        users.setPassword("password");
        users.setRoleName(RoleName.ADMIN);
        users.setEnabled(false);
        users.setAccountNonExpired(false);
        users.setAccountNonLocked(false);
        users.setCredentialsNonExpired(false);

//        usersForm = new UsersForm(customUserDetailsService);
        usersForm = new UsersForm();
        usersForm.setUserToForm(users);

        assertEquals("username", usersForm.getUsername());
        assertEquals("password", usersForm.getPassword());
        assertEquals(RoleName.ADMIN, usersForm.getRoleName());
        assertFalse(usersForm.getEnabled());
        assertFalse(usersForm.getAccountNonExpired());
        assertFalse(usersForm.getAccountNonLocked());
        assertFalse(usersForm.getCredentialsNonExpired());
    }
}