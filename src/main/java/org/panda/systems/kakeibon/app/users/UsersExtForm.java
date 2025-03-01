package org.panda.systems.kakeibon.app.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import org.panda.systems.kakeibon.domain.model.users.UsersExt;
import org.panda.systems.kakeibon.domain.service.users.UserExtsService;
import org.panda.systems.kakeibon.utils.common.Converter;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Component
@Table(name = "users")
public class UsersExtForm implements Serializable, Converter {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UserExtsService userExtsService;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "メールアドレスは必須です")
    @Column
    private String email;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @NotEmpty(message = "電話番号は必須です")
    @Column(name = "phone_number")
    private String phoneNumber;

    @PastOrPresent
    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    // Default constructor
    public UsersExtForm() {

        this.userExtsService = null;
    }

    public UsersExtForm(UserExtsService userExtsService) {

        this.userExtsService = userExtsService;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public UserExtsService getUserExtsService() {
        return userExtsService;
    }

    public UsersExtForm setUserExtToForm(
            UsersExt usersExt) {

        this.setUserId(usersExt.getUserId());
        this.setLastName(usersExt.getLastName());
        this.setFirstName(usersExt.getFirstName());
        this.setEmail(usersExt.getEmail());
        this.setBirthDay(usersExt.getBirthDay());
        this.setPhoneNumber(usersExt.getPhoneNumber());
        this.setEntryDate(usersExt.getEntryDate());
        this.setUpdateDate(usersExt.getUpdateDate());

        return this;
    }
}
