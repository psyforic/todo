package com.bawp.todoister.main.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedModel extends ViewModel {
    private boolean edit;
    private final MutableLiveData<Task> selectedItem = new MutableLiveData<>();

    public void setSelectedItem(Task task) {
        selectedItem.setValue(task);
    }

    public LiveData<Task> getSelectedItem() {
        return selectedItem;
    }

    public void setEdit(boolean edit) {
        this.edit=edit;
    }

    public boolean getIsEdited() {
        return edit;
    }
}
