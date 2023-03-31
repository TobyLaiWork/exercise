package com.todolist.task;

import com.todolist.entity.Staff;
import com.todolist.entity.Task;
import com.todolist.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StaffService staffService;

    public Task get(Integer id){
        return taskRepository.findById(id).get();
    }

    public Task get(String id){
        return this.get(Integer.parseInt(id));
    }

    public List<Task> getAllTasks(){
        return (List<Task>) taskRepository.findAll();
    }

    public List<Task> getTasksByStaffId(int staffId){
        Staff staff = staffService.get(staffId);
        if(staff == null) return new ArrayList<>();
        return new ArrayList<>(staff.getTaskSet());
    }

    public void save(Task task, String staffId) {

        String [] staffIdList = staffId.split(",");
        for (String idString : staffIdList){
            if(!idString.isBlank()){
                int id = Integer.parseInt(idString);
                Staff s = staffService.get(id);
                task.getStaffSet().add(s);
                s.getTaskSet().add(task);
                staffService.save(s);
            }
        }
        taskRepository.save(task);

    }

    public Task deleteById(int id){
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isPresent()){
            Task task = taskOptional.get();
            task.removeAllStaffPic();
            taskRepository.deleteById(id);
            return task;
        }

        return null;

    }

}
