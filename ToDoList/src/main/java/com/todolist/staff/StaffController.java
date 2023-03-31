package com.todolist.staff;

import com.todolist.entity.Staff;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

@Controller
public class StaffController {
    @Autowired
    private StaffService service;



    @PostMapping("/staff/login")
    public String login(Staff staff, RedirectAttributes redirectAttributes, HttpServletResponse response){
        Staff loggedInStaff = service.login(staff);
        if(loggedInStaff != null){
            int cookieAgeInSecond = 86400;
            Cookie cookie = new Cookie("staffId", loggedInStaff.getId().toString());
            cookie.setMaxAge(cookieAgeInSecond);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:".concat("/");
        }
        else{
            redirectAttributes.addAttribute("message", "login fail, not such user.");
            return "login";
        }
    }


    @GetMapping("/staff/logout")
    public String logout(RedirectAttributes redirectAttributes, HttpServletResponse response){
        Cookie cookie = new Cookie("staffId", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:".concat("/");
    }
}
