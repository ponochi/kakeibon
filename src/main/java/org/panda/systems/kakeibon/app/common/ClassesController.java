package org.panda.systems.kakeibon.app.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.panda.systems.kakeibon.domain.model.common.FirstClass;
import org.panda.systems.kakeibon.domain.model.common.SecondClass;
import org.panda.systems.kakeibon.domain.model.common.ThirdClass;
import org.panda.systems.kakeibon.domain.service.common.FirstClassService;
import org.panda.systems.kakeibon.domain.service.common.SecondClassService;
import org.panda.systems.kakeibon.domain.service.common.ThirdClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/classes", method = {RequestMethod.GET, RequestMethod.POST})
public class ClassesController {

    private final FirstClassService firstClassService;
    private final SecondClassService secondClassService;
    private final ThirdClassService thirdClassService;

    public ClassesController() {
        this.firstClassService = null;
        this.secondClassService = null;
        this.thirdClassService = null;
    }

    @Autowired
    public ClassesController(
            FirstClassService firstClassService,
            SecondClassService secondClassService,
            ThirdClassService thirdClassService) {
        this.firstClassService = firstClassService;
        this.secondClassService = secondClassService;
        this.thirdClassService = thirdClassService;

    }

//    @ModelAttribute
//    ClassesForm setUpForm() {
//        ClassesForm form = new ClassesForm();
//
//        return form;
//    }

    @RequestMapping(path = "/objToJson", method = {RequestMethod.GET, RequestMethod.POST})
    String list(Model model)
            throws JsonProcessingException {
        // Get firstClasses data from DB
        List<FirstClass> firstClassesData
                = Objects.requireNonNull(firstClassService)
                .findAll();

        JSONObject jsonObject = new JSONObject();

        List<FirstClass> firstList
                = new ArrayList<FirstClass>();

        for (var fClassData : firstClassesData) {
            firstList.add(new FirstClass(
                    fClassData.getUserId(),
                    fClassData.getFirstClassId(),
                    fClassData.getFirstClassName()));
        }

        jsonObject.put("firstClasses", firstList);

        ObjectMapper mapper = new ObjectMapper();
        String firstClasses = mapper.writeValueAsString(jsonObject);

        //===========================================================
        // Get secondClasses data from DB
        List<SecondClass> secondClassesData
                = secondClassService.findAll();

        List<SecondClass> secondList
                = new ArrayList<SecondClass>();

        for (var sClassData : secondClassesData) {
            secondList.add(new SecondClass(
                    sClassData.getUserId(),
                    sClassData.getFirstClassId(),
                    sClassData.getSecondClassId(),
                    sClassData.getSecondClassName(),
                    sClassData.getDisabled()));
        }

        jsonObject.put("secondClasses", secondList);

        mapper = new ObjectMapper();
        String secondClasses = mapper.writeValueAsString(secondList);

        //===========================================================
        // Get thirdClasses data from DB
        List<ThirdClass> thirdClassesData
                = thirdClassService.findAll();

        List<ThirdClass> thirdList
                = new ArrayList<ThirdClass>();

        for (var tClassData : thirdClassesData) {
            thirdList.add(new ThirdClass(
                    tClassData.getUserId(),
                    tClassData.getFirstClassId(),
                    tClassData.getSecondClassId(),
                    tClassData.getThirdClassId(),
                    tClassData.getThirdClassName(),
                    tClassData.getDisabled()));
        }

        jsonObject.put("thirdClasses", thirdList);

        mapper = new ObjectMapper();
        String thirdClasses = mapper.writeValueAsString(thirdList);

        model.addAttribute("firstClasses", firstClasses);
        model.addAttribute("secondClasses", secondClasses);
        model.addAttribute("thirdClasses", thirdClasses);
        return "common/showClasses";
    }
}
