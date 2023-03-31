package com.todolist.staff;

import com.todolist.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StaffRestController {
    @Autowired
    private StaffService service;

    @PostMapping ("/staff/check_exist")
    public String checkDeplicateEmail(@RequestParam("ids") String staffIds){
        boolean notExist = false;
        if(staffIds == null || staffIds.isEmpty()){
            return "empty";
        }
        try{
            String [] idArray = staffIds.split(",");
            for(String id : idArray){
                Staff s = service.get(id);
                if(s == null){
                    notExist = true;
                    break;
                }
            }
        }catch (Exception e){
            notExist = true;
        }

        return notExist? "NotExist" : "OK";

    }
}
