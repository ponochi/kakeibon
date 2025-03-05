package org.panda.systems.kakeibon.app.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.model.users.UsersExt;
import org.panda.systems.kakeibon.domain.service.users.UserExtsService;
import org.panda.systems.kakeibon.utils.common.Converter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersExtFormTest implements Converter {

    @MockitoBean
    UserExtsService userExtsService;

    UsersExt usersExt;
    UsersExtForm usersExtForm;

    @BeforeEach
    void setUp() {
        this.usersExt = new UsersExt();
        this.usersExt.setUserId(1L);
        this.usersExt.setLastName("Foo2");
        this.usersExt.setFirstName("Bar2");
        this.usersExt.setEmail("juiceB@example.com");
        this.usersExt.setBirthDay(
                LocalDate.parse("1970-06-30"));
        this.usersExt.setPhoneNumber("00011112222");
        this.usersExt.setEntryDate(
                ZonedDateTime.parse(
                        "2024-07-19T12:30:45+09:00"));
        this.usersExt.setUpdateDate(null);

        usersExtForm = new UsersExtForm(this.usersExt);
    }

    @Test
    void testSetAndGetUserId() {
        usersExtForm.setUserId(1L);
        assertEquals(1L, usersExtForm.getUserId());
    }

    @Test
    void testSetAndGetLastName() {
        usersExtForm.setLastName("lastName");
        assertEquals("lastName", usersExtForm.getLastName());
    }

    @Test
    void testSetAndGetFirstName() {
        usersExtForm.setFirstName("firstName");
        assertEquals("firstName", usersExtForm.getFirstName());
    }

    @Test
    void testSetAndGetEmail() {
        usersExtForm.setEmail("email");
        assertEquals("email", usersExtForm.getEmail());
    }

    @Test
    void testSetAndGetBirthDay() {
        usersExtForm.setBirthDay(
                LocalDate.parse(
                        "2025-02-09",
                        DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals(
                LocalDate.parse(
                        "2025-02-09",
                        DateTimeFormatter.ISO_LOCAL_DATE),
                usersExtForm.getBirthDay());
    }

    @Test
    void testSetAndGetPhoneNumber() {
        usersExtForm.setPhoneNumber("phoneNumber");
        assertEquals("phoneNumber", usersExtForm.getPhoneNumber());
    }

    @Test
    void testSetAndGetEntryDate() {
        usersExtForm.setEntryDate(
                ZonedDateTime.parse("2025-02-09T12:30:45+09:00",
                        DateTimeFormatter.ISO_ZONED_DATE_TIME));
        assertEquals(
                ZonedDateTime.parse(
                                "2025-02-09T12:30:45+09:00")
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                ZonedDateTime.parse(
                                usersExtForm.getEntryDate().toString(),
                                DateTimeFormatter
                                        .ISO_ZONED_DATE_TIME
                                        .withZone(ZoneOffset.ofHours(9)
                                        )
                        )
                        .toString()
        );
    }

    @Test
    void testSetAndGetUpdateDate() {
        usersExtForm.setEntryDate(
                ZonedDateTime.parse("2025-02-09T12:30:45+09:00",
                        DateTimeFormatter.ISO_ZONED_DATE_TIME));
        assertEquals(
                ZonedDateTime.parse(
                                "2025-02-09T12:30:45+09:00")
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                ZonedDateTime.parse(
                                usersExtForm.getEntryDate().toString(),
                                DateTimeFormatter
                                        .ISO_ZONED_DATE_TIME
                                        .withZone(ZoneOffset.ofHours(9)
                                        )
                        )
                        .toString()
        );
    }

    @Test
    void testSetUserExtToForm() {
        // 1st case
        UsersExt usersExt = new UsersExt();
        usersExt.setUserId(1L);
        usersExt.setLastName("FooA");
        usersExt.setFirstName("BarA");
        usersExt.setEmail("juiceAAA@example.com");
        usersExt.setBirthDay(
                LocalDate.parse("1971-12-31"));
        usersExt.setPhoneNumber("00011112222");
        usersExt.setEntryDate(
                ZonedDateTime.parse(
                        "2024-07-19T12:30:45+09:00"));
        usersExt.setUpdateDate(
                ZonedDateTime.parse(
                        "2025-02-10T23:59:59+09:00"));

        usersExtForm.setUserExtToForm(usersExt);

        assertEquals(1L, usersExtForm.getUserId());
        assertEquals("FooA", usersExtForm.getLastName());
        assertEquals("BarA", usersExtForm.getFirstName());
        assertEquals(
                "juiceAAA@example.com",
                usersExtForm.getEmail());
        assertEquals(
                LocalDate.parse("1971-12-31"),
                usersExtForm.getBirthDay());
        assertEquals("00011112222",
                usersExtForm.getPhoneNumber());
        assertEquals(
                ZonedDateTime.parse(
                                "2024-07-19T12:30:45+09:00")
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                ZonedDateTime.parse(usersExtForm.getEntryDate().toString(),
                                DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .toString());
        assertEquals(
                ZonedDateTime.parse(
                                "2025-02-10T23:59:59+09:00")
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                ZonedDateTime.parse(usersExtForm.getUpdateDate().toString(),
                                DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .toString());
        // 2nd case
        usersExt = new UsersExt();
        usersExt.setUserId(2L);
        usersExt.setLastName("FooB");
        usersExt.setFirstName("BarB");
        usersExt.setEmail("juiceBBB@example.com");
        usersExt.setBirthDay(
                LocalDate.parse("1960-06-30"));
        usersExt.setPhoneNumber("33344445555");
        usersExt.setEntryDate(
                ZonedDateTime.parse(
                        "2022-02-02T02:02:02+09:00",
                        DateTimeFormatter.ISO_ZONED_DATE_TIME));
        usersExt.setUpdateDate(
                ZonedDateTime.parse(
                        "2025-02-10T12:30:30+09:00",
                        DateTimeFormatter.ISO_ZONED_DATE_TIME));

        usersExtForm.setUserExtToForm(usersExt);

        assertEquals(2L, usersExtForm.getUserId());
        assertEquals("FooB", usersExtForm.getLastName());
        assertEquals("BarB", usersExtForm.getFirstName());
        assertEquals(
                "juiceBBB@example.com",
                usersExtForm.getEmail());
        assertEquals(
                LocalDate.parse("1960-06-30"),
                usersExtForm.getBirthDay());
        assertEquals("33344445555",
                usersExtForm.getPhoneNumber());
        assertEquals(
                ZonedDateTime.parse(
                                "2022-02-02T02:02:02+09:00")
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                ZonedDateTime.parse(
                                usersExtForm.getEntryDate().toString())
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        assertEquals(
                ZonedDateTime.parse(
                                "2025-02-10T12:30:30+09:00")
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                ZonedDateTime.parse(
                                usersExtForm.getUpdateDate().toString())
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }
}