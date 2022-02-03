package com.hfad.workout;

import static com.hfad.workout.MainActivity.todoDao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hfad.workout.models.TodoItem;

import java.util.Date;

public class NewTodo extends AppCompatActivity {
   EditText titleText,descriptionTxt;
   Spinner spinner;
   Button save;
   String title,descrition,periority;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setIcon(R.drawable.w_logo);
        titleText=(EditText) findViewById(R.id.todotitle);
        descriptionTxt=(EditText) findViewById(R.id.tododescrition);
        spinner=(Spinner) findViewById(R.id.periorities);
        save=(Button) findViewById(R.id.update);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=titleText.getText().toString();
                descrition=descriptionTxt.getText().toString();
                periority=spinner.getSelectedItem().toString();
                if(title.isEmpty()|| descrition.isEmpty()){
                    Toast.makeText(NewTodo.this,"Not allowed to save empty",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    TodoItem todoItem=new TodoItem();
                    todoItem.setPeriority(periority);
                    todoItem.setCreatedAt(new Date());
                    todoItem.setUpdatedAt(new Date());
                    todoItem.setDone(false);
                    todoItem.setTitle(title);
                    todoItem.setDescription(descrition);
                    AsyncTask.execute(()-> {
                        todoDao.insertTodo(todoItem);
                        for (TodoItem todoitem:todoDao.getAll()) {
                            Log.i("Saved: ",todoitem.toString());
                        }

                        //NewTodo.this.finish();
                    });
                    Toast.makeText(NewTodo.this,"New todo is created!",Toast.LENGTH_SHORT).show();
                    NewTodo.this.finish();
                }
            }
        });
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                NewTodo.this.finish();

        }
        return true;
    }
}