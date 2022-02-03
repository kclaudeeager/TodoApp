package com.hfad.workout;
import static com.hfad.workout.MainActivity.todoDao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.workout.models.TodoItem;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    EditText titleText,descriptionTxt;
    Spinner spinner;
    Button save,edit,delete;
    TextView created,modified,header;
    String title,descrition,periority;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleText=(EditText) findViewById(R.id.title_detail);
        descriptionTxt=(EditText) findViewById(R.id.tododescrition_detail);
        spinner=(Spinner) findViewById(R.id.periorities_detail);
        save=(Button) findViewById(R.id.update);
        delete=(Button) findViewById(R.id.delete);
        edit=(Button) findViewById(R.id.edit);
        created=(TextView) findViewById(R.id.created);
        modified=(TextView) findViewById(R.id.modified);
        checkBox=(CheckBox)findViewById(R.id.isDone) ;
        header=(TextView) findViewById(R.id.head);
        Intent intent = getIntent();
        TodoItem todoItem= (TodoItem) intent.getSerializableExtra("todoItem");
        header.setText(header.getText()+""+todoItem.getId());
        //Log.i("Todo: ",todoItem.toString());
        titleText.setText(todoItem.getTitle());
        descriptionTxt.setText(todoItem.getDescription());
        String created_at="",modified_at="";
        if(todoItem.getCreatedAt()!=null){
            created_at=todoItem.getCreatedAt().toString();
        }
        if(todoItem.getUpdatedAt()!=null){
            modified_at=todoItem.getUpdatedAt().toString();
        }
        created.setText("Created at: "+ created_at);
        modified.setText("Updated at: "+modified_at);
        checkBox.setChecked(todoItem.isDone());
        String [] periorities={"High","Low","Medium"};
        for(int i=0; i < periorities.length; i++) {
            if(todoItem.getPeriority().trim().equals(periorities[i].toString())){
                spinner.setSelection(i);
                break;
            }
        }
        spinner.setEnabled(false);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this,"Deleting Todo item "+todoItem.getId(),Toast.LENGTH_SHORT).show();
                AsyncTask.execute(()-> {
                    todoDao.delete(todoItem);
                    DetailActivity.this.finish();
                });
            }
        });
edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        titleText.setEnabled(true);
        descriptionTxt.setEnabled(true);
        spinner.setEnabled(true);
        checkBox.setEnabled(true);
        save.setEnabled(true);
    }
});
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Date created_at,modified_at;
        created_at=todoItem.getCreatedAt();
        modified_at=new Date();
        title=titleText.getText().toString();
        descrition=descriptionTxt.getText().toString();
        periority=spinner.getSelectedItem().toString();
        boolean isDone=checkBox.isChecked();
        todoItem.setUpdatedAt(modified_at);
        created.setText("Updated at: "+modified_at);
        todoItem.setCreatedAt(created_at);
        todoItem.setTitle(title);
        todoItem.setDone(isDone);
        todoItem.setPeriority(periority);
        todoItem.setDescription(descrition);
        AsyncTask.execute(()-> { todoDao.update(todoItem);});
        Toast.makeText(DetailActivity.this,"Updated Todo item "+todoItem.getId(),Toast.LENGTH_SHORT).show();
    }
});

    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
               DetailActivity.this.finish();

        }
        return true;
    }
}
