package ca.georgebrown.comp3074.pocketmealapp.ui.profile_edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileEditViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProfileEditViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profile edit fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}