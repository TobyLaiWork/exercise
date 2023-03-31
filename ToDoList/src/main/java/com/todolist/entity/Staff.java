package com.todolist.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(length = 64, nullable = false)
    private String password;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 64, nullable = false)
    private boolean enabled;
    @Column(length = 64, nullable = false)
    private boolean isAdmin;

    @ManyToMany(mappedBy = "staffSet")
    private Set<Task> taskSet = new HashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<ToDoList> toDoListSet;

    public Staff(String email, String password, String name, boolean enabled, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.isAdmin = isAdmin;
    }

    public Staff() {

    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", isAdmin=" + isAdmin +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

    public Set<ToDoList> getToDoListSet() {
        return toDoListSet;
    }

    public void setToDoListSet(Set<ToDoList> toDoListSet) {
        this.toDoListSet = toDoListSet;
    }
}


