package org.panda.systems.kakeibon.utils.common;

import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

public interface Utils {
    public default Object getObj(MvcResult result, String objName) {
        return Objects.requireNonNull(
                        result.getModelAndView())
                .getModel()
                .get(objName);
    }
}
