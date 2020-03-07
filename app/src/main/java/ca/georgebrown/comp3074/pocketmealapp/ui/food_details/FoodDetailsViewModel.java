package ca.georgebrown.comp3074.pocketmealapp.ui.food_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FoodDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is food details fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}