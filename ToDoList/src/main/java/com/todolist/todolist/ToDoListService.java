package com.todolist.todolist;

import com.todolist.entity.Staff;
import com.todolist.entity.ToDoList;
import com.todolist.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private StaffService staffService;

    public ToDoList get(Integer id){
        return toDoListRepository.findById(id).get();
    }

    public ToDoList get(String id){
        return this.get(Integer.parseInt(id));
    }

    public List<ToDoList> getAllToDoLists(){
        return (List<ToDoList>) toDoListRepository.findAll();
    }

    public List<ToDoList> getToDoListsByStaffId(int staffId){
        Staff staff = staffService.get(staffId);
        if(staff == null) return new ArrayList<>();
        return new ArrayList<>(staff.getToDoListSet());
    }


    public void save(ToDoList toDoList, String staffId) {
        Staff staff = staffService.get(staffId);
        toDoList.setStaff(staff);
        toDoListRepository.save(toDoList);
    }

    public ToDoList deleteById(Integer id) {
        Optional<ToDoList> toDoListOptional = toDoListRepository.findById(id);
        if(toDoListOptional.isPresent()){
            ToDoList toDoList = toDoListOptional.get();
            toDoList.removeAllItem();
            toDoListRepository.deleteById(id);
            return toDoList;
        }
        return null;
    }

}
