package ca.georgebrown.comp3074.pocketmealapp.ui.add_food;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddFoodViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddFoodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add food fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}