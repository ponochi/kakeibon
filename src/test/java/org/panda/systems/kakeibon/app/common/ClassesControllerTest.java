package org.panda.systems.kakeibon.app.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.panda.systems.kakeibon.WebSecurityConfig;
import org.panda.systems.kakeibon.domain.repository.common.FirstClassRepository;
import org.panda.systems.kakeibon.domain.repository.common.SecondClassRepository;
import org.panda.systems.kakeibon.domain.repository.common.ThirdClassRepository;
import org.panda.systems.kakeibon.domain.service.common.FirstClassService;
import org.panda.systems.kakeibon.domain.service.common.SecondClassService;
import org.panda.systems.kakeibon.domain.service.common.ThirdClassService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@AutoConfigureMockMvc
@EnableWebSecurity
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ClassesControllerTest {

    private MockMvc mockMvc;

    FirstClassRepository firstClassRepository;
    FirstClassService firstClassService;
    SecondClassRepository secondClassRepository;
    SecondClassService secondClassService;
    ThirdClassRepository thirdClassRepository;
    ThirdClassService thirdClassService;

    WebSecurityConfig webSecurityConfig;

    ClassesController classesController;

    @BeforeEach
    void setUp() {
        firstClassRepository = mock(FirstClassRepository.class);
        firstClassService = new FirstClassService(firstClassRepository);
        secondClassRepository = mock(SecondClassRepository.class);
        secondClassService = new SecondClassService(secondClassRepository);
        thirdClassRepository = mock(ThirdClassRepository.class);
        thirdClassService = new ThirdClassService(thirdClassRepository);

        webSecurityConfig = new WebSecurityConfig();

        classesController
                = new ClassesController(
                new FirstClassService(),
                new SecondClassService(),
                new ThirdClassService());

        mockMvc = MockMvcBuilders
                .standaloneSetup(classesController)
                .build();
    }

    @Test
    void testClassesControllerWithoutParameters() {
        ClassesController classesController = new ClassesController();
        assertNotNull(classesController);
    }

    @Test
    void testClassesControllerWithParameters() {
        ClassesController classesController
                = new ClassesController(
                firstClassService,
                secondClassService,
                thirdClassService);
        assertNotNull(classesController);
    }
    @Test
    void setUpForm() {
    }

    @Test
    void list() {
    }
}