package mthompson.ggc.edu.palandrone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

public class PalandroneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palandrone);

        final ImageView ivImage = (ImageView) findViewById(R.id.ivIcon);
        final EditText etInput = (EditText) findViewById(R.id.etInput);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ivImage.setImageResource(
                        PalandroneUtil.isPalandrone(etInput.toString())?
                                R.drawable.yes :
                                R.drawable.no);
            }
        };

        etInput.addTextChangedListener(watcher);
    }


}
