package com.bawp.todoister.main.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.todoister.R;
import com.bawp.todoister.main.model.Task;
import com.bawp.todoister.main.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Task> taskList;
    public final ToDoClickListener toDoClickListener;

    public RecyclerViewAdapter(List<Task> taskList, ToDoClickListener toDoClickListener) {
        this.taskList = taskList;
        this.toDoClickListener = toDoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        String formattedDate = Utils.formatDate(task.getDate());
        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_enabled}
        }, new int[]{
                Color.DKGRAY,//disabled
                Utils.priorityColor(task)
        });
        holder.todo.setText(task.getTask());
        holder.todayChip.setText(formattedDate);
        holder.todayChip.setTextColor(Utils.priorityColor(task));
        holder.todayChip.setChipIconTint(colorStateList);

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CheckBox radioButton;
        public TextView todo;
        public Chip todayChip;
        public CardView cardView;
        ToDoClickListener onTodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            todo = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            cardView = itemView.findViewById(R.id.todo_row_layout);
            this.onTodoClickListener = toDoClickListener;
            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Task selectedTask = taskList.get(getAdapterPosition());
            int id = v.getId();
            if (id == R.id.todo_row_layout) {
                toDoClickListener.onTodoClick(selectedTask);
            } else if (id == R.id.todo_radio_button) {
                toDoClickListener.onTodoRadioButton(selectedTask);
            }
        }
    }
}
