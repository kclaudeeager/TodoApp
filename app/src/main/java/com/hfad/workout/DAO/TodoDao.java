package com.hfad.workout.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hfad.workout.models.TodoItem;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM TodoItem")
    List<TodoItem> getAll();

    @Query("SELECT * FROM TodoItem WHERE id IN (:todoIds)")
    List<TodoItem> loadAllByIds(int[] todoIds);

    @Query("SELECT * FROM TodoItem WHERE title LIKE :title LIMIT 1")
    TodoItem findByTitle(String title);
    @Query("SELECT * FROM TodoItem WHERE id=:id")
    TodoItem findById(String id);
    @Insert
    void insertAll(TodoItem... todoItems);
    @Insert
    void insertTodo(TodoItem todoItem);
    @Update
    void update(TodoItem todoItem);

    @Delete
    void delete(TodoItem todoItem);
    @Delete
    void deleteAll(TodoItem... todoItems);
}