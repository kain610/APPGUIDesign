package com.example.guidesign.ui.gallery;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class showviewaction extends ViewModel {

    private MutableLiveData<String> mText;



    public showviewaction() {


        mText = new MutableLiveData<>();
        mText.setValue("123!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
