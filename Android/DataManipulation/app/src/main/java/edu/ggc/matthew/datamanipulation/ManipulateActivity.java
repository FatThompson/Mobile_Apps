package edu.ggc.matthew.datamanipulation;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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
        ListView listView = (ListView) findViewById(R.id.listViewYears);
        listView.setAdapter(new ArrayAdapter<String>(ManipulateActivity.this, android.R.layout.simple_list_item_1, data));
    }
}
