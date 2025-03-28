package app.insight.model;

import java.util.List;

public class MarsWeatherData {
    private List<String> solKeys;

    public List<String> getSolKeys() {
        return solKeys;
    }

    public void setSolKeys(List<String> solKeys) {
        this.solKeys = solKeys;
    }

    public int getNumberOfSols() {
        return solKeys != null ? solKeys.size() : 0;
    }
}