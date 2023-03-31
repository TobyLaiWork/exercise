package com.todolist.entity;

import jakarta.persistence.*;

@Entity
@Table(name="todolist_item")
public class ToDoListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30, nullable = false)
    private String type;
    @Column(length = 200, nullable = true)
    private String data;
    @Column(length = 30, nullable = true)
    private String status;

    public ToDoListItem() {
    }

    public ToDoListItem(String type, String data, String status) {
        this.type = type;
        this.data = data;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getDetail(){
        return data;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ToDoListItem{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}
