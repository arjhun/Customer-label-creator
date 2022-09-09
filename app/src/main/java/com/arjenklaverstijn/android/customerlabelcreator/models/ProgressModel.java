package com.arjenklaverstijn.android.customerlabelcreator.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgressModel extends ViewModel {

    // Create a LiveData with a String
    private MutableLiveData<Integer> progress;

    public MutableLiveData<Integer> getCurrentProgress() {
        if (progress == null) {
            progress = new MutableLiveData<Integer>();
            progress.setValue(0);
        }
        return progress;
    }

    public void increment() {
        getCurrentProgress().setValue(getCurrentProgress().getValue() + 1);
    }
}
