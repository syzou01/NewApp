package com.swufe.newapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreCountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_count);
        Log.i("ScoreCount", "onCreate: ");
    }

    TextView score1=(TextView)findViewById(R.id.score1);
    TextView score2=(TextView)findViewById(R.id.score2);


    public void btn1(View v) {
        if(v.getId()==R.id.btn1a)
            show1(1);
        else
            show2(1);
    }

    public void btn2(View v) {
        if(v.getId()==R.id.btn2a)
            show1(2);
        else
            show2(2);
    }

    public void btn3(View v) {
        if(v.getId()==R.id.btn3a)
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
        String oldScore = (String) score1.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore) + i);
        score1.setText(newScore);
    }

    private void show2(int i){
        String oldScore = (String) score2.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore) + i);
        score2.setText(""+ newScore);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ScoreCount", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ScoreCount", "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("ScoreCount", "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ScoreCount", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ScoreCount", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ScoreCount", "onDestroy: ");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea = ((TextView)findViewById(R.id.score1)).getText().toString();
        String scoreb = ((TextView)findViewById(R.id.score2)).getText().toString();

        Log.i("ScoreCount", "onSaveInstanceState: ");
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score");
        String scoreb = savedInstanceState.getString("teamb_score");

        Log.i("ScoreCount", "onRestoreInstanceState: ");
        ((TextView)findViewById(R.id.score1)).setText(scorea);
        ((TextView)findViewById(R.id.score2)).setText(scoreb);
    }

}