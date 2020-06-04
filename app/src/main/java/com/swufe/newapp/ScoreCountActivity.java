package com.swufe.newapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreCountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_count);
    }


    public void btn1(View v) {
        if(v.getId()==R.id.btn1a)
            show1(1);
        else
            show2(1);
    }

    public void btn2(View v) {
        if(v.getId()==R.id.btn1a)
            show1(2);
        else
            show2(2);
    }

    public void btn3(View v) {
        if(v.getId()==R.id.btn1a)
            show1(3);
        else
            show2(3);
    }

    public void btnReset(View v) {
        TextView out = (TextView)findViewById(R.id.score1);
        out.setText("0");
        out = (TextView)findViewById(R.id.score2);
        out.setText("0");
    }

    private void show1(int i){
        TextView out = (TextView)findViewById(R.id.score1);
        String oldScore = (String) out.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore) + i);
        out.setText(newScore);
    }

    private void show2(int i){
        TextView out = (TextView)findViewById(R.id.score2);
        String oldScore = (String) out.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore) + i);
        out.setText(newScore);
    }
}