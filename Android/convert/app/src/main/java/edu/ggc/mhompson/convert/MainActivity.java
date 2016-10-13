package edu.ggc.mhompson.convert;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the default spinner detail
        final Spinner baseSpinner =(Spinner) findViewById(R.id.base);
        final Spinner convertToSpinner =(Spinner) findViewById(R.id.convertTo);
//
//        this does not work
//        baseSpinner.setSelection(R.integer.baseStarter);
//        convertToSpinner.setSelection(R.integer.convertToStarter);

        //get the button off screen
        final Button convert = (Button) findViewById(R.id.btnConvert);

        //the brains
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //seperated to make it easier to read
                btnConvertOnClickListener();
            }
        });

    }

    /**
     * creates to listener for the Convert Button
     * Because ctl+y deletes lines in Linux, I had
     * To retype this method, so there are limited logs,
     * because there are no additional logs needed
     * in my opinion.
     */
    private void btnConvertOnClickListener(){
        //get the views off screen
        final TextView output = (TextView) findViewById(R.id.convertOutput);
        final EditText convertAmount = (EditText) findViewById(R.id.convertAmount);
        final Spinner baseSpinner =(Spinner) findViewById(R.id.base);
        final Spinner convertToSpinner =(Spinner) findViewById(R.id.convertTo);

        //defaults
        double original = 0;
        double conversionRate = 0;
        String baseText;
        String convertToText;
        String outputString;

        try{
            //get the original value
            original = Double.parseDouble(convertAmount.getText().toString());

            //get the currencies
            baseText = baseSpinner.getSelectedItem().toString();
            convertToText = convertToSpinner.getSelectedItem().toString();

            //run the converter
            FetchAsyncTask task = new FetchAsyncTask();
            task.execute(baseText,convertToText);
            conversionRate = task.get();

            //format
            DecimalFormat format = new DecimalFormat("#,###.00");
            outputString = format.format(original*conversionRate);

            //display data
            Log.i("MainActivity","Output:  "+outputString);
            output.setText(outputString);

        } catch (InterruptedException IE){
            IE.printStackTrace();
        } catch (ExecutionException EE) {
            EE.printStackTrace();
        }

    }
}