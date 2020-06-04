package com.swufe.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.swufe.newapp.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView out;
    EditText inp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out=(TextView)findViewById(R.id.output);
        inp=(EditText)findViewById(R.id.input);
        Button btn = (Button)findViewById(R.id.button);
    }

    @Override
    public void onClick(View v) {
        Log.i("click","click...");
        String str = inp.getText().toString();
        double num = Float.parseFloat(str)*1.8+32;
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        out.setText(df.format(num));
    }

    public void btnClick(View btn){
        Log.i("click","click called..");
    }
}
