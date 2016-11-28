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
    public static final String PREFS_NAME = TAG + "MyPrefsFile";
    public static final String LIST_NAME = TAG  + "list_of_years";
    public ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate);

        loadData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }
    @Override
    protected void onStop(){
        super.onStop();
        saveData();
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
     * Loads the data
     */
    private void loadData() {
        // Restore preferences
        SharedPreferences sharedData;
        data = new ArrayList<String>();
        try {
            sharedData = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

//          data = new ArrayList(sharedData.getStringSet(LIST_NAME,null));

            ArrayList<String> tempData = new ArrayList<String>();
            try {
                int length = sharedData.getInt(LIST_NAME, MODE_PRIVATE);
                for (int i=0; i<length; i++){
                    data.add(sharedData.getString(LIST_NAME+i+"",null));
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }catch(NullPointerException npe){
            Log.e(TAG,"NULL POINTER EXECEPTION");
        }
        catch(Exception e){
            Log.e(TAG,"SharedData Does not Exist");
        }

        if(data.size()!=0) {
            //display the data
            displayData();
        }
    }

    /**
     * saves the data to a shared preference
     * this is from the Android API
     */
    public void saveData() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        try {
            SharedPreferences prefData = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefData.edit();
//        editor.putStringSet(LIST_NAME,new HashSet<String>(data));

            editor.putInt(LIST_NAME, data.size() );
            for (int i = 0; i < data.size(); i++) {
                editor.putString(LIST_NAME + i + "", data.get(i));
            }

            // Commit the edits!
            editor.commit();

        }catch(Exception e){
            e.printStackTrace();
        }
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


}
