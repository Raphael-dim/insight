package app.insight.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.insight.R;
import app.insight.model.SolData;
import app.insight.service.NasaApiService;
import app.insight.view.SolAdapter;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView solsRecyclerView;
    private TextView totalSolsTextView;
    private SolAdapter solAdapter;
    private List<SolData> sols;
    private NasaApiService nasaApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialisation des vues
        solsRecyclerView = findViewById(R.id.solsRecyclerView);
        totalSolsTextView = findViewById(R.id.totalSolsTextView);

        // Configuration de la RecyclerView
        solsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        solAdapter = new SolAdapter();
        solsRecyclerView.setAdapter(solAdapter);

        // Initialisation de la liste des sols
        sols = new ArrayList<>();

        // Initialisation du service API
        nasaApiService = new NasaApiService(this);

        // Chargement des données depuis l'API
        loadWeatherData();
    }

    private void loadWeatherData() {
        nasaApiService.getWeatherData(new NasaApiService.WeatherCallback() {
            @Override
            public void onSuccess(org.json.JSONObject response) {
                try {
                    sols.clear();
                    // Parcourir les sols dans la réponse
                    for (int i = 0; i < response.getJSONArray("sol_keys").length(); i++) {
                        String solKey = response.getJSONArray("sol_keys").getString(i);
                        org.json.JSONObject solData = response.getJSONObject(solKey);

                        // Create new SolData object
                        SolData sol = new SolData(solKey);

                        // Set temperature data
                        org.json.JSONObject atData = solData.getJSONObject("AT");
                        sol.setTemperature(new SolData.WeatherData(
                                atData.getDouble("av"),
                                atData.getDouble("mn"),
                                atData.getDouble("mx")));

                        // Set pressure data
                        org.json.JSONObject preData = solData.getJSONObject("PRE");
                        sol.setPressure(new SolData.WeatherData(
                                preData.getDouble("av"),
                                preData.getDouble("mn"),
                                preData.getDouble("mx")));

                        // Set wind speed data
                        org.json.JSONObject hwsData = solData.getJSONObject("HWS");
                        sol.setWindSpeed(new SolData.WeatherData(
                                hwsData.getDouble("av"),
                                hwsData.getDouble("mn"),
                                hwsData.getDouble("mx")));

                        // Set season data
                        sol.setNorthernSeason(solData.getString("Northern_season"));
                        sol.setSouthernSeason(solData.getString("Southern_season"));

                        // Set most common wind direction
                        org.json.JSONObject wdData = solData.getJSONObject("WD").getJSONObject("most_common");
                        sol.setMostCommonWindDirection(new SolData.WindDirection(
                                wdData.getDouble("compass_degrees"),
                                wdData.getString("compass_point")));

                        // Parse wind direction data
                        org.json.JSONObject wdObject = solData.getJSONObject("WD");
                        for (int j = 0; j < 16; j++) {
                            if (wdObject.has(String.valueOf(j))) {
                                org.json.JSONObject directionData = wdObject.getJSONObject(String.valueOf(j));
                                sol.getWindDirections().put(j, new SolData.WindDirectionData(
                                        directionData.getDouble("compass_degrees"),
                                        directionData.getString("compass_point"),
                                        directionData.getDouble("compass_right"),
                                        directionData.getDouble("compass_up"),
                                        directionData.getInt("ct")));
                            }
                        }

                        // Set UTC times
                        sol.setFirstUTC(solData.getString("First_UTC"));
                        sol.setLastUTC(solData.getString("Last_UTC"));

                        sols.add(sol);
                    }

                    // Mise à jour de l'adaptateur
                    solAdapter.setSols(sols);

                    // Mise à jour du compteur total
                    updateTotalSolsCount();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                // Gérer l'erreur ici
            }
        });
    }

    private void updateTotalSolsCount() {
        String countText = "Nombre total de sols : " + sols.size();
        totalSolsTextView.setText(countText);
    }
}