package edu.ggc.matthew.datamanipulation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ManipulateActivity extends AppCompatActivity {
    public static final String TAG = "datamanipulation";
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String LIST_NAME = "list";
    public ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate);

        // Restore preferences
        SharedPreferences sharedData = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        data = new ArrayList(sharedData.getStringSet(LIST_NAME,null));
        Log.i(TAG,data.toString());
        displayData();
    }
    /**
     *  displays the data to the screen
     */
    private void displayData(){
        saveData();
        ListView listView = (ListView) findViewById(R.id.listViewYears);
        listView.setAdapter(new ArrayAdapter<String>(ManipulateActivity.this, android.R.layout.simple_list_item_1, data));
    }

    /**
     * sort by ascend
     */
    private void sortASC(){
        Collections.sort(data);
        displayData();
    }
    public void sortASC(View view){sortASC();}

    /**
     * sort by descend
     */
    private void sortDESC(){
        Collections.sort(data);
        Collections.reverse(data);
        displayData();
    }
    public void sortDESC(View view){sortDESC();}

    /**
     * random
     */
    private void sort_shuffle(){
        Collections.shuffle(data);
        displayData();
    }
    public void sort_shuffle(View view){sort_shuffle();}
    /**
     * saves the data to a shared preference
     * this is from the Android API
     */
    public void saveData(){
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences prefData = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefData.edit();
        editor.putStringSet(LIST_NAME,new HashSet<String>(data));

        // Commit the edits!
        editor.commit();
    }

}
