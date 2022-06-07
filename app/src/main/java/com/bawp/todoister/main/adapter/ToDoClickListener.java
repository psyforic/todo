package com.bawp.todoister.main.adapter;

import com.bawp.todoister.main.model.Task;

public interface ToDoClickListener {
    void onTodoClick(Task task);
    void onTodoRadioButton(Task task);
}
