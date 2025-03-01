package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SecondClassEntityPkey implements Serializable {

    @Column
    private Long userId;
    @Column
    private Long firstClassId;
    @Column
    private Long secondClassId;
    @Column
    private boolean disabled;

    // Default constructor
    public SecondClassEntityPkey() {
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

    public Long getSecondClassId() {
        return secondClassId;
    }

    public void setSecondClassId(Long secondClassId) {
        this.secondClassId = secondClassId;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
