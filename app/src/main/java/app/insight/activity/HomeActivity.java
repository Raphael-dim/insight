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

                        // Extraire les données de température et de pression
                        double temperature = solData.getJSONObject("AT").getDouble("av");
                        double pressure = solData.getJSONObject("PRE").getDouble("av");

                        // Créer un nouvel objet SolData
                        sols.add(new SolData(solKey, temperature, pressure));
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