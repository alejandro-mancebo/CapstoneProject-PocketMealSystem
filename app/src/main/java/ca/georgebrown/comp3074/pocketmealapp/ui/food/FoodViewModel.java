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
        mText.setValue("This is food list fragment");
       // dbHelper = new DBHelper();

    }

    public LiveData<String> getText() {
        return mText;
    }
  /*  public void setList(String username, double lon, double lat, ListView li, Context c){

        dbHelper = new DBHelper();
        dbHelper.getSpecificArrayList(username,lon,lat,li,c);

    }*/
}