package com.classy.survivegame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Game extends AppCompatActivity {

    private ImageButton[] arrows;
    private int[] steps;
    private int stepIndex = 0;
    private String city, id;

    private TextView game_LBL_result, game_LBL_pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        id = getIntent().getStringExtra("EXTRA_ID");
        city = getIntent().getStringExtra("EXTRA_STATE");

        if (id == null || city == null || id.length() < 1) {
            Toast.makeText(this, "Missing game data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViews();
        createStepsFromID(id);
        displayPattern();
        initViews();
    }

    private void findViews() {
        arrows = new ImageButton[]{
                findViewById(R.id.game_BTN_up),
                findViewById(R.id.game_BTN_down),
                findViewById(R.id.game_BTN_left),
                findViewById(R.id.game_BTN_right)
        };
        game_LBL_result = findViewById(R.id.game_LBL_result);
        game_LBL_pattern = findViewById(R.id.game_LBL_pattern);
    }

    private void initViews() {
        for (int i = 0; i < arrows.length; i++) {
            final int direction = i;
            arrows[i].setOnClickListener(v -> arrowClicked(direction));
        }
    }

    private void createStepsFromID(String id) {
        steps = new int[id.length()];
        for (int i = 0; i < id.length(); i++) {
            steps[i] = Character.getNumericValue(id.charAt(i)) % 4;
        }
    }

    private void displayPattern() {
        StringBuilder patternText = new StringBuilder("Pattern:\n");
        for (int step : steps) {
            patternText.append(getArrowSymbol(step)).append(" ");
        }
        game_LBL_pattern.setText(patternText.toString().trim());
    }

    private String getArrowSymbol(int direction) {
        switch (direction) {
            case 0: return "↑";
            case 1: return "↓";
            case 2: return "←";
            case 3: return "→";
            default: return "?";
        }
    }

    private void arrowClicked(int direction) {
        if (steps[stepIndex] == direction) {
            stepIndex++;
            if (stepIndex == steps.length) {
                game_LBL_result.setText("You survived in " + city + "!");
                returnToMenuAfterDelay();
            }
        } else {
            game_LBL_result.setText("Wrong move! You failed.");
            returnToMenuAfterDelay();
        }
    }

    private void returnToMenuAfterDelay() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Activity_Game.this, Activity_Menu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }, 2000); // 2 שניות עיכוב
    }
}
