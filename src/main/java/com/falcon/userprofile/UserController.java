package com.falcon.userprofile;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository
            ,PasswordEncoder passwordEncoder
            ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public String listUsers(Model model) {
        model.addAttribute(this.userRepository.findAll());
        return "users/userlist";
    }

    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        model.addAttribute(new User());
        return "users/userform";
    }

    @GetMapping("/users/{id}/edit")
    public String editUserForm(Model model, @PathVariable long id) {
        model.addAttribute(this.userRepository.findById(id).orElseGet(User::new));
        return "users/userform";
    }

    @PostMapping({"/users"})
    public String saveUser(@Valid User user
            , Errors errors
            , final RedirectAttributes redirect
            , Model model
            ) {
        if (errors.hasErrors()) {
            //model.addAttribute(new User());
            return "users/userform";
        }
        String successMsg = "";
        if (user.getId() != 0) {
            User fromDb = this.userRepository.findById(user.getId()).orElseGet(User::new);
            user.setPassword(fromDb.getPassword());
            successMsg = "User edited successfully.";
        } else {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            successMsg = "New user added successfully.";
        }
        this.userRepository.save(user);
        redirect.addFlashAttribute("message", successMsg);
        return "redirect:/users";
    }
}
