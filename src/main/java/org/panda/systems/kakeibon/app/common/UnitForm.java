package org.panda.systems.kakeibon.app.common;

import jakarta.persistence.*;
import org.panda.systems.kakeibon.domain.model.common.Unit;
import org.panda.systems.kakeibon.domain.service.common.UnitService;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "unit")
public class UnitForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UnitService unitService;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @SequenceGenerator(name = "unit_seq", allocationSize = 1)
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "unit_name")
    private String unitName;

    // Default constructor
    public UnitForm() {

        this.unitService = null;
    }

    public UnitForm(UnitService unitService) {

        this.unitService = unitService;
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

    public UnitForm setUnitFormByDB(Long unitId) {

        Unit unit = unitService.findByUnitId(unitId);

        this.unitId = unit.getUnitId();
        this.unitName = unit.getUnitName();

        return this;
    }
}
