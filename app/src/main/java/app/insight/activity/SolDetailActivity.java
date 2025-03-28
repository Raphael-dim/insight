package app.insight.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import app.insight.R;
import app.insight.model.SolData;

public class SolDetailActivity extends AppCompatActivity {
    public static final String EXTRA_SOL_DATA = "sol_data";

    private TextView solNumberTextView;
    private TextView temperatureTextView;
    private TextView pressureTextView;
    private TextView seasonTextView;
    private TextView windSpeedTextView;
    private TextView windDirectionTextView;
    private TextView firstUTCTextView;
    private TextView lastUTCTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sol_detail);

        // Initialize views
        initializeViews();

        // Get Sol data from intent
        SolData solData = (SolData) getIntent().getSerializableExtra(EXTRA_SOL_DATA);
        if (solData != null) {
            displaySolData(solData);
        }
    }

    private void initializeViews() {
        solNumberTextView = findViewById(R.id.solNumberTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        pressureTextView = findViewById(R.id.pressureTextView);
        seasonTextView = findViewById(R.id.seasonTextView);
        windSpeedTextView = findViewById(R.id.windSpeedTextView);
        windDirectionTextView = findViewById(R.id.windDirectionTextView);
        firstUTCTextView = findViewById(R.id.firstUTCTextView);
        lastUTCTextView = findViewById(R.id.lastUTCTextView);
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

        // Display season info
        String seasonText = String.format("Saison :\nNord: %s\nSud: %s",
                solData.getNorthernSeason(),
                solData.getSouthernSeason());
        seasonTextView.setText(seasonText);

        // Display wind data
        String windSpeedText = String.format("Vitesse du vent :\navg: %.2f\nmin: %.2f\nmax: %.2f",
                solData.getWindSpeed().getAverage(),
                solData.getWindSpeed().getMin(),
                solData.getWindSpeed().getMax());
        windSpeedTextView.setText(windSpeedText);

        // Display most common wind direction
        String windDirText = String.format("Direction du vent la plus fréquente :\n%s (%.1f°)",
                solData.getMostCommonWindDirection().getCompassPoint(),
                solData.getMostCommonWindDirection().getCompassDegrees());
        windDirectionTextView.setText(windDirText);

        // Display UTC times
        firstUTCTextView.setText("Premier relevé : " + solData.getFirstUTC());
        lastUTCTextView.setText("Dernier relevé : " + solData.getLastUTC());
    }
}