package app.insight.model;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class SolData {
    private String solNumber;
    private double temperature;
    private double pressure;

    public SolData(String solNumber, double temperature, double pressure) {
        this.solNumber = solNumber;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    public SolData(JSONObject json) {
        this.fromJSONObject(json);
    }

    // Getters and setters
    public String getSolNumber() {
        return solNumber;
    }

    public void setSolNumber(String solNumber) {
        this.solNumber = solNumber;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    private void fromJSONObject(JSONObject json) {
        try {
            this.solNumber = json.getString("id");
            this.pressure = json.getDouble("PRE");
            this.temperature = json.getDouble("TEMP");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}