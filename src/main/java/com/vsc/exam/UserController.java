package com.vsc.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("user") RegisterDTO registerDTO) {
        return send("register");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/register")
    public ModelAndView register(@Validated @ModelAttribute("user") RegisterDTO registerDTO, BindingResult result, RedirectAttributes redirectAttributes)
            throws UserAlreadyExistsException {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", registerDTO);
            return redirect("register");
        }

        userService.register(registerDTO);
        return redirect("login");
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login")
    public ModelAndView login() {
        return send("longin");
    }
}
