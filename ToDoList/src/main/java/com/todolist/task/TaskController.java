package com.todolist.task;

import com.todolist.entity.Staff;
import com.todolist.entity.Task;
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
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private StaffService staffService;

    @GetMapping("/task")
    public String taskMainPage(HttpServletRequest request, Model model, @RequestParam(name = "keyword", required = false) String keyword){
        Staff staff = staffService.getStaffFromCookie(request);
        List<Task> taskList;

        if(staff.isAdmin()){
            if(keyword != null && !keyword.isBlank()){

                try{
                    int staffId = Integer.parseInt(keyword);
                    taskList = taskService.getTasksByStaffId(staffId);
                }
                catch (NumberFormatException e){
                    taskList = taskService.getAllTasks();
                    model.addAttribute("message", "Staff ID (" + keyword + ") is not valid. The Staff ID should be a number.");
                }

            }
            else{
                taskList = taskService.getAllTasks();
            }

        }
        else{
            taskList = taskService.getTasksByStaffId(staff.getId());
        }

        Collections.sort(taskList, Comparator.comparing(Task::getId));
        Collections.reverse(taskList);

        model.addAttribute("staff", staff);
        model.addAttribute("taskList", taskList);
        model.addAttribute("keyword", keyword);

        return "task";
    }

    @GetMapping("/task/new")
    public String newtask(HttpServletRequest request, Model model){
        Staff staff = staffService.getStaffFromCookie(request);
        Task task = new Task();
        model.addAttribute("staff", staff);
        model.addAttribute("pageTitle", "New Task");
        model.addAttribute("task", task);
        return "task_form";
    }


    @PostMapping("/task/save")
    public String saveTask(Task task, RedirectAttributes redirectAttributes, @RequestParam(name="staffPicId") String staffId, @RequestParam(name="completed", required = false) boolean completed){
        if(!completed){

            task.setStatus(Task.TaskStatus.NEW.toString());

            if(!staffId.isBlank() && task.getStatus().equals(Task.TaskStatus.NEW.toString())){
                task.setStatus(Task.TaskStatus.ASSIGNED.toString());
            }

        }
        else{
            task.setStatus(Task.TaskStatus.COMPLETED.toString());
        }

        taskService.save(task, staffId) ;
        redirectAttributes.addFlashAttribute("message", "The task has been saved successfully.");

        return "redirect:/task";
    }


    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable(name = "id") Integer id, HttpServletRequest request, Model model){
        Staff staff = staffService.getStaffFromCookie(request);
        Task task = taskService.get(id);
        model.addAttribute("staff", staff);
        model.addAttribute("pageTitle", "Edit Task (ID: " + id + ")");
        model.addAttribute("task", task);

        return "task_form";

    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){

        Task deletedTast = taskService.deleteById(id);
        if(deletedTast != null){
            redirectAttributes.addFlashAttribute("message", "The task ID " + id + " has been deleted successfully");
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Fail to delete task ID " + id );
        }

        return "redirect:/task";

    }



}
