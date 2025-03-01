package org.panda.systems.kakeibon.utils.common;

import org.panda.systems.kakeibon.app.users.UsersExtForm;
import org.panda.systems.kakeibon.app.users.UsersForm;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetails;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoginProcess {

    public static MvcResult ProcessingLoginUsersEditConfirmOk(
            MockMvc mockMvc,
            AccessMethod method,
            String accessUrl,
            String expectUrl,
            UsersForm usersForm,
            UsersExtForm usersExtForm,
            Users users) {

        UserDetails userDetails
                = new CustomUserDetails(users);

        if (method == AccessMethod.GET) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.get(accessUrl);
        } else if (method == AccessMethod.POST) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.post(accessUrl);
        }

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(accessUrl);

        return doMockMvcPerformUsersEditConfirmOk(
                mockMvc, userDetails, usersForm, usersExtForm, request, accessUrl, expectUrl);
    }

    public static MvcResult ProcessingLoginUsersCreateConfirmUniqueConstraintError(
            MockMvc mockMvc,
            AccessMethod method,
            String accessUrl,
            String expectUrl,
            UsersForm usersForm,
            UsersExtForm usersExtForm,
            Users users) {

        UserDetails userDetails
                = new CustomUserDetails(users);

        if (method == AccessMethod.GET) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.get(accessUrl);
        } else if (method == AccessMethod.POST) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.post(accessUrl);
        }

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(accessUrl);

        return doMockMvcPerformUsersCreateConfirmUniqueConstraintError(
                mockMvc, userDetails, usersForm, usersExtForm, request, accessUrl, expectUrl);
    }

    public static MvcResult ProcessingLoginUsersCreateConfirmOk(
            MockMvc mockMvc,
            AccessMethod method,
            String accessUrl,
            String expectUrl,
            UsersForm usersForm,
            UsersExtForm usersExtForm,
            Users users) {

        UserDetails userDetails
                = new CustomUserDetails(users);

        if (method == AccessMethod.GET) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.get(accessUrl);
        } else if (method == AccessMethod.POST) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.post(accessUrl);
        }

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(accessUrl);

        return doMockMvcPerformUsersCreateConfirmOk(
                mockMvc, userDetails, usersForm, usersExtForm, request, accessUrl, expectUrl);
    }

    public static MvcResult ProcessingLoginUsersEditForm(
            MockMvc mockMvc,
            AccessMethod method,
            String accessUrl,
            String expectUrl,
            Long userId,
            Users users,
            UsersForm usersForm) {

        UserDetails userDetails
                = new CustomUserDetails(users);

        if (method == AccessMethod.GET) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.get(accessUrl);
        } else if (method == AccessMethod.POST) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.post(accessUrl);
        }

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(accessUrl);

        return doMockMvcPerformUsersEditForm(
                mockMvc, userId, userDetails, usersForm, request, accessUrl, expectUrl);
    }

    public static MvcResult ProcessingLogin(
            MockMvc mockMvc,
            AccessMethod method,
            String accessUrl,
            String expectUrl,
            Users users) {

        UserDetails userDetails
                = new CustomUserDetails(users);

        if (method == AccessMethod.GET) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.get(accessUrl);
        } else if (method == AccessMethod.POST) {
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.post(accessUrl);
        }

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(accessUrl);

        return doMockMvcPerform(
                mockMvc, userDetails, request, accessUrl, expectUrl);
    }

    private static MvcResult doMockMvcPerformUsersEditConfirmOk(
            MockMvc mockMvc, UserDetails userDetails, UsersForm usersForm, UsersExtForm usersExtForm,
            MockHttpServletRequestBuilder request,
            String accessUrl, String expectUrl) {

        MvcResult result;
        try {
            result = mockMvc.perform(request
                            .param("username", userDetails.getUsername())
                            .param("password", userDetails.getPassword())
                            .accept(MediaType.TEXT_HTML)
                            .flashAttr("usersForm", usersForm)
                            .flashAttr("usersExtForm", usersExtForm)
                            .flashAttr("userDetails", userDetails))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(expectUrl))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static MvcResult doMockMvcPerformUsersCreateConfirmUniqueConstraintError(
            MockMvc mockMvc, UserDetails userDetails, UsersForm usersForm, UsersExtForm usersExtForm,
            MockHttpServletRequestBuilder request,
            String accessUrl, String expectUrl) {

        MvcResult result;
        try {
            result = mockMvc.perform(request
                            .param("username", userDetails.getUsername())
                            .param("password", userDetails.getPassword())
                            .accept(MediaType.TEXT_HTML)
                            .flashAttr("usersForm", usersForm)
                            .flashAttr("usersExtForm", usersExtForm)
                            .flashAttr("userDetails", userDetails))
                    .andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andExpect(status().isBadRequest())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static MvcResult doMockMvcPerformUsersCreateConfirmOk(
            MockMvc mockMvc, UserDetails userDetails, UsersForm usersForm, UsersExtForm usersExtForm,
            MockHttpServletRequestBuilder request,
            String accessUrl, String expectUrl) {

        MvcResult result;
        try {
            result = mockMvc.perform(request
                            .param("username", userDetails.getUsername())
                            .param("password", userDetails.getPassword())
                            .accept(MediaType.TEXT_HTML)
                            .flashAttr("usersForm", usersForm)
                            .flashAttr("usersExtForm", usersExtForm)
                            .flashAttr("userDetails", userDetails))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(expectUrl))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static MvcResult doMockMvcPerformUsersEditForm(
            MockMvc mockMvc, Long userId, UserDetails userDetails, UsersForm usersForm,
            MockHttpServletRequestBuilder request,
            String accessUrl, String expectUrl) {

        MvcResult result;
        try {
            result = mockMvc.perform(request
                            .param("username", userDetails.getUsername())
                            .param("password", userDetails.getPassword())
                            .accept(MediaType.TEXT_HTML)
                            .param("Long", "userId")
                            .flashAttr("userDetails", userDetails)
                            .flashAttr("usersForm", usersForm))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl(expectUrl))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static MvcResult doMockMvcPerform(
            MockMvc mockMvc, UserDetails userDetails, MockHttpServletRequestBuilder request,
            String accessUrl, String expectUrl) {

        MvcResult result;
        try {
            result = mockMvc.perform(request
                            .param("username", userDetails.getUsername())
                            .param("password", userDetails.getPassword())
                            .accept(MediaType.TEXT_HTML)
                            .flashAttr("userDetails", userDetails))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl(expectUrl))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
