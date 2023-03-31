package com.todolist.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="todolist")
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = true)
    private Task task;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 200, nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name="staff_id", nullable=false)
    private Staff staff;

    @OneToMany
    @JoinColumn(name = "todolist_id")
    private Set<ToDoListItem> toDoListItemSet = new HashSet<>();

    public ToDoList(String type, String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ToDoList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTaskId() {
        if(task == null) return "None";
        return task.getId().toString();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public String getStatus() {
        int totalItems = toDoListItemSet.size();
        int completeCount = 0;

        for(ToDoListItem item : toDoListItemSet){
            if(item.getStatus().toLowerCase().equals("completed")){
                completeCount++;
            }
            if(item.getType().toLowerCase().equals("remark")){
                totalItems -- ;
            }
        }

        if(totalItems == 0) return "empty";
        if(completeCount == totalItems) return "completed";

        return String.format("%.2f",(completeCount/(float)totalItems) * 100.0) +"%";
    }

    public void removeAllItem(){
        this.toDoListItemSet.clear();
    }

    public Set<ToDoListItem> getToDoListItemSet() {
        return toDoListItemSet;
    }

    public void setToDoListItemSet(Set<ToDoListItem> toDoListItemSet) {
        this.toDoListItemSet = toDoListItemSet;
    }

    public Staff getStaff() {
        return staff;
    }

    public int getStaffId() {
        return staff.getId();
    }

    public String getStaffName() {
        return staff.getName();
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "id=" + id +
                ", task=" + task +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + getStatus() + '\'' +
                ", toDoListItemSet=" + toDoListItemSet +
                '}';
    }
}
