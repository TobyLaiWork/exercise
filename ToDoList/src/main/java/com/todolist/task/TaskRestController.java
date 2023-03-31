package com.todolist.task;

import com.todolist.entity.Staff;
import com.todolist.entity.Task;
import com.todolist.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TaskRestController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private StaffService staffService;

    @PostMapping("/task/check_exist")
    public String checkTaskExist(@RequestParam("_taskId") String taskId, @RequestParam("_staffId") String staffId){
        boolean notExist = false;
        if(taskId == null || taskId.isEmpty()){
            return "empty";
        }
        try{
            Staff currentStaff = staffService.get(staffId);
            Task task = taskService.get(taskId);
            if(task == null){
                return "NotExist";
            }
            else{
                Set<Staff> staffSet = task.getStaffSet();
                boolean belongToStaff = false;
                for(Staff s : staffSet){
                    if(s.getId().equals(currentStaff.getId())){
                        return "OK";
                    }
                }
                return "NotBelong";
            }

        }catch (Exception e){
            return "NotExist";
        }

    }

}
