package com.classy.survivegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Activity_Menu extends Activity {

    private MaterialButton menu_BTN_start;
    private TextInputEditText menu_EDT_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu_BTN_start = findViewById(R.id.menu_BTN_start);
        menu_EDT_id = findViewById(R.id.menu_EDT_id);

        menu_BTN_start.setOnClickListener(v -> makeServerCall());
    }

    private void makeServerCall() {
        String url = getString(R.string.url);
        new Thread(() -> {
            String data = getJSON(url);
            runOnUiThread(() -> {
                String id = menu_EDT_id.getText().toString();

                if (id.length() >= 8 && data != null && !data.isEmpty()) {
                    startGame(id, data);
                } else {
                    Toast.makeText(this, "Enter an ID with at least 8 digits", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    public static String getJSON(String url) {
        String data = "";
        HttpsURLConnection con = null;

        try {
            URL u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            br.close();
            data = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }


    private void startGame(String id, String data) {
        String[] rawCities = data.split(",");
        String[] cities = new String[rawCities.length];
        for (int i = 0; i < rawCities.length; i++) {
            cities[i] = rawCities[i].trim();
        }
        if (id.length() < 8 || cities.length == 0) {
            Toast.makeText(this, "Missing game data", Toast.LENGTH_SHORT).show();
            return;
        }
        char lastChar = id.charAt(id.length() - 1);
        int digit = Character.isDigit(lastChar) ? Character.getNumericValue(lastChar) : 0;

        int index = digit % cities.length;
        String city = cities[index];

        Toast.makeText(this, "Your City: " + city, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Activity_Game.class);
        intent.putExtra("EXTRA_ID", id);
        intent.putExtra("EXTRA_STATE", city);
        startActivity(intent);
    }
}
