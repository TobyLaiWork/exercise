package com.todolist;

import com.todolist.entity.Staff;
import com.todolist.staff.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private StaffService staffService;
    @GetMapping("/")
    public String viewHomePage(HttpServletRequest request, Model model){
        Staff staffInCookie = staffService.getStaffFromCookie(request);

        if(staffInCookie != null){
            model.addAttribute("staff", staffInCookie);
            return "index";
        }else{
            Staff staff = new Staff();
            staff.setEmail("test@test.com"); //for testing only
//            model.addAttribute("staff", new Staff());
            model.addAttribute("staff", staff);
            return "login";
        }
    }
}
