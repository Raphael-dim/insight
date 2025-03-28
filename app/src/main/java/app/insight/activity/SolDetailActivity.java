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
    private boolean isScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sol_detail);

        initializeViews();

        sols = (List<SolData>) getIntent().getSerializableExtra(EXTRA_SOLS_LIST);
        currentIndex = getIntent().getIntExtra(EXTRA_CURRENT_INDEX, 0);
        SolData solData = (SolData) getIntent().getSerializableExtra(EXTRA_SOL_DATA);

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
                // Seuil minimum de déplacement pour déclencher la navigation
                float minScrollDistance = 50;

                if (!isScrolling && Math.abs(distanceY) > minScrollDistance) {
                    isScrolling = true;
                    if (distanceY > 0) {
                        navigateToPreviousSol();
                    } else {
                        navigateToNextSol();
                    }
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                isScrolling = false;
            }
        });

        View gestureDetectorView = findViewById(R.id.gestureDetectorView);
        gestureDetectorView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                isScrolling = false;
            }
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
        if (solData == null) {
            solNumberTextView.setText(R.string.data_not_available);
            temperatureTextView.setText(R.string.temperature_na);
            pressureTextView.setText(R.string.pressure_na);
            windRoseView.setWindValues(new float[16]);
            return;
        }

        solNumberTextView.setText(getString(R.string.sol_number, Integer.parseInt(solData.getSolKey())));

        String tempText = getString(R.string.temperature_format,
                solData.getTemperature().getAverage(),
                solData.getTemperature().getMin(),
                solData.getTemperature().getMax());
        temperatureTextView.setText(tempText);

        String pressureText = getString(R.string.pressure_format,
                solData.getPressure().getAverage(),
                solData.getPressure().getMin(),
                solData.getPressure().getMax());
        pressureTextView.setText(pressureText);

        float[] windValues = new float[16];
        Map<Integer, SolData.WindDirectionData> windDirections = solData.getWindDirections();

        int maxCount = 0;
        for (SolData.WindDirectionData data : windDirections.values()) {
            maxCount = Math.max(maxCount, data.getCount());
        }

        for (int i = 0; i < 16; i++) {
            SolData.WindDirectionData data = windDirections.get(i);
            if (data != null) {
                windValues[i] = (float) data.getCount() / maxCount;
            } else {
                windValues[i] = 0;
            }
        }
        windRoseView.setWindValues(windValues);
    }
}