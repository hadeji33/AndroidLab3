package lairg.lab3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView aX;
    TextView aY;
    TextView aZ;
    TextView mX;
    TextView mY;
    TextView mZ;
    TextView proximity;
    TextView light;
    SensorManager sensorManager;
    Sensor aSensor;
    Sensor pSensor;
    Sensor mSensor;
    Sensor lSensor;
    int brightness;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aX = (TextView)findViewById(R.id.textView2);
        aY = (TextView)findViewById(R.id.textView3);
        aZ = (TextView)findViewById(R.id.textView4);
        mX = (TextView)findViewById(R.id.textView6);
        mY = (TextView)findViewById(R.id.textView7);
        mZ = (TextView)findViewById(R.id.textView8);
        proximity = (TextView)findViewById(R.id.textView10);
        light = (TextView)findViewById(R.id.textView12);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        pSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            aX.setText(Float.toString(sensorEvent.values[0]));
            aY.setText(Float.toString(sensorEvent.values[1]));
            aZ.setText(Float.toString(sensorEvent.values[2]));
        }
        if(sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            mX.setText(Float.toString(sensorEvent.values[0]));
            mY.setText(Float.toString(sensorEvent.values[1]));
            mZ.setText(Float.toString(sensorEvent.values[2]));
        }
        if(sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY){
            proximity.setText(Float.toString(sensorEvent.values[0]));
        }
        if(sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT){
            light.setText(Float.toString(sensorEvent.values[0]));
            if (sensorEvent.values[0] < 30) {
                brightness = 10;
            } else {
                if (sensorEvent.values[0] < 3000) {
                    brightness = 5;
                } else {
                    brightness = 1;
                }
            }

            WindowManager.LayoutParams layout = getWindow().getAttributes();
            layout.screenBrightness = brightness;
            getWindow().setAttributes(layout);
        }


    }

    @Override
    public void onStart(){
        super.onStart();
        sensorManager.registerListener(this, aSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, mSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, pSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, lSensor, sensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    public void onStop(){
        super.onStop();
        sensorManager.unregisterListener(this, aSensor);
        sensorManager.unregisterListener(this, mSensor);
        sensorManager.unregisterListener(this, pSensor);
        sensorManager.unregisterListener(this, lSensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}

