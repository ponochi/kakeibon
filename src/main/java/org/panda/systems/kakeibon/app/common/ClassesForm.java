package org.panda.systems.kakeibon.app.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import org.panda.systems.kakeibon.domain.model.common.FirstClass;
import org.panda.systems.kakeibon.domain.model.common.SecondClass;
import org.panda.systems.kakeibon.domain.model.common.ThirdClass;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class ClassesForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "first_class_seq", allocationSize = 1)
    @PositiveOrZero
    @Column(name = "first_class_id")
    private Long firstClassId;
    @Column
    private String firstClassName;
    @Column
    private FirstClass firstClass;
    @Column
    private List<FirstClass> firstClasses;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "second_class_seq", allocationSize = 1)
    @PositiveOrZero
    @Column(name = "second_class_id")
    private Long secondClassId;
    @Column
    private String secondClassName;
    @Column
    private SecondClass secondClass;
    @Column
    private List<SecondClass> secondClasses;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "third_class_seq", allocationSize = 1)
    @PositiveOrZero
    @Column(name = "third_class_id")
    private Long thirdClassId;
    @Column
    private String thirdClassName;
    @Column
    private ThirdClass thirdClass;
    @Column
    private List<ThirdClass> thirdClasses;

    // Default constructor
    public ClassesForm() {
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

    public FirstClass getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(FirstClass firstClass) {
        this.firstClass = firstClass;
    }

    public List<FirstClass> getFirstClasses() {
        return firstClasses;
    }

    public void setFirstClasses(List<FirstClass> firstClasses) {
        this.firstClasses = firstClasses;
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

    public SecondClass getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(SecondClass secondClass) {
        this.secondClass = secondClass;
    }

    public List<SecondClass> getSecondClasses() {
        return secondClasses;
    }

    public void setSecondClasses(List<SecondClass> secondClasses) {
        this.secondClasses = secondClasses;
    }

    public Long getThirdClassId() {
        return thirdClassId;
    }

    public void setThirdClassId(Long thirdClassId) {
        this.thirdClassId = thirdClassId;
    }

    public String getThirdClassName() {
        return thirdClassName;
    }

    public void setThirdClassName(String thirdClassName) {
        this.thirdClassName = thirdClassName;
    }

    public ThirdClass getThirdClass() {
        return thirdClass;
    }

    public void setThirdClass(ThirdClass thirdClass) {
        this.thirdClass = thirdClass;
    }

    public List<ThirdClass> getThirdClasses() {
        return thirdClasses;
    }

    public void setThirdClasses(List<ThirdClass> thirdClasses) {
        this.thirdClasses = thirdClasses;
    }

}
