package com.example.rick.waterlevelchart;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private WaterLevelChartView mTempView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTempView = (WaterLevelChartView) findViewById(R.id.mTemp);
        mTempView.setTemp("m",0.f,12.0f,"0.5", 2.5f, 6.4f, this, 250);
    }
}
