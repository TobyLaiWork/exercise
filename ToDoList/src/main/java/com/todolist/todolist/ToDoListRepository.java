package com.todolist.todolist;

import com.todolist.entity.Staff;
import com.todolist.entity.ToDoList;
import org.springframework.data.repository.CrudRepository;


public interface ToDoListRepository extends CrudRepository<ToDoList, Integer> {

}