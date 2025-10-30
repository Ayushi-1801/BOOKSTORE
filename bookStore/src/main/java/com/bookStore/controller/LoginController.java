//package com.bookStore.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class LoginController {
//
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";  // loads login.html
//    }
//
//    @GetMapping("/signup")
//    public String signupPage() {
//        return "signup"; // loads signup.html
//    }
//}
package com.bookStore.controller;

import com.bookStore.entity.User;
import com.bookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

//    @PostMapping("/login")
//    public String processLogin(@RequestParam String email,
//                               @RequestParam String password,
//                               Model model) {
//
//        User user = userRepo.findByEmail(email);
//
//        if (user != null && user.getPassword().equals(password)) {
//            return "redirect:/books";
//        } else {
//            model.addAttribute("error", "Invalid email or password");
//            return "login";
//        }
//    }
@PostMapping("/login")
public String processLogin(@RequestParam String email,
                           @RequestParam String password,
                           Model model,
                           HttpSession session) {

    User user = userRepo.findByEmail(email);

    if (user != null && user.getPassword().equals(password)) {
        // ✅ Store user details in session
        session.setAttribute("loggedUser", user);

        return "redirect:/books";
    } else {
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }
}


    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user) {
        userRepo.save(user);
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logoutPage( HttpSession session) {
        session.invalidate();
        return "logout"; // loads logout.html
    }


}
