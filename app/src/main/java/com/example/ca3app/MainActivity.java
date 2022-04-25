package com.example.ca3app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public class getPlayer implements Runnable{
        private volatile JSONObject value;
        EditText editBox = (EditText)findViewById(R.id.UserInput);
        String api = String.format("https://ca3api.azurewebsites.net/Player/byUsername/%s", editBox.getText().toString());

        @Override
        public void run() {
            try {
                URL url = new URL(api);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer content = new StringBuffer();
                while ((line = input.readLine()) != null) {
                    content.append(line);
                }
                input.close();
                value = new JSONObject(content.toString());
            }catch (Exception e){
                System.out.println(e);
            }
        }

        public JSONObject getValue(){
            return value;
        }
    }

    public class getBoard implements Runnable{
        private volatile JSONObject value;
        EditText editBox = (EditText)findViewById(R.id.UserInput);
        String api = String.format("https://ca3api.azurewebsites.net/Player/getKdByUsername/%s", editBox.getText().toString());

        @Override
        public void run() {
            try {
                URL url = new URL(api);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer content = new StringBuffer();
                while ((line = input.readLine()) != null) {
                    content.append(line);
                }
                input.close();
                value = new JSONObject(content.toString());
            }catch (Exception e){
                System.out.println(e);
            }
        }

        public JSONObject getValue(){
            return value;
        }
    }

    public void back(View v){
        setContentView(R.layout.activity_main);
    }

    public void searchPlayer(View v){
        try{
            getPlayer player = new getPlayer();
            Thread getPlayerThread = new Thread(player);
            getPlayerThread.start();
            getPlayerThread.join();

            JSONObject result = player.getValue();
            setContentView(R.layout.user_info);

            TextView KHead = (TextView)findViewById(R.id.killsHeader);
            KHead.setText(R.string.kills);
            TextView DHead = (TextView)findViewById(R.id.deathsheader);
            DHead.setText(R.string.deaths);
            TextView AHead = (TextView)findViewById(R.id.assistsheader);
            AHead.setText(R.string.assists);
            TextView PHead = (TextView)findViewById(R.id.posheader);
            PHead.setText(R.string.position);
            TextView MKHead = (TextView)findViewById(R.id.mkillsheader);
            MKHead.setText(R.string.maxkills);
            TextView WHead = (TextView)findViewById(R.id.winsheader);
            WHead.setText(R.string.wins);
            TextView LHead = (TextView)findViewById(R.id.lossheader);
            LHead.setText(R.string.losses);
            TextView HKHead = (TextView)findViewById(R.id.highestkills);
            HKHead.setText(R.string.highestKills);

            TextView UName = (TextView)findViewById(R.id.usernameDisplay);
            TextView KCount = (TextView)findViewById(R.id.killcount);
            TextView DCount = (TextView)findViewById(R.id.deathcount);
            TextView ACount = (TextView)findViewById(R.id.assistcount);
            TextView LPos = (TextView)findViewById(R.id.position);
            TextView mKills = (TextView)findViewById(R.id.mkillscount);
            TextView WCount = (TextView)findViewById(R.id.winscount);
            TextView LCount = (TextView)findViewById(R.id.losscount);
            TextView HKCount = (TextView)findViewById(R.id.highkillcount);
            UName.setText((CharSequence)result.get("user_name"));
            KCount.setText(result.get("kills").toString());
            DCount.setText(result.get("deaths").toString());
            ACount.setText(result.get("assists").toString());
            LPos.setText(result.get("leaderboard_pos").toString());
            mKills.setText(result.get("max_kills").toString());
            WCount.setText(result.get("wins").toString());
            LCount.setText(result.get("losses").toString());
            HKCount.setText(result.get("highest_killstreak").toString());


        }catch(Exception e){
            TextView UName = (TextView)findViewById(R.id.usernameDisplay);
            UName.setText("User not found!");
        }


    }
}