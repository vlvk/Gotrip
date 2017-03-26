package com.av.richardcheung.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.HashMap;
import java.util.List;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();

        //生成动态数组，并且转载数据
        String _pos = intent.getStringExtra("position");
        int pos = Integer.parseInt(_pos);
        List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) intent.getSerializableExtra("data");
        HashMap<String, Object> item = items.get(pos);

        List<HashMap<String, String>> ways = (List<HashMap<String, String>>) item.get("ways");

        //绑定XML中的ListView，作为Item的容器
        ListView list = (ListView) findViewById(R.id.detailViews);

        //生成适配器，数组 --> ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(this,
                ways,//数据来源
                R.layout.detail_item,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[]{"data", "time", "num", "StartTime", "EndTime"},

                //ListItem的XML文件里面的两个TextView ID
                new int[]{R.id.planDate, R.id.tripTime, R.id.detailNum, R.id.startTime, R.id.endTime});
        //添加并且显示
        list.setAdapter(mSchedule);
    }
}
