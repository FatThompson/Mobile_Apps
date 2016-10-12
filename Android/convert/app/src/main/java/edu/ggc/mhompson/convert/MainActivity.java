package edu.ggc.mhompson.convert;

import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final  Button convert = (Button) findViewById(R.id.btnconvert);
        final TextView output = (TextView) findViewById(R.id.convertOutput);

        convert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                double original = 100.0D;
                double rate = 0.33333333D;
                FetchAsyncTask task = new FetchAsyncTask();
                task.execute("USB","GBP");
                //is API24
                //DecimalFormat formater = new DecimalFormat("#,###.00");
                output.setText((original*rate) + "");

            }
        });
    }
}
