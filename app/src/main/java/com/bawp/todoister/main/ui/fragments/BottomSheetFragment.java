package com.bawp.todoister.main.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawp.todoister.R;
import com.bawp.todoister.main.model.Priority;
import com.bawp.todoister.main.model.SharedModel;
import com.bawp.todoister.main.model.Task;
import com.bawp.todoister.main.model.TaskViewModel;
import com.bawp.todoister.main.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    @BindView(R.id.enter_todo_et)
    EditText enterToDo;
    @BindView(R.id.today_calendar_button)
    ImageButton calenderButton;
    @BindView(R.id.priority_todo_button)
    ImageButton priorityButton;
    @BindView(R.id.radioGroup_priority)
    RadioGroup priorityRadioGroup;
    @BindView(R.id.calendar_view)
    CalendarView calendarView;
    @BindView(R.id.calendar_group)
    Group calendarGroup;
    @BindView(R.id.save_todo_button)
    ImageButton saveButton;

    @BindView(R.id.tomorrow_chip)
    Chip tomorrow_chip;
    @BindView(R.id.next_week_chip)
    Chip next_week_chip;
    @BindView(R.id.today_chip)
    Chip today_chip;

    private RadioButton selectedRadioButton;
    private int selectedButtonIndex;
    private Unbinder unbinder;
    private Date dueDate;
    private Calendar calendar = Calendar.getInstance();
    private SharedModel sharedModel;
    private boolean edit;
    private Priority priority;

    public BottomSheetFragment() {

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedModel = new ViewModelProvider(requireActivity())
                .get(SharedModel.class);
        enterToDo.setText("");
        if (sharedModel.getSelectedItem().getValue() != null) {
            Task task = sharedModel.getSelectedItem().getValue();
            enterToDo.setText(task.getTask());
        }
        onClickListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedModel.getSelectedItem().getValue() != null) {
            edit = sharedModel.getIsEdited();
            Task task = sharedModel.getSelectedItem().getValue();
            enterToDo.setText(task.getTask());
        }
    }

    public void onClickListeners() {
        calenderButton.setOnClickListener(v -> {
            calendarGroup.setVisibility(calendarGroup.getVisibility()
                    == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideKeyBoard(v);
        });
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
            Log.d("Cal", "OnViewCreated:===>month" + (month + 1) + ",dayOfMonth");
        });
        priorityButton.setOnClickListener(v -> {
            Utils.hideKeyBoard(v);
            priorityRadioGroup.setVisibility(priorityRadioGroup.getVisibility()
                    == View.GONE ? View.VISIBLE : View.GONE);
            priorityRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (priorityRadioGroup.getVisibility() == View.VISIBLE) {
                    selectedButtonIndex = checkedId;
                    selectedRadioButton = v.findViewById(selectedButtonIndex);
                    if (group.getCheckedRadioButtonId() == R.id.radioButton_high) {
                        priority = Priority.HIGH;
                    } else if (group.getCheckedRadioButtonId() == R.id.radioButton_med) {
                        priority = Priority.MEDIUM;
                    } else if (group.getCheckedRadioButtonId() == R.id.radioButton_low) {
                        priority = Priority.LOW;
                    } else {
                        priority = Priority.LOW;
                    }
                } else {
                    priority = Priority.LOW;
                }
            });
        });
        saveButton.setOnClickListener(v -> {
            String task = enterToDo.getText().toString().trim();
            if (!TextUtils.isEmpty(task) && dueDate != null && priority != null) {
                Task newTask = new Task(task, priority, dueDate, Calendar.getInstance().getTime(), false);
                if (!edit) {
                    TaskViewModel.insert(newTask);
                } else {
                    Task editTask = sharedModel.getSelectedItem().getValue();
                    editTask.setTask(task);
                    editTask.setDateCreated(Calendar.getInstance().getTime());
                    editTask.setPriority(priority);
                    editTask.setDate(dueDate);
                    TaskViewModel.update(editTask);
                    sharedModel.setEdit(false);
                }
                enterToDo.setText("");
                if (this.isVisible()) {
                    this.dismiss();
                }
            } else {
                Snackbar.make(saveButton, R.string.empty_field, BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
        today_chip.setOnClickListener(this);
        tomorrow_chip.setOnClickListener(this);
        next_week_chip.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.today_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 0);
                dueDate = calendar.getTime();
                break;
            case R.id.tomorrow_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                dueDate = calendar.getTime();
                break;
            case R.id.next_week_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                dueDate = calendar.getTime();
                break;
        }
    }
}