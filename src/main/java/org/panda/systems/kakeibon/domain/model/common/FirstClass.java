package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

// Income and Expenditure
//
@Entity
@Table(name = "first_class")
public class FirstClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Long userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "first_class_seq", allocationSize = 1)
    @Column(name = "first_class_id")
    private Long firstClassId;

    @Column
    private String firstClassName;

    // Default constructor
    public FirstClass() {
    }

    // Constructor Injection
    public FirstClass(
            Long userId,
            Long firstClassId,
            String firstClassName) {
        this.userId = userId;
        this.firstClassId = firstClassId;
        this.firstClassName = firstClassName;
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

    public String getFirstClassName() {
        return firstClassName;
    }

    public void setFirstClassName(String firstClassName) {
        this.firstClassName = firstClassName;
    }
}
