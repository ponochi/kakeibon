package org.panda.systems.kakeibon.domain.service.users;

import org.panda.systems.kakeibon.app.users.UsersExtForm;
import org.panda.systems.kakeibon.domain.model.users.UsersExt;
import org.panda.systems.kakeibon.domain.repository.users.UsersExtRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
//@Transactional
public class UserExtsService implements Serializable {

    public final UsersExtRepository usersExtRepository;

    public UserExtsService(UsersExtRepository usersExtRepository) {
        this.usersExtRepository = usersExtRepository;
    }

    public List<UsersExt> findAll() {
        return usersExtRepository.findAll();
    }

    public UsersExt findByUserId(Long userId) {
        return (UsersExt) usersExtRepository.findByUserExtId(userId);
    }

    public UsersExtForm findByUserExtToForm(Long userId) {

        UsersExtForm usersExtForm = new UsersExtForm();

        UsersExt usersExt = (UsersExt) usersExtRepository.findByUserExtId(userId);

        if (usersExt != null) {
            usersExtForm.setUserId(usersExt.getUserId());
            usersExtForm.setLastName(usersExt.getLastName());
            usersExtForm.setFirstName(usersExt.getFirstName());
            usersExtForm.setEmail(usersExt.getEmail());
            usersExtForm.setBirthDay(usersExt.getBirthDay());
            usersExtForm.setPhoneNumber(usersExt.getPhoneNumber());
            usersExtForm.setEntryDate(
                    ZonedDateTime.parse(
                            usersExt.getEntryDate().toString(),
                            DateTimeFormatter.ISO_ZONED_DATE_TIME
                                    .withZone(ZoneOffset.ofHours(9))));
            if (Objects.isNull(usersExt.getUpdateDate())) {
                usersExtForm.setUpdateDate(null);
            } else {
                usersExtForm.setUpdateDate(
                        ZonedDateTime.parse(usersExt.getUpdateDate()
                                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)));
            }

            return usersExtForm;
        }
        return null;
    }

    public Integer getMaxId() {
        return usersExtRepository.getMaxId();
    }

    //    @Transactional
    public UsersExt saveUserExt(UsersExt usersExt) {
        return (UsersExt) usersExtRepository.saveAndFlush(usersExt);
    }

    public List<UsersExtForm> findAllUserForm() {
        List<UsersExt> usersExt = usersExtRepository.findAll();

        List<UsersExtForm> usersExtForms = new ArrayList<>();
        for (UsersExt userExt : usersExt) {
            UsersExtForm usersExtForm = new UsersExtForm();
            usersExtForm.setUserId(userExt.getUserId());
            usersExtForm.setLastName(userExt.getLastName());
            usersExtForm.setFirstName(userExt.getFirstName());
            usersExtForm.setEmail(userExt.getEmail());
            usersExtForm.setBirthDay(userExt.getBirthDay());
            usersExtForm.setPhoneNumber(userExt.getPhoneNumber());

            usersExtForms.add(usersExtForm);
        }
        return usersExtForms;
    }
}
