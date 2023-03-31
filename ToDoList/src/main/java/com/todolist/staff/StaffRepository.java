package com.todolist.staff;

import com.todolist.entity.Staff;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
    public Staff findByEmail(String email);

}
