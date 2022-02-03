package com.hfad.workout.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName="TodoItem")
public class TodoItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
     private int id;
    @ColumnInfo(name = "title")
    private String title;

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", periority='" + periority + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", done=" + done +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPeriority(String periority) {
        this.periority = periority;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @ColumnInfo(name = "description")
  private  String description;
    @ColumnInfo(name = "periority")
  private   String periority;
    @ColumnInfo(name = "created_at")
    private Date createdAt;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;
    @ColumnInfo(name = "done")
    private boolean done;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPeriority() {
        return periority;
    }

    public boolean isDone() {
        return done;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
//    public TodoItem(int id, String title, String description, String periority, Date createdAt, Date modifieddAt) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.periority = periority;
//        this.createdAt = createdAt;
//        this.updatedAt = modifieddAt;
//    }
//    @Ignore
//    public TodoItem(String title, String description, String periority) {
//        this.title = title;
//        this.description = description;
//        this.periority = periority;
//    }
//    @Ignore
//    public TodoItem(int id, String title, String description, String periority) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.periority = periority;
//    }

}
