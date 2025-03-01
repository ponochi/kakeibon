package org.panda.systems.kakeibon.app.common;

import org.panda.systems.kakeibon.WebSecurityConfig;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.panda.systems.kakeibon.domain.service.users.UserExtsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/menu", method = GET)
//@PreAuthorize(hasRole('ROLE_ADMIN', 'ROLE_USER''))
public class MainController {

    // Default constructor
    public MainController() {}

    @RequestMapping(value = "/mainMenu", method = GET)
    String mainMenu(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        Logger logger = LoggerFactory.getLogger("Main");
        logger.info("====KakeiBon Ver.0.3.3==============================");

        model.addAttribute("userDetails", userDetails);
        return "topMenu";
    }
}
