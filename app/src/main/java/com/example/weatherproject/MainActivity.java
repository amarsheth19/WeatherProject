package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    String content = "";
    Button button;
    Thread t1;
    Thread t2;
    String longitude = "";
    String latitude = "";
    private String quote = "";
    private JSONObject jsonObject;
    private JSONObject j;
    private int color;

    TextView date;
    TextView currentTime;
    TextView time3hours;
    TextView time6hours;
    TextView time9hours;
    TextView time12hours;
    TextView currentTemp;
    TextView high3hours;
    TextView high6hours;
    TextView high9hours;
    TextView high12hours;
    TextView quoteTextView;
    TextView low3hours;
    TextView low6hours;
    TextView low9hours;
    TextView low12hours;

    ImageView currentImage;
    ImageView image3hours;
    ImageView image6hours;
    ImageView image9hours;
    ImageView image12hours;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        editText = findViewById(R.id.id_editText);
        button = findViewById(R.id.button);

        date = findViewById(R.id.id_date);
        currentTime = findViewById(R.id.id_currentTime);
        time3hours = findViewById(R.id.id_3pastTime);
        time6hours = findViewById(R.id.id_6pastTime);
        time9hours = findViewById(R.id.id_9pastTime);
        time12hours = findViewById(R.id.id_12pastTime);
        currentTemp = findViewById(R.id.id_currentTemp);
        high3hours = findViewById(R.id.id_3pastHigh);
        high6hours = findViewById(R.id.id_6pastHigh);
        high9hours = findViewById(R.id.id_9pastHigh);
        high12hours = findViewById(R.id.id_12pastHigh);
        quoteTextView = findViewById(R.id.id_Quote);
        low3hours = findViewById(R.id.id_3pastLow);
        low6hours = findViewById(R.id.id_6pastLow);
        low9hours = findViewById(R.id.id_9pastLow);
        low12hours = findViewById(R.id.id_12pastLow);

        currentImage = findViewById(R.id.id_currentImage);
        image3hours = findViewById(R.id.id_image3past);
        image6hours = findViewById(R.id.id_image6past);
        image9hours = findViewById(R.id.id_image9past);
        image12hours = findViewById(R.id.id_image12past);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncThread task = new AsyncThread();
                task.execute(editText.getText().toString()); //used in async task



            }
        });



    }

    public int getImageCode(int code){

        //Sesame Street Quotes

        if(code>=200 && code<=232) {
            color = Color.BLUE;
            quoteTextView.setText("A good friend can help you weather any storm");
            return R.drawable.thunderingicon;
        }
        if((code>=300 && code<=321) || (code>=520 && code<=531)) {
            color = Color.CYAN;
            quoteTextView.setText("You can still play in the rain!!");
            return R.drawable.drizzlingicon;
        }
        if(code>=700 && code<=781) {
            color = Color.LTGRAY;
            quoteTextView.setText("There certainly has been a lot of rhyming confusions. Maybe too much fog got in their heads.");
            return R.drawable.misticon;
        }
        if((code>=600 && code<=622) || code==511) {
            color = Color.WHITE;
            quoteTextView.setText("Watching the first snowfall with friends, what could be better?");
            return R.drawable.snowicon;
        }
        if(code>=500 && code<=504) {
            color = Color.BLUE;
            quoteTextView.setText("Red, it looks like we're in for some pretty serious weather!");
            return R.drawable.rainingicon;
        }
        if(code==800) {
            color = Color.YELLOW;
            quoteTextView.setText("Sunny day, come out and play!!");
            return R.drawable.sunnyicon;
        }
        if(code>=802 && code<=803) {
            color = Color.LTGRAY;
            quoteTextView.setText("Thank you clouds for giving us rain to help the flowers grow");
            return R.drawable.singlecloudicon;
        }
        if(code==801) {
            color = Color.LTGRAY;
            quoteTextView.setText("A sunny day is right around the corner");
            return R.drawable.sunbehindcloudicon;
        }
        if(code==804) {
            color = Color.LTGRAY;
            quoteTextView.setText("Every cloud has some silver lining!");
            return R.drawable.doublecloudicon;
        }
        else{
            color = Color.WHITE;
            quoteTextView.setText("What a beautiful Sunny Day, Sweepin’ the clouds away");
            return R.drawable.sunnyicon;
        }
    }

    public class AsyncThread extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {

            try {

                URL url1 = new URL("http://api.openweathermap.org/geo/1.0/zip?zip=" + strings[0] + ",us&appid=1309cf1511835ff03ec26cc76b23965c");
                Log.d("TAG", url1.toString());
                URLConnection urlConnection1 = url1.openConnection();
                Log.d("TAG", "1");
                InputStream inputStream1 = urlConnection1.getInputStream();
                Log.d("TAG", "2");
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                Log.d("TAG", "3");

                String linetemp = "";
                String lonlatstring = "";

                while ((linetemp = bufferedReader1.readLine()) != null){
                    //Log.d("Poop","2");
                    lonlatstring += linetemp;
                }
                Log.d("TAG", lonlatstring);

                jsonObject = new JSONObject(lonlatstring);

                latitude = jsonObject.get("lat").toString();
                longitude = jsonObject.get("lon").toString();

                Log.d("TAG", "lat: " + jsonObject.getString("lat").toString());
                Log.d("TAG", "lon: " + jsonObject.getString("lon").toString());


                Log.d("Poop", "a");
                Log.d("Poop", latitude);
                Log.d("Poop", longitude);
                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=1309cf1511835ff03ec26cc76b23965c");
                Log.d("Poop", url.toString());
                Log.d("Poop", "c");
                URLConnection urlConnection = url.openConnection();
                Log.d("Poop", "d");
                InputStream inputStream = urlConnection.getInputStream();
                Log.d("Poop", "e");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                Log.d("Poop", "f");
                //String content = "";
                String line;
                Log.d("Poop", "1");
                //b = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    //Log.d("Poop","2");
                    content = line;
                    //b.append(line);
                }

                try {
                    j = new JSONObject(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }catch (Exception e) {
                e.printStackTrace();
            }


            return j;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //super.onPostExecute(jsonObject);

            try {
                String temp = "";
                double tempint = 0;
                int tempTime = 0;
                String timeTag = "";
                Log.d("Test", "start");
                temp = j.getJSONArray("list").getJSONObject(0).getString("dt_txt").toString();
                date.setText(temp.substring(0, 10));
                Log.d("Test", "Date: "+date.getText());
                tempTime = (Integer.parseInt(temp.substring(11,13)))-5;
                if(tempTime<0)
                    tempTime+=24;
                if(tempTime >= 12) {
                    if(tempTime != 12)
                        tempTime = tempTime % 12;
                    timeTag = "PM";
                }
                else if(tempTime == 0) {
                    tempTime = 12;
                    timeTag = "AM";
                }
                else
                    timeTag = "AM";
                currentTime.setText(tempTime + ":00 " + timeTag);
                tempint = j.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp");
                currentTemp.setText((((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                Log.d("Test", "Current Temp: "+ currentTemp.getText());
                Log.d("Test", "Where: "+ j.getJSONObject("city").getString("name"));
                currentImage.setImageResource(getImageCode(j.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getInt("id")));
                currentTemp.setTextColor(color);
                currentTime.setTextColor(color);

                temp = j.getJSONArray("list").getJSONObject(1).getString("dt_txt").toString();
                tempTime = (Integer.parseInt(temp.substring(11,13)))-5;
                if(tempTime<0)
                    tempTime+=24;
                if(tempTime >= 12) {
                    if(tempTime != 12)
                        tempTime = tempTime % 12;
                    timeTag = "PM";
                }
                else if(tempTime == 0) {
                    tempTime = 12;
                    timeTag = "AM";
                }
                else
                    timeTag = "AM";
                time3hours.setText(tempTime + ":00 " + timeTag);
                tempint = j.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp_min");
                low3hours.setText("Low: " +(((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                tempint = j.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp_max");
                high3hours.setText("High: "+(((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                image3hours.setImageResource(getImageCode(j.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getInt("id")));
                time3hours.setTextColor(color);
                low3hours.setTextColor(color);
                high3hours.setTextColor(color);


                temp = j.getJSONArray("list").getJSONObject(2).getString("dt_txt").toString();
                tempTime = (Integer.parseInt(temp.substring(11,13)))-5;
                if(tempTime<0)
                    tempTime+=24;
                if(tempTime >= 12) {
                    if(tempTime != 12)
                        tempTime = tempTime % 12;
                    timeTag = "PM";
                }
                else if(tempTime == 0) {
                    tempTime = 12;
                    timeTag = "AM";
                }
                else
                    timeTag = "AM";
                time6hours.setText(tempTime + ":00 " + timeTag);
                tempint = j.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp_min");
                low6hours.setText("Low: "+(((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                tempint = j.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp_max");
                high6hours.setText("High: "+(((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                image6hours.setImageResource(getImageCode(j.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getInt("id")));
                time6hours.setTextColor(color);
                low6hours.setTextColor(color);
                high6hours.setTextColor(color);


                temp = j.getJSONArray("list").getJSONObject(3).getString("dt_txt").toString();
                tempTime = (Integer.parseInt(temp.substring(11,13)))-5;
                if(tempTime<0)
                    tempTime+=24;
                if(tempTime >= 12) {
                    if(tempTime != 12)
                        tempTime = tempTime % 12;
                    timeTag = "PM";
                }
                else if(tempTime == 0) {
                    tempTime = 12;
                    timeTag = "AM";
                }
                else
                    timeTag = "AM";
                time9hours.setText(tempTime + ":00 " + timeTag);
                tempint = j.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp_min");
                low9hours.setText("Low: "+(((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                tempint = j.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp_max");
                high9hours.setText("High: "+(((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                image9hours.setImageResource(getImageCode(j.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getInt("id")));
                time9hours.setTextColor(color);
                low9hours.setTextColor(color);
                high9hours.setTextColor(color);


                temp = j.getJSONArray("list").getJSONObject(4).getString("dt_txt").toString();
                tempTime = (Integer.parseInt(temp.substring(11,13)))-5;
                if(tempTime<0)
                    tempTime+=24;
                if(tempTime >= 12) {
                    if(tempTime != 12)
                        tempTime = tempTime % 12;
                    timeTag = "PM";
                }
                else if(tempTime == 0) {
                    tempTime = 12;
                    timeTag = "AM";
                }
                else
                    timeTag = "AM";
                time12hours.setText(tempTime + ":00 " + timeTag);
                tempint = j.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp_min");
                Log.d("Test", "Last Low: "+ tempint);
                low12hours.setText("Low: "+(((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                tempint = j.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp_max");
                Log.d("Test", "Last High: "+ tempint);
                high12hours.setText("High: "+(((int)(10*(((double)tempint-273.15)*(1.8)+32.0)))/(double)10)+ "\u2109");
                image12hours.setImageResource(getImageCode(j.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getInt("id")));
                time12hours.setTextColor(color);
                low12hours.setTextColor(color);
                high12hours.setTextColor(color);
                currentImage.setBackgroundColor(Color.WHITE);
                image3hours.setBackgroundColor(Color.WHITE);
                image6hours.setBackgroundColor(Color.WHITE);
                image9hours.setBackgroundColor(Color.WHITE);
                image12hours.setBackgroundColor(Color.WHITE);

                int useless = getImageCode(j.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getInt("id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}