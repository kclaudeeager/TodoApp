package com.hfad.workout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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
       getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setDisplayShowTitleEnabled(true);
       getSupportActionBar().setIcon(R.drawable.w_logo);
        final   Handler handler=new Handler();
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LayoutInflater layoutInflater=LayoutInflater.from(MainActivity.this);
                FrameLayout frameLayout=findViewById(R.id.frameLayout);
                View view;
                int total_task=todoItemsToUse.size();
                int active_tasks=0;
                int done_tasks=0;
                for (TodoItem todoItem:todoItemsToUse){
                    if (todoItem.isDone()){
                        done_tasks++;
                    }
                    else
                        active_tasks++;
                }
                if(todoItemsToUse.size()==0){
                   view=layoutInflater.inflate(R.layout.zero_task,frameLayout);

                }
                else{

                   view= layoutInflater.inflate(R.layout.with_task,frameLayout);
                    TextView total_active_view= (TextView) view.findViewById(R.id.total_active);
                    TextView total_tasks_view= (TextView) view.findViewById(R.id.total_with_task);
                    TextView total_done_view= (TextView) view.findViewById(R.id.total_done);
                    total_active_view.setText(""+active_tasks);
                    total_tasks_view.setText(""+total_task);
                    total_done_view.setText(""+done_tasks);

                }
            }
        },2000);

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