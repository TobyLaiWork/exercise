package com.todolist.todolist;

import com.todolist.entity.Staff;
import com.todolist.entity.ToDoList;
import com.todolist.entity.ToDoListItem;
import com.todolist.staff.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ToDoListItemController {
    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private StaffService staffService;


    @GetMapping("/todolistitem/new")
    public String newToDoListItem(HttpServletRequest request, Model model, @RequestParam(name="todolistId") String todolistId){
        Staff staff = staffService.getStaffFromCookie(request);
        ToDoList toDoList = toDoListService.get(todolistId);
        ToDoListItem toDoListItem = new ToDoListItem();
        model.addAttribute("staff", staff);
        model.addAttribute("pageTitle", "New Todo List Item (List ID: " + todolistId + ")");
        model.addAttribute("toDoList", toDoList);
        model.addAttribute("toDoListItem", toDoListItem);
        return "todolistitem_form";
    }


    @PostMapping("/todolistitem/save")
    public String saveToDoList(ToDoList toDoList, RedirectAttributes redirectAttributes, @RequestParam(name="staffPicId") String staffId, @RequestParam(name="completed", required = false) boolean completed){

        toDoListService.save(toDoList, staffId) ;
        redirectAttributes.addFlashAttribute("message", "The todo list has been saved successfully.");

        return "redirect:/todolist";
    }


    @GetMapping("/todolistitem/edit/{id}")
    public String editTask(@PathVariable(name = "id") Integer id, HttpServletRequest request, Model model){
        Staff staff = staffService.getStaffFromCookie(request);
        ToDoList toDoList = toDoListService.get(id);
        model.addAttribute("staff", staff);
        model.addAttribute("pageTitle", "Edit Todo List (ID: " + id + ")");
        model.addAttribute("toDoList", toDoList);

        return "todolist_form";

    }

    @GetMapping("/todolistitem/delete/{id}")
    public String deleteToDoList(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){

        ToDoList deletedToDoList = toDoListService.deleteById(id);
        if(deletedToDoList != null){
            redirectAttributes.addFlashAttribute("message", "The todo list ID " + id + " has been deleted successfully");
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Fail to delete todo list ID " + id );
        }
        return "redirect:/todolist";
    }
    

}
