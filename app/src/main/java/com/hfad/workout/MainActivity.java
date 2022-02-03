package com.hfad.workout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
           TodoListAdapter adapter ;
           RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
       getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setDisplayShowTitleEnabled(true);
       getSupportActionBar().setIcon(R.drawable.w_logo);
        todoItemsToUse=new ArrayList<>();
     Compute();
    }

public void onShowNewTodo(View view){
    Intent intent;
    intent = new Intent(this,
            NewTodo.class);
    startActivity(intent);
}

           @Override
           protected void onResume() {
               super.onResume();

           }
public void Compute(){
    todoItemsToUse.clear();
    todoItemsToUse=new ArrayList<>();

    final   Handler handler=new Handler();

    AsyncTask.execute(()->{
        TodoDatabase db = Room.databaseBuilder(this,
                TodoDatabase.class, "TodoDatabase").build();
        todoDao = db.todoDao();
        for (TodoItem todoitem:todoDao.getAll()) {
            if (!todoItemsToUse.contains(todoitem))
                todoItemsToUse.add(todoitem);
            Log.i("Saved: ",todoitem.toString());
        }
    } );
    adapter = new TodoListAdapter(todoItemsToUse, this);
    recyclerView=(RecyclerView) findViewById(R.id.Todo_list);
    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    recyclerView.setVerticalScrollBarEnabled(true);
    recyclerView.setAdapter(adapter);
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            LayoutInflater layoutInflater=LayoutInflater.from(MainActivity.this);
            FrameLayout frameLayout=findViewById(R.id.frameLayout);
            frameLayout.removeAllViews();
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
                Button start=(Button) view.findViewById(R.id.newT);
                start.setBackgroundColor(Color.BLACK);
                start.setBackgroundResource(R.drawable.high_periority);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        intent = new Intent(MainActivity.this,
                                NewTodo.class);
                        startActivity(intent);
                    }
                });

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


}
           @Override
           protected void onRestart() {
               super.onRestart();
               //adapter.notifyDataSetChanged();
              Compute();

           }

           @Override
           protected void onStart() {
               super.onStart();

           }
       }