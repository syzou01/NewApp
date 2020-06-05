package com.swufe.newapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateListActivity extends ListActivity implements  Runnable{
    // String data[] =  {"one"};
    Handler handler;
    String TAG = "Rate";
    private  String logDate="";
    private final String DATE_SP_KEY="lastRateDateStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_rate_list); 父类已包含页面布局，不需要填充

        List<String> list1 = new ArrayList <String>();
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);
        setListAdapter(adapter);

        Thread thread = new Thread(this);
        thread.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==7){
                    List<String> list2 = (List<String>)msg.obj;
                    ListAdapter  adapter =  new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    @Override
    public  void run(){
        //获取网络数据，放入到list中，带回主线程
        List<String> retList = new ArrayList <String>();

        //从网络中获取数据
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();  // 使用此方法时，不用再写上方获取网络信息的语句
            Log.i(TAG,"run:  "+ doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table1 = tables.get(0);
            Elements tds = table1.getElementsByTag("td");

            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG,"run: "+str1+"==>"+val);
                retList.add(str1+"==>"+val);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
