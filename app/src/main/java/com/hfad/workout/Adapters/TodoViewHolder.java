package com.hfad.workout.Adapters;
import static com.hfad.workout.MainActivity.todoDao;
import static com.hfad.workout.MainActivity.todoItemsToUse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.workout.DetailActivity;
import com.hfad.workout.R;
import com.hfad.workout.models.TodoItem;

import java.util.ArrayList;
import java.util.Date;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    TextView  titleTxt,periority,createdAt,updatedAt;
    CheckBox done;
    TodoListAdapter todoAdapter;
    TodoItem todoItem;
    Button view;
    public TodoViewHolder(@NonNull View itemView,Context context) {

        super(itemView);
        view=(Button) itemView.findViewById(R.id.watch);
        titleTxt=(TextView) itemView.findViewById(R.id.title);
        periority=(TextView) itemView.findViewById(R.id.periority);
        done=(CheckBox) itemView.findViewById(R.id.done);
        createdAt=(TextView) itemView.findViewById(R.id.createdAt);
        updatedAt=(TextView) itemView.findViewById(R.id.updatedAt);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(context,
                        DetailActivity.class);
                intent.putExtra("todoItem",todoItem);
                context.startActivity(intent);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todoItem.setDone(done.isChecked());
                todoItem.setUpdatedAt(new Date());
                AsyncTask.execute(()-> {
                    todoDao.update(todoItem);
                });

            }
        });
    }
    public void BindStudent(final TodoItem todoItem){

        titleTxt.setText(todoItem.getTitle());
        periority.setText(todoItem.getPeriority());
        if (periority.getText().equals("High")){
            periority.setBackgroundResource(R.drawable.high_periority);
            periority.setTextColor(Color.parseColor(String.valueOf("#C1CF16")));
        }
        else if(periority.getText().equals("Medium")){
            periority.setBackgroundResource(R.drawable.medium_periority);
            periority.setTextColor(Color.parseColor(String.valueOf("#F4F5F6")));
        }
        else{
            periority.setBackgroundResource(R.drawable.low_periority);
            periority.setTextColor(Color.parseColor(String.valueOf("#0C0D0D")));
        }

        done.setChecked(todoItem.isDone());
        String created_at="",modified_at="";
        if(todoItem.getCreatedAt()!=null){
            created_at=todoItem.getCreatedAt().toString();
        }
        if(todoItem.getUpdatedAt()!=null){
         modified_at=todoItem.getUpdatedAt().toString();
        }
        createdAt.setText("Created at: "+ created_at);
        updatedAt.setText("Updated at: "+modified_at);
        this.todoItem=todoItem;
    }


}