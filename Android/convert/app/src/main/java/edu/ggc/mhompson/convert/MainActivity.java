package edu.ggc.mhompson.convert;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button convert = (Button) findViewById(R.id.btnConvert);
        final TextView output = (TextView) findViewById(R.id.convertOutput);
        final EditText convertAmount = (EditText) findViewById(R.id.convertAmount);
        final Spinner baseSpinner =(Spinner) findViewById(R.id.base);
        final Spinner convertToSpinner =(Spinner) findViewById(R.id.convertTo);




        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //defaults
                double original = 0;
                double conversionRate = 0;
                String baseText;
                String convertToText;


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

                    //format and display data
                    DecimalFormat format = new DecimalFormat("#,###.00");
                    output.setText(format.format(original*conversionRate+""));

                } catch (InterruptedException IE){
                    IE.printStackTrace();
                } catch (ExecutionException EE) {
                    EE.printStackTrace();
                }
            }
        });
    }
}