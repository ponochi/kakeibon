package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

// Income and Expenditure
//
@Component
@Entity
@Table(name = "second_class")
@IdClass(SecondClassEntityPkey.class)
public class SecondClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "first_class_id")
    private Long firstClassId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "second_class_seq", allocationSize = 1)
    @Column(name = "second_class_id")
    private Long secondClassId;

    @Id
    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "second_class_name")
    private String secondClassName;

    // Default constructor
    public SecondClass() {
    }

    // Constructor Injection
    public SecondClass(
            Long userId,
            Long firstClassId,
            Long secondClassId,
            String secondClassName,
            Boolean disabled) {
        this.userId = userId;
        this.firstClassId = firstClassId;
        this.secondClassId = secondClassId;
        this.secondClassName = secondClassName;
        this.disabled = disabled;
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

    public String getSecondClassName() {
        return secondClassName;
    }

    public void setSecondClassName(String secondClassName) {
        this.secondClassName = secondClassName;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
