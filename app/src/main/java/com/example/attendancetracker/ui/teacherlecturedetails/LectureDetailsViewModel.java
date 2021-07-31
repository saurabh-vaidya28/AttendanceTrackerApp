package com.example.attendancetracker.ui.teacherlecturedetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LectureDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LectureDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is lecture details fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
