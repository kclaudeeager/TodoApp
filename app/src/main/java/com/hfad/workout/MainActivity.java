package com.hfad.workout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hfad.workout.Adapters.TodoListAdapter;
import com.hfad.workout.DAO.TodoDao;
import com.hfad.workout.Databases.TodoDatabase;
import com.hfad.workout.R;
import com.hfad.workout.models.TodoItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
       {
           public static  ArrayList<TodoItem> todoItemsToUse;
           public static TodoDao todoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
        todoItemsToUse=new ArrayList<>();
        AsyncTask.execute(()->{
            TodoDatabase db = Room.databaseBuilder(this,
                    TodoDatabase.class, "TodoDatabase").build();
            todoDao = db.todoDao();
        } );
        AsyncTask.execute(()-> {
            for (TodoItem todoitem:todoDao.getAll()) {
                todoItemsToUse.add(todoitem);
                Log.i("Saved: ",todoitem.toString());
            }
                });

        TodoListAdapter adapter = new TodoListAdapter(todoItemsToUse, this);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.Todo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setAdapter(adapter);
    }
    public void onShowDetails(View view) {

    }
public void onShowNewTodo(View view){
    Intent intent;
    intent = new Intent(this,
            NewTodo.class);
    startActivity(intent);
}

           @Override
           protected void onStart() {
               super.onStart();
               todoItemsToUse=new ArrayList<>();
               AsyncTask.execute(()-> {
                   for (TodoItem todoitem:todoDao.getAll()) {
                       if(!todoItemsToUse.contains(todoitem))
                       todoItemsToUse.add(todoitem);
                       Log.i("Saved: ",todoitem.toString());
                   }

               });
               TodoListAdapter adapter = new TodoListAdapter(todoItemsToUse, this);
               RecyclerView recyclerView=(RecyclerView) findViewById(R.id.Todo_list);
               recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
               recyclerView.setVerticalScrollBarEnabled(true);
               recyclerView.setAdapter(adapter);
           }
       }