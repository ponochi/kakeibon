package org.panda.systems.kakeibon.app.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("login")
    public String viewLoginForm(Model model) {
//    Logger logger = LoggerFactory.getLogger("LoginController");
//    logger.debug("================================================");
        return "/top";
    }
}
