package org.panda.systems.kakeibon.domain.service.spec;

import jakarta.transaction.Transactional;
import org.panda.systems.kakeibon.app.spec.SpecificationForm;
import org.panda.systems.kakeibon.domain.model.spec.Specification;
import org.panda.systems.kakeibon.domain.repository.spec.SpecificationRepository;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecificationService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final SpecificationRepository specificationRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public SpecificationService(
            SpecificationRepository specificationRepository,
            CustomUserDetailsService customUserDetailsService
    ) {

        this.specificationRepository = specificationRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Transactional
    public Long getNextSpecificationId(Long specificationGroupId) {
        return specificationRepository.getNextSpecificationId(specificationGroupId);
    }

    @Transactional
    public List<Specification> findAll() {
        return specificationRepository.findAll();
    }

    @Transactional
    public List<Specification> findBySpecificationGroupIdAndIdAndDeleted(
            Long specificationGroupId, Long userId, Boolean deleted) {
        return specificationRepository
                .findBySpecificationGroupIdAndUserIdAndDeletedOrderBySpecificationIdDesc(
                        specificationGroupId,
                        userId,
                        deleted);
    }

    @Transactional
    public List<Specification> findBySpecificationGroupIdAndUserIdAndDeleted(
            Long specificationGroupId, Long userId, Boolean deleted) {
        return specificationRepository
                .findBySpecificationGroupIdAndUserIdAndDeletedOrderBySpecificationIdDesc(
                        specificationGroupId,
                        userId,
                        deleted);
    }

    @Transactional
    public List<SpecificationForm> findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
            Long specificationGroupId, Long userId, Boolean deleted) {
        List<Specification> specs
                = specificationRepository
                .findBySpecificationGroupIdAndUserIdAndDeletedOrderBySpecificationIdDesc(
                        specificationGroupId,
                        userId,
                        deleted);
        SpecificationForm specForm = new SpecificationForm();

        List<SpecificationForm> forms = new ArrayList<>();
        for (Specification specification : specs) {
            forms.add(
                    specForm.setSpecificationToForm(specification));
        }
        return forms;
    }

    public Specification findBySpecificationGroupIdAndSpecificationIdAndUsernameAndDeleted(
            Long specificationGroupId,
            Long specificationId,
            String username,
            Boolean deleted) {
        return specificationRepository
                .findBySpecificationGroupIdAndSpecificationIdAndUserIdAndDeleted(
                        specificationGroupId,
                        specificationId,
                        customUserDetailsService.findByUsername(username).getUsers().getUserId(),
                        deleted);
    }

    @Transactional
    public void saveAndFlush(Specification entity) {
        specificationRepository.saveAndFlushSpecification(
                entity.getSpecificationGroupId(),
                entity.getSpecificationId(),
                entity.getUserId(),
                entity.getBrandName(),
                entity.getPrice(),
                entity.getCurrencyId(),
                entity.getQuantity(),
                entity.getUnitId(),
                entity.getTaxTypeId(),
                entity.getTaxRateId(),
                entity.getTax(),
                entity.getSpecMemo(),
                entity.getDeleted(),
                entity.getEntryDate(),
                entity.getUpdateDate());
    }

    @Transactional
    public void saveAndFlushSpecificationDelete(Specification entity) {
        entity.setDeleted(true);
        if (entity.getEntryDate() == null) {
            entity.setEntryDate(ZonedDateTime.now());
        } else {
            entity.setUpdateDate(ZonedDateTime.now());
        }
        specificationRepository.saveAndFlush(entity);
    }

//  @Transactional
//  public void saveAllSpecifications(List<Specification> entities) {
//    specificationRepository.saveAllAndFlush(entities);
//  }

    @Transactional
    public void saveAllSpecificationsDelete(Long specificationGroupId) {
        specificationRepository.deleteAllSpecDetail(
                specificationGroupId,
                LocalDateTime.now());
    }

    @Transactional
    public void updateSpecDetail(
            Specification spec) {
        specificationRepository.update(
                spec.getSpecificationGroupId(),
                spec.getSpecificationId(),
                spec.getUserId(),
                spec.getBrandName(),
                spec.getPrice(),
                spec.getCurrencyId(),
                spec.getQuantity(),
                spec.getUnitId(),
                spec.getTaxTypeId(),
                spec.getTaxRateId(),
                spec.getTax(),
                spec.getSpecMemo(),
                spec.getDeleted(),
                spec.getEntryDate(),
                ZonedDateTime.now());
    }

    @Transactional
    public void updateSpecDetailDeleted(
            Specification spec) {
        specificationRepository.deleteSpecDetail(
                spec.getSpecificationGroupId(),
                spec.getSpecificationId(),
                LocalDateTime.now());
    }
}
