package ca.georgebrown.comp3074.pocketmealapp.ui.food;

import android.content.Context;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ca.georgebrown.comp3074.pocketmealapp.DBHelper;
import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;

public class FoodViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private DBHelper dbHelper;


    public FoodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Food List (sort by Km)");
    }

    public LiveData<String> getText() {
        return mText;
    }

}