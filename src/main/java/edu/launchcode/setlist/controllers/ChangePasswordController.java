package edu.launchcode.setlist.controllers;

import edu.launchcode.setlist.models.PasswordResetDto;
import edu.launchcode.setlist.models.User;
import edu.launchcode.setlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/change-password")
public class ChangePasswordController {
    @Autowired private UserService userService;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("passwordResetForm")
    public PasswordResetDto passwordReset() {
        return new PasswordResetDto();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayChangePasswordPage(Model model){
        return "password/change-password";

    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public String processChangePasswordPage(Model model, @Valid PasswordResetDto form,
                                            BindingResult result, RedirectAttributes redirectAttributes){
        if (! form.getPassword().equals(form.getConfirmPassword())){
            model.addAttribute("error-message", "Passwords do not match.");
            return "password/change-password";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        String updatedPassword = passwordEncoder.encode(form.getPassword());
        userService.updatePassword(updatedPassword, user.getId());
        return "redirect:/home";
    }
}
