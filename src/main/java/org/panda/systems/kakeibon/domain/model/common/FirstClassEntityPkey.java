package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Embeddable
public class FirstClassEntityPkey implements Serializable {

    @Column
    private Long userId;
    @Column
    private Long firstClassId;

    // Default constructor
    public FirstClassEntityPkey() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFirstClassId() {
        return firstClassId;
    }

    public void setFirstClassId(Long firstClassId) {
        this.firstClassId = firstClassId;
    }
}
