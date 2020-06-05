package com.swufe.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.swufe.newapp.R;

public class RatecfgActivity extends AppCompatActivity {
    public  final  String TAG = " ConfigActivity ";
    EditText dollarText;
    EditText euroText;
    EditText wonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratecfg);

        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro2   = intent.getFloatExtra("euro_rate_key", 0.0f);
        float won2    = intent.getFloatExtra("won_rate_key",   0.0f);
        Log.i(TAG," onCreate: dollar2= "+dollar2);
        Log.i(TAG," onCreate: euro2= "+euro2);
        Log.i(TAG," onCreate:won2= "+won2);
        dollarText = (EditText)findViewById(R.id.dollar_rate);
        euroText = (EditText)findViewById(R.id.euro_rate);
        wonText = (EditText)findViewById(R.id.won_rate);
        dollarText.setText(String.valueOf(dollar2));
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));
    }
    public  void save(View btn){
        Log.i("cfg","save:");
        float newDollar = Float.parseFloat(dollarText.getText().toString());
        float newEuro = Float.parseFloat(euroText.getText().toString());
        float newWon = Float.parseFloat(wonText.getText().toString());
        Log.i(TAG, "获取到新的值");
        Log.i(TAG," onCreate: dollar2= "+newDollar);
        Log.i(TAG," onCreate: euro2= "+newEuro);
        Log.i(TAG," onCreate:won2= "+newWon);
        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);
        intent.putExtras(bdl);
        setResult(2,intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
