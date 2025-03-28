package app.insight.model;

import java.util.List;
import java.util.Map;

public class MarsWeatherResponse {
    private List<String> solKeys;
    private Map<String, app.insight.model.SolValidityChecks> validityChecks;

    public MarsWeatherResponse(List<String> solKeys, Map<String, SolValidityChecks> validityChecks) {
        this.solKeys = solKeys;
        this.validityChecks = validityChecks;
    }

    public List<String> getSolKeys() {
        return solKeys;
    }

    public void setSolKeys(List<String> solKeys) {
        this.solKeys = solKeys;
    }

    public Map<String, SolValidityChecks> getValidityChecks() {
        return validityChecks;
    }

    public void setValidityChecks(Map<String, SolValidityChecks> validityChecks) {
        this.validityChecks = validityChecks;
    }
}