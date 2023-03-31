package com.todolist.todolist;

import com.todolist.entity.ToDoList;
import com.todolist.entity.ToDoListItem;
import org.springframework.data.repository.CrudRepository;


public interface ToDoListItemRepository extends CrudRepository<ToDoListItem, Integer> {

}