package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

// Income and Expenditure
//
@Entity
@Table(name = "third_class")
@IdClass(ThirdClassEntityPkey.class)
public class ThirdClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "first_class_id")
    private Long firstClassId;

    @Id
    @Column(name = "second_class_id")
    private Long secondClassId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "third_class_seq", allocationSize = 1)
    @Column(name = "third_class_id")
    private Long thirdClassId;

    @Id
    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "third_class_name")
    private String thirdClassName;

    // Default constructor
    public ThirdClass() {
    }

    // Constructor Injection
    public ThirdClass(
            Long userId,
            Long firstClassId,
            Long secondClassId,
            Long thirdClassId,
            String thirdClassName,
            Boolean disabled) {
        this.userId = userId;
        this.firstClassId = firstClassId;
        this.secondClassId = secondClassId;
        this.thirdClassId = thirdClassId;
        this.thirdClassName = thirdClassName;
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

    public Long getThirdClassId() {
        return thirdClassId;
    }

    public void setThirdClassId(Long thirdClassId) {
        this.thirdClassId = thirdClassId;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getThirdClassName() {
        return thirdClassName;
    }

    public void setThirdClassName(String thirdClassName) {
        this.thirdClassName = thirdClassName;
    }
}
