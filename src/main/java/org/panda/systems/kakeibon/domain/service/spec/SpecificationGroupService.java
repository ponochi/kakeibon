package org.panda.systems.kakeibon.domain.service.spec;


import jakarta.transaction.Transactional;
import org.panda.systems.kakeibon.app.spec.SpecificationGroupForm;
import org.panda.systems.kakeibon.domain.model.spec.SpecificationGroup;
import org.panda.systems.kakeibon.domain.repository.spec.SpecificationGroupRepository;
import org.panda.systems.kakeibon.domain.repository.spec.SpecificationRepository;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecificationGroupService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public final SpecificationGroupRepository specificationGroupRepository;
    public final SpecificationRepository specificationRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public SpecificationGroupService(
            SpecificationGroupRepository specificationGroupRepository,
            SpecificationRepository specificationRepository,
            CustomUserDetailsService customUserDetailsService) {
        this.specificationGroupRepository = specificationGroupRepository;
        this.specificationRepository = specificationRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Transactional
    public Long getMaxGroupId() {
        return specificationGroupRepository.getMaxGroupId();
    }

    @Transactional
    public List<SpecificationGroup> findAll() {

        return specificationGroupRepository.findAll(
                Sort.by(Sort.Direction.DESC,
                        "specificationGroupId")
        );
    }

    @Transactional
    public List<SpecificationGroup> findAllByUsernameAndDeleted(
            String username, Boolean deleted) {

        return specificationGroupRepository.findAllByUserIdAndDeleted(
                customUserDetailsService.findByUsername(username).getUsers().getUserId(),
                deleted,
                Sort.by(Sort.Direction.DESC,
                        "specificationGroupId")
        );
    }

    @Transactional
    public SpecificationGroup findBySpecificationGroupIdAndUserIdAndDeleted(
            Long specificationGroupId, Long userId, Boolean deleted) {

        return specificationGroupRepository
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        deleted);
    }

    @Transactional
    public List<SpecificationGroupForm> findAllToForm(Boolean deleted) {

        List<SpecificationGroup> specificationGroups
                = specificationGroupRepository.findAllByDeleted(
                deleted,
                Sort.by(
                        Sort.Direction.DESC,
                        "specificationGroupId")
        );

        List<SpecificationGroupForm> groupForms = new ArrayList<>();
        for (SpecificationGroup specificationGroup : specificationGroups) {
            SpecificationGroupForm specificationGroupForm
                    = new SpecificationGroupForm().setSpecificationGroupToForm(specificationGroup);
            if (specificationGroupForm != null) {
                groupForms.add(specificationGroupForm);
            }
        }
        return groupForms;
    }

    @Transactional
    public void saveAndFlush(SpecificationGroup entity) {
        specificationGroupRepository.saveAndFlush(entity);
    }
}
