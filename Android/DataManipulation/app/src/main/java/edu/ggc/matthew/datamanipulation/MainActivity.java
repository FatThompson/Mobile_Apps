package edu.ggc.matthew.datamanipulation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "datamanipulation";
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String LIST_NAME = "list";
    public ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restore preferences
        SharedPreferences sharedData = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean silent = sharedData.getBoolean("silentMode", false);
//        setSilent(silent);
        data = new ArrayList(sharedData.getStringSet(LIST_NAME,null));

        //convert to arraylist and overirght with more data. (simi inifinte)
        Log.i(TAG,data.toString());


    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "Stopping");
        saveData();
    }

    /**
     * saves the data to a shared prefrence
     */
    public void saveData(){
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(LIST_NAME,new HashSet<String>(data));

        // Commit the edits!
        editor.commit();
    }

    /**
     * Addes data to the array list then saves.
     * @param dataStr
     */
    public void addData(String dataStr){
        data.add(dataStr);
        saveData();
    }
}
