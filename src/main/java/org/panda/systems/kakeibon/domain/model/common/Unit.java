package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "unit")
public class Unit implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "unit_seq", allocationSize = 1)
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "unit_name")
    private String unitName;

    // Default constructor
    public Unit() {
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
