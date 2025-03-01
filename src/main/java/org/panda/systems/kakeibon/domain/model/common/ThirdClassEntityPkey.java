package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Embeddable
public class ThirdClassEntityPkey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_class_id")
    private Long firstClassId;

    @Column(name = "second_class_id")
    private Long secondClassId;

    @Column(name = "third_class_id")
    private Long thirdClassId;

    @Column(name = "disabled")
    private boolean disabled;

    // Default constructor
    public ThirdClassEntityPkey() {
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

    public Long getThirdClassId() {
        return thirdClassId;
    }

    public void setThirdClassId(Long thirdClassId) {
        this.thirdClassId = thirdClassId;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
