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
public class ToDoListController {
    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private StaffService staffService;

    @GetMapping("/todolist")
    public String todoListMainPage(HttpServletRequest request, Model model, @RequestParam(name = "keyword", required = false) String keyword){
        Staff staff = staffService.getStaffFromCookie(request);
        List<ToDoList> toDoLists;

        if(staff.isAdmin()){
            if(keyword != null && !keyword.isBlank()){

                try{
                    int staffId = Integer.parseInt(keyword);
                    toDoLists = toDoListService.getToDoListsByStaffId(staffId);
                }
                catch (NumberFormatException e){
                    toDoLists = toDoListService.getAllToDoLists();
                    model.addAttribute("message", "Staff ID (" + keyword + ") is not valid. The Staff ID should be a number.");
                }

            }
            else{
                toDoLists = toDoListService.getAllToDoLists();
            }

        }
        else{
            toDoLists = toDoListService.getToDoListsByStaffId(staff.getId());
        }

        Collections.sort(toDoLists, Comparator.comparing(ToDoList::getId));
        Collections.reverse(toDoLists);

        model.addAttribute("staff", staff);
        model.addAttribute("toDoLists", toDoLists);
        model.addAttribute("keyword", keyword);

        return "todolist";
    }


    @GetMapping("/todolist/new")
    public String newToDoList(HttpServletRequest request, Model model){
        Staff staff = staffService.getStaffFromCookie(request);
        ToDoList toDoList = new ToDoList();
        model.addAttribute("staff", staff);
        model.addAttribute("pageTitle", "New Todo List");
        model.addAttribute("toDoList", toDoList);
        return "todolist_form";
    }


    @PostMapping("/todolist/save")
    public String saveToDoList(ToDoList toDoList, RedirectAttributes redirectAttributes, @RequestParam(name="staffPicId") String staffId, @RequestParam(name="completed", required = false) boolean completed){

        toDoListService.save(toDoList, staffId) ;
        redirectAttributes.addFlashAttribute("message", "The todo list has been saved successfully.");

        return "redirect:/todolist";
    }


    @GetMapping("/todolist/edit/{id}")
    public String editTask(@PathVariable(name = "id") Integer id, HttpServletRequest request, Model model){
        Staff staff = staffService.getStaffFromCookie(request);
        ToDoList toDoList = toDoListService.get(id);
        model.addAttribute("staff", staff);
        model.addAttribute("pageTitle", "Edit Todo List (ID: " + id + ")");
        model.addAttribute("toDoList", toDoList);

        return "todolist_form";

    }

    @GetMapping("/todolist/delete/{id}")
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


    @GetMapping("/todolist/edit/{id}/newitem")
    public String newToDoListItem(@PathVariable(name = "id") Integer id, HttpServletRequest request, Model model, @RequestParam(name="todolistId") String todolistId){
        Staff staff = staffService.getStaffFromCookie(request);
        ToDoList toDoList = toDoListService.get(todolistId);
        ToDoListItem toDoListItem = new ToDoListItem();
        model.addAttribute("staff", staff);
        model.addAttribute("pageTitle", "New Todo List Item (List ID: " + todolistId + ")");
        model.addAttribute("toDoList", toDoList);
        model.addAttribute("toDoListItem", toDoListItem);
        return "todolistitem_form";
    }

}
