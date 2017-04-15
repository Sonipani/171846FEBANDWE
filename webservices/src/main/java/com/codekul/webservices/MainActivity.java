package com.codekul.webservices;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codekul.webservices.domain.CityWeather;
import com.codekul.webservices.domain.Main;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void weather(View view) {

        pd = ProgressDialog.show(this, "Weather", "Fetching weather info");
        App app = (App) getApplication();
        app.q().add(
                new StringRequest(
                    "http://api.openweathermap.org/data/2.5/weather?q=" + city() + "?mode=json&units=metric&cnt=7&appid=7406c21bb1cb9f59d936a59c4e890279",
                    this::onSuccess,
                    this::onError
        ));
    }

    private void onError(VolleyError volleyError) {
        pd.dismiss();
    }

    private void onSuccess(String s) {
        Log.i(TAG, "JSON - "+s);
        CityWeather weather = ((App)getApplication()).gson().fromJson(s, CityWeather.class);

        weather(weather.getMain());
        pd.dismiss();
    }

    private String city() {
        return ((EditText) findViewById(R.id.edtCity)).getText().toString();
    }

    private void weather(Main main) {
        ((TextView)findViewById(R.id.textInfo)).setText("");
        ((TextView)findViewById(R.id.textInfo)).append("\n Temp - "+main.getTemp());
        ((TextView)findViewById(R.id.textInfo)).append("\n Pressure - "+main.getPressure());
        ((TextView)findViewById(R.id.textInfo)).append("\n Min - "+main.getTempMin());
        ((TextView)findViewById(R.id.textInfo)).append("\n Max - "+main.getTempMax());
        ((TextView)findViewById(R.id.textInfo)).append("\n MSL - "+main.getSeaLevel());
    }
}
