package com.swufe.newapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RateActivity extends AppCompatActivity implements View.OnClickListener,Runnable {

    Handler handler;
    private TextView mTextView;
    private final String TAG = "Rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;
    private  String updateDate = "";

    EditText rmb;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Log.i(TAG, "onCreate: ");



        rmb = (EditText) findViewById(R.id.rmb);
        show = (TextView) findViewById(R.id.show);

        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);

        dollarRate = sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate = sharedPreferences.getFloat("won_rate",0.0f);

        Log.i(TAG, "onCreate: sp dollarRate=" + dollarRate);
        Log.i(TAG, "onCreate: sp euroRate=" + euroRate);
        Log.i(TAG, "onCreate: sp wonRate=" + wonRate);

        //获取当前系统时间
        // new Date();  //或者
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //"yyyyMMdd" 也行，小写的m表示分钟
        final String todayStr = sdf.format(today);

        Log.i(TAG,"OnCreat: sp dollarRate=" +dollarRate);
        Log.i(TAG,"OnCreat: sp euroRate=" +euroRate);
        Log.i(TAG,"OnCreat: sp wonRate=" +wonRate);
        Log.i(TAG,"OnCreat: sp updateDate=" +updateDate);
        Log.i(TAG,"OnCreat: sp todayStr=" +todayStr);

        ///判断时间
        if(!todayStr.equals(updateDate)){
            Log.i(TAG,"OnCreat: 需要更新");
            //开启子线程
            Thread t = new Thread(this);
            t.start();
        }else {
            Log.i(TAG,"OnCreat: 不需要更新");
        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==5){
                    Bundle bd1= (Bundle) msg.obj;
                    dollarRate=bd1.getFloat("dollar-rate");
                    euroRate=bd1.getFloat("euro-rate");
                    wonRate=bd1.getFloat("won-rate");

                    Log.i(TAG,"handleMessage: dollarRate:"+dollarRate);
                    Log.i(TAG,"handleMessage: euroRate:"+euroRate);
                    Log.i(TAG,"handleMessage: wonRate:"+wonRate);

                    //保存更新的日期
                    SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat("dollar_rate",dollarRate);
                    editor.putFloat("euro_rate",euroRate);
                    editor.putFloat("won_rate",wonRate);
                    editor.putString("update_date",todayStr);
                    editor.apply();

                    Toast.makeText(RateActivity.this,"汇率已经更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        }; //重写方法，要有;
    }

    @Override
    public void onClick(View btn) {
        String str = rmb.getText().toString();
        Log.i(TAG, "onClick: get str=" + str);

        float r = 0;
        if(str.length()>0){
            r = Float.parseFloat(str);
        }else{
            //用户没有输入内容
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i(TAG, "onClick: r=" + r);

        DecimalFormat df = new DecimalFormat("#.00");

        //计算
        if(btn.getId()==R.id.dollar){
            show.setText(df.format(r*dollarRate));
        }else if(btn.getId()==R.id.euro){
            show.setText(df.format(r*euroRate));
        }else{
            show.setText(df.format(r*wonRate));
        }
    }

    public void openOne(View btn){
        Openconfig();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==2){

            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.1f);
            euroRate = bundle.getFloat("key_euro",0.1f);
            wonRate = bundle.getFloat("key_won",0.1f);
            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
        }

        //将新设置的汇率写到SP里
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("dollar_rate",dollarRate);
        editor.putFloat("euro_rate",euroRate);
        editor.putFloat("won_rate",wonRate);
        editor.commit();
        Log.i(TAG, "onActivityResult: 数据已保存到sharedPreferences");

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_config){
            Openconfig();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Openconfig() {
        Intent config = new Intent(this, RatecfgActivity.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);

        Log.i(TAG, "openOne: dollarRate=" + dollarRate);
        Log.i(TAG, "openOne: euroRate=" + euroRate);
        Log.i(TAG, "openOne: wonRate=" + wonRate);

        //startActivity(config);
        startActivityForResult(config, 1);
    }

    @Override
    public void run() {
        Log.i(TAG,"run:run()...");
        //当前停止两秒钟（演示）
        for (int i=1;i<3;i++){
            Log.i(TAG,"run: i="+i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*URL url = null;
        try {
            url = new URL("http://www.usd-cny.com/icbc.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG, "run: html=" + html);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Bundle bundle ;
        bundle = getFromBOC();
        //bundle中保存所获取的汇率
        //获取MSG对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.obj = "Hello from run()";
        msg.obj = bundle;
        handler.sendMessage(msg);
    }

    /*private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }*/

    private Bundle getFromBOC( ) {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();  // 使用此方法时，不用再写上方获取网络信息的语句
            // doc = Jsoup.parse(html);
            Log.i(TAG,"run:  "+ doc.title());
            Elements tables = doc.getElementsByTag("table");
//            int i = 1;
////            for(Element table : tables){
////                Log.i(TAG,"run:  table["+i+"]="+ table);
////                i++;
////            }
            Element table1 = tables.get(0);
            // Log.i(TAG,"run: table1 = "+ table1);
            //获取TD中的数据
            Elements tds = table1.getElementsByTag("td");
            //Log.i(TAG,"run:  text="+tds.text());
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                Log.i(TAG,"run: "+td1.text() + "==>"+td2.text());
                String str1 = td1.text();
                String val = td2.text();
                if("美元".equals(str1)){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));
                }
                else if ("欧元".equals(str1)){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));
                }
                else if("韩元".equals(str1)){
                    bundle.putFloat("won-rate",100f/Float.parseFloat(val));
                }
            }
//            for(Element td : tds){
//               // Log.i(TAG,"run: td="+td);
//                Log.i(TAG,"run: text="+td.text());
//                Log.i(TAG,"run: html="+td.html());
//
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bundle;
    }

}