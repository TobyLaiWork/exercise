package com.todolist.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="task")
public class Task {

    public enum TaskStatus  {
        NEW("New"),
        ASSIGNED("Assigned"),
        COMPLETED("Completed");

        private final String text;

        TaskStatus(final String text){
            this.text = text;
        }

        @Override
        public String toString(){
            return text;
        }

    };
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 200, nullable = true)
    private String description;
    @Column(length = 30, nullable = true)
    private String status;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "task_PIC",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private Set<Staff> staffSet = new HashSet<>();

    public Task() {
    }

    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Set<Staff> getStaffSet() {
        return staffSet;
    }

    public void removeStaffPic(Staff staff){
        this.staffSet.remove(staff);
    }

    public void removeAllStaffPic(){
        this.staffSet.clear();
    }


    public void setStaffSet(Set<Staff> staffSet) {
        this.staffSet = staffSet;
    }

    @Transient
    public String getStaffPicId() {
        if(staffSet.size() == 0) return "";
        String picString = "";
        for(Staff s : staffSet){
            picString += s.getId() + ", ";
        }

        return picString.substring(0, picString.length() - 2);
    }

    @Transient
    public String getStaffPicName() {
        if(staffSet.size() == 0) return "None";
        String picString = "";
        for(Staff s : staffSet){
            picString += s.getName() + ", ";
        }

        return picString.substring(0, picString.length() - 2);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", staffSet=" + getStaffPicId() +
                '}';
    }
}
