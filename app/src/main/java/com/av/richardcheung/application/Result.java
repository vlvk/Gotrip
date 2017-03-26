package com.av.richardcheung.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Result extends AppCompatActivity {

    List<HashMap<String, Object>> items = null;
    SimpleAdapter mSchedule;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //绑定XML中的ListView，作为Item的容器
        list = (ListView) findViewById(R.id.MyListView);

        //生成动态数组，并且转载数据
        items = getListData(0);

        //生成适配器，数组 --> ListItem
        mSchedule = new SimpleAdapter(this,
                items,//数据来源
                R.layout.my_listitems,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[]{"name", "describe", "price"},

                //ListItem的XML文件里面的两个TextView ID
                new int[]{R.id.ItemTitle, R.id.ItemText, R.id.ItemPrice});
        //添加并且显示
        list.setAdapter(mSchedule);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(position);
            }
        });
    }

    private List<HashMap<String, Object>> getListData( int mode ) {
        List<HashMap<String, Object>> Data = new ArrayList<>();
        String url = "http://192.168.1.103/?uid=1&pwd=root";
//      MyHttp myHttp = new MyHttp();
//      String retStr = myHttp.httpGet(url);
        String retStr = "{ \"train\":[ { \"name\":\"火车一\", \"describe\":\"省时间\", \"ways\":[{\"data\": \"3-19\",\"time\": \"20Hour\",\"num\":\"G1737\"," +
                "\"StartTime\":\"17:48\",\"EndTime\":\"20:47\",\"StartPlace\":\"南京南\",\"EndPlace\":\"武汉\"},{\"data\": \"3-19\",\"time\": \"21Hour\",\"num\":\"G1756\"," +
                "\"StartTime\":\"17:56\",\"EndTime\":\"20:58\",\"StartPlace\":\"南京南\",\"EndPlace\":\"武汉\"}], \"price\":\"100\" }, { \"name\":\"火车二\", \"describe\":\"省钱\"," +
                "\"ways\":[], \"price\":\"200\" } ], \"plane\":[ { \"name\":\"飞机一\", \"describe\":\"省时间\", \"ways\":[], \"price\":\"300\" }, { \"name\":\"飞机二\", \"describe\":\"省钱\", \"ways\":[], \"price\":\"400\" } ] }";

        if( mode == 1 ) {
            retStr = "{ \"train\":[ { \"name\":\"火车一\", \"describe\":\"省时间\", \"ways\":[{\"data\": \"3-19\",\"time\": \"20Hour\",\"num\":\"G1737\"," +
                    "\"StartTime\":\"17:48\",\"EndTime\":\"20:47\",\"StartPlace\":\"南京南\",\"EndPlace\":\"武汉\"},{\"data\": \"3-19\",\"time\": \"21Hour\",\"num\":\"G1756\"," +
                    "\"StartTime\":\"17:56\",\"EndTime\":\"20:58\",\"StartPlace\":\"南京南\",\"EndPlace\":\"武汉\"}], \"price\":\"100\" }, { \"name\":\"火车二\", \"describe\":\"省钱\"," +
                    "\"ways\":[], \"price\":\"200\" } ], \"plane\":[] }";
        }
        if( mode == 2 ) {
            retStr = "{ \"train\":[ { \"name\":\"火车一\", \"describe\":\"省时间\", \"ways\":[{\"data\": \"3-19\",\"time\": \"20Hour\",\"num\":\"G1737\"," +
                    "\"StartTime\":\"17:48\",\"EndTime\":\"20:47\",\"StartPlace\":\"南京南\",\"EndPlace\":\"武汉\"},{\"data\": \"3-19\",\"time\": \"21Hour\",\"num\":\"G1756\"," +
                    "\"StartTime\":\"17:56\",\"EndTime\":\"20:58\",\"StartPlace\":\"南京南\",\"EndPlace\":\"武汉\"}], \"price\":\"100\" }, { \"name\":\"火车二\", \"describe\":\"省钱\"," +
                    "\"ways\":[], \"price\":\"200\" } ], \"plane\":[] }";
        }
        if( mode == 3 ) {
            retStr = "{ \"train\":[ { \"name\":\"火车一\", \"describe\":\"省钱\",\"ways\":[], \"price\":\"200\" } ], \"plane\":[] }";
        }
        try {
            JSONObject data = new JSONObject(retStr);
            JSONArray jsonArray = data.getJSONArray("train");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", jsonObject.getString("name"));
                    hashMap.put("value", String.valueOf(i));
                    hashMap.put("price", jsonObject.getString("price"));
                    hashMap.put("ways", getWays(jsonObject.getJSONArray("ways")));
                    hashMap.put("describe", jsonObject.getString("describe"));
                    Data.add(hashMap);
                }
            }
            jsonArray = data.getJSONArray("plane");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", jsonObject.getString("name"));
                    hashMap.put("price", jsonObject.getString("price"));
                    hashMap.put("value", String.valueOf(i));
                    hashMap.put("ways", getWays(jsonObject.getJSONArray("ways")));
                    hashMap.put("describe", jsonObject.getString("describe"));
                    Data.add(hashMap);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Data;
    }

    private List<HashMap<String, String>> getWays(JSONArray jsonArray) {
        List<HashMap<String, String>> item = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); ++i) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("data", jsonObject.getString("data"));
                    hashMap.put("time", jsonObject.getString("time"));
                    hashMap.put("num", jsonObject.getString("num"));
                    hashMap.put("StartTime", jsonObject.getString("StartTime"));
                    hashMap.put("EndTime", jsonObject.getString("EndTime"));
                    item.add(hashMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    private void showDetails(int _pos) {
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("position", String.valueOf(_pos));
        intent.putExtra("data", (Serializable) items);
        startActivity(intent);
    }

    public void showPrices(View view) {
        items = getListData(1);
        mSchedule =  new SimpleAdapter(this,
                items,//数据来源
                R.layout.my_listitems,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[]{"name", "describe", "price"},

                //ListItem的XML文件里面的两个TextView ID
                new int[]{R.id.ItemTitle, R.id.ItemText, R.id.ItemPrice});

        //添加并且显示
        list.setAdapter(mSchedule);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(position);
            }
        });
    }

    public void showTime(View view) {
        items = getListData(2);
        mSchedule =  new SimpleAdapter(this,
                items,//数据来源
                R.layout.my_listitems,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[]{"name", "describe", "price"},

                //ListItem的XML文件里面的两个TextView ID
                new int[]{R.id.ItemTitle, R.id.ItemText, R.id.ItemPrice});

        //添加并且显示
        list.setAdapter(mSchedule);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(position);
            }
        });
    }

    public void showLength(View view) {
        items = getListData(3);
        mSchedule =  new SimpleAdapter(this,
                items,//数据来源
                R.layout.my_listitems,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[]{"name", "describe", "price"},

                //ListItem的XML文件里面的两个TextView ID
                new int[]{R.id.ItemTitle, R.id.ItemText, R.id.ItemPrice});

        //添加并且显示
        list.setAdapter(mSchedule);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(position);
            }
        });
    }
}
