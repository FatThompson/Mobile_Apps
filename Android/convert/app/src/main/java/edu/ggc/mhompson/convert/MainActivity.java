package edu.ggc.mhompson.convert;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

                //get and Caculate the convertion
                double convertionRate = 0;
                try{
                    FetchAsyncTask task = new FetchAsyncTask();
                    task.execute("USD","GBP");
                    convertionRate = task.get();
                }catch (ExecutionException EE){
                    Log.e("CurrConv","ExecutionException:  "+EE);
                }
                catch(Exception E){
                    Log.e("CurrConv","Other Exception:  "+E);
                }
                finally {
                    Log.i("CurrConv","Conversion Rate:  "+convertionRate);
                }

                //Format the data and post
                DecimalFormat formater = new DecimalFormat("#,###.00");
                output.setText(formater.format(original*convertionRate));

            }
        });
    }
}
