package com.iezview.managerdemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[SensorManager.DATA_X];
            float y = sensorEvent.values[SensorManager.DATA_Y];
            float z = sensorEvent.values[SensorManager.DATA_Z];
            View3DManager.updateData(x, y, z);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSensor();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        View3DManager.init(this, metrics.widthPixels / 2, metrics.heightPixels / 2, metrics
                .widthPixels / 2-20);
        for (int i = 0; i < 20; i++) {
            View view = getLayoutInflater().inflate(R.layout.view_item, null);
            view.setTag(new Point(360 / 20 * i, -30, 0));
            View3DManager.addView(view);
        }
    }

    private void initSensor() {
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION, true);
        manager.registerListener(listener, sensor, SensorManager
                .SENSOR_DELAY_UI);
    }
}
