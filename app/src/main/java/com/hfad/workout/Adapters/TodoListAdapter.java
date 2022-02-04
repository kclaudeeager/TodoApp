package com.hfad.workout.Adapters;
import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

import com.hfad.workout.R;
import com.hfad.workout.models.TodoItem;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    ArrayList<TodoItem> todoItems;
    TodoViewHolder todoViewHolder;
    Context context;
    public TodoListAdapter(ArrayList<TodoItem> todoItems, TodoViewHolder todoViewHolder) {
        this.todoItems = todoItems;
        this.todoViewHolder = todoViewHolder;
    }

    public TodoListAdapter(TodoViewHolder todoViewHolder) {
        this.todoViewHolder= todoViewHolder;
    }

    public TodoListAdapter(ArrayList<TodoItem> todoItems,Context context) {
        this.todoItems = todoItems;
        this.context=context;
        //Log.d("add: ",students.get(1).getFname());
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item,parent,false);
        return new TodoViewHolder(view,parent.getContext());

    }

    public TodoListAdapter() {
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        final TodoItem todoItem=todoItems.get(position);
        holder.BindStudent(todoItem);
        todoViewHolder=holder;
    }
public void updateList(ArrayList<TodoItem> todoItemArrayList){
        todoItems=todoItemArrayList;
        notifyDataSetChanged();
}
    @Override
    public int getItemCount() {
        return todoItems.size();
    }
}