package com.hfad.workout.Databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.hfad.workout.Converters;
import com.hfad.workout.DAO.TodoDao;
import com.hfad.workout.models.TodoItem;

@Database(entities = {TodoItem.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();

}