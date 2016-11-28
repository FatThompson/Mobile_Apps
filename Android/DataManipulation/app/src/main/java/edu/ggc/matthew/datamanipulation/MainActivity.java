package edu.ggc.matthew.datamanipulation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "datamanipulation";
    public static final String PREFS_NAME = TAG + "MyPrefsFile";
    public static final String LIST_NAME = TAG  + "list_of_years";
    public ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restore preferences
        loadData();

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG,"in onRestart");
        loadData();
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG,"in onResume");
        loadData();
    }

    @Override
    protected void onStop(){
        super.onStop();
        saveData();
    }

    /**
     * Adds data to the array list then saves.
     * @param dataStr
     */
    private void addData(String dataStr){
        data.add(dataStr);
        saveData();
        displayData();
    }

    /**
     * removes all the data
     */
    private void clearData(){
        data.clear();
        saveData();
        displayData();
    }
    public void clearData(View view){clearData();}

    /**
     *  displays the data to the screen
     */
    private void displayData(){
        ListView listView = (ListView) findViewById(R.id.listViewYears);
        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data));

        Log.i(TAG, data.toString());
    }
    /**
     * generates the random year.
     * Has possiblity for recursive infinite loop.
     */
    public void generateData(){
        int rndYear = 1;
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        //create and check date
        while(!isLeapYear(rndYear)){
            //based on the first date of the leap year eligibility
            rndYear = new Random().nextInt(curYear - 1582) + 1582;
        }
        addData(rndYear + "");
        saveData();
    }

    /**
     * the callable for the buttons
     * @param view
     */
    public void generateData(View view) {
        generateData();
        saveData();
    }

    /**
     * checks the year
     * @param year
     * @return
     */
    private boolean isLeapYear(int year){
        return ((year%4 == 0 && year % 100 !=0)||year%400==0);
    }

    /**
     * Loads the data from the database
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
     * Opens the next activity
     * @param view
     */
    public void openManipulate(View view) {
        Intent intent = new Intent(MainActivity.this, ManipulateActivity.class);
        startActivity(intent);
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

}
