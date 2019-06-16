package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class QuartzConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;  //generator = "system-uuid"
    @Column(length = 50)
    private String groupName;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String cron;
    @Column(nullable = false)
    Boolean status = false;
    @Column(length = 100)
    private String msg;
    @Column(length = 50)
    private String quartzClass;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getQuartzClass() {
        return quartzClass;
    }

    public void setQuartzClass(String quartzClass) {
        this.quartzClass = quartzClass;
    }

    @Override
    public String toString() {
        return "QuartzConfig{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", name='" + name + '\'' +
                ", cron='" + cron + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", quartzClass='" + quartzClass + '\'' +
                '}';
    }
}