package app.insight.activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

import app.insight.R;
import app.insight.model.SolData;
import app.insight.view.WindRoseView;

public class SolDetailActivity extends AppCompatActivity {
    public static final String EXTRA_SOL_DATA = "sol_data";
    public static final String EXTRA_SOLS_LIST = "sols_list";
    public static final String EXTRA_CURRENT_INDEX = "current_index";

    private TextView solNumberTextView;
    private TextView temperatureTextView;
    private TextView pressureTextView;
    private WindRoseView windRoseView;
    private List<SolData> sols;
    private int currentIndex;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sol_detail);

        // Initialize views
        initializeViews();

        // Get data from intent
        sols = (List<SolData>) getIntent().getSerializableExtra(EXTRA_SOLS_LIST);
        currentIndex = getIntent().getIntExtra(EXTRA_CURRENT_INDEX, 0);
        SolData solData = (SolData) getIntent().getSerializableExtra(EXTRA_SOL_DATA);

        // Initialize gesture detector
        initializeGestureDetector();

        if (solData != null) {
            displaySolData(solData);
        }
    }

    private void initializeViews() {
        solNumberTextView = findViewById(R.id.solNumberTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        pressureTextView = findViewById(R.id.pressureTextView);
        windRoseView = findViewById(R.id.windRoseView);
    }

    private void initializeGestureDetector() {
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (Math.abs(distanceY) > Math.abs(distanceX)) {
                    if (distanceY > 0) {
                        // Scroll vers le bas - sol suivant
                        navigateToNextSol();
                    } else {
                        // Scroll vers le haut - sol précédent
                        navigateToPreviousSol();
                    }
                }
                return true;
            }
        });

        // Attacher le GestureDetector à la vue principale
        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });
    }

    private void navigateToNextSol() {
        if (currentIndex < sols.size() - 1) {
            currentIndex++;
            displaySolData(sols.get(currentIndex));
        }
    }

    private void navigateToPreviousSol() {
        if (currentIndex > 0) {
            currentIndex--;
            displaySolData(sols.get(currentIndex));
        }
    }

    private void displaySolData(SolData solData) {
        solNumberTextView.setText("Sol n°" + solData.getSolKey());

        // Display temperature data
        String tempText = String.format("Température :\navg: %.2f\nmin: %.2f\nmax: %.2f",
                solData.getTemperature().getAverage(),
                solData.getTemperature().getMin(),
                solData.getTemperature().getMax());
        temperatureTextView.setText(tempText);

        // Display pressure data
        String pressureText = String.format("Pression :\navg: %.2f\nmin: %.2f\nmax: %.2f",
                solData.getPressure().getAverage(),
                solData.getPressure().getMin(),
                solData.getPressure().getMax());
        pressureTextView.setText(pressureText);

        // Initialize wind rose with wind data
        float[] windValues = new float[16]; // 16 directions
        Map<Integer, SolData.WindDirectionData> windDirections = solData.getWindDirections();

        // Trouver le maximum de compteurs pour la normalisation
        int maxCount = 0;
        for (SolData.WindDirectionData data : windDirections.values()) {
            maxCount = Math.max(maxCount, data.getCount());
        }

        // Remplir le tableau des valeurs normalisées
        for (int i = 0; i < 16; i++) {
            SolData.WindDirectionData data = windDirections.get(i);
            if (data != null) {
                windValues[i] = (float) data.getCount() / maxCount;
            } else {
                windValues[i] = 0;
            }
        }
        windRoseView.setWindValues(windValues);

        // Display season info
        String seasonText = String.format("Saison :\nNord: %s\nSud: %s",
                solData.getNorthernSeason(),
                solData.getSouthernSeason());
        // seasonTextView.setText(seasonText);

        // Display wind data
        String windSpeedText = String.format("Vitesse du vent :\navg: %.2f\nmin: %.2f\nmax: %.2f",
                solData.getWindSpeed().getAverage(),
                solData.getWindSpeed().getMin(),
                solData.getWindSpeed().getMax());
        // windSpeedTextView.setText(windSpeedText);

        // Display most common wind direction
        String windDirText = String.format("Direction du vent la plus fréquente :\n%s (%.1f°)",
                solData.getMostCommonWindDirection().getCompassPoint(),
                solData.getMostCommonWindDirection().getCompassDegrees());
        // windDirectionTextView.setText(windDirText);

        // Display UTC times
        // firstUTCTextView.setText("Premier relevé : " + solData.getFirstUTC());
        // lastUTCTextView.setText("Dernier relevé : " + solData.getLastUTC());
    }
}