package org.panda.systems.kakeibon.app.users;

import org.panda.systems.kakeibon.WebSecurityConfig;
import org.panda.systems.kakeibon.domain.model.users.RoleName;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.model.users.UsersExt;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetails;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.panda.systems.kakeibon.domain.service.users.UserExtsService;
import org.panda.systems.kakeibon.utils.common.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/users", method = {RequestMethod.GET, RequestMethod.POST})
public class UsersController implements Converter {
    private final CustomUserDetailsService customUserDetailsService;
    private final UserExtsService userExtsService;
    private final WebSecurityConfig webSecurityConfig;

    // Default constructor
    public UsersController() {
        customUserDetailsService = null;
        userExtsService = null;
        webSecurityConfig = null;
    }

    // Constructor Injection
    @Autowired
    public UsersController(
            CustomUserDetailsService customUserDetailsService,
            UserExtsService userExtsService,
            WebSecurityConfig webSecurityConfig
    ) {
        this.customUserDetailsService = customUserDetailsService;
        this.userExtsService = userExtsService;
        this.webSecurityConfig = webSecurityConfig;
    }

    void setUsers(UsersForm form, Users users) {
        users.setUsername(form.getUsername());
        users.setUserId(form.getUserId());
        users.setPassword(form.getPassword());
        users.setRoleName(form.getRoleName());
        users.setEnabled(form.getEnabled());
        users.setAccountNonExpired(form.getAccountNonExpired());
        users.setAccountNonLocked(form.getAccountNonLocked());
        users.setCredentialsNonExpired(form.getCredentialsNonExpired());
    }

    void setUsersExt(UsersExtForm form, UsersExt usersExt) {
        usersExt.setUserId(form.getUserId());
        usersExt.setLastName(form.getLastName());
        usersExt.setFirstName(form.getFirstName());
        usersExt.setEmail(form.getEmail());
        usersExt.setBirthDay(form.getBirthDay());
        usersExt.setPhoneNumber(form.getPhoneNumber());
        if (form.getEntryDate() == null) {
            usersExt.setEntryDate(ZonedDateTime.now());
            usersExt.setUpdateDate(usersExt.getUpdateDate());
        } else {
            usersExt.setEntryDate(form.getEntryDate());
            usersExt.setUpdateDate(ZonedDateTime.now());
        }
    }

    @PostMapping(value = "/list")
    String list(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        List<CustomUserDetails> customUserDetails;
        customUserDetails
                = Objects.requireNonNull(customUserDetailsService)
                .findAllUsersToForm();

        model.addAttribute("principal", userDetails);
        model.addAttribute("customUserDetails", customUserDetails);
        return "users/showList";
    }

    @PostMapping(value = "/{userId}/show")
    String show(@PathVariable("userId") Long userId,
                @AuthenticationPrincipal UserDetails userDetails,
                Model model) {
        Users users = Objects.requireNonNull(customUserDetailsService)
                .findByUserId(userId);

        if (Objects.isNull(users)) {
            return "users/list";
        }

        UsersForm usersForm = new UsersForm(users);

//        usersForm.setUserId(users.getUserId());
//        usersForm.setUsername(users.getUsername());
//        usersForm.setPassword(users.getPassword());
//        usersForm.setRoleName(users.getRoleName());
//        usersForm.setEnabled(users.getEnabled());
//        usersForm.setAccountNonExpired(true);
//        usersForm.setAccountNonLocked(true);
//        usersForm.setCredentialsNonExpired(true);

        UsersExt usersExt = Objects.requireNonNull(userExtsService)
                .findByUserId(userId);
        UsersExtForm usersExtForm = new UsersExtForm(usersExt);
        usersExtForm.setUserId(usersExt.getUserId());
        usersExtForm.setLastName(usersExt.getLastName());
        usersExtForm.setFirstName(usersExt.getFirstName());
        usersExtForm.setEmail(usersExt.getEmail());
        usersExtForm.setBirthDay(usersExt.getBirthDay());
        usersExtForm.setPhoneNumber(usersExt.getPhoneNumber());
        usersExtForm.setEntryDate(
                ZonedDateTime.parse(usersExt.getEntryDate().toString(),
                        DateTimeFormatter.ISO_ZONED_DATE_TIME
                                .withZone(ZoneOffset.ofHours(9))));
        if (Objects.isNull(usersExt.getUpdateDate())) {
            usersExtForm.setUpdateDate(null);
        } else {
            usersExtForm.setUpdateDate(
                    ZonedDateTime.parse(usersExt.getUpdateDate().toString(),
                            DateTimeFormatter.ISO_ZONED_DATE_TIME
                                    .withZone(ZoneOffset.ofHours(9))));
        }

        usersForm.setUsersExtForm(usersExtForm);

        model.addAttribute("principal", userDetails);
        model.addAttribute("usersForm", usersForm);
        return "users/showDetail";
    }

    @PostMapping("/create")
    String createForm(
            @ModelAttribute UsersForm usersForm,
            @ModelAttribute UsersExtForm usersExtForm,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        usersForm.setUsername(null);
        usersForm.setPassword(null);
        usersForm.setRoleName(RoleName.USER);
        usersForm.setEnabled(true);
        usersForm.setAccountNonExpired(true);
        usersForm.setAccountNonLocked(true);
        usersForm.setCredentialsNonExpired(true);

        usersForm.setUsersExtForm(usersExtForm);

        List<RoleName> roleNames = RoleName.getRoleNameList();

        model.addAttribute("principal", userDetails);
        model.addAttribute("usersForm", usersForm);
        model.addAttribute("roleNames", roleNames);
        return "users/createDetail";
    }

    @PostMapping(value = "/{userId}/edit")
    String editForm(@PathVariable("userId") Long userId,
                    @ModelAttribute UsersForm usersForm,
                    @AuthenticationPrincipal UserDetails userDetails,
                    Model model) {

        Users users
                = Objects.requireNonNull(customUserDetailsService)
                .findByUserId(userId);
        usersForm.setUsername(users.getUsername());
        usersForm.setPassword(users.getPassword());
        usersForm.setRoleName(users.getRoleName());
        usersForm.setEnabled(users.getEnabled());
        usersForm.setAccountNonExpired(users.getAccountNonExpired());
        usersForm.setAccountNonLocked(users.getAccountNonLocked());
        usersForm.setCredentialsNonExpired(users.getCredentialsNonExpired());

        UsersExtForm usersExtForm
                = Objects.requireNonNull(userExtsService).findByUserExtToForm(userId);
        usersForm.setUsersExtForm(usersExtForm);

        List<RoleName> roleNames = RoleName.getRoleNameList();

        model.addAttribute("principal", userDetails);
        model.addAttribute("usersForm", usersForm);
        model.addAttribute("roleNames", roleNames);
        return "users/editDetail";
    }

    @SuppressWarnings("CallToPrintStackTrace")
    @PostMapping("/createConfirm")
    String createConfirm(
            @Validated @ModelAttribute UsersForm usersForm,
            BindingResult result,
            @Validated @ModelAttribute UsersExtForm usersExtForm,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        if (Objects.isNull(usersForm.getUsername())) {
            result.rejectValue(
                    "username",
                    "error.username",
                    "ユーザ名は必須です");
            return createForm(
                    usersForm, usersExtForm, userDetails, model);
        }

        if (Objects.isNull(usersForm.getPassword())) {
            result.rejectValue(
                    "password",
                    "error.password",
                    "パスワードは必須です");
            return createForm(
                    usersForm, usersExtForm, userDetails, model);
        }

        if (customUserDetailsService
                .existsByUsername(usersForm.getUsername())) {
            result.rejectValue(
                    "username",
                    "error.username",
                    "このユーザ名は既に登録されています");
            return createForm(
                    usersForm, usersExtForm, userDetails, model);
        }

        if (result.hasErrors()) {
            return createForm(
                    usersForm, usersExtForm, userDetails, model);
        }

        Users users = new Users();
        setUsers(usersForm, users);
        try {
            customUserDetailsService.saveAndFlush(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CustomUserDetails customUserDetails
                = new CustomUserDetails(users);

        UsersExt usersExt = new UsersExt();
        setUsersExt(usersExtForm, usersExt);
        usersExt.setUserId(customUserDetails.getUsers().getUserId());
        UsersExt resultUsersExt
                = Objects.requireNonNull(userExtsService).saveUserExt(usersExt);

        model.addAttribute("principal", userDetails);
        return "redirect:/users/list";
    }

    @PostMapping(value = "{userId}/editConfirm")
    String editConfirm(
            @PathVariable("userId") Long userId,
            @Validated @ModelAttribute UsersForm usersForm,
            @Validated @ModelAttribute UsersExtForm usersExtForm,
            @AuthenticationPrincipal UserDetails userDetails,
            BindingResult result,
            Model model) {

        if (Objects.isNull(usersForm.getUsername())) {
            result.rejectValue(
                    "username",
                    "error.username",
                    "ユーザ名は必須です");
            return editForm(
                    userId, usersForm, userDetails, model);
        }

        if (Objects.isNull(usersForm.getPassword())) {
            result.rejectValue(
                    "password",
                    "error.password",
                    "パスワードは必須です");
            return editForm(
                    userId, usersForm, userDetails, model);
        }

        if (customUserDetailsService
                .existsByUsername(usersForm.getUsername())) {
            result.rejectValue(
                    "username",
                    "error.username",
                    "このユーザ名は既に登録されています");
            return editForm(
                    userId, usersForm, userDetails, model);
        }

        if (result.hasErrors()) {
            return editForm(userId, usersForm, userDetails, model);
        }

        Users users = Objects.requireNonNull(customUserDetailsService).findByUserId(userId);
        usersForm = new UsersForm(users);

        try {
            customUserDetailsService.saveAndFlush(usersForm.getUsersFromForm());
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        UsersExt usersExt = Objects.requireNonNull(userExtsService).findByUserId(userId);
        setUsersExt(usersExtForm, usersExt);
        userExtsService.saveUserExt(usersExt);

        model.addAttribute("principal", userDetails);
        return "redirect:/users/list";
    }
}
