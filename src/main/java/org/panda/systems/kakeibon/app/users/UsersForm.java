package org.panda.systems.kakeibon.app.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.panda.systems.kakeibon.domain.model.users.RoleName;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
@Table(name = "users")
@SecondaryTable(name = "users_ext",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class UsersForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

//    private final CustomUserDetailsService customUserDetailsService;

    @NotEmpty(message = "ユーザ名は必須です")
    @Column(name = "username")
    private String username;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "user_id", table = "users_ext",
            referencedColumnName = "user_id",
            insertable = false, updatable = false)
    @PrimaryKeyJoinColumn
    @Column(name = "user_id")
    private UsersExtForm usersExtForm;

    @NotEmpty(message = "パスワードは必須です")
    @Column(name = "password")
    private String password;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "accountNonExpired")
    private Boolean accountNonExpired;

    @Column(name = "accountNonLocked")
    private Boolean accountNonLocked;

    @Column(name = "credentialsNonExpired")
    private Boolean credentialsNonExpired;

    // Default constructor
    public UsersForm() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UsersExtForm getUsersExtForm() {
        return usersExtForm;
    }

    public void setUsersExtForm(UsersExtForm usersExtForm) {
        this.usersExtForm = usersExtForm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public UsersForm setUserToForm(
            Users users) {

        this.setUserId(users.getUserId());
        this.setUsername(users.getUsername());
        this.setPassword(users.getPassword());
        this.setRoleName(users.getRoleName());
        this.setEnabled(users.getEnabled());
        this.setAccountNonExpired(users.getAccountNonExpired());
        this.setAccountNonLocked(users.getAccountNonLocked());
        this.setCredentialsNonExpired(users.getCredentialsNonExpired());

        return this;
    }
}
